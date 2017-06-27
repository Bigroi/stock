-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               5.1.41-community - MySQL Community Server (GPL)
-- Операционная система:         Win32
-- HeidiSQL Версия:              9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Дамп структуры базы данных stock
CREATE DATABASE IF NOT EXISTS `stock` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `stock`;

-- Дамп структуры для таблица stock.archive
CREATE TABLE IF NOT EXISTS `archive` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `salerId` bigint(20) DEFAULT NULL,
  `customerId` bigint(20) DEFAULT NULL,
  `productId` bigint(20) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `tms_tmp` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_archive_company` (`salerId`),
  KEY `FK_archive_company_2` (`customerId`),
  KEY `FK_archive_product` (`productId`),
  CONSTRAINT `FK_archive_company` FOREIGN KEY (`salerId`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_archive_company_2` FOREIGN KEY (`customerId`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_archive_product` FOREIGN KEY (`productId`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица stock.blacklist
CREATE TABLE IF NOT EXISTS `blacklist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` bigint(20) DEFAULT NULL,
  `lot_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_blacklist_application` (`app_id`),
  KEY `FK_blacklist_lot` (`lot_id`),
  CONSTRAINT `FK_blacklist_application` FOREIGN KEY (`app_id`) REFERENCES `tender` (`id`),
  CONSTRAINT `FK_blacklist_lot` FOREIGN KEY (`lot_id`) REFERENCES `lot` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица stock.company
CREATE TABLE IF NOT EXISTS `company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `reg_number` varchar(100) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица stock.lot
CREATE TABLE IF NOT EXISTS `lot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) DEFAULT NULL,
  `poductId` bigint(20) DEFAULT NULL,
  `min_price` decimal(10,2) DEFAULT NULL,
  `salerId` bigint(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `exp_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `saler` (`salerId`),
  KEY `product` (`poductId`),
  CONSTRAINT `FK_lot_company` FOREIGN KEY (`salerId`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_lot_product` FOREIGN KEY (`poductId`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица stock.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;

-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица stock.tender
CREATE TABLE IF NOT EXISTS `tender` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) DEFAULT NULL,
  `productId` bigint(20) DEFAULT NULL,
  `max_price` decimal(10,2) DEFAULT NULL,
  `customerId` bigint(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `exp_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_application_product` (`productId`),
  KEY `FK_application_company` (`customerId`),
  CONSTRAINT `FK_tender_company` FOREIGN KEY (`customerId`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_tender_product` FOREIGN KEY (`productId`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица stock.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_user_company` (`companyId`),
  CONSTRAINT `FK_user_company` FOREIGN KEY (`companyId`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Экспортируемые данные не выделены.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
