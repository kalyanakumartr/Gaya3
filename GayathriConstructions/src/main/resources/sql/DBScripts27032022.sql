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

-- Dumping data for table gayathri_constructions.inventory: ~0 rows (approximately)
DELETE FROM `inventory`;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;

-- Dumping structure for table gayathri_constructions.material
CREATE TABLE IF NOT EXISTS `material` (
  `materialId` varchar(50) NOT NULL,
  `materialName` varchar(50) NOT NULL,
  `numberCode` int(11) NOT NULL,
  `displayOrder` int(11) NOT NULL,
  `description` varchar(50) NOT NULL,
  `createdBy` varchar(50) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedBy` varchar(50) DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `status` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`materialId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table gayathri_constructions.material: ~0 rows (approximately)
DELETE FROM `material`;
/*!40000 ALTER TABLE `material` DISABLE KEYS */;
/*!40000 ALTER TABLE `material` ENABLE KEYS */;

-- Dumping structure for table gayathri_constructions.rental
CREATE TABLE IF NOT EXISTS `rental` (
  `rentalId` varchar(50) NOT NULL,
  `personName` varchar(50) NOT NULL,
  `address` varchar(500) NOT NULL,
  `mobileNo` varchar(15) NOT NULL DEFAULT '0',
  `alternateNo` varchar(15) DEFAULT NULL,
  `advanceAmount` double NOT NULL DEFAULT '0',
  `rentedDate` datetime NOT NULL,
  `rentalStatus` varchar(50) NOT NULL,
  `createdBy` varchar(50) NOT NULL,
  `createdDate` datetime NOT NULL,
  PRIMARY KEY (`rentalId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table gayathri_constructions.rental: ~0 rows (approximately)
DELETE FROM `rental`;
/*!40000 ALTER TABLE `rental` DISABLE KEYS */;
/*!40000 ALTER TABLE `rental` ENABLE KEYS */;

-- Dumping structure for table gayathri_constructions.rental_invoice
CREATE TABLE IF NOT EXISTS `rental_invoice` (
  `invoiceId` varchar(50) NOT NULL,
  `masterInvoiceId` varchar(50) NOT NULL,
  `rentalId` varchar(50) NOT NULL,
  `invoiceDate` datetime NOT NULL,
  `startDate` datetime NOT NULL,
  `endDate` datetime DEFAULT NULL,
  `invoiceAmount` double NOT NULL DEFAULT '0',
  `invoiceStatus` varchar(50) NOT NULL DEFAULT '0',
  `paymentAmount` double NOT NULL DEFAULT '0',
  `paymentDate` datetime DEFAULT NULL,
  PRIMARY KEY (`invoiceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table gayathri_constructions.rental_invoice: ~0 rows (approximately)
DELETE FROM `rental_invoice`;
/*!40000 ALTER TABLE `rental_invoice` DISABLE KEYS */;
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

-- Dumping data for table gayathri_constructions.rental_item: ~0 rows (approximately)
DELETE FROM `rental_item`;
/*!40000 ALTER TABLE `rental_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `rental_item` ENABLE KEYS */;

-- Dumping structure for table gayathri_constructions.rental_item_history
CREATE TABLE IF NOT EXISTS `rental_item_history` (
  `autoId` bigint(20) NOT NULL AUTO_INCREMENT,
  `itemId` varchar(50) NOT NULL,
  `returnQuantity` int(11) NOT NULL,
  `returnDate` datetime NOT NULL,
  PRIMARY KEY (`autoId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table gayathri_constructions.rental_item_history: ~0 rows (approximately)
DELETE FROM `rental_item_history`;
/*!40000 ALTER TABLE `rental_item_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `rental_item_history` ENABLE KEYS */;

-- Dumping structure for table gayathri_constructions.users
CREATE TABLE IF NOT EXISTS `users` (
  `employeeId` varchar(50) NOT NULL,
  `userName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
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
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
