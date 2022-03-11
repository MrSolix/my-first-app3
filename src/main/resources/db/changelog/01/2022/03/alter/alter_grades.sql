-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-11
ALTER TABLE if exists grades ADD CONSTRAINT grades_student_id_fkey FOREIGN KEY (student_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
