-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-18
ALTER TABLE if exists user_role ADD CONSTRAINT user_role_role_id_fkey FOREIGN KEY (role_id) REFERENCES role (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
