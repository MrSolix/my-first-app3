-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-9
CREATE TABLE if not exists user_authority (user_id INTEGER NOT NULL, authority_id INTEGER NOT NULL);
