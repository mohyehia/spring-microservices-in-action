create table if not exists organizations (
    id serial primary key,
    name varchar(250),
    slug varchar(250),
    created_at timestamp
);