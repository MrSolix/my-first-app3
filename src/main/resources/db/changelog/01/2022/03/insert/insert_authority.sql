-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-21
INSERT INTO authority (id,name) VALUES
	 (1,'READ_SALARIES')
	 ON CONFLICT DO NOTHING;