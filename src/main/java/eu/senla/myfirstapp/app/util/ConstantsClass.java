package eu.senla.myfirstapp.app.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstantsClass {
    //JPA
    public static final String REMOVE_TEACHER_FROM_GROUP = "update Group g set g.teacher = null where g.id = :id and g.teacher.id = :teacher_id";
    public static final String GET_ALL_STUDENTS = "findAllStudents";
    public static final String GET_STUDENT_BY_NAME = "findStudentByName";
    public static final String GET_STUDENT_BY_ID = "findStudentById";
    public static final String GET_ALL_TEACHERS = "findAllTeachers";
    public static final String GET_TEACHER_BY_NAME = "findTeacherByName";
    public static final String GET_TEACHER_BY_ID = "findTeacherById";
    public static final String GET_ALL_ADMINS = "findAllAdmins";
    public static final String GET_ADMIN_BY_NAME = "findAdminByName";
    public static final String GET_ADMIN_BY_ID = "findAdminById";


    //ERRORS
    public static final String PERSON_NOT_FOUND = "Person not found";
    public static final String GROUP_NOT_FOUND = "Group not found";
    public static final String ERROR_FROM_REMOVE = "Error from remove";
    public static final String ERROR_FROM_UPDATE = "Error from update";
    public static final String ERROR_FROM_SAVE = "Error from save";
    public static final String ERROR_FROM_FIND = "Error from find";
    public static final String ERROR_FROM_FIND_ALL = "Error from findAll";
}
