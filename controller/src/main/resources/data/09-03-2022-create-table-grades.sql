CREATE TABLE grades
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    theme_name VARCHAR(50)                              NOT NULL,
    grade      INTEGER                                  NOT NULL,
    student_id INTEGER,
    CONSTRAINT grades_pkey PRIMARY KEY (id),
    CONSTRAINT grades_student_id_fkey FOREIGN KEY (student_id)
        REFERENCES users (id)
);
