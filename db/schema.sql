create database tasks;

create table users (
    id serial primary key,
    name text NOT NULL,
    email text unique NOT NULL,
    password text NOT NULL
);

CREATE TABLE IF NOT EXISTS item(
    id serial primary key,
    description text NOT NULL,
    create_date timestamp NOT NULL,
    finished boolean NOT NULL DEFAULT false
);

