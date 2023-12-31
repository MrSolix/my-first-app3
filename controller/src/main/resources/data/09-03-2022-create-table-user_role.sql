CREATE TABLE user_role
(
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    CONSTRAINT user_role_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES users (id),
    CONSTRAINT user_role_role_id_fkey FOREIGN KEY (role_id)
        REFERENCES role (id)
);
