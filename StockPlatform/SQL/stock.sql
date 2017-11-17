-- --------------------------------------------------------
-- ����:                         127.0.0.1
-- ������ �������:               5.1.41-community - MySQL Community Server (GPL)
-- ������������ �������:         Win32
-- HeidiSQL ������:              9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- ���� ��������� ���� ������ stock
DROP DATABASE IF EXISTS `stock`;
CREATE DATABASE IF NOT EXISTS `stock` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `stock`;

-- ���� ��������� ��� ������� stock.blacklist
CREATE TABLE IF NOT EXISTS `blacklist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tender_Id` bigint(20) DEFAULT NULL,
  `lot_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_blacklist_application` (`tender_Id`),
  KEY `FK_blacklist_lot` (`lot_Id`),
  CONSTRAINT `FK_blacklist_application` FOREIGN KEY (`tender_Id`) REFERENCES `tender` (`id`),
  CONSTRAINT `FK_blacklist_lot` FOREIGN KEY (`lot_Id`) REFERENCES `lot` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ���� ������ ������� stock.blacklist: ~3 rows (��������������)
/*!40000 ALTER TABLE `blacklist` DISABLE KEYS */;
INSERT INTO `blacklist` (`id`, `tender_Id`, `lot_Id`) VALUES
	(1, 1, 3),
	(2, 3, 1),
	(3, 3, 3);
/*!40000 ALTER TABLE `blacklist` ENABLE KEYS */;

-- ���� ��������� ��� ������� stock.company
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

-- ���� ������ ������� stock.company: ~4 rows (��������������)
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`, `name`, `email`, `phone`, `reg_number`, `country`, `city`, `status`) VALUES
	(1, 'aaa', 'bbb', 'fff', 'df', 'bl', 'minsk', 'VERIFIED'),
	(2, 'evgen', 'evgen', 'evgen', 'evgen', 'evgen', 'evgen', 'VERIFIED'),
	(3, 'user', 'userMail', '12314', '443asd12', 'Bel', 'Mn', 'NOT_VERIFIED'),
	(10, 'user', 'we', '23', '23qwe', 'bel', 'mn', 'REVOKED');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;

-- ���� ��������� ��� ������� stock.deals
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ���� ������ ������� stock.deals: ~3 rows (��������������)
/*!40000 ALTER TABLE `deals` DISABLE KEYS */;
INSERT INTO `deals` (`id`, `lot_Id`, `tender_Id`, `deals_Time`) VALUES
	(1, 3, 3, '2017-07-10'),
	(2, 1, 1, '2017-07-09'),
	(3, 1, 1, '2017-08-24');
/*!40000 ALTER TABLE `deals` ENABLE KEYS */;

-- ���� ��������� ��� ������� stock.email
CREATE TABLE IF NOT EXISTS `email` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `to_email` varchar(100) DEFAULT NULL,
  `email_subject` varchar(100) DEFAULT NULL,
  `email_text` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ���� ������ ������� stock.email: ~5 rows (��������������)
/*!40000 ALTER TABLE `email` DISABLE KEYS */;
INSERT INTO `email` (`id`, `to_email`, `email_subject`, `email_text`) VALUES
	(2, 'asd@asd,com', 'sdfsdf', 'asfsdf'),
	(5, '@todo', 'subj', 'text'),
	(6, 'abc@mail.ru', 'Bingo!!!', '?? ? ??? ????????????? ??????? ??????????? ?????? ?? ?????? ???? ?1'),
	(7, 'abc@mail.ru', 'Bingo!!!', '?? ? ??? ????????????? ??????? ??????????? ?????? ?? ?????? ???? ?1'),
	(8, 'abc@mail.ru', '????? ?? ??????.', '? ?????????, ??? ????????????? ??????? ????????? ?? ?????? ???? ?3.');
/*!40000 ALTER TABLE `email` ENABLE KEYS */;

-- ���� ��������� ��� ������� stock.lot
CREATE TABLE IF NOT EXISTS `lot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) DEFAULT NULL,
  `poduct_Id` bigint(20) DEFAULT NULL,
  `min_price` decimal(10,2) DEFAULT NULL,
  `seller_Id` bigint(20) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `exp_date` date DEFAULT NULL,
  `volume_of_lot` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `saler` (`seller_Id`),
  KEY `product` (`poduct_Id`),
  CONSTRAINT `FK_lot_company` FOREIGN KEY (`seller_Id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_lot_product` FOREIGN KEY (`poduct_Id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ���� ������ ������� stock.lot: ~5 rows (��������������)
/*!40000 ALTER TABLE `lot` DISABLE KEYS */;
INSERT INTO `lot` (`id`, `description`, `poduct_Id`, `min_price`, `seller_Id`, `status`, `exp_date`, `volume_of_lot`) VALUES
	(1, 'test', 1, 10.00, 1, 'EXPIRED', '2017-08-23', 400),
	(3, 'апельсины', 111, 5.00, 1, 'IN_GAME', '2017-06-28', 120),
	(4, 'JAVATEST', 1, 110.00, 1, 'EXPIRED', '2017-09-20', 0),
	(5, 'test Add', 1, 10.00, 1, 'IN_GAME', '2017-08-23', 600),
	(11, 'бананы', 1, 10.00, 10, 'EXPIRED', '2017-08-25', 600);
/*!40000 ALTER TABLE `lot` ENABLE KEYS */;

-- ���� ��������� ��� ������� stock.predeal
CREATE TABLE IF NOT EXISTS `predeal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sellerHashCode` varchar(50) DEFAULT NULL,
  `customerHashCode` varchar(50) DEFAULT NULL,
  `tender_Id` bigint(20) DEFAULT NULL,
  `lot_Id` bigint(20) DEFAULT NULL,
  `sellerApprov` varchar(1) DEFAULT NULL,
  `custApprov` varchar(1) DEFAULT NULL,
  `dealDate` date DEFAULT NULL,
  `volume` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_predeal_tender` (`tender_Id`),
  KEY `FK_predeal_lot` (`lot_Id`),
  CONSTRAINT `FK_predeal_lot` FOREIGN KEY (`lot_Id`) REFERENCES `lot` (`id`),
  CONSTRAINT `FK_predeal_tender` FOREIGN KEY (`tender_Id`) REFERENCES `tender` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ���� ������ ������� stock.predeal: ~2 rows (��������������)
/*!40000 ALTER TABLE `predeal` DISABLE KEYS */;
INSERT INTO `predeal` (`id`, `sellerHashCode`, `customerHashCode`, `tender_Id`, `lot_Id`, `sellerApprov`, `custApprov`, `dealDate`, `volume`) VALUES
	(3, 'qwerty', 'qwerdfk', 1, 1, 'Y', 'Y', '2017-08-24', NULL),
	(5, 'bbb', 'cc', 11, 4, 'Y', 'Y', '2017-08-24', NULL);
/*!40000 ALTER TABLE `predeal` ENABLE KEYS */;

-- ���� ��������� ��� ������� stock.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `archive` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8;

-- ���� ������ ������� stock.product: ~7 rows (��������������)
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`id`, `name`, `description`, `archive`) VALUES
	(1, 'apple', 'product', 'Y'),
	(13, 'TEST_ARCHIVE', 'product', 'Y'),
	(111, 'кики', 'так себе', 'N'),
	(126, 'test', 'tse', 'Y'),
	(127, 'rt1222323', 'rt', 'N'),
	(131, 'TRUE', 'TRUE', 'N'),
	(132, 'Stock', 'StockPlatform', 'Y');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;

-- ���� ��������� ��� ������� stock.tender
CREATE TABLE IF NOT EXISTS `tender` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) DEFAULT NULL,
  `product_Id` bigint(20) DEFAULT NULL,
  `max_price` decimal(10,2) DEFAULT NULL,
  `customer_Id` bigint(20) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `exp_date` date DEFAULT NULL,
  `volume_of_tender` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_application_product` (`product_Id`),
  KEY `FK_application_company` (`customer_Id`),
  CONSTRAINT `FK_tender_company` FOREIGN KEY (`customer_Id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_tender_product` FOREIGN KEY (`product_Id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ���� ������ ������� stock.tender: ~5 rows (��������������)
