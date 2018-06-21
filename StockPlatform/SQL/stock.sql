-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.1.41-community - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for stock
DROP DATABASE IF EXISTS `stock`;
CREATE DATABASE IF NOT EXISTS `stock` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `stock`;

-- Dumping structure for table stock.address
DROP TABLE IF EXISTS `address`;
CREATE TABLE IF NOT EXISTS `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(50) NOT NULL,
  `country` varchar(50) NOT NULL,
  `address` varchar(500) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `company_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ADDRESS_company` (`company_id`),
  CONSTRAINT `FK_ADDRESS_company` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table stock.address: ~2 rows (approximately)
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` (`id`, `city`, `country`, `address`, `latitude`, `longitude`, `company_id`) VALUES
	(1, 'Варшава', 'Польша', 'Niepodleglosci', 52.2012734, 21.0109725, 21),
	(3, 'Минск', 'Беларусь', 'Скорины 39А', 53.9270629, 27.6972198, 23);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;

-- Dumping structure for table stock.black_list
DROP TABLE IF EXISTS `black_list`;
CREATE TABLE IF NOT EXISTS `black_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tender_Id` bigint(20) DEFAULT NULL,
  `lot_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_blacklist_application` (`tender_Id`),
  KEY `FK_blacklist_lot` (`lot_Id`),
  CONSTRAINT `FK_black_list_lot` FOREIGN KEY (`lot_Id`) REFERENCES `lot` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_black_list_tender` FOREIGN KEY (`tender_Id`) REFERENCES `tender` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Dumping data for table stock.black_list: ~0 rows (approximately)
/*!40000 ALTER TABLE `black_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `black_list` ENABLE KEYS */;

