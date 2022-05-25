delete from user_role;
delete from salaries;
delete from users;

insert into users(id, user_name, password, name, age, user_type)values
(1, 'teacher1', '$2a$12$3sntQS7oN3j/kLxHGtrUSOgXvd6USe3awF9.nLlTpgswWVSjjqHCG', 'Cheburator', 123, 'teacher'),
(2, 'qwe453', '$2a$12$PGIDIEUTp2M/aoQ.NF7blO9th24vNu1YbamXdoZwy9iWinqHTMMH6', 'qwe', 123, 'student'),
(3, 'teacher12', '$2a$12$fRb6vuqoZlPp2bdWXd4TZ.Fj./Dx5Or47AU9xJc/TKlxZpy8YYG1u', 'kek', 12, 'teacher'),
(4, 'teacher', '$2a$12$8RWIkuNE37xVh3Qj6qPZpOAENv1FPaffdtXpSNGrWJxCoe.PVk32q', 'Ychitel', 12, 'teacher'),
(5, 'admin', '$2a$12$VwWdjeAqdDPtg0MKip2.T.9zbtulMCKL3iXa5DYBvjH6A4h/uYcn2', 'admin', 999, 'admin'),
(6, 'student', '$2a$12$iDPdhEo8ewcqwqagAVjYJ.SMES4piBWmusiZ76uoR.vKCI1aceYBW', 'Slavik', 26, 'student');

insert into user_role(user_id, role_id) values
(5, 3),
(1, 2),
(2, 1),
(3, 2),
(4, 2),
(6, 1);

insert into salaries(teacher_id, salary) values
(1, 2000),
(3, 5000),
(4, 10000);

alter sequence users_id_seq start 10;