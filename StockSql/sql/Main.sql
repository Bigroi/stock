SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;


DROP DATABASE IF EXISTS `@schema@`;
CREATE DATABASE IF NOT EXISTS `@schema@` DEFAULT CHARACTER SET utf8;
USE `@schema@`;

CREATE TABLE IF NOT EXISTS `ADDRESS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(50) NOT NULL,
  `country` varchar(50) NOT NULL,
  `address` varchar(500) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `company_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ADDRESS_company` (`company_id`),
  CONSTRAINT `FK_ADDRESS_company` FOREIGN KEY (`company_id`) REFERENCES `COMPANY` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

ALTER TABLE `ADDRESS` DISABLE KEYS;
INSERT INTO `ADDRESS` (`id`, `city`, `country`, `address`, `latitude`, `longitude`, `company_id`) VALUES
	(1, 'Warsaw', 'Poland', '', 52.21667, 21.03333, 0),
	(3, 'Kraków', 'Poland', 'Jana Pawla 2, 1', 50.06667, 19.95, 23),
	(4, 'Wrocław', 'Poland', 'Jana Pawla 2, 1', 51.11667, 17.03333, 24),
	(5, 'Łódź', 'Poland', 'Jana Pawla 2, 1', 51.78333, 19.46667, 25),
	(6, 'Poznań', 'Poland', 'Jana Pawla 2, 1', 52.33333, 17, 26),
	(7, 'Gdańsk', 'Poland', 'Jana Pawla 2, 1', 54.36667, 18.63333, 27),
	(8, 'Szczecin', 'Poland', 'Jana Pawla 2, 1', 53.43333, 14.53333, 28),
	(9, 'Bydgoszcz', 'Poland', 'Jana Pawla 2, 1', 53.11667, 18, 29),
	(10, 'Lublin', 'Poland', 'Jana Pawla 2, 1', 51.23333, 22.56667, 30),
	(11, 'Katowice', 'Poland', 'Jana Pawla 2, 1', 50.25, 19, 31),
	(12, 'Białystok', 'Poland', 'Jana Pawla 2, 1', 53.11667, 23.16667, 32),
	(13, 'Kraków', 'Poland', '', 50.06667, 19.95, 0),
	(14, 'Wrocław', 'Poland', '', 51.11667, 17.03333, 0),
	(15, 'Łódź', 'Poland', '', 51.78333, 19.46667, 0),
	(16, 'Poznań', 'Poland', '', 52.33333, 17, 0),
	(17, 'Gdańsk', 'Poland', '', 54.36667, 18.63333, 0),
	(18, 'Szczecin', 'Poland', '', 53.43333, 14.53333, 0),
	(19, 'Bydgoszcz', 'Poland', '', 53.11667, 18, 0),
	(20, 'Lublin', 'Poland', '', 51.23333, 22.56667, 0),
	(21, 'Katowice', 'Poland', '', 50.25, 19, 0),
	(22, 'Białystok', 'Poland', '', 53.11667, 23.16667, 0);
ALTER TABLE `ADDRESS` ENABLE KEYS;

CREATE TABLE IF NOT EXISTS `BLACK_LIST` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tender_Id` bigint(20) DEFAULT NULL,
  `lot_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_blacklist_application` (`tender_Id`),
  KEY `FK_blacklist_lot` (`lot_Id`),
  CONSTRAINT `FK_black_list_lot` FOREIGN KEY (`lot_Id`) REFERENCES `LOT` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_black_list_tender` FOREIGN KEY (`tender_Id`) REFERENCES `TENDER` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE IF NOT EXISTS `COMPANY` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 NOT NULL,
  `phone` varchar(50) CHARACTER SET utf8 NOT NULL,
  `reg_number` varchar(100) CHARACTER SET utf8 NOT NULL,
  `status` varchar(50) CHARACTER SET utf8 NOT NULL,
  `address_id` bigint(20) DEFAULT NULL,
  `type` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_company_address` (`address_id`),
  CONSTRAINT `FK_company_address` FOREIGN KEY (`address_id`) REFERENCES `ADDRESS` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

