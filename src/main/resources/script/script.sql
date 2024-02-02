-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema paymybuddy
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema paymybuddy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `paymybuddy` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `paymybuddy` ;

-- -----------------------------------------------------
-- Table `paymybuddy`.`person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`person` (
    `email` VARCHAR(255) NOT NULL,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `role` VARCHAR(255) NOT NULL DEFAULT 'USER',
    PRIMARY KEY (`email`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `paymybuddy`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`account` (
                                                      `account_id` INT NOT NULL AUTO_INCREMENT,
                                                      `finances` FLOAT NOT NULL,
                                                      `email` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`account_id`),
    UNIQUE INDEX `email` (`email` ASC) VISIBLE,
    CONSTRAINT `account_ibfk_1`
    FOREIGN KEY (`email`)
    REFERENCES `paymybuddy`.`person` (`email`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 40002
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `paymybuddy`.`account_bank`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`account_bank` (
                                                           `account` INT NOT NULL,
                                                           `balance` FLOAT NULL DEFAULT NULL,
                                                           PRIMARY KEY (`account`),
    CONSTRAINT `account_bank_ibfk_1`
    FOREIGN KEY (`account`)
    REFERENCES `paymybuddy`.`account` (`account_id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `paymybuddy`.`listoffriend`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`listoffriend` (
    `person_id` VARCHAR(255) NOT NULL,
    `friend_id` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`person_id`, `friend_id`),
    INDEX `friend_id` (`friend_id` ASC) VISIBLE,
    CONSTRAINT `listoffriend_ibfk_1`
    FOREIGN KEY (`person_id`)
    REFERENCES `paymybuddy`.`person` (`email`),
    CONSTRAINT `listoffriend_ibfk_2`
    FOREIGN KEY (`friend_id`)
    REFERENCES `paymybuddy`.`person` (`email`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `paymybuddy`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`transaction` (
                                                          `transaction_id` INT NOT NULL AUTO_INCREMENT,
                                                          `account_source_id` INT NULL DEFAULT NULL,
                                                          `account_recipient_id` INT NULL DEFAULT NULL,
                                                          `amount` FLOAT NULL DEFAULT NULL,
                                                          `description` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`transaction_id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 20
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

USE `paymybuddy`;

DELIMITER $$
USE `paymybuddy`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `paymybuddy`.`CreateAccountAfterInsert`
AFTER INSERT ON `paymybuddy`.`person`
FOR EACH ROW
BEGIN
INSERT INTO Account (email, finances) VALUES (NEW.email, 0.0);
END$$

USE `paymybuddy`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `paymybuddy`.`AccountInsertTrigger`
AFTER INSERT ON `paymybuddy`.`account`
FOR EACH ROW
BEGIN
INSERT INTO account_bank (account, balance)
VALUES (NEW.account_id, 0.0);
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
