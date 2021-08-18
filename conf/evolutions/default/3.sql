-- book

-- !Ups

CREATE TABLE `company` (
    `name` varchar(255) NOT NULL,
    `age` Int NOT NULL,
    PRIMARY KEY (`name`)
);

-- !Downs

DROP TABLE `company`;