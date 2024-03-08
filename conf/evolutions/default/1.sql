# --- !Ups

CREATE TABLE project (
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL
);

CREATE TABLE task (
    id SERIAL NOT NULL PRIMARY KEY,
    color varchar(255) NOT NULL,
    status varchar(255) NOT NULL,
    project integer NOT NULL REFERENCES project (ID)
);

# --- !Downs

DROP TABLE task;
DROP TABLE project;
