-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: csj
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userID` varchar(45) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `phone` varchar(60) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `employDate` date DEFAULT NULL,
  `resignDate` date DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `active` tinyint DEFAULT NULL,
  `imageName` varchar(200) DEFAULT NULL,
  `accountHistory` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (2,'staff-0001','admin','1000:01e117262ba0c4d6db120114b1a2a160:01c457d5cebed753b43c372780c7cc40d2b26f0d71d22e21770303c4a3c95d7d4a009d649b9316c37562f86dec4ee7b865dc7a395a1e95a07ea3800b7487ba56','aung',20,'male','097789889','Myeik','admin',300000,'2022-11-21','2022-11-21','working',1,'3c045d0e-b5ed-4312-aa25-74892e8be396.jpg','createdBy-null@2022-11-21,modifiedBy-null@2022-11-21,modifiedBy-staff-0001@2022-11-23,modifiedBy-staff-0003@2022-11-23'),(3,'staff-0002','cashier','1000:28ecde9cb16c85330228b33bc4a67a92:3940604f9109abff46d51d830ae8c4d106db786cd942aeb7ea1d93db73462b249d18e24ab66307a863bd8db52c4d99f643941901212ff4222b871aa61779b1e3','ei',22,'female','0925','Taunggyi','cashier',3000000,'2022-11-21','2022-11-21','working',0,'307cd420-639b-4ffc-9d33-aba3d4b8c45a.jpg','createdBy-null@2022-11-21,modifiedBy-null@2022-11-21,deletedBy-staff-0003@2022-11-23'),(4,'staff-0003','admin3','1000:66b8e7bee30682d09bfc539fb126c895:af722803c7801b1c36acc68b3e1e572d7bbd97b9c20512002c6e4e11c815f69b7fa9d6e7e4c75e7de0ea5cbff8b03ec0b272f21d837150cf79d6022545cb43bc','cow',45,'male','097823341','Thar Kay ta','admin',30000,'2022-11-23','2022-11-23','resign',1,'2cabd61c-160b-4482-adb5-5271a4e991d1.jpg','createdBy-staff-0001@2022-11-22,modifiedBy-staff-0001@2022-11-22,modifiedBy-staff-0001@2022-11-22,modifiedBy-staff-0003@2022-11-23'),(5,'staff-0004','cashier2','1000:cbe02cdf42cec28dd41fa0346d1ac218:a543299693b33f19db7351922dd2b36f3cf71208f9309cc768d41f1b4170bf7a10926b04993d0981084de674e8946bb3af6f0a663639d650d9afe1143ca62cb0','dazai',56,'male','092046611','Kalaw','cashier',200000,'2022-11-23','2022-11-23','working',1,'4dfa584e-37f4-46d2-b435-ef660a409c53.jpg','createdBy-staff-0001@2022-11-23');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-05 18:29:02
