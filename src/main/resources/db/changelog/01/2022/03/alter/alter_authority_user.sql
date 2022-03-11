-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-17
ALTER TABLE if exists user_authority ADD CONSTRAINT user_authority_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
