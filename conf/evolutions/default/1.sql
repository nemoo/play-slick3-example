# DC schema
 
# --- !Ups


CREATE TABLE ACTIVITY (
    ID integer NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NAME varchar(255) NOT NULL,
);


CREATE TABLE ITEM (
    ID integer NOT NULL AUTO_INCREMENT PRIMARY KEY,
    COLOR varchar(255) NOT NULL,
);


 
# --- !Downs

DROP TABLE ACTIVITY;
DROP TABLE ITEM;  
