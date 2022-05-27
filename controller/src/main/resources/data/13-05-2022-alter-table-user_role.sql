alter table user_role
drop constraint user_role_user_id_fkey;

alter table user_role
add constraint user_role_user_id_fkey
foreign key (user_id) references users(id) on delete cascade;
