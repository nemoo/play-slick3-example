# DC schema
 
# --- !Ups


CREATE TABLE activity (
    id integer NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) NOT NULL,
);



 
# --- !Downs

DROP TABLE activity; 
