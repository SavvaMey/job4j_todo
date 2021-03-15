create database tasks;
CREATE TABLE IF NOT EXISTS item(
    id serial primary key,
    description text NOT NULL,
    create_date timestamp NOT NULL,
    finished boolean NOT NULL DEFAULT false
);