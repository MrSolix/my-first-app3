-- liquibase formatted sql

-- changeset vyacheslav:1646924875501-2
ALTER TABLE if exists users ADD CONSTRAINT users_user_name_key UNIQUE (user_name);

