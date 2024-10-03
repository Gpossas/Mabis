create table tables(
    id UUID default gen_random_uuid() primary key,
    table_number INTEGER unique not null,
    capacity INTEGER not null,
    qr_code_url text,
    status varchar(8) check (status in ('active', 'inactive', 'reserved')) default 'inactive'
);