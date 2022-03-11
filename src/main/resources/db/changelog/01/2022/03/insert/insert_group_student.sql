-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-24
INSERT INTO group_student (group_id,student_id) VALUES
	 (1,2),
	 (2,2)
	 ON CONFLICT DO NOTHING;