ALTER TABLE `COMPANY` DISABLE KEYS;
INSERT INTO `COMPANY` (`id`, `name`, `phone`, `reg_number`, `status`, `address_id`, `type`) VALUES
	(0, 'Admin', '+4800000001', '0000000000', 'VERIFIED', 1, 'TRADER'),
	(23, 'Apple Trader', '+4800000002', '1234567890', 'VERIFIED', 3, 'TRADER'),
	(24, 'Apple Farmer', '+4800000003', '0987654321', 'VERIFIED', 4, 'TRADER'),
	(25, 'Potato Farmer', '+4800000004', '0192837465', 'VERIFIED', 5, 'TRADER'),
	(26, 'Potato Trader', '+4800000005', '1029384756', 'VERIFIED', 6, 'TRADER'),
	(27, 'Common Trader', '+4800000006', '1209348756', 'VERIFIED', 7, 'TRADER'),
	(28, 'Small Shop', '+4800000007', '0912873465', 'VERIFIED', 8, 'TRADER'),
	(29, 'Middle Shop', '+4800000008', '0981236547', 'VERIFIED', 9, 'TRADER'),
	(30, 'Big Shop', '+4800000009', '1230984567', 'VERIFIED', 10, 'TRADER'),
	(31, 'Huge Shop', '+4800000010', '1209837465', 'VERIFIED', 11, 'TRADER'),
	(32, 'transport', '+4800000099', '1209837465', 'VERIFIED', 11, 'TRANS'),
	(33, 'Micro Shop', '+4800000011', '0912873456', 'VERIFIED', 12, 'TRADER');
ALTER TABLE `COMPANY` ENABLE KEYS;

