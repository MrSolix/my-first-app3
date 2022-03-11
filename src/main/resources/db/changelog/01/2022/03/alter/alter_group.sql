-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-13
ALTER TABLE if exists "group" ADD CONSTRAINT group_teacher_id_fkey FOREIGN KEY (teacher_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
