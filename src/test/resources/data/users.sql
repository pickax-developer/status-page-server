set
    FOREIGN_KEY_CHECKS = 0;
truncate table `users`;
set
    FOREIGN_KEY_CHECKS = 1;

insert into users(id, email, password, status, sign_up_at)
values (1, 'user1@ruu.kr', 'qwerR1234!', 'JOIN', '2024-01-01 00:00:00'),
       (2, 'user2@ruu.kr', 'qwerR1234!', 'JOIN', '2024-01-01 00:00:10');
