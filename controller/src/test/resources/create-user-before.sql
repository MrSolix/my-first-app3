delete from user_role;
delete from salaries;
delete from users;

insert into users(id, user_name, password, name, age, user_type)values
(5, 'admin', '$2a$12$VwWdjeAqdDPtg0MKip2.T.9zbtulMCKL3iXa5DYBvjH6A4h/uYcn2', 'admin', 999, 'admin'),
(4, 'teacher', '$2a$12$8RWIkuNE37xVh3Qj6qPZpOAENv1FPaffdtXpSNGrWJxCoe.PVk32q', 'Ychitel', 12, 'teacher');

insert into user_role(user_id, role_id) values
(5, 3),
(4, 2);

insert into salaries(teacher_id, salary) values
(4, 10000);