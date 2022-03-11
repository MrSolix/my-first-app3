-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-6
CREATE TABLE if not exists group_student
        (group_id INTEGER,
        student_id INTEGER,
	    CONSTRAINT student_group_id_fkey FOREIGN KEY(group_id)
	    REFERENCES "group"(id),
        CONSTRAINT group_student_id_fkey FOREIGN KEY(student_id)
	    REFERENCES users(id));
