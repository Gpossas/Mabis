create table orders(
    id UUID default gen_random_uuid() primary key,
    quantity integer not null,
    price numeric(6, 2) not null,
    description text,
    status varchar(9) check (status in ('PENDING', 'PREPARING', 'CANCELLED', 'COMPLETED')) default 'PENDING',
    updated_at timestamp,
    created_at timestamp not null,
    table_id UUID,
    menu_item_id UUID,
    constraint fk_table
        foreign key (table_id)
        references tables (id),
    constraint fk_menu_item
        foreign key (menu_item_id)
        references menu_items (id)
);