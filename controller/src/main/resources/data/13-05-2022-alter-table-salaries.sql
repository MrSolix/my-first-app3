alter table salaries
drop constraint salary_teacher_id_fkey;

alter table salaries
add constraint salary_teacher_id_fkey
foreign key (teacher_id) references users(id) on delete cascade;