-- Dumping structure for table stock.company
DROP TABLE IF EXISTS `company`;
CREATE TABLE IF NOT EXISTS `company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 NOT NULL,
  `phone` varchar(50) CHARACTER SET utf8 NOT NULL,
  `reg_number` varchar(100) CHARACTER SET utf8 NOT NULL,
  `status` varchar(50) CHARACTER SET utf8 NOT NULL,
  `address_id` bigint(20) DEFAULT NULL,
  `type` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_company_address` (`address_id`),
  CONSTRAINT `FK_company_address` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Dumping data for table stock.company: ~2 rows (approximately)
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`, `name`, `phone`, `reg_number`, `status`, `address_id`, `type`) VALUES
	(21, 'Admin', '+375298202267', '123', 'VERIFIED', 1, '\'TRADER\''),
	(23, 'дорд', '+375298202264', '654654', 'VERIFIED', 3, '\'TRADER\'');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;

-- Dumping structure for table stock.deal
DROP TABLE IF EXISTS `deal`;
CREATE TABLE IF NOT EXISTS `deal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lot_id` bigint(20) DEFAULT NULL,
  `tender_id` bigint(20) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `buyer_choice` tinyint(4) NOT NULL,
  `seller_choice` tinyint(4) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `max_transport_price` decimal(10,2) NOT NULL,
  `volume` int(11) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `seller_foto` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `seller_address_id` bigint(20) NOT NULL,
  `buyer_address_id` bigint(20) NOT NULL,
  `seller_description` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `buyer_description` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_deals_lot` (`lot_id`),
  KEY `FK_deals_tender` (`tender_id`),
  KEY `FK_deal_product` (`product_id`),
  KEY `FK_deal_address` (`seller_address_id`),
  KEY `FK_deal_address_2` (`buyer_address_id`),
  CONSTRAINT `FK_deal_address` FOREIGN KEY (`seller_address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `FK_deal_address_2` FOREIGN KEY (`buyer_address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `FK_deal_lot` FOREIGN KEY (`lot_id`) REFERENCES `lot` (`id`) ON DELETE SET NULL,
  CONSTRAINT `FK_deal_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_deal_tender` FOREIGN KEY (`tender_id`) REFERENCES `tender` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Dumping data for table stock.deal: ~5 rows (approximately)
/*!40000 ALTER TABLE `deal` DISABLE KEYS */;
INSERT INTO `deal` (`id`, `lot_id`, `tender_id`, `time`, `buyer_choice`, `seller_choice`, `price`, `max_transport_price`, `volume`, `product_id`, `seller_foto`, `seller_address_id`, `buyer_address_id`, `seller_description`, `buyer_description`) VALUES
	(14, 22, 20, '2018-06-21 21:12:22', 1, 1, 10.50, 0.00, 167, 135, NULL, 3, 1, NULL, 'test'),
	(15, 22, 20, '2018-06-21 21:12:27', 1, 1, 10.50, 0.00, 334, 135, NULL, 3, 1, NULL, 'test'),
	(18, 22, 20, '2018-06-21 21:12:30', 1, 1, 10.50, 0.00, 100, 135, NULL, 3, 1, 'test', 'ljhkjhk'),
	(32, 22, 21, '2018-06-21 21:12:34', 1, 1, 5.50, 0.00, 100, 135, NULL, 3, 1, 'test', 'ljhkjhk'),
	(33, 23, 21, '2018-06-21 21:12:37', 1, 1, 5.38, 0.00, 100, 135, NULL, 3, 1, 'test', 'ljhkjhk');
/*!40000 ALTER TABLE `deal` ENABLE KEYS */;

-- Dumping structure for table stock.email
DROP TABLE IF EXISTS `email`;
CREATE TABLE IF NOT EXISTS `email` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `recipient` varchar(100) CHARACTER SET utf8 NOT NULL,
  `subject` varchar(200) CHARACTER SET utf8 NOT NULL,
  `body` varchar(5000) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Dumping data for table stock.email: ~0 rows (approximately)
/*!40000 ALTER TABLE `email` DISABLE KEYS */;
/*!40000 ALTER TABLE `email` ENABLE KEYS */;

-- Dumping structure for table stock.generated_key
DROP TABLE IF EXISTS `generated_key`;
CREATE TABLE IF NOT EXISTS `generated_key` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `generated_key` varchar(50) NOT NULL,
  `expiration_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- Dumping data for table stock.generated_key: ~2 rows (approximately)
/*!40000 ALTER TABLE `generated_key` DISABLE KEYS */;
INSERT INTO `generated_key` (`id`, `generated_key`, `expiration_time`) VALUES
	(6, 'sdf', '2018-05-19 15:26:51'),
	(8, 'ALJOL2OYI39TBv9bHlcfoqzius5ij7ojeRARuZqpLMSRnrLmtd', '2018-05-19 00:00:00');
/*!40000 ALTER TABLE `generated_key` ENABLE KEYS */;

-- Dumping structure for table stock.lot
DROP TABLE IF EXISTS `lot`;
CREATE TABLE IF NOT EXISTS `lot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) CHARACTER SET utf8 DEFAULT NULL,
  `product_id` bigint(20) NOT NULL,
  `min_price` decimal(10,2) NOT NULL,
  `min_volume` int(11) NOT NULL,
  `max_volume` int(11) NOT NULL,
  `company_id` bigint(20) NOT NULL,
  `status` varchar(50) CHARACTER SET utf8 NOT NULL,
  `creation_date` date NOT NULL,
  `exparation_date` date NOT NULL,
  `foto` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `address_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `saler` (`company_id`),
  KEY `product` (`product_id`),
  KEY `FK_lot_address` (`address_id`),
  CONSTRAINT `FK_lot_address` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `FK_lot_company` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_lot_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Dumping data for table stock.lot: ~3 rows (approximately)
/*!40000 ALTER TABLE `lot` DISABLE KEYS */;
INSERT INTO `lot` (`id`, `description`, `product_id`, `min_price`, `min_volume`, `max_volume`, `company_id`, `status`, `creation_date`, `exparation_date`, `foto`, `address_id`) VALUES
	(22, 'test', 135, 1.00, 1, 0, 23, 'ACTIVE', '2018-05-19', '2019-05-19', NULL, 3),
	(23, 'test', 135, 0.75, 1, 0, 23, 'ACTIVE', '2018-05-19', '2019-05-19', NULL, 3),
	(24, 'test', 136, 0.75, 1, 0, 23, 'ACTIVE', '2018-05-19', '2019-05-19', NULL, 3);
/*!40000 ALTER TABLE `lot` ENABLE KEYS */;

-- Dumping structure for table stock.product
DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 NOT NULL,
  `description` varchar(2000) CHARACTER SET utf8 DEFAULT NULL,
  `delivary_price` decimal(10,2) NOT NULL,
  `removed` char(1) CHARACTER SET utf8 NOT NULL,
  `picture` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Dumping data for table stock.product: ~2 rows (approximately)
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`id`, `name`, `description`, `delivary_price`, `removed`, `picture`) VALUES
	(135, 'Яблоки', 'Хорошие яблоки', 2.00, 'N', '/img/apple.png'),
	(136, 'Картофель', 'Хороший картофель', 2.00, 'N', '/img/potato.png');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;

-- Dumping structure for table stock.tender
DROP TABLE IF EXISTS `tender`;
CREATE TABLE IF NOT EXISTS `tender` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) CHARACTER SET utf8 DEFAULT NULL,
  `product_id` bigint(20) NOT NULL,
  `max_price` decimal(10,2) NOT NULL,
  `min_volume` int(11) NOT NULL,
  `max_volume` int(11) NOT NULL,
  `company_id` bigint(20) NOT NULL,
  `status` varchar(50) CHARACTER SET utf8 NOT NULL,
  `creation_date` date NOT NULL,
  `exparation_date` date NOT NULL,
  `address_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_application_product` (`product_id`),
  KEY `FK_application_company` (`company_id`),
  KEY `FK_tender_address` (`address_id`),
  CONSTRAINT `FK_tender_address` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `FK_tender_company` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_tender_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Dumping data for table stock.tender: ~2 rows (approximately)
/*!40000 ALTER TABLE `tender` DISABLE KEYS */;
INSERT INTO `tender` (`id`, `description`, `product_id`, `max_price`, `min_volume`, `max_volume`, `company_id`, `status`, `creation_date`, `exparation_date`, `address_id`) VALUES
	(20, 'ljhkjhk', 135, 20.00, 1, 7000, 21, 'ACTIVE', '2016-04-17', '2019-05-17', 1),
	(21, 'ljhkjhk', 135, 10.00, 1, 6800, 21, 'ACTIVE', '2016-04-17', '2019-05-17', 1);
/*!40000 ALTER TABLE `tender` ENABLE KEYS */;

-- Dumping structure for table stock.transport_proposition
DROP TABLE IF EXISTS `transport_proposition`;
CREATE TABLE IF NOT EXISTS `transport_proposition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deal_id` bigint(20) NOT NULL,
  `company_id` bigint(20) NOT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_transport_proposition_deal` (`deal_id`),
  KEY `FK_transport_proposition_company` (`company_id`),
  CONSTRAINT `FK_transport_proposition_company` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_transport_proposition_deal` FOREIGN KEY (`deal_id`) REFERENCES `deal` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table stock.transport_proposition: ~1 rows (approximately)
/*!40000 ALTER TABLE `transport_proposition` DISABLE KEYS */;
INSERT INTO `transport_proposition` (`id`, `deal_id`, `company_id`, `price`) VALUES
	(1, 15, 21, 2);
/*!40000 ALTER TABLE `transport_proposition` ENABLE KEYS */;

-- Dumping structure for table stock.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 NOT NULL,
  `company_id` bigint(20) NOT NULL,
  `key_id` bigint(20) DEFAULT NULL,
  `login_count` int(11) NOT NULL DEFAULT '0',
  `last_login` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_user_company` (`company_id`),
  KEY `FK_user_keys` (`key_id`),
  CONSTRAINT `FK_user_company` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_user_generated_key` FOREIGN KEY (`key_id`) REFERENCES `generated_key` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Dumping data for table stock.user: ~2 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `username`, `password`, `company_id`, `key_id`, `login_count`, `last_login`) VALUES
	(19, 'admin@stock.by', '', 21, NULL, 69, '2018-06-20 17:18:34'),
	(20, 'css@tenant.ch', '12345677', 23, NULL, 1, '2018-05-17 20:34:36');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table stock.user_role
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role` varchar(50) CHARACTER SET utf8 NOT NULL,
  KEY `FK_user_role_user` (`user_id`),
  CONSTRAINT `FK_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Dumping data for table stock.user_role: ~3 rows (approximately)
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`user_id`, `role`) VALUES
	(19, 'ROLE_ADMIN'),
	(19, 'ROLE_USER'),
	(20, 'ROLE_USER');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
