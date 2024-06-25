insert into tb_roles (id, ROLE_NAME) values (1, 'ROLE_ADMINISTRATOR')
insert into tb_roles (id, ROLE_NAME) values (2, 'ROLE_CUSTOMER')

insert into tb_users (id, name, email, password) values (1, 'admin', 'admin@email.com', '$2a$12$RwenD5QK1fYaW9hohxAr8.udZ./W9NUZOJLHO4WJ0rTxuUgRIHvdi')
insert into tb_users (id, name, email, password) values (2, 'customer', 'customer@email.com', '$2a$12$RwenD5QK1fYaW9hohxAr8.udZ./W9NUZOJLHO4WJ0rTxuUgRIHvdi')

insert into tb_users_roles (ROLE_ID, USER_ID) values (1, 1)
insert into tb_users_roles (ROLE_ID, USER_ID) values (2, 2)

