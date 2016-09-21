create schema user_catalog DEFAULT CHARACTER SET utf8;
CREATE USER 'dude'@'%' IDENTIFIED BY 'dude';
GRANT ALL PRIVILEGES ON *.* TO 'dude'@'%';
CREATE USER 'dude'@'localhost' IDENTIFIED BY 'dude';
GRANT ALL PRIVILEGES ON *.* TO 'dude'@'localhost';

CREATE TABLE IF NOT EXISTS `user_catalog`.`users` (
			`id` BIGINT NOT NULL AUTO_INCREMENT,
			`username` VARCHAR(100) NOT NULL,
			`first_name` VARCHAR(50) NOT NULL,
			`last_name` VARCHAR(50) NOT NULL,
			PRIMARY KEY (`id`),
			UNIQUE INDEX `username_UNIQUE` (`username` ASC))
			ENGINE = InnoDB;