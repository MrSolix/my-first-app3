-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-26
INSERT INTO salaries (teacher_id,salary) VALUES
	 (4,2000),
	 (3,5000),
	 (6,10000)
	 ON CONFLICT DO NOTHING;