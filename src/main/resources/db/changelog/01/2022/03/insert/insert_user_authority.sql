-- liquibase formatted sql

-- changeset vyacheslav:1646923667211-27
INSERT INTO user_authority (user_id,authority_id) VALUES
	 (1,1)
	 ON CONFLICT DO NOTHING;