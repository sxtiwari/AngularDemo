CREATE DATABASE project;

CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(1024) NOT NULL,
  `isGuest` bit(1) NOT NULL DEFAULT b'0',
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModifiedDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `email_INDEX` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;


CREATE TABLE `userprofile` (
  `id` int(10) unsigned NOT NULL,
  `userId` int(10) unsigned NOT NULL,
  `isActive` tinyint(1) NOT NULL DEFAULT '1',
  `title` enum('Mr','Ms','Mrs') NOT NULL DEFAULT 'Mr',
  `firstName` varchar(45) NOT NULL,
  `middleName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `dob` varchar(45) DEFAULT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModifiedDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `USERID_INDEX` (`userId`),
  CONSTRAINT `FK_USER_ID` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
