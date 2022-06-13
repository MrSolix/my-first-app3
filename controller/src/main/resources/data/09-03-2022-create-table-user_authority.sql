CREATE TABLE user_authority
(
    user_id      INTEGER NOT NULL,
    authority_id INTEGER NOT NULL,
    CONSTRAINT user_authority_authority_id_fkey FOREIGN KEY (authority_id)
        REFERENCES authority (id),
    CONSTRAINT user_authority_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES users (id)
);
