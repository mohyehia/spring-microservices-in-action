alter table organizations
add column if not exists contact_email varchar(200) null,
add column if not exists contact_phone varchar(200) null