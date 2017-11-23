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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.blacklist: ~3 rows (приблизительно)
/*!40000 ALTER TABLE `blacklist` DISABLE KEYS */;
INSERT IGNORE INTO `blacklist` (`id`, `tender_Id`, `lot_Id`) VALUES
	(1, 1, 3),
	(2, 3, 1),
	(3, 3, 3);
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
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.company: ~12 rows (приблизительно)
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT IGNORE INTO `company` (`id`, `name`, `email`, `phone`, `reg_number`, `country`, `city`, `status`) VALUES
	(1, 'aaa', 'bbb', '09988776', 'df', 'bl', 'minsk', 'VERIFIED'),
	(2, 'evgen', 'evgen', 'evgen', 'evgen', 'evgen', 'evgen', 'VERIFIED'),
	(3, 'user', 'userMail', '12314', '443asd12', 'Bel', 'Mn', 'VERIFIED'),
	(10, 'user', 'we', '23', '23qwe', 'bel', 'mn', 'VERIFIED'),
	(11, 'asdasd', 'asdasdas', 'asdas', '123123', 'asdasd', 'asdasd', 'NOT_VERIFIED'),
	(12, 'asdasd', 'asdasdas', 'asdas', '123123', 'asdasd', 'asdasd', 'NOT_VERIFIED');
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.deals: ~3 rows (приблизительно)
/*!40000 ALTER TABLE `deals` DISABLE KEYS */;
INSERT IGNORE INTO `deals` (`id`, `lot_Id`, `tender_Id`, `deals_Time`) VALUES
	(1, 3, 3, '2017-07-10'),
	(2, 1, 1, '2017-07-09'),
	(3, 1, 1, '2017-08-24');
/*!40000 ALTER TABLE `deals` ENABLE KEYS */;

-- Дамп структуры для таблица stock.email
CREATE TABLE IF NOT EXISTS `email` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `to_email` varchar(100) DEFAULT NULL,
  `email_subject` varchar(100) DEFAULT NULL,
  `email_text` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.email: ~6 rows (приблизительно)
/*!40000 ALTER TABLE `email` DISABLE KEYS */;
INSERT IGNORE INTO `email` (`id`, `to_email`, `email_subject`, `email_text`) VALUES
	(2, 'asd@asd,com', 'sdfsdf', 'asfsdf'),
	(5, '@todo', 'subj', 'text'),
	(6, 'abc@mail.ru', 'Bingo!!!', '?? ? ??? ????????????? ??????? ??????????? ?????? ?? ?????? ???? ?1'),
	(7, 'abc@mail.ru', 'Bingo!!!', '?? ? ??? ????????????? ??????? ??????????? ?????? ?? ?????? ???? ?1'),
	(8, 'abc@mail.ru', '????? ?? ??????.', '? ?????????, ??? ????????????? ??????? ????????? ?? ?????? ???? ?3.'),
	(9, 'we', 'Ваш пароль сброшен.', 'Здравствуйте!\r\nПароль пользователя user2 был изменён.\r\nНовый пароль: .*kTaSpk .\r\nС уважением, администрация ресурса.');
/*!40000 ALTER TABLE `email` ENABLE KEYS */;

-- Дамп структуры для таблица stock.lot
CREATE TABLE IF NOT EXISTS `lot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) DEFAULT NULL,
  `product_Id` bigint(20) DEFAULT NULL,
  `min_price` decimal(10,2) DEFAULT NULL,
  `seller_Id` bigint(20) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `exp_date` date DEFAULT NULL,
  `volume` int(11) DEFAULT NULL,
  `min_Volume` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `saler` (`seller_Id`),
  KEY `product` (`product_Id`),
  CONSTRAINT `FK_lot_company` FOREIGN KEY (`seller_Id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_lot_product` FOREIGN KEY (`product_Id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.lot: ~7 rows (приблизительно)
