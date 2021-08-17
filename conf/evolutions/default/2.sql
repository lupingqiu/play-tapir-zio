-- book

-- !Ups

CREATE TABLE `user` (
    `name` varchar(255) NOT NULL,
    `age` Int NOT NULL,
    PRIMARY KEY (`name`)
);

-- !Downs

DROP TABLE `user`;