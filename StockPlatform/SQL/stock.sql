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
DROP DATABASE IF EXISTS `stock`;
CREATE DATABASE IF NOT EXISTS `stock` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `stock`;

-- Дамп структуры для таблица stock.blacklist
CREATE TABLE IF NOT EXISTS `blacklist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tender_Id` bigint(20) DEFAULT NULL,
  `lot_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_blacklist_application` (`tender_Id`),
  KEY `FK_blacklist_lot` (`lot_Id`),
  CONSTRAINT `FK_blacklist_application` FOREIGN KEY (`tender_Id`) REFERENCES `tender` (`id`),
  CONSTRAINT `FK_blacklist_lot` FOREIGN KEY (`lot_Id`) REFERENCES `lot` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.blacklist: ~2 rows (приблизительно)
/*!40000 ALTER TABLE `blacklist` DISABLE KEYS */;
INSERT INTO `blacklist` (`id`, `tender_Id`, `lot_Id`) VALUES
	(1, 1, 3),
	(2, 3, 1);
/*!40000 ALTER TABLE `blacklist` ENABLE KEYS */;

-- Дамп структуры для таблица stock.company
CREATE TABLE IF NOT EXISTS `company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `reg_number` varchar(100) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.company: ~2 rows (приблизительно)
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`, `name`, `email`, `phone`, `reg_number`, `country`, `city`, `status`) VALUES
	(1, 'bigroi', 'abc@mail.ru', '123123', 'qwe123', 'Belarus', 'Minsk', 'VERIFIED'),
	(10, 'JAVA', 'emailJAVA', '16532432', '123еолола', 'Belarus', 'Minsk', 'VERIFIED');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;

-- Дамп структуры для таблица stock.deals
CREATE TABLE IF NOT EXISTS `deals` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lot_Id` bigint(20) DEFAULT NULL,
  `tender_Id` bigint(20) DEFAULT NULL,
  `deals_Time` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_deals_lot` (`lot_Id`),
  KEY `FK_deals_tender` (`tender_Id`),
  CONSTRAINT `FK_deals_lot` FOREIGN KEY (`lot_Id`) REFERENCES `lot` (`id`),
  CONSTRAINT `FK_deals_tender` FOREIGN KEY (`tender_Id`) REFERENCES `tender` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.deals: ~2 rows (приблизительно)
/*!40000 ALTER TABLE `deals` DISABLE KEYS */;
INSERT INTO `deals` (`id`, `lot_Id`, `tender_Id`, `deals_Time`) VALUES
	(1, 3, 3, '2017-07-10'),
	(2, 1, 1, '2017-07-09');
/*!40000 ALTER TABLE `deals` ENABLE KEYS */;

-- Дамп структуры для таблица stock.email
CREATE TABLE IF NOT EXISTS `email` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `to_email` varchar(100) DEFAULT NULL,
  `email_subject` varchar(100) DEFAULT NULL,
  `email_text` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.email: ~3 rows (приблизительно)
/*!40000 ALTER TABLE `email` DISABLE KEYS */;
INSERT INTO `email` (`id`, `to_email`, `email_subject`, `email_text`) VALUES
	(1, 'blabla@email.com', 'subject', 'text'),
	(2, 'asd@asd,com', 'sdfsdf', 'asfsdf'),
	(5, '@todo', 'subj', 'text');
/*!40000 ALTER TABLE `email` ENABLE KEYS */;

-- Дамп структуры для таблица stock.lot
CREATE TABLE IF NOT EXISTS `lot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) DEFAULT NULL,
  `poduct_Id` bigint(20) DEFAULT NULL,
  `min_price` decimal(10,2) DEFAULT NULL,
  `seller_Id` bigint(20) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `exp_date` date DEFAULT NULL,
  `volume_of_lot` INT DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `saler` (`seller_Id`),
  KEY `product` (`poduct_Id`),
  CONSTRAINT `FK_lot_company` FOREIGN KEY (`seller_Id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_lot_product` FOREIGN KEY (`poduct_Id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.lot: ~3 rows (приблизительно)
/*!40000 ALTER TABLE `lot` DISABLE KEYS */;
INSERT INTO `lot` (`id`, `description`, `poduct_Id`, `min_price`, `seller_Id`, `status`, `exp_date`, `volume_of_lot`) VALUES
	(1, 'картоха 2017', 1, 2.00, 1, 'EXPIRED', '2016-06-26', 110),
	(3, 'киви', 111, 5.00, 1, 'IN_GAME', '2017-06-28', 120),
	(4, 'креветки', 1, 110.00, 1, 'DRAFT', '2017-07-10', 130);
/*!40000 ALTER TABLE `lot` ENABLE KEYS */;

-- Дамп структуры для таблица stock.predeal
CREATE TABLE IF NOT EXISTS `predeal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sellerHashCode` varchar(50) DEFAULT NULL,
  `customerHashCode` varchar(50) DEFAULT NULL,
  `tender_Id` bigint(20) DEFAULT NULL,
  `lot_Id` bigint(20) DEFAULT NULL,
  `sellerApprov` varchar(1) NOT NULL,
  `custApprov` varchar(1) NOT NULL,
  `dealDate` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_predeal_tender` (`tender_Id`),
  KEY `FK_predeal_lot` (`lot_Id`),
  CONSTRAINT `FK_predeal_lot` FOREIGN KEY (`lot_Id`) REFERENCES `lot` (`id`),
  CONSTRAINT `FK_predeal_tender` FOREIGN KEY (`tender_Id`) REFERENCES `tender` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.predeal: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `predeal` DISABLE KEYS */;
/*!40000 ALTER TABLE `predeal` ENABLE KEYS */;

-- Дамп структуры для таблица stock.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.product: ~3 rows (приблизительно)
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`id`, `name`, `description`) VALUES
	(1, 'apple', 'product'),
	(6, 'картоха', 'хорошая'),
	(111, 'кики', 'так себе');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;

-- Дамп структуры для таблица stock.tender
CREATE TABLE IF NOT EXISTS `tender` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) DEFAULT NULL,
  `product_Id` bigint(20) DEFAULT NULL,
  `max_price` decimal(10,2) DEFAULT NULL,
  `customer_Id` bigint(20) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `exp_date` date DEFAULT NULL,
  `volume_of_tender` INT DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_application_product` (`product_Id`),
  KEY `FK_application_company` (`customer_Id`),
  CONSTRAINT `FK_tender_company` FOREIGN KEY (`customer_Id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_tender_product` FOREIGN KEY (`product_Id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.tender: ~3 rows (приблизительно)
/*!40000 ALTER TABLE `tender` DISABLE KEYS */;
INSERT INTO `tender` (`id`, `description`, `product_Id`, `max_price`, `customer_Id`, `status`, `exp_date`, `volume_of_tender`) VALUES
	(1, 'темт', 1, 4.00, 1, 'EXPIRED', '2017-06-27', 150),
	(3, 'дордло', 111, 2.00, 1, 'IN_GAME', '2017-06-28', 160),
	(11, 'Javatest', 1, 4.00, 1, 'EXPIRED', '2017-07-10', 170);
/*!40000 ALTER TABLE `tender` ENABLE KEYS */;

-- Дамп структуры для таблица stock.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `company_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_user_company` (`company_Id`),
  CONSTRAINT `FK_user_company` FOREIGN KEY (`company_Id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.user: ~2 rows (приблизительно)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `login`, `password`, `company_Id`) VALUES
	(1, 'Admin', '1', 1),
	(2, 'яфй', '2', 10);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
