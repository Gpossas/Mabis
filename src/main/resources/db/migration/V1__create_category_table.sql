create table categories(
    id UUID default gen_random_uuid() primary key,
    name varchar(30) not null
);