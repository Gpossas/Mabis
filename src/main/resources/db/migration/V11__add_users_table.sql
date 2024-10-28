create table users(
    id UUID default gen_random_uuid() primary key,
    email varchar(100) unique not null,
    first_name varchar(20) not null,
    last_name varchar(50),
    password TEXT not null,
    role varchar(6) not null,
    constraint allowed_roles
        check (role in ('OWNER', 'WAITER', 'COOK'))
);