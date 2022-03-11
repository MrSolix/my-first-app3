-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-12
ALTER TABLE if exists group_student ADD CONSTRAINT group_student_id_fkey FOREIGN KEY (student_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;