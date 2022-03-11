-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-16
ALTER TABLE if exists user_authority ADD CONSTRAINT user_authority_authority_id_fkey FOREIGN KEY (authority_id) REFERENCES authority (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
