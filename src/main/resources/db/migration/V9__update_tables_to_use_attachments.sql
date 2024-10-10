alter table menu_items drop column image_url;
alter table menu_items add column image_id UUID;
alter table menu_items add constraint attachment_fk foreign key (image_id) references attachments(id);

alter table tables drop column qr_code_url;
alter table tables drop column token;
alter table tables add column qr_code_id UUID;
alter table tables add constraint attachment_fk foreign key (qr_code_id) references attachments(id);