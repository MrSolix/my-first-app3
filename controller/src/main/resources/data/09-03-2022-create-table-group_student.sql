CREATE TABLE group_student
(group_id INTEGER,
student_id INTEGER,
CONSTRAINT student_group_id_fkey FOREIGN KEY(group_id)
REFERENCES "group"(id),
CONSTRAINT group_student_id_fkey FOREIGN KEY(student_id)
REFERENCES users(id));