/*!40000 ALTER TABLE `lot` DISABLE KEYS */;
INSERT IGNORE INTO `lot` (`id`, `description`, `product_Id`, `min_price`, `seller_Id`, `status`, `exp_date`, `volume`, `min_Volume`) VALUES
	(1, 'test', 1, 10.00, 1, 'CANCELED', '2017-08-23', 400, 1),
	(3, 'ааа', 111, 5.00, 1, 'CANCELED', '2017-06-28', 120, 1),
	(4, 'JAVATEST', 1, 110.00, 1, 'CANCELED', '2017-09-20', 0, 1),
	(5, 'test Add', 1, 10.00, 1, 'CANCELED', '2017-08-23', 600, 1),
	(11, 'ввв', 1, 10.00, 10, 'CANCELED', '2017-08-25', 600, 1),
	(15, '234234', 111, 11.00, 1, 'CANCELED', '2017-11-18', 123, 6667777),
	(16, '234234', 111, 11.00, 1, 'CANCELED', '2017-11-18', 1235, 111),
	(17, 'dsfdsfdsf', 111, 110.20, 1, 'DRAFT', '2017-11-23', 3432, 3424),
	(18, 'VIRA', 111, 30.33, 1, 'DRAFT', '2017-11-23', 3330, 3330);
/*!40000 ALTER TABLE `lot` ENABLE KEYS */;

-- Дамп структуры для таблица stock.predeal
CREATE TABLE IF NOT EXISTS `predeal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sellerHashCode` varchar(50) NOT NULL,
  `customerHashCode` varchar(50) NOT NULL,
  `tender_Id` bigint(20) NOT NULL,
  `lot_Id` bigint(20) NOT NULL,
  `sellerApprov` varchar(1) NOT NULL,
  `custApprov` varchar(1) NOT NULL,
  `dealDate` date NOT NULL,
  `volume` int(11) NOT NULL,
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
  `archive` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.product: ~8 rows (приблизительно)
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT IGNORE INTO `product` (`id`, `name`, `description`, `archive`) VALUES
	(1, 'apple', 'product', 'Y'),
	(13, 'TEST_ARCHIVE', 'product', 'Y'),
	(111, 'киви', 'вкусно', 'N'),
	(126, 'test', 'tse', 'Y'),
	(127, 'rt1222323', 'rt', 'N'),
	(131, 'TRUE1', 'TRUE', 'N'),
	(132, 'Stock', 'StockPlatform1', 'Y'),
	(133, '1', '1', 'N');
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
  `volume` int(11) DEFAULT NULL,
  `max_Volume` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_application_product` (`product_Id`),
  KEY `FK_application_company` (`customer_Id`),
  CONSTRAINT `FK_tender_company` FOREIGN KEY (`customer_Id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_tender_product` FOREIGN KEY (`product_Id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.tender: ~8 rows (приблизительно)
/*!40000 ALTER TABLE `tender` DISABLE KEYS */;
INSERT IGNORE INTO `tender` (`id`, `description`, `product_Id`, `max_price`, `customer_Id`, `status`, `exp_date`, `volume`, `max_Volume`) VALUES
	(1, 'Javatest', 1, 0.00, 1, 'CANCELED', '2017-08-23', 500, 777),
	(2, 'киви', 126, 3.00, 10, 'CANCELED', '2017-11-14', 100, 1),
	(3, 'бананы', 111, 2.00, 10, 'CANCELED', '2017-06-28', 16, 1),
	(11, 'Javatest', 1, 0.00, 1, 'CANCELED', '2017-07-10', 170, 34),
	(12, 'lotCancel', 1, 0.00, 1, 'CANCELED', '2017-08-23', 6663, 1),
	(13, 'sdsd', 111, 12.00, 1, 'CANCELED', '2017-11-20', 123, 1),
	(15, 'Test', 111, 0.00, 1, 'DRAFT', '2017-11-23', 10, 666);
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.user: ~9 rows (приблизительно)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT IGNORE INTO `user` (`id`, `login`, `password`, `company_Id`) VALUES
	(1, 'Admin', '1', 1),
	(2, 'user', '2', 10),
	(3, 'user2', '.*kTaSpk', 10),
	(4, 'юзер', '1', 2),
	(5, 'admin1w', '1', 11),
	(6, 'admin1w1', '1', 12);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Дамп структуры для таблица stock.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` bigint(20) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  KEY `FK_user_role_user` (`user_id`),
  CONSTRAINT `FK_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.user_role: ~8 rows (приблизительно)
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT IGNORE INTO `user_role` (`user_id`, `role`) VALUES
	(1, 'ROLE_ADMIN'),
	(1, 'ROLE_USER'),
	(3, 'ROLE_USER'),
	(4, 'ROLE_USER'),
	(2, 'ROLE_USER');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
