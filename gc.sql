-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema gc
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema gc
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `gc` DEFAULT CHARACTER SET utf8 ;
USE `gc` ;

-- -----------------------------------------------------
-- Table `gc`.`gift_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gc`.`gift_certificate` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(200) NOT NULL,
  `price` FLOAT UNSIGNED NOT NULL,
  `duration` INT NOT NULL,
  `create_date` DATETIME NOT NULL,
  `last_update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 162
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `gc`.`tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gc`.`tags` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 162
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `gc`.`gc_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gc`.`gc_tag` (
  `gc_id` INT UNSIGNED NOT NULL,
  `tag_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`gc_id`, `tag_id`),
  INDEX `fk_tag_has_gift_certificate_gift_certificate1_idx` (`gc_id` ASC) VISIBLE,
  INDEX `fk_tag_has_gift_certificate_tag_idx` (`tag_id` ASC) VISIBLE,
  CONSTRAINT `fk_tag_has_gift_certificate_gift_certificate1`
    FOREIGN KEY (`gc_id`)
    REFERENCES `gc`.`gift_certificate` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_tag_has_gift_certificate_tag`
    FOREIGN KEY (`tag_id`)
    REFERENCES `gc`.`tags` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `gc`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gc`.`users` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `gc`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gc`.`orders` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL,
  `cost` FLOAT NOT NULL,
  `create_date` DATETIME NOT NULL,
  `cert_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_orders_gift_certificate1_idx` (`cert_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `gc`.`users` (`id`),
  CONSTRAINT `fk_orders_gift_certificate1`
    FOREIGN KEY (`cert_id`)
    REFERENCES `gc`.`gift_certificate` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
