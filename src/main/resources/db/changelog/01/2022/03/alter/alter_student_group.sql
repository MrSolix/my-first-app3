-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-15
ALTER TABLE if exists group_student ADD CONSTRAINT student_group_id_fkey FOREIGN KEY (group_id) REFERENCES "group" (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
