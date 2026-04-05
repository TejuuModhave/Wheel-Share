

CREATE DATABASE IF NOT EXISTS businessproject;
USE businessproject;

-- 1. Create User table (handles both Owners and Customers)
CREATE TABLE IF NOT EXISTS `user` (
  `u_id` int NOT NULL AUTO_INCREMENT,
  `uemail` varchar(255) DEFAULT NULL,
  `uname` varchar(255) DEFAULT NULL,
  `unumber` bigint DEFAULT NULL,
  `upassword` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. Create Admin table (Optional: for platform management)
CREATE TABLE IF NOT EXISTS `admin` (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `admin_email` varchar(255) DEFAULT NULL,
  `admin_name` varchar(255) DEFAULT NULL,
  `admin_number` varchar(255) DEFAULT NULL,
  `admin_password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. Create Product table (Represents Equipment)
CREATE TABLE IF NOT EXISTS `product` (
  `pid` int NOT NULL AUTO_INCREMENT,
  `pdescription` varchar(255) DEFAULT NULL,
  `pname` varchar(255) DEFAULT NULL,
  `pprice` double NOT NULL, -- Price per day
  `owner_id` int DEFAULT NULL, -- NEW FIELD: Links to User who listed it
  PRIMARY KEY (`pid`),
  KEY `FK_product_owner` (`owner_id`),
  CONSTRAINT `FK_product_owner` FOREIGN KEY (`owner_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. Create Orders table (Represents Rental Requests)
CREATE TABLE IF NOT EXISTS `orders` (
  `o_id` int NOT NULL AUTO_INCREMENT,
  `o_name` varchar(255) DEFAULT NULL,
  `o_price` double NOT NULL,
  `o_quantity` int NOT NULL, -- Number of days requested
  `order_date` datetime(6) DEFAULT NULL,
  `total_ammout` double NOT NULL,
  `user_u_id` int DEFAULT NULL, -- Customer who requested it
  `status` varchar(50) DEFAULT 'PENDING', -- NEW FIELD: PENDING, APPROVED, REJECTED
  PRIMARY KEY (`o_id`),
  KEY `FK_orders_user` (`user_u_id`),
  CONSTRAINT `FK_orders_user` FOREIGN KEY (`user_u_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================
-- Insert Sample Data
-- ==========================================

-- Insert User 1 (Equipment Owner: Farmer John)
INSERT INTO `user` (`uemail`, `uname`, `unumber`, `upassword`) 
VALUES ('john@farm.com', 'Farmer John', 9876543210, 'john123');
SET @owner1_id = LAST_INSERT_ID();

-- Insert User 2 (Customer/Renter: Builder Bob)
INSERT INTO `user` (`uemail`, `uname`, `unumber`, `upassword`) 
VALUES ('bob@build.com', 'Builder Bob', 8765432109, 'bob123');
SET @customer1_id = LAST_INSERT_ID();

-- Insert Admin
INSERT INTO `admin` (`admin_email`, `admin_name`, `admin_number`, `admin_password`) 
VALUES ('admin@wheelshare.com', 'Super Admin', '1234567890', 'admin123');

-- Insert Equipment listed by Farmer John
INSERT INTO `product` (`pname`, `pdescription`, `pprice`, `owner_id`) 
VALUES 
('Mahindra Tractor 575 DI', 'Heavy duty 45HP tractor, ideal for farming.', 1500.0, @owner1_id),
('JCB Excavator 3DX', 'Backhoe loader for digging and earth moving.', 3500.0, @owner1_id);

-- Builder Bob requests the Tractor for 3 days
INSERT INTO `orders` (`o_name`, `o_price`, `o_quantity`, `order_date`, `total_ammout`, `user_u_id`, `status`) 
VALUES 
('Mahindra Tractor 575 DI', 1500.0, 3, NOW(), 4500.0, @customer1_id, 'PENDING');

COMMIT;
