create table menu_items(
    id UUID default gen_random_uuid() primary key,
    name varchar(250) not null,
    price numeric(6, 2) not null,
    image_url text,
    description text,
    dish_type_id UUID,
    constraint  fk_dish_type
        foreign key (dish_type_id)
        references dish_types (id)
);