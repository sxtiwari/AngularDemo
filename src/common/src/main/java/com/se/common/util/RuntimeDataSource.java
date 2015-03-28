/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Nov 27, 2014 1:25:35 PM 2014
 *
 *********************************************************/

package com.se.common.util;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.se.common.constant.DatabaseProperties;
import com.se.common.logger.AppLogger;
import com.se.common.logger.AppLoggerFactory;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 
 * @author Saurabh Tiwari
 *
 */
@Component
public class RuntimeDataSource extends HikariDataSource {

	@Autowired
	private PropertiesReader propertiesReader;

	private static AppLogger logger = AppLoggerFactory.getLogger();

	@PostConstruct
	public void setDataSourceProperties() {
		this.setJdbcUrl(this.getDbUrl());
		this.setUsername(propertiesReader
				.getStringValue(DatabaseProperties.DB_USER));
		this.setPassword(propertiesReader
				.getStringValue(DatabaseProperties.DB_PASSWORD));
		this.setDriverClassName(propertiesReader
				.getStringValue(DatabaseProperties.DB_DRIVER));
		this.setMinimumIdle(1);
	}

	@PreDestroy
	public void destroy() {
		this.close();
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				logger.info(String.format("deregistering jdbc driver: %s",
						driver));
			} catch (SQLException e) {
				logger.error(
						String.format("Error deregistering driver %s", driver),
						e);
			}
		}
	}

	public String getDbUrl() {
		String dbHost = propertiesReader
				.getStringValue(DatabaseProperties.DB_HOST);
		String dbPort = propertiesReader
				.getStringValue(DatabaseProperties.DB_PORT);
		return "jdbc:mysql://"
				+ dbHost
				+ ":"
				+ dbPort
				+ "/project"
				+ "?cachePrepStmts="
				+ propertiesReader
						.getStringValue(DatabaseProperties.DB_CACHE_PREPARED_STATEMENTS)
				+ "&prepStmtCacheSize="
				+ propertiesReader
						.getStringValue(DatabaseProperties.DB_PREPARED_STATEMENT_CACHE_SIZE)
				+ "&prepStmtCacheSqlLimit="
				+ propertiesReader
						.getStringValue(DatabaseProperties.DB_PREPARED_STATEMENT_CACHE_SQL_LIMIT)
				+ "&useServerPrepStmts="
				+ propertiesReader
						.getStringValue(DatabaseProperties.DB_USE_SERVER_PREPARED_STATEMENT);
	}
}
