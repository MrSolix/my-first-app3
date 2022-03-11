-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-10
CREATE TABLE if not exists user_role (user_id INTEGER NOT NULL, role_id INTEGER NOT NULL);
