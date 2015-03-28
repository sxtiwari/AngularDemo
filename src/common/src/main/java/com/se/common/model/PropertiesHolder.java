/*********************************************************
 * Copyright (C) 2014 S. Enterprises
 * All Rights Reserved.
 *
 * Contributors: Saurabh Tiwari
 * Nov 27, 2014 1:25:35 PM 2014
 *
 *********************************************************/

package com.se.common.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.se.common.util.GenericUtil;

/**
 * 
 * @author Saurabh Tiwari
 *
 */
@Component
public class PropertiesHolder {
	@Autowired
	private GenericUtil genericUtil;

	private Properties properties = new Properties();

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * Loads all the properties from classpath and system properties.
	 * 
	 * System properties take precedence over those defined in classpath.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 */
	@PostConstruct
	void initPropertiesHolder() throws FileNotFoundException, IOException,
			Exception {
		String envFolderPath = genericUtil.getPropertiesPath();
		properties.putAll(loadClasspathProperties(envFolderPath));
		properties.putAll(loadSystemProperties());
	}

	@SuppressWarnings("unused")
	private Properties loadFileSystemProperties(String folderPath)
			throws FileNotFoundException, IOException {
		File resource = new File(folderPath);
		File[] files = resource.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				Pattern pattern = Pattern.compile("(.)+[.properties]");
				Matcher matcher = pattern.matcher(name);
				return matcher.matches();
			}
		});

		Properties finalProperties = new Properties();
		for (File file : files) {
			Properties properties = new Properties();
			try {
				properties.load(new FileInputStream(file));
			} catch (Exception e) {

			}
			for (Entry<Object, Object> entry : properties.entrySet()) {
				finalProperties.put(entry.getKey().toString(), entry.getValue()
						.toString());
			}
		}

		return finalProperties;
	}

	private Properties loadClasspathProperties(String folderPath)
			throws FileNotFoundException, IOException, Exception {
		// ClassLoader.getResourcesAsStream() if applied to a directory,
		// returns stream containing all the file names one in each line.
		InputStream stream = this.getClass().getClassLoader()
				.getResourceAsStream(folderPath);
		List<String> fileNames = IOUtils.readLines(stream);
		Properties finalProperties = new Properties();
		for (String fileName : fileNames) {
			if (fileName.matches("(.)+[.properties]")) {
				Properties properties = new Properties();
				properties.load(this.getClass().getClassLoader()
						.getResourceAsStream(folderPath + "/" + fileName));
				for (Entry<Object, Object> entry : properties.entrySet()) {
					finalProperties.put(entry.getKey().toString(), entry
							.getValue().toString());
				}
			}
		}

		return finalProperties;
	}

	private Properties loadSystemProperties() {
		return System.getProperties();
	}
}
