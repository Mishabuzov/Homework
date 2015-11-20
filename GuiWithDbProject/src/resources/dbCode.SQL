CREATE SCHEMA `gui` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `gui`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `parent_id` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

ALTER TABLE `gui`.`category`
ADD INDEX `FK_parent_idx` (`parent_id` ASC);
ALTER TABLE `gui`.`category`
ADD CONSTRAINT `FK_parent`
  FOREIGN KEY (`parent_id`)
  REFERENCES `gui`.`category` (`id`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

  CREATE TABLE `gui`.`goods` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` DOUBLE NOT NULL,
  `weight` DOUBLE NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  INDEX `FK_categoryRef_idx` (`category_id` ASC),
  CONSTRAINT `FK_categoryRef`
    FOREIGN KEY (`category_id`)
    REFERENCES `gui`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

ALTER TABLE gui.goods ADD CHECK (price >= 0);
ALTER TABLE gui.goods ADD CHECK (weight >= 0);