-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-28
INSERT INTO user_role (user_id,role_id) VALUES
	 (5,1),
	 (6,2),
	 (2,1),
	 (4,2),
	 (3,2),
	 (1,3)
	 ON CONFLICT DO NOTHING;