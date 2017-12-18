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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.blacklist: ~4 rows (приблизительно)
/*!40000 ALTER TABLE `blacklist` DISABLE KEYS */;
INSERT INTO `blacklist` (`id`, `tender_Id`, `lot_Id`) VALUES
	(4, 18, 9),
	(5, 18, 9),
	(6, 18, 9),
	(7, 18, 9);
/*!40000 ALTER TABLE `blacklist` ENABLE KEYS */;

-- Дамп структуры для таблица stock.company
CREATE TABLE IF NOT EXISTS `company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `reg_number` varchar(100) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `longitude` decimal(10,2) DEFAULT NULL,
  `latitude` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.company: ~2 rows (приблизительно)
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`, `name`, `phone`, `reg_number`, `country`, `city`, `address`, `status`, `longitude`, `latitude`) VALUES
	(1, 'aaa', '+375291655733', 'df', 'ÐÐµÐ»Ð°ÑÑÑÑ', 'ÐÐ¸Ð½ÑÐº', 'ÐÐµÐ·Ð°Ð²Ð¸ÑÐ¸Ð¼Ð¾ÑÑÐ¸ 125', 'VERIFIED', 27.64, 53.94),
	(16, 'nasia', '+375298202264', '123', 'belarus', 'minsk', 'Независимости 125', 'VERIFIED', 27.64, 53.94);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;

-- Дамп структуры для таблица stock.deal
CREATE TABLE IF NOT EXISTS `deal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lot_Id` bigint(20) DEFAULT NULL,
  `tender_Id` bigint(20) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `customer_approved` char(1) DEFAULT NULL,
  `seller_id` bigint(20) NOT NULL,
  `seller_approved` char(1) DEFAULT NULL,
  `customer_id` bigint(20) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `volume` int(11) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_deals_lot` (`lot_Id`),
  KEY `FK_deals_tender` (`tender_Id`),
  KEY `FK_deal_company` (`seller_id`),
  KEY `FK_deal_company_2` (`customer_id`),
  KEY `FK_deal_product` (`product_id`),
  CONSTRAINT `FK_deal_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_deals_lot` FOREIGN KEY (`lot_Id`) REFERENCES `lot` (`id`) ON DELETE SET NULL,
  CONSTRAINT `FK_deals_tender` FOREIGN KEY (`tender_Id`) REFERENCES `tender` (`id`) ON DELETE SET NULL,
  CONSTRAINT `FK_deal_company` FOREIGN KEY (`seller_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_deal_company_2` FOREIGN KEY (`customer_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.deal: ~1 rows (приблизительно)
/*!40000 ALTER TABLE `deal` DISABLE KEYS */;
INSERT INTO `deal` (`id`, `lot_Id`, `tender_Id`, `time`, `customer_approved`, `seller_id`, `seller_approved`, `customer_id`, `price`, `volume`, `product_id`) VALUES
	(4, 9, 18, '2017-12-18 14:45:08', NULL, 1, 'N', 16, 34.00, 344, 13);
/*!40000 ALTER TABLE `deal` ENABLE KEYS */;

-- Дамп структуры для таблица stock.email
CREATE TABLE IF NOT EXISTS `email` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `to_email` varchar(100) DEFAULT NULL,
  `email_subject` varchar(100) DEFAULT NULL,
  `email_text` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.email: ~4 rows (приблизительно)
/*!40000 ALTER TABLE `email` DISABLE KEYS */;
INSERT INTO `email` (`id`, `to_email`, `email_subject`, `email_text`) VALUES
	(1, 'Независимости 125', 'Отказ от сделки.', 'К сожалению, Ваш потенциальный партнер отказался от Вашего лота №18.'),
	(2, 'Независимости 125', 'Отказ от сделки.', 'К сожалению, Ваш потенциальный партнер отказался от Вашего лота №18.'),
	(3, 'Независимости 125', 'Отказ от сделки.', 'К сожалению, Ваш потенциальный партнер отказался от Вашего лота №18.'),
	(4, 'Независимости 125', 'Отказ от сделки.', 'К сожалению, Ваш потенциальный партнер отказался от Вашего лота №18.');
