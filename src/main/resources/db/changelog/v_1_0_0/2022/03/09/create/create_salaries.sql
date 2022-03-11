-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-8
CREATE TABLE if not exists salaries
        (teacher_id BIGINT NOT NULL,
        salary numeric NOT NULL,
        CONSTRAINT salary_teacher_id_fkey FOREIGN KEY(teacher_id)
	    REFERENCES users(id));
