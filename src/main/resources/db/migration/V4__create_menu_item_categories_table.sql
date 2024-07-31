create table menu_items_categories(
    menu_item_id UUID,
    category_id UUID,
    constraint fk_menu_item
        foreign key (menu_item_id)
        references menu_items (id)
        on delete cascade,
    constraint fk_category
        foreign key (category_id)
        references categories (id)
        on delete cascade,
    unique (menu_item_id, category_id)
);