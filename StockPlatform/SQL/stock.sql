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

-- Дамп структуры для таблица stock.application
CREATE TABLE IF NOT EXISTS `application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) NOT NULL DEFAULT '0',
  `product` bigint(20) NOT NULL DEFAULT '0',
  `max_price` decimal(10,0) NOT NULL DEFAULT '0',
  `customer` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_application_product` (`product`),
  KEY `FK_application_company` (`customer`),
  CONSTRAINT `FK_application_company` FOREIGN KEY (`customer`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_application_product` FOREIGN KEY (`product`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Дамп данных таблицы stock.application: ~0 rows (приблизительно)
DELETE FROM `application`;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
/*!40000 ALTER TABLE `application` ENABLE KEYS */;

-- Дамп структуры для таблица stock.archive
CREATE TABLE IF NOT EXISTS `archive` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `saler` bigint(20) DEFAULT '0',
  `customer` bigint(20) DEFAULT '0',
  `product` bigint(20) DEFAULT '0',
  `price` bigint(20) DEFAULT '0',
  `tms_tmp` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_archive_company` (`saler`),
  KEY `FK_archive_company_2` (`customer`),
  KEY `FK_archive_product` (`product`),
  CONSTRAINT `FK_archive_product` FOREIGN KEY (`product`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_archive_company` FOREIGN KEY (`saler`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_archive_company_2` FOREIGN KEY (`customer`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Дамп данных таблицы stock.archive: ~0 rows (приблизительно)
DELETE FROM `archive`;
/*!40000 ALTER TABLE `archive` DISABLE KEYS */;
/*!40000 ALTER TABLE `archive` ENABLE KEYS */;

-- Дамп структуры для таблица stock.blacklist
CREATE TABLE IF NOT EXISTS `blacklist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` bigint(20) NOT NULL DEFAULT '0',
  `lot_id` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_blacklist_application` (`app_id`),
  KEY `FK_blacklist_lot` (`lot_id`),
  CONSTRAINT `FK_blacklist_application` FOREIGN KEY (`app_id`) REFERENCES `application` (`id`),
  CONSTRAINT `FK_blacklist_lot` FOREIGN KEY (`lot_id`) REFERENCES `lot` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Дамп данных таблицы stock.blacklist: ~0 rows (приблизительно)
DELETE FROM `blacklist`;
/*!40000 ALTER TABLE `blacklist` DISABLE KEYS */;
/*!40000 ALTER TABLE `blacklist` ENABLE KEYS */;

-- Дамп структуры для таблица stock.company
CREATE TABLE IF NOT EXISTS `company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '0',
  `email` varchar(50) NOT NULL DEFAULT '0',
  `phone` int(50) NOT NULL DEFAULT '0',
  `reg_number` varchar(100) NOT NULL DEFAULT '0',
  `country` varchar(50) NOT NULL DEFAULT '0',
  `city` varchar(50) NOT NULL DEFAULT '0',
  `user_id` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Дамп данных таблицы stock.company: ~0 rows (приблизительно)
DELETE FROM `company`;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
/*!40000 ALTER TABLE `company` ENABLE KEYS */;

-- Дамп структуры для таблица stock.lot
CREATE TABLE IF NOT EXISTS `lot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) DEFAULT NULL,
  `poduct` bigint(20) DEFAULT NULL,
  `min_price` decimal(10,0) DEFAULT NULL,
  `saler` bigint(20) DEFAULT NULL,
  `exp_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `saler` (`saler`),
  KEY `product` (`poduct`),
  CONSTRAINT `product` FOREIGN KEY (`poduct`) REFERENCES `product` (`id`),
  CONSTRAINT `saler` FOREIGN KEY (`saler`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Дамп данных таблицы stock.lot: ~0 rows (приблизительно)
DELETE FROM `lot`;
/*!40000 ALTER TABLE `lot` DISABLE KEYS */;
/*!40000 ALTER TABLE `lot` ENABLE KEYS */;

-- Дамп структуры для таблица stock.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Дамп данных таблицы stock.product: ~0 rows (приблизительно)
DELETE FROM `product`;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;

-- Дамп структуры для таблица stock.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Дамп данных таблицы stock.user: ~0 rows (приблизительно)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
