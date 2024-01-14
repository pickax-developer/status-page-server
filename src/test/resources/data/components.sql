set
    FOREIGN_KEY_CHECKS = 0;
truncate table `components`;
set
    FOREIGN_KEY_CHECKS = 1;

insert into components(id, name, description, component_status, frequency, is_active, site_id)
values (1, '1 name', '1 description', 'NONE', 5, true, 1),
       (2, '2 name', '2 description', 'NONE', 5, false, 1);
