set
    FOREIGN_KEY_CHECKS = 0;
truncate table `sites`;
set
    FOREIGN_KEY_CHECKS = 1;

insert into sites(id, name, description, url, site_registration_status, secret_key, user_id)
values (1, '1 name', '1 description', 'http://dasfas', 'COMPLETED', 'qewafasf', 1),
       (2, '2 name', '2 description', 'http://dqdwqdw', 'COMPLETED', 'dasdasdas', 2);
