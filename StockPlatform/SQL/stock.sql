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

-- Дамп структуры для таблица stock.blacklist
CREATE TABLE IF NOT EXISTS `blacklist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenderId` bigint(20) DEFAULT NULL,
  `lotId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_blacklist_application` (`tenderId`),
  KEY `FK_blacklist_lot` (`lotId`),
  CONSTRAINT `FK_blacklist_application` FOREIGN KEY (`tenderId`) REFERENCES `tender` (`id`),
  CONSTRAINT `FK_blacklist_lot` FOREIGN KEY (`lotId`) REFERENCES `lot` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.blacklist: ~6 rows (приблизительно)
/*!40000 ALTER TABLE `blacklist` DISABLE KEYS */;
INSERT IGNORE INTO `blacklist` (`id`, `tenderId`, `lotId`) VALUES
	(1, 1, 1),
	(2, 11, 4);
/*!40000 ALTER TABLE `blacklist` ENABLE KEYS */;

-- Дамп структуры для таблица stock.company
CREATE TABLE IF NOT EXISTS `company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
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
INSERT IGNORE INTO `company` (`id`, `name`, `email`, `phone`, `reg_number`, `country`, `city`, `status`) VALUES
	(1, 'bigroi', 'abc@mail.ru', '123123', 'qwe123', 'Belarus', 'Minsk', 'DRAFT'),
	(10, 'agroBel', 'abc@mail.ru', '345345', 'qwerty', 'Belarus', 'Gomel', 'DRAFT');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;

-- Дамп структуры для таблица stock.deals
CREATE TABLE IF NOT EXISTS `deals` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lotId` bigint(20) DEFAULT NULL,
  `tenderId` bigint(20) DEFAULT NULL,
  `dealsTime` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_deals_lot` (`lotId`),
  KEY `FK_deals_tender` (`tenderId`),
  CONSTRAINT `FK_deals_lot` FOREIGN KEY (`lotId`) REFERENCES `lot` (`id`),
  CONSTRAINT `FK_deals_tender` FOREIGN KEY (`tenderId`) REFERENCES `tender` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.deals: ~1 rows (приблизительно)
/*!40000 ALTER TABLE `deals` DISABLE KEYS */;
INSERT IGNORE INTO `deals` (`id`, `lotId`, `tenderId`, `dealsTime`) VALUES
	(1, 1, 1, '2017-07-05'),
	(2, 3, 11, '2017-07-06');
/*!40000 ALTER TABLE `deals` ENABLE KEYS */;

-- Дамп структуры для таблица stock.lot
CREATE TABLE IF NOT EXISTS `lot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) DEFAULT NULL,
  `poductId` bigint(20) DEFAULT NULL,
  `min_price` decimal(10,2) DEFAULT NULL,
  `salerId` bigint(20) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `exp_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `saler` (`salerId`),
  KEY `product` (`poductId`),
  CONSTRAINT `FK_lot_company` FOREIGN KEY (`salerId`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_lot_product` FOREIGN KEY (`poductId`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.lot: ~4 rows (приблизительно)
/*!40000 ALTER TABLE `lot` DISABLE KEYS */;
INSERT IGNORE INTO `lot` (`id`, `description`, `poductId`, `min_price`, `salerId`, `status`, `exp_date`) VALUES
	(1, 'урожай 2017', 1, 2.00, 1, 'DRAFT', '2016-06-26'),
	(3, 'урожай 2017', 111, 5.00, 1, 'DRAFT', '2017-06-28'),
	(4, 'урожай 2017', 6, 110.00, 1, 'DRAFT', '2017-06-29');
/*!40000 ALTER TABLE `lot` ENABLE KEYS */;

-- Дамп структуры для таблица stock.predeal
CREATE TABLE IF NOT EXISTS `predeal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sellerHashCode` varchar(50) DEFAULT NULL,
  `customerHashCode` varchar(50) DEFAULT NULL,
  `tenderId` bigint(20) DEFAULT NULL,
  `lotId` bigint(20) DEFAULT NULL,
  `sallerApprov` varchar(50) NOT NULL,
  `custApprov` varchar(50) NOT NULL,
  `dealDate` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_predeal_tender` (`tenderId`),
  KEY `FK_predeal_lot` (`lotId`),
  CONSTRAINT `FK_predeal_lot` FOREIGN KEY (`lotId`) REFERENCES `lot` (`id`),
  CONSTRAINT `FK_predeal_tender` FOREIGN KEY (`tenderId`) REFERENCES `tender` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.predeal: ~1 rows (приблизительно)
/*!40000 ALTER TABLE `predeal` DISABLE KEYS */;
INSERT IGNORE INTO `predeal` (`id`, `sellerHashCode`, `customerHashCode`, `tenderId`, `lotId`, `sallerApprov`, `custApprov`, `dealDate`) VALUES
	(8, 'asdasdasd', 'adasdasdasd', 1, 1, 'Y', 'N', '2017-07-05');
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
INSERT IGNORE INTO `product` (`id`, `name`, `description`) VALUES
	(1, 'картошка', 'супер укрожай 2017'),
	(6, 'лук', 'супер укрожай 2017'),
	(111, 'помидоры', 'супер укрожай 2017');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;

-- Дамп структуры для таблица stock.tender
CREATE TABLE IF NOT EXISTS `tender` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) DEFAULT NULL,
  `productId` bigint(20) DEFAULT NULL,
  `max_price` decimal(10,2) DEFAULT NULL,
  `customerId` bigint(20) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `exp_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_application_product` (`productId`),
  KEY `FK_application_company` (`customerId`),
  CONSTRAINT `FK_tender_company` FOREIGN KEY (`customerId`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_tender_product` FOREIGN KEY (`productId`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.tender: ~3 rows (приблизительно)
/*!40000 ALTER TABLE `tender` DISABLE KEYS */;
INSERT IGNORE INTO `tender` (`id`, `description`, `productId`, `max_price`, `customerId`, `status`, `exp_date`) VALUES
	(1, 'куплю 10 тонн', 1, 4.00, 1, 'DRAFT', '2017-06-27'),
	(3, 'продам в розницу', 6, 2.00, 1, 'DRAFT', '2017-06-28'),
	(11, 'куплю недорого', 111, 4.00, 1, 'DRAFT', '2017-07-05');
/*!40000 ALTER TABLE `tender` ENABLE KEYS */;

-- Дамп структуры для таблица stock.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_user_company` (`companyId`),
  CONSTRAINT `FK_user_company` FOREIGN KEY (`companyId`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы stock.user: ~2 rows (приблизительно)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT IGNORE INTO `user` (`id`, `login`, `password`, `companyId`) VALUES
	(1, 'Admin', '1', 1),
	(2, 'Админ', '2', 10);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
