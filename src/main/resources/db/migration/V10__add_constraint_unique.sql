alter table tables add constraint unique_qr_code unique (qr_code_id);
alter table menu_items add constraint unique_image unique (image_id);