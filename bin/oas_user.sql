SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mydb`;

-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`user` (
  `id` VARCHAR(50) NOT NULL ,
  `user_name` VARCHAR(100) NOT NULL ,
  `password` VARCHAR(50) NOT NULL ,
  `role_id` VARCHAR(50) NOT NULL ,
  `state` TINYINT NULL ,
  `insert_ts` TIMESTAMP NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`role`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`role` (
  `id` VARCHAR(50) NOT NULL ,
  `role_name` VARCHAR(100) NOT NULL ,
  `role_desc` VARCHAR(200) NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_user` (`id` ASC) ,
  CONSTRAINT `fk_user`
    FOREIGN KEY (`id` )
    REFERENCES `mydb`.`user` (`role_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`contact_details`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`contact_details` (
  `id` VARCHAR(50) NOT NULL ,
  `user_id` VARCHAR(50) NOT NULL ,
  `email` VARCHAR(50) NOT NULL ,
  `fax` VARCHAR(50) NULL ,
  `phone` VARCHAR(100) NULL ,
  `mobile` VARCHAR(50) NULL ,
  `city` VARCHAR(50) NOT NULL ,
  `street` VARCHAR(150) NOT NULL ,
  `house_no` VARCHAR(25) NOT NULL ,
  `zip` VARCHAR(25) NULL ,
  `floor` VARCHAR(50) NULL ,
  `country_code` VARCHAR(5) NULL ,
  `state` VARCHAR(25) NULL ,
  `other_info` VARCHAR(150) NULL ,
  `status` TINYINT NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_user` (`user_id` ASC) ,
  CONSTRAINT `fk_user`
    FOREIGN KEY (`user_id` )
    REFERENCES `mydb`.`user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`payment_info`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`payment_info` (
  `id` VARCHAR(50) NOT NULL ,
  `account_holder_name` VARCHAR(150) NOT NULL ,
  `account_number` VARCHAR(50) NOT NULL ,
  `bank_name` VARCHAR(100) NULL ,
  `bank_code` VARCHAR(50) NULL ,
  `bank_city` VARCHAR(50) NULL ,
  `user_id`  NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_user_payment` (`user_id` ASC) ,
  CONSTRAINT `fk_user_payment`
    FOREIGN KEY (`user_id` )
    REFERENCES `mydb`.`user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`advertisement_info`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`advertisement_info` (
  `id` VARCHAR(50) NOT NULL ,
  `title` VARCHAR(150) NULL ,
  `start_date` DATE NULL ,
  `end_date` DATE NULL ,
  `insert_ts` TIMESTAMP NULL ,
  `update_ts` TIMESTAMP NULL ,
  `status` TINYINT NULL ,
  `category_id` VARCHAR(50) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`payment_detail_info`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`payment_detail_info` (
  `id` VARCHAR(50) NOT NULL ,
  `user_id`  NULL ,
  `advertisement_id`  NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`rental_advertisement`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`rental_advertisement` (
  `id` VARCHAR(50) NOT NULL ,
  `description` TEXT NULL ,
  `surrounding_info` TEXT NULL ,
  `other_info`  NULL ,
  `rent`  NULL ,
  `additional cost`  NULL ,
  `deposit`  NULL ,
  `size`  NULL ,
  `construction_year`  NULL ,
  `availability`  NULL ,
  `floor` VARCHAR(45) NULL ,
  `lift_available`  NULL ,
  `furnished_apartment`  NULL ,
  `furnished_kitchen`  NULL ,
  `balcony`  NULL ,
  `central_heating`  NULL ,
  `laminated`  NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
