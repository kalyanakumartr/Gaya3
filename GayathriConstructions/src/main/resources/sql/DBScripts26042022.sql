-- --------------------------------------------------------
-- Host:                         localhost
-- Server version:               8.0.18 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for gayathri_constructions
CREATE DATABASE IF NOT EXISTS `gayathri_constructions` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `gayathri_constructions`;

-- Dumping structure for table gayathri_constructions.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `customerId` varchar(50) NOT NULL,
  `customerName` varchar(50) NOT NULL,
  `address` varchar(500) NOT NULL,
  `mobileNo` varchar(15) NOT NULL DEFAULT '0',
  `alternateNo` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table gayathri_constructions.customer: ~2 rows (approximately)
DELETE FROM `customer`;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`customerId`, `customerName`, `address`, `mobileNo`, `alternateNo`) VALUES
	('CUST1000', 'TamilSelvan', 'Kovilambakkam', '9790238428', '2342342344'),
	('CUST1001', 'Ananth', 'Aadaambakkama', '9671034234', '9897812312');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;

-- Dumping structure for table gayathri_constructions.inventory
CREATE TABLE IF NOT EXISTS `inventory` (
  `inventoryId` varchar(50) NOT NULL,
  `materialId` varchar(50) NOT NULL,
  `materialCost` double NOT NULL DEFAULT '1',
  `rentalItemCost` double NOT NULL DEFAULT '1',
  `quantity` int(11) NOT NULL DEFAULT '0',
  `availableQuantity` int(11) NOT NULL DEFAULT '0',
  `rentedQuantity` int(11) NOT NULL DEFAULT '0',
  `brokenQuantity` int(11) NOT NULL DEFAULT '0',
  `createdBy` varchar(50) NOT NULL,
  `createdDate` datetime NOT NULL,
  `modifiedBy` varchar(50) NOT NULL,
  `modifiedDate` datetime NOT NULL,
  `status` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`inventoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table gayathri_constructions.inventory: ~2 rows (approximately)
DELETE FROM `inventory`;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` (`inventoryId`, `materialId`, `materialCost`, `rentalItemCost`, `quantity`, `availableQuantity`, `rentedQuantity`, `brokenQuantity`, `createdBy`, `createdDate`, `modifiedBy`, `modifiedDate`, `status`) VALUES
	('IN1000', 'MAT1000', 1, 1, 100, 50, 40, 10, 'System', '2022-04-08 20:10:50', 'System', '2022-04-08 20:11:02', b'1'),
	('IN1001', 'MAT1001', 1, 1, 200, 124, 56, 20, 'System', '2022-04-08 20:10:50', 'System', '2022-04-08 20:11:02', b'1');
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;

-- Dumping structure for table gayathri_constructions.material
CREATE TABLE IF NOT EXISTS `material` (
  `materialId` varchar(50) NOT NULL,
  `materialName` varchar(50) NOT NULL,
  `numberCode` varchar(50) NOT NULL DEFAULT '',
  `displayOrder` int(11) NOT NULL,
  `description` varchar(50) NOT NULL,
  `createdBy` varchar(50) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedBy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `status` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`materialId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table gayathri_constructions.material: ~2 rows (approximately)
DELETE FROM `material`;
/*!40000 ALTER TABLE `material` DISABLE KEYS */;
INSERT INTO `material` (`materialId`, `materialName`, `numberCode`, `displayOrder`, `description`, `createdBy`, `createdDate`, `modifiedBy`, `modifiedDate`, `status`) VALUES
	('MAT1000', 'Chair', 'C123', 100, 'Chairs', NULL, '2022-04-03 08:55:52', NULL, '2022-04-03 08:55:50', b'1'),
	('MAT1001', 'Driller ', 'D100', 1, 'Drilller Maching for Walls', 'System', '2022-04-03 08:55:52', NULL, '2022-04-03 08:55:50', b'1');
/*!40000 ALTER TABLE `material` ENABLE KEYS */;

-- Dumping structure for table gayathri_constructions.paymenthistory
CREATE TABLE IF NOT EXISTS `paymenthistory` (
  `autoId` int(11) NOT NULL AUTO_INCREMENT,
  `invoiceId` varchar(50) DEFAULT NULL,
  `rentalId` varchar(50) DEFAULT NULL,
  `paymentAmount` double DEFAULT NULL,
  `paymentDate` datetime DEFAULT NULL,
  PRIMARY KEY (`autoId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table gayathri_constructions.paymenthistory: ~0 rows (approximately)
DELETE FROM `paymenthistory`;
/*!40000 ALTER TABLE `paymenthistory` DISABLE KEYS */;
INSERT INTO `paymenthistory` (`autoId`, `invoiceId`, `rentalId`, `paymentAmount`, `paymentDate`) VALUES
	(1, 'IVR1000', 'R1000', 500, '2022-04-22 11:00:29'),
	(2, 'IVR1000', 'R1000', 25, '2022-04-26 14:00:27'),
	(3, 'IVR1001', 'R1000', 175, '2022-04-26 14:00:27');
/*!40000 ALTER TABLE `paymenthistory` ENABLE KEYS */;

-- Dumping structure for table gayathri_constructions.rental
CREATE TABLE IF NOT EXISTS `rental` (
  `rentalId` varchar(50) NOT NULL,
  `customerId` varchar(50) NOT NULL,
  `advanceAmount` double NOT NULL DEFAULT '0',
  `rentedDate` datetime NOT NULL,
  `rentalStatus` varchar(50) NOT NULL,
  `createdBy` varchar(50) NOT NULL,
  `createdDate` datetime NOT NULL,
  PRIMARY KEY (`rentalId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table gayathri_constructions.rental: ~2 rows (approximately)
DELETE FROM `rental`;
/*!40000 ALTER TABLE `rental` DISABLE KEYS */;
INSERT INTO `rental` (`rentalId`, `customerId`, `advanceAmount`, `rentedDate`, `rentalStatus`, `createdBy`, `createdDate`) VALUES
	('R1000', 'CUST1000', 5000, '2022-04-08 21:53:43', 'Rented', 'System', '2022-04-08 21:53:49'),
	('R1001', 'CUST1001', 0, '2022-04-08 21:53:43', 'Rented', 'System', '2022-04-08 21:53:49');
/*!40000 ALTER TABLE `rental` ENABLE KEYS */;

-- Dumping structure for table gayathri_constructions.rental_invoice
CREATE TABLE IF NOT EXISTS `rental_invoice` (
  `invoiceId` varchar(50) NOT NULL,
  `invoiceNo` varchar(50) DEFAULT NULL,
  `masterInvoiceId` varchar(50) NOT NULL,
  `rentalId` varchar(50) NOT NULL,
  `invoiceDate` datetime DEFAULT NULL,
  `startDate` datetime NOT NULL,
  `endDate` datetime DEFAULT NULL,
  `invoiceAmount` double NOT NULL DEFAULT '0',
  `invoiceStatus` varchar(50) NOT NULL DEFAULT '0',
  `paymentAmount` double NOT NULL DEFAULT '0',
  `calculatedDate` datetime DEFAULT NULL,
  `active` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`invoiceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table gayathri_constructions.rental_invoice: ~2 rows (approximately)
DELETE FROM `rental_invoice`;
/*!40000 ALTER TABLE `rental_invoice` DISABLE KEYS */;
INSERT INTO `rental_invoice` (`invoiceId`, `invoiceNo`, `masterInvoiceId`, `rentalId`, `invoiceDate`, `startDate`, `endDate`, `invoiceAmount`, `invoiceStatus`, `paymentAmount`, `calculatedDate`, `active`) VALUES
	('IVR1000', 'INVN123', 'IVR1000', 'R1000', NULL, '2022-04-20 00:00:00', NULL, 525, 'Pending', 500, '2022-04-22 19:07:00', b'0'),
	('IVR1001', NULL, 'IVR1000', 'R1000', NULL, '2022-04-20 00:00:00', NULL, 675.5, 'Pending', 200, '2022-04-26 12:34:00', b'1');
/*!40000 ALTER TABLE `rental_invoice` ENABLE KEYS */;

-- Dumping structure for table gayathri_constructions.rental_item
CREATE TABLE IF NOT EXISTS `rental_item` (
  `itemId` varchar(50) NOT NULL DEFAULT 'AUTO_INCREMENT',
  `inventoryId` varchar(50) NOT NULL DEFAULT '0',
  `rentalId` varchar(50) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT '0',
  `balanceQuantity` int(11) NOT NULL DEFAULT '0',
  `price` double NOT NULL DEFAULT '0',
  `totalCost` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`itemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table gayathri_constructions.rental_item: ~1 rows (approximately)
DELETE FROM `rental_item`;
/*!40000 ALTER TABLE `rental_item` DISABLE KEYS */;
INSERT INTO `rental_item` (`itemId`, `inventoryId`, `rentalId`, `quantity`, `balanceQuantity`, `price`, `totalCost`) VALUES
	('ITM1000', 'IN1000', 'R1000', 10, 0, 2.5, 25),
	('ITM1001', 'IN1001', 'R1000', 20, 0, 7.5, 150);
/*!40000 ALTER TABLE `rental_item` ENABLE KEYS */;

-- Dumping structure for table gayathri_constructions.rental_item_history
CREATE TABLE IF NOT EXISTS `rental_item_history` (
  `autoId` bigint(20) NOT NULL AUTO_INCREMENT,
  `itemId` varchar(50) NOT NULL,
  `returnQuantity` int(11) NOT NULL,
  `returnDate` datetime NOT NULL,
  PRIMARY KEY (`autoId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table gayathri_constructions.rental_item_history: ~0 rows (approximately)
DELETE FROM `rental_item_history`;
/*!40000 ALTER TABLE `rental_item_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `rental_item_history` ENABLE KEYS */;

-- Dumping structure for table gayathri_constructions.users
CREATE TABLE IF NOT EXISTS `users` (
  `employeeId` varchar(50) NOT NULL,
  `userName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `emailId` varchar(50) NOT NULL,
  `mobileNo` varchar(50) NOT NULL,
  `createdBy` varchar(50) NOT NULL,
  `createdDate` datetime NOT NULL,
  `modifiedBy` varchar(50) NOT NULL,
  `modifiedDate` datetime NOT NULL,
  `status` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table gayathri_constructions.users: ~0 rows (approximately)
DELETE FROM `users`;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`employeeId`, `userName`, `lastName`, `password`, `emailId`, `mobileNo`, `createdBy`, `createdDate`, `modifiedBy`, `modifiedDate`, `status`) VALUES
	('GC0001', 'Murugan', 'S', '$2a$12$WmqGtGCwEOU9TLEFo83ADOfVB0ycdqbZ8DLeSDHeKljqIMtM2sRI.', 'tamils1978@gmail.com', '9790756096', 'System', '2022-04-02 10:58:50', 'System', '2022-04-02 10:58:52', b'1'),
	('System', 'System', 'User', 'Test', 'system@gayathriconstructions.com', '1234567890', 'System', '2022-04-02 10:58:50', 'System', '2022-04-02 10:58:52', b'1');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
