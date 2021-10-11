-- MySQL Script generated by MySQL Workbench
-- Mon 11 Oct 2021 07:29:40 AM -03
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema wlist
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema wlist
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `wlist` ;
USE `wlist` ;

-- -----------------------------------------------------
-- Table `wlist`.`USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wlist`.`USER` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'User table identifier',
  `USERNAME` VARCHAR(100) NOT NULL COMMENT 'Username used to log into the app',
  `EMAIL` VARCHAR(255) NULL COMMENT 'User email address',
  `PASSWORD` VARCHAR(20) NOT NULL COMMENT 'User password (must be stored encrypted)',
  `FIRST_NAME` VARCHAR(255) NOT NULL COMMENT 'User first name',
  `LAST_NAME` VARCHAR(255) NOT NULL COMMENT 'User last name',
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
COMMENT = 'User associated to the wish list.';


-- -----------------------------------------------------
-- Table `wlist`.`WISH_LIST`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wlist`.`WISH_LIST` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'Wish list identifier',
  `NAME` VARCHAR(255) NOT NULL COMMENT 'Wish list short name',
  `DESCRIPTION` VARCHAR(4000) NULL COMMENT 'Wish list description',
  `PUBLIC` TINYINT NOT NULL DEFAULT 1 COMMENT 'Indicator that this wishlist is public or private. Allowed values are Y-Yes or N-No.',
  `DATE_CREATED` DATETIME NOT NULL COMMENT 'The Wish list creation date',
  `DATE_MODIFIED` DATETIME NULL COMMENT 'The Wish list change date',
  `USER_ID` BIGINT(20) NOT NULL COMMENT 'The wish list owner ID',
  `TYPE` CHAR(1) NOT NULL DEFAULT 'P' COMMENT 'Type Allowed values are T - temporary, like supermarket, or P - persistent',
  `OPENED` TINYINT NOT NULL DEFAULT 1 COMMENT 'Indicator if the list is open or closed. Allowed values are Y-Yes or N-No.',
  PRIMARY KEY (`ID`, `USER_ID`),
  INDEX `fk_WISH_LIST_USER_idx` (`USER_ID` ASC) VISIBLE,
  CONSTRAINT `fk_WISH_LIST_USER`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `wlist`.`USER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Table used to store users wish lists';


-- -----------------------------------------------------
-- Table `wlist`.`ITEM`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wlist`.`ITEM` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'Wish list item ID',
  `NAME` VARCHAR(100) NOT NULL COMMENT 'Wish list item name',
  `URL` VARCHAR(300) NULL COMMENT 'Wish list item URL',
  `DATE_CREATED` DATETIME NOT NULL COMMENT 'Wish list item creation date',
  `CURRENT_PRICE` DECIMAL NOT NULL COMMENT 'Wish list item current price',
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
COMMENT = 'Wish list items';


-- -----------------------------------------------------
-- Table `wlist`.`ROLE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wlist`.`ROLE` (
  `NAME` VARCHAR(20) NOT NULL COMMENT 'Role name',
  PRIMARY KEY (`NAME`))
ENGINE = InnoDB
COMMENT = 'User role';


-- -----------------------------------------------------
-- Table `wlist`.`USER_ROLE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wlist`.`USER_ROLE` (
  `USER_ID` BIGINT(20) NOT NULL,
  `ROLE_NAME` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`USER_ID`, `ROLE_NAME`),
  INDEX `fk_USER_has_ROLE_ROLE1_idx` (`ROLE_NAME` ASC) VISIBLE,
  INDEX `fk_USER_has_ROLE_USER1_idx` (`USER_ID` ASC) VISIBLE,
  CONSTRAINT `fk_USER_has_ROLE_USER1`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `wlist`.`USER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_has_ROLE_ROLE1`
    FOREIGN KEY (`ROLE_NAME`)
    REFERENCES `wlist`.`ROLE` (`NAME`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `wlist`.`WISH_LIST_ITEM`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wlist`.`WISH_LIST_ITEM` (
  `WISH_LIST_ID` BIGINT(20) NOT NULL COMMENT 'Wish list ID',
  `ITEM_ID` BIGINT(20) NOT NULL COMMENT 'Item ID',
  PRIMARY KEY (`WISH_LIST_ID`, `ITEM_ID`),
  INDEX `fk_WISH_LIST_has_ITEM_ITEM1_idx` (`ITEM_ID` ASC) VISIBLE,
  INDEX `fk_WISH_LIST_has_ITEM_WISH_LIST1_idx` (`WISH_LIST_ID` ASC) VISIBLE,
  CONSTRAINT `fk_WISH_LIST_has_ITEM_WISH_LIST1`
    FOREIGN KEY (`WISH_LIST_ID`)
    REFERENCES `wlist`.`WISH_LIST` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_WISH_LIST_has_ITEM_ITEM1`
    FOREIGN KEY (`ITEM_ID`)
    REFERENCES `wlist`.`ITEM` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Joint table of wish list items';


-- -----------------------------------------------------
-- Table `wlist`.`ITEM_HISTORY`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wlist`.`ITEM_HISTORY` (
  `DATE` DATETIME NOT NULL COMMENT 'History date ',
  `PRICE` DECIMAL NOT NULL COMMENT 'History price',
  `ITEM_ID` BIGINT(20) NOT NULL COMMENT 'Item identifier',
  PRIMARY KEY (`ITEM_ID`),
  CONSTRAINT `fk_ITEM_HISTORY_ITEM1`
    FOREIGN KEY (`ITEM_ID`)
    REFERENCES `wlist`.`ITEM` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Item price history';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
