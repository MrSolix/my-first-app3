-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-23
INSERT INTO "group" (id,teacher_id) VALUES
	 (1,4),
	 (2,3),
	 (3,6)
	 ON CONFLICT DO NOTHING;