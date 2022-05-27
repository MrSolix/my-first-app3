package eu.senla.dutov.util;

public class ControllerConstantClass {

    public static final String URI_JSON_SALARIES = "/json/salaries";
    public static final String URI_JSON_STUDENTS = "/json/students";
    public static final String URI_JSON_TEACHERS = "/json/teachers";
    public static final String URI_ID = "/{id}";
    public static final String URI_AVERAGE_SALARY = "/{id}/average/minRange={min}&maxRange={max}";
    public static final int MIN_VALUE = 1;
    public static final int MAX_VALUE = 12;

    private ControllerConstantClass() {
    }
}
