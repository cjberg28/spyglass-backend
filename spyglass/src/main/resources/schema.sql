CREATE TABLE `spyglass`.`goals` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NULL,
  `image_src` VARCHAR(255) NULL,
  `target_date` DATE NOT NULL,
  `target_amount` FLOAT NOT NULL,
  `current_amount` FLOAT NOT NULL,
  PRIMARY KEY (`id`));
  
  CREATE TABLE `spyglass`.`users` (
  `email` VARCHAR(255) NOT NULL,
  `first_name` VARCHAR(255) NULL,
  `last_name` VARCHAR(255) NULL,
  `date_of_birth` DATE NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`email`));