/*!40000 ALTER TABLE `email` ENABLE KEYS */;

-- Дамп структуры для таблица stock.lot
CREATE TABLE IF NOT EXISTS `lot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) DEFAULT NULL,
  `product_Id` bigint(20) NOT NULL,
  `min_price` decimal(10,2) NOT NULL,
  `seller_Id` bigint(20) NOT NULL,
  `status` varchar(50) NOT NULL,
  `exp_date` date NOT NULL,
  `max_volume` int(11) NOT NULL,
  `min_Volume` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `saler` (`seller_Id`),
  KEY `product` (`product_Id`),
  CONSTRAINT `FK_lot_company` FOREIGN KEY (`seller_Id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_lot_product` FOREIGN KEY (`product_Id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.lot: ~2 rows (приблизительно)
/*!40000 ALTER TABLE `lot` DISABLE KEYS */;
INSERT INTO `lot` (`id`, `description`, `product_Id`, `min_price`, `seller_Id`, `status`, `exp_date`, `max_volume`, `min_Volume`) VALUES
	(8, 'kjkhkhj', 13, 123.00, 1, 'ACTIVE', '2017-12-17', 123333, 1233),
	(9, 'test Lot', 13, 100.00, 1, 'ACTIVE', '2017-12-17', 123124499, 123);
/*!40000 ALTER TABLE `lot` ENABLE KEYS */;

-- Дамп структуры для таблица stock.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` varchar(50) DEFAULT NULL,
  `archive` char(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.product: ~9 rows (приблизительно)
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`id`, `name`, `description`, `archive`) VALUES
	(1, 'apple', 'product', 'N'),
	(13, 'banan', 'product', 'N'),
	(111, 'РєРёРІРё', 'РІРєСѓСЃРЅРѕ', 'N'),
	(126, 'morkovka', 'super', 'N'),
	(127, 'kapusta', 'rt', 'N'),
	(131, 'tikva', 'TRUE', 'N'),
	(132, 'ukrop', 'StockPlatform1', 'N'),
	(133, 'kartoxa!', '1', 'N'),
	(134, 'goroh', 'long', 'N');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;

-- Дамп структуры для таблица stock.tender
CREATE TABLE IF NOT EXISTS `tender` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) DEFAULT NULL,
  `product_Id` bigint(20) NOT NULL,
  `max_price` decimal(10,2) NOT NULL,
  `customer_Id` bigint(20) NOT NULL,
  `status` varchar(50) NOT NULL,
  `exp_date` date NOT NULL,
  `max_volume` int(11) NOT NULL,
  `min_Volume` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_application_product` (`product_Id`),
  KEY `FK_application_company` (`customer_Id`),
  CONSTRAINT `FK_tender_company` FOREIGN KEY (`customer_Id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_tender_product` FOREIGN KEY (`product_Id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.tender: ~1 rows (приблизительно)
/*!40000 ALTER TABLE `tender` DISABLE KEYS */;
INSERT INTO `tender` (`id`, `description`, `product_Id`, `max_price`, `customer_Id`, `status`, `exp_date`, `max_volume`, `min_Volume`) VALUES
	(18, 'TEST TENDER', 13, 29.00, 16, 'ACTIVE', '2017-12-17', 24610, 234);
/*!40000 ALTER TABLE `tender` ENABLE KEYS */;

-- Дамп структуры для таблица stock.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `company_Id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_user_company` (`company_Id`),
  CONSTRAINT `FK_user_company` FOREIGN KEY (`company_Id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.user: ~2 rows (приблизительно)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `login`, `password`, `company_Id`) VALUES
	(1, 'Admin@stock.by', '1', 1),
	(14, 'ana@gmail.com', '1', 16);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Дамп структуры для таблица stock.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role` varchar(50) NOT NULL,
  KEY `FK_user_role_user` (`user_id`),
  CONSTRAINT `FK_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.user_role: ~3 rows (приблизительно)
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`user_id`, `role`) VALUES
	(1, 'ROLE_ADMIN'),
	(1, 'ROLE_USER'),
	(14, 'ROLE_USER');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
