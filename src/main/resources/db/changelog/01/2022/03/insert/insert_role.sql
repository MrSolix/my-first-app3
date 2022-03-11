-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-25
INSERT INTO "role" (id,name) VALUES
	 (1,'STUDENT'),
	 (2,'TEACHER'),
	 (3,'ADMIN')
	 ON CONFLICT DO NOTHING;