/*!40000 ALTER TABLE `tender` DISABLE KEYS */;
INSERT INTO `tender` (`id`, `description`, `product_Id`, `max_price`, `customer_Id`, `status`, `exp_date`, `volume_of_tender`) VALUES
	(1, 'Javatest', 1, 4.00, 1, 'CANCELED', '2017-08-23', 500),
	(2, 'огурцы', 126, 3.00, 10, 'IN_GAME', '2017-11-14', 100),
	(3, 'картошка', 111, 2.00, 10, 'CANCELED', '2017-06-28', 16),
	(11, 'Javatest', 1, 4.00, 1, 'EXPIRED', '2017-07-10', 170),
	(12, 'lotCancel', 1, 4.00, 1, 'CANCELED', '2017-08-23', 666);
/*!40000 ALTER TABLE `tender` ENABLE KEYS */;

-- ���� ��������� ��� ������� stock.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `company_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_user_company` (`company_Id`),
  CONSTRAINT `FK_user_company` FOREIGN KEY (`company_Id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ���� ������ ������� stock.user: ~4 rows (��������������)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `login`, `password`, `company_Id`) VALUES
	(1, 'Admin', '1', 1),
	(2, 'user', '2', 10),
	(3, 'user2', '123', 10),
	(4, 'юзер3', '1', 2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- ���� ��������� ��� ������� stock.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` bigint(20) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  KEY `FK_user_role_user` (`user_id`),
  CONSTRAINT `FK_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ���� ������ ������� stock.user_role: ~4 rows (��������������)
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`user_id`, `role`) VALUES
	(1, 'ADMIN'),
	(1, 'USER'),
	(3, 'USER'),
	(4, 'USER');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
