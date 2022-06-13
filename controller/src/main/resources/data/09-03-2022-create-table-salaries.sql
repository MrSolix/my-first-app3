CREATE TABLE salaries
(
    teacher_id BIGINT  NOT NULL,
    salary     numeric NOT NULL,
    CONSTRAINT salary_teacher_id_fkey FOREIGN KEY (teacher_id)
        REFERENCES users (id)
);
