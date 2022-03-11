-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-14
ALTER TABLE if exists salaries ADD CONSTRAINT salary_teacher_id_fkey FOREIGN KEY (teacher_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE CASCADE;
