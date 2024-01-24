set
    FOREIGN_KEY_CHECKS = 0;
truncate table `components`;
set
    FOREIGN_KEY_CHECKS = 1;

insert into components(id, name, description, component_status, frequency, is_active, site_id, last_updated_at)
values (1, '1 name', '1 description', 'NONE', 5, true, 1, null),
       (2, '2 name', '2 description', 'NONE', 5, false, 1, null),
       (3, '3 name', '3 description', 'NONE', 5, true, 1, '2020-12-08T11:44:30.327959'),
       (4, '4 name', '4 description', 'NONE', 5, true, 1, '2020-11-08T11:44:30.327959'),
       (5, '5 name', '5 description', 'NONE', 5, true, 1, '2022-12-08T11:44:30.327959'),
       (6, '6 name', '6 description', 'NONE', 5, true, 1, '2021-11-08T11:44:30.327959');