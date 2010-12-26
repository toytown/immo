ALTER TABLE user CHANGE COLUMN user_id user_id INT NOT NULL;

ALTER TABLE user DROP PRIMARY KEY;

ALTER TABLE user DROP INDEX UNQ_;

DROP TABLE user;

CREATE TABLE user (
	user_id INT NOT NULL AUTO_INCREMENT,
	insert_date DATETIME,
	password VARCHAR(50) NOT NULL,
	status VARCHAR(1),
	user_name VARCHAR(50) NOT NULL,
	PRIMARY KEY (user_id)
);

CREATE UNIQUE INDEX UNQ_ ON user (user_id ASC);

