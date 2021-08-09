-- book

-- !Ups

CREATE TABLE ptz_book (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    title varchar(255) NOT NULL,
    published TIMESTAMP(3) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE  KEY book_uniq_key(title)
);

-- !Downs

DROP TABLE ptz_book;