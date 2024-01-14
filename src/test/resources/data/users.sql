set
    FOREIGN_KEY_CHECKS = 0;
truncate table `users`;
set
    FOREIGN_KEY_CHECKS = 1;

insert into users(id)
values (1),
       (2);
