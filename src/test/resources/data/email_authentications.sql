set
FOREIGN_KEY_CHECKS = 0;
truncate table `email_authentications`;
set
FOREIGN_KEY_CHECKS = 1;

insert into email_authentications(id, email, code, expiration_at)
values (1, 'validuser1@ruu.kr', '098677', TIMESTAMPADD(MINUTE, 10, now())),
       (2, 'invaliduser2@ruu.kr', '345334', '2024-01-01 00:00:10');