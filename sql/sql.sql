set search_path = demo;

CREATE SEQUENCE s_post START WITH 1 INCREMENT BY 50;

CREATE TABLE post(
                     id BIGINT PRIMARY KEY DEFAULT nextval('s_post'),
                     name VARCHAR(255),
                     type BIGINT
);