CREATE TABLE IF NOT EXISTS `DEAL` (
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
  CONSTRAINT `FK_deal_address` FOREIGN KEY (`seller_address_id`) REFERENCES `ADDRESS` (`id`),
  CONSTRAINT `FK_deal_address_2` FOREIGN KEY (`buyer_address_id`) REFERENCES `ADDRESS` (`id`),
  CONSTRAINT `FK_deal_lot` FOREIGN KEY (`lot_id`) REFERENCES `LOT` (`id`) ON DELETE SET NULL,
  CONSTRAINT `FK_deal_product` FOREIGN KEY (`product_id`) REFERENCES `PRODUCT` (`id`),
  CONSTRAINT `FK_deal_tender` FOREIGN KEY (`tender_id`) REFERENCES `TENDER` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE IF NOT EXISTS `EMAIL` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `recipient` varchar(100) NOT NULL,
  `subject` varchar(200) NOT NULL,
  `body` varchar(5000) NOT NULL,
  `file_name` varchar(50) DEFAULT NULL,
  `file` blob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6414512 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `GENERATED_KEY` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `generated_key` varchar(50) NOT NULL,
  `expiration_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `LOT` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) CHARACTER SET utf8 DEFAULT NULL,
  `product_id` bigint(20) NOT NULL,
  `price` decimal(10,2) NOT NULL,
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
  CONSTRAINT `FK_lot_address` FOREIGN KEY (`address_id`) REFERENCES `ADDRESS` (`id`),
  CONSTRAINT `FK_lot_company` FOREIGN KEY (`company_id`) REFERENCES `COMPANY` (`id`),
  CONSTRAINT `FK_lot_product` FOREIGN KEY (`product_id`) REFERENCES `PRODUCT` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

ALTER TABLE `LOT` DISABLE KEYS;
INSERT INTO `LOT` (`id`, `description`, `product_id`, `price`, `min_volume`, `max_volume`, `company_id`, `status`, `creation_date`, `exparation_date`, `foto`, `address_id`) VALUES
	(25, 'I am an apple farmer', 135, 0.20, 10000, 30000, 24, 'ACTIVE', '2018-05-21', '2018-07-21', NULL, 4),
	(26, 'I am an apple trader', 135, 0.20, 1000, 120000, 23, 'ACTIVE', '2018-05-21', '2018-07-21', NULL, 3),
	(27, 'I am a common trader', 135, 0.17, 8000, 100000, 27, 'ACTIVE', '2018-05-21', '2018-07-21', NULL, 7),
	(28, 'I am a potato farmer', 136, 0.21, 3000, 30000, 25, 'ACTIVE', '2018-05-21', '2018-07-21', NULL, 5),
	(31, 'I am a potato trader', 136, 0.34, 1000, 120000, 26, 'ACTIVE', '2018-05-21', '2018-07-21', NULL, 6),
	(32, 'I am a common trader', 136, 0.40, 8000, 80000, 27, 'ACTIVE', '2018-05-21', '2018-07-21', NULL, 7);
ALTER TABLE `LOT` ENABLE KEYS;

CREATE TABLE IF NOT EXISTS `PRODUCT` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 NOT NULL,
  `description` varchar(2000) CHARACTER SET utf8 DEFAULT NULL,
  `delivary_price` decimal(10,2) NOT NULL,
  `removed` char(1) CHARACTER SET utf8 NOT NULL,
  `picture` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

ALTER TABLE `PRODUCT` DISABLE KEYS;
INSERT INTO `PRODUCT` (`id`, `name`, `description`, `delivary_price`, `removed`, `picture`) VALUES
	(135, 'Apple', 'An apple is a sweet, edible fruit produced by an apple tree (Malus pumila)', 0.20, 'N', '/Static/img/apple.png'),
	(136, 'Potato', 'The potato is a starchy, tuberous crop from the perennial nightshade Solanum tuberosum', 0.20, 'N', '/Static/img/potato.png');
ALTER TABLE `PRODUCT` ENABLE KEYS;

CREATE TABLE IF NOT EXISTS `SQL_UPDATE` (
  `LAST_UPDATE` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `SQL_UPDATE` (`LAST_UPDATE`) VALUES
	(0);

CREATE TABLE IF NOT EXISTS `TENDER` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) CHARACTER SET utf8 DEFAULT NULL,
  `product_id` bigint(20) NOT NULL,
  `price` decimal(10,2) NOT NULL,
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
  CONSTRAINT `FK_tender_address` FOREIGN KEY (`address_id`) REFERENCES `ADDRESS` (`id`),
  CONSTRAINT `FK_tender_company` FOREIGN KEY (`company_id`) REFERENCES `COMPANY` (`id`),
  CONSTRAINT `FK_tender_product` FOREIGN KEY (`product_id`) REFERENCES `PRODUCT` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

ALTER TABLE `TENDER` DISABLE KEYS;
INSERT INTO `TENDER` (`id`, `description`, `product_id`, `price`, `min_volume`, `max_volume`, `company_id`, `status`, `creation_date`, `exparation_date`, `address_id`) VALUES
	(23, 'I am a micro', 135, 0.60, 100, 5000, 32, 'ACTIVE', '2018-05-21', '2018-07-21', 12),
	(24, 'I am a small', 135, 0.60, 5000, 15000, 28, 'ACTIVE', '2018-05-21', '2018-07-21', 8),
	(25, 'I am a middle', 135, 0.53, 8000, 40000, 29, 'ACTIVE', '2018-05-21', '2018-07-21', 9),
	(26, 'I am a big', 135, 0.47, 25000, 80000, 30, 'ACTIVE', '2018-05-21', '2018-07-21', 10),
	(27, 'I am a huge', 135, 0.38, 50000, 150000, 31, 'ACTIVE', '2018-05-21', '2018-07-21', 11),
	(28, 'I am a micro', 136, 0.60, 100, 5000, 32, 'ACTIVE', '2018-05-21', '2018-07-21', 12),
	(29, 'I am a small', 136, 0.60, 5000, 15000, 28, 'ACTIVE', '2018-05-21', '2018-07-21', 8),
	(30, 'I am a middle', 136, 0.53, 8000, 40000, 29, 'ACTIVE', '2018-05-21', '2018-07-21', 9),
	(31, 'I am a big', 136, 0.47, 25000, 80000, 30, 'ACTIVE', '2018-05-21', '2018-07-21', 10),
	(32, 'I am a huge', 136, 0.38, 50000, 150000, 31, 'ACTIVE', '2018-05-21', '2018-07-21', 11);
ALTER TABLE `TENDER` ENABLE KEYS;

CREATE TABLE IF NOT EXISTS `TRANSPORT_PROPOSITION` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deal_id` bigint(20) NOT NULL,
  `company_id` bigint(20) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `status` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_transport_proposition_deal` (`deal_id`),
  KEY `FK_transport_proposition_company` (`company_id`),
  CONSTRAINT `FK_transport_proposition_company` FOREIGN KEY (`company_id`) REFERENCES `COMPANY` (`id`),
  CONSTRAINT `FK_transport_proposition_deal` FOREIGN KEY (`deal_id`) REFERENCES `DEAL` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `USER` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 NOT NULL,
  `password` varchar(60) CHARACTER SET utf8 NOT NULL,
  `company_id` bigint(20) NOT NULL,
  `key_id` bigint(20) DEFAULT NULL,
  `login_count` int(11) NOT NULL DEFAULT '0',
  `last_login` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_user_company` (`company_id`),
  KEY `FK_user_keys` (`key_id`),
  CONSTRAINT `FK_user_company` FOREIGN KEY (`company_id`) REFERENCES `COMPANY` (`id`),
  CONSTRAINT `FK_user_generated_key` FOREIGN KEY (`key_id`) REFERENCES `GENERATED_KEY` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

ALTER TABLE `USER` DISABLE KEYS;
INSERT INTO `USER` (`id`, `username`, `password`, `company_id`, `key_id`, `login_count`, `last_login`) VALUES
	(19, 'admin@stock.by', '$2a$10$uzpFt2/Uxp3XWXh/.lTK3uptEwIEw.j4W1aP3MGSuQ3G7HkL2BBRK', 0, NULL, 94, '2018-08-05 22:24:26'),
	(20, 'shop1@gmail.com', '$2a$10$uzpFt2/Uxp3XWXh/.lTK3uptEwIEw.j4W1aP3MGSuQ3G7HkL2BBRK', 32, NULL, 1, '2018-06-21 17:04:10'),
	(21, 'shop2@gmail.com', '$2a$10$uzpFt2/Uxp3XWXh/.lTK3uptEwIEw.j4W1aP3MGSuQ3G7HkL2BBRK', 28, NULL, 0, '2018-05-17 20:34:36'),
	(22, 'shop3@gmail.com', '$2a$10$uzpFt2/Uxp3XWXh/.lTK3uptEwIEw.j4W1aP3MGSuQ3G7HkL2BBRK', 29, NULL, 0, '2018-05-17 20:34:36'),
	(23, 'shop4@gmail.com', '$2a$10$uzpFt2/Uxp3XWXh/.lTK3uptEwIEw.j4W1aP3MGSuQ3G7HkL2BBRK', 30, NULL, 0, '2018-05-17 20:34:36'),
	(24, 'shop5@gmail.com', '$2a$10$uzpFt2/Uxp3XWXh/.lTK3uptEwIEw.j4W1aP3MGSuQ3G7HkL2BBRK', 31, NULL, 0, '2018-05-17 20:34:36'),
	(25, 'applefarmer1@gmail.com', '$2a$10$uzpFt2/Uxp3XWXh/.lTK3uptEwIEw.j4W1aP3MGSuQ3G7HkL2BBRK', 23, NULL, 2, '2018-06-21 16:54:08'),
	(26, 'applefarmer2@gmail.com', '$2a$10$uzpFt2/Uxp3XWXh/.lTK3uptEwIEw.j4W1aP3MGSuQ3G7HkL2BBRK', 24, NULL, 1, '2018-06-21 16:54:42'),
	(27, 'commonfarmer@gmail.com', '$2a$10$uzpFt2/Uxp3XWXh/.lTK3uptEwIEw.j4W1aP3MGSuQ3G7HkL2BBRK', 27, NULL, 1, '2018-06-21 16:55:17'),
	(28, 'potatofarmer1@gmail.com', '$2a$10$uzpFt2/Uxp3XWXh/.lTK3uptEwIEw.j4W1aP3MGSuQ3G7HkL2BBRK', 25, NULL, 1, '2018-06-21 16:55:59'),
	(29, 'potatofarmer2@gmail.com', '$2a$10$uzpFt2/Uxp3XWXh/.lTK3uptEwIEw.j4W1aP3MGSuQ3G7HkL2BBRK', 26, NULL, 1, '2018-06-21 16:56:26');
ALTER TABLE `USER` ENABLE KEYS;

CREATE TABLE IF NOT EXISTS `USER_ROLE` (
  `user_id` bigint(20) NOT NULL,
  `role` varchar(50) CHARACTER SET utf8 NOT NULL,
  KEY `FK_user_role_user` (`user_id`),
  CONSTRAINT `FK_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `USER` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

ALTER TABLE `USER_ROLE` DISABLE KEYS;
INSERT INTO `USER_ROLE` (`user_id`, `role`) VALUES
	(19, 'ROLE_ADMIN'),
	(19, 'ROLE_USER'),
	(21, 'ROLE_USER'),
	(22, 'ROLE_USER'),
	(22, 'ROLE_USER'),
	(24, 'ROLE_USER'),
	(25, 'ROLE_USER'),
	(26, 'ROLE_USER'),
	(28, 'ROLE_USER'),
	(20, 'ROLE_USER'),
	(29, 'ROLE_USER'),
	(27, 'ROLE_USER');
ALTER TABLE `USER_ROLE` ENABLE KEYS;

SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS);
