-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-5
CREATE TABLE if not exists "group"
        (id INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
        teacher_id INTEGER,
        CONSTRAINT group_pkey PRIMARY KEY (id),
        CONSTRAINT group_teacher_id_fkey FOREIGN KEY(teacher_id)
	    REFERENCES users(id));