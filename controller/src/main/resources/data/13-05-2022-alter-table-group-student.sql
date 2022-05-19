alter table group_student
drop constraint group_student_id_fkey;

alter table group_student
add constraint group_student_id_fkey
foreign key (student_id) references users(id) on delete cascade;