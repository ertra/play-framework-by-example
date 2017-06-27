# Users schema

# --- !Ups

CREATE TABLE Book (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    author varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO Book VALUES (null,'tomas1', 'zemam1');
INSERT INTO Book VALUES (null,'tomas2', 'zemam2');
INSERT INTO Book VALUES (null,'tomas3', 'zemam3');
INSERT INTO Book VALUES (null,'tomas4', 'zemam4');



# --- !Downs

DROP TABLE Book;
