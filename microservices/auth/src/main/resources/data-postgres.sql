insert into users(id, username, password, role) values (1, 'pera', '[-51, 86, 53, -60, -34, 56, 94, -77, 74, -42, 123, 124, 114, -28, -19, 31]', 0);
insert into users(id, username, password, role) values (2, 'pera3', '[-51, 86, 53, -60, -34, 56, 94, -77, 74, -42, 123, 124, 114, -28, -19, 31]', 0);
insert into users(id, username, password, role) values (3, 'mika', '[-51, 86, 53, -60, -34, 56, 94, -77, 74, -42, 123, 124, 114, -28, -19, 31]', 0);
insert into users(id, username, password, role) values (4, 'zika', '[-51, 86, 53, -60, -34, 56, 94, -77, 74, -42, 123, 124, 114, -28, -19, 31]', 0);
insert into users(id, username, password, role) values (5, 'admin', '[-51, 86, 53, -60, -34, 56, 94, -77, 74, -42, 123, 124, 114, -28, -19, 31]', 1);
insert into users(id, username, password, role) values (6, 'tomica', '[-51, 86, 53, -60, -34, 56, 94, -77, 74, -42, 123, 124, 114, -28, -19, 31]', 2);

insert into regular_user(id, user_id, name, surname, gender, email, phone_number, birth_date, website, biography, apitoken) values (1, 1, 'pera', 'peric', 0, 'pera', '', '2001-02-25 08:00:00.000000', '', 'i am pera', null);
insert into regular_user(id, user_id, name, surname, gender, email, phone_number, birth_date, website, biography, apitoken) values (2, 2, 'pera3', 'peric3', 0, 'pera3', '', '2001-02-25 08:00:00.000000', '', 'i am pera3', null);
insert into regular_user(id, user_id, name, surname, gender, email, phone_number, birth_date, website, biography, apitoken) values (3, 3, 'mika', 'mikic', 0, 'mika', '', '2001-02-25 08:00:00.000000', '', 'i am mika', null);
insert into regular_user(id, user_id, name, surname, gender, email, phone_number, birth_date, website, biography, apitoken) values (4, 4, 'zika', 'zikic', 0, 'zika', '', '2001-02-25 08:00:00.000000', '', 'i am zika', null);
insert into regular_user(id, user_id, name, surname, gender, email, phone_number, birth_date, website, biography, apitoken) values (5, 6, 'tomica', 'tomic', 0, 'zika', '', '2001-02-25 08:00:00.000000', 'http://localhost:3200/', 'i am tomica', 'eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktZXhhbXBsZSIsImF1ZCI6IndlYiIsImlhdCI6MTYyNTc1MDg5OSwiZXhwIjoxNjI2NjUwODk5LCJ1c2VybmFtZSI6InRvbWljYSJ9.HeIAjpvNuNu9X7qP0DD9v13LruJyso91xfAO5Y_QtugTodbi1NZ6Hk_uXIgYbs_wl_OqAZKLlRD_JcVhdLqU5Q');

insert into admin(id, user_id) values (1, 5);