create table attachments(
    id UUID default gen_random_uuid() primary key,
    name text not null,
    url text not null
);