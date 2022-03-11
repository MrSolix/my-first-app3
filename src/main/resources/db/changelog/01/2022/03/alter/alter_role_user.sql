-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-19
ALTER TABLE if exists user_role ADD CONSTRAINT user_role_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
