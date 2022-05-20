package eu.senla.dutov.controller.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.dutov.exception.DataBaseException;
import eu.senla.dutov.model.auth.Role;
import eu.senla.dutov.model.group.Group;
import eu.senla.dutov.model.people.Student;
import eu.senla.dutov.model.people.Teacher;
import eu.senla.dutov.model.people.grades.Grade;
import eu.senla.dutov.service.person.PersonService;
import eu.senla.dutov.service.person.StudentService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudentJsonControllerTest {

    private static MockMvc mockMvc;

    private static final StudentService studentService = mock(StudentService.class);

    private static Student slavik;
    private static Student qwe453;

    @BeforeAll
    static void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new StudentJsonController(studentService)).build();
    }

    @BeforeEach
    void setStudent() {
        slavik = new Student()
                .withId(6)
                .withUserName("student")
                .withPassword("$2a$12$iDPdhEo8ewcqwqagAVjYJ.SMES4piBWmusiZ76uoR.vKCI1aceYBW")
                .withName("Slavik")
                .withAge(26)
                .withGroups(Set.of(
                        new Group().withId(19),
                        new Group().withId(29)))
                .withGrades(List.of(
                        new Grade()
                                .withId(176)
                                .withName("Math")
                                .withGrade(55),
                        new Grade()
                                .withId(177)
                                .withName("Math")
                                .withGrade(32),
                        new Grade()
                                .withId(178)
                                .withName("English")
                                .withGrade(78),
                        new Grade()
                                .withId(179)
                                .withName("English")
                                .withGrade(89),
                        new Grade()
                                .withId(175)
                                .withName("abs")
                                .withGrade(54)));
        qwe453 = new Student()
                .withId(2)
                .withUserName("qwe453")
                .withPassword("$2a$12$PGIDIEUTp2M/aoQ.NF7blO9th24vNu1YbamXdoZwy9iWinqHTMMH6")
                .withName("qwe")
                .withAge(123)
                .withGroups(Set.of(
                        new Group().withId(1),
                        new Group().withId(2)))
                .withGrades(List.of(
                        new Grade()
                                .withId(1)
                                .withName("Math")
                                .withGrade(55),
                        new Grade()
                                .withId(2)
                                .withName("Math")
                                .withGrade(32),
                        new Grade()
                                .withId(3)
                                .withName("English")
                                .withGrade(78),
                        new Grade()
                                .withId(4)
                                .withName("English")
                                .withGrade(89)));
    }

//    @Test
//    void getAllWhenListOfStudentsHasStudentsAndOneTeacherShouldReturnAllStudents() throws Exception {
//        when(studentService.findAll()).thenReturn(List.of(
//                qwe453,
//                slavik,
//                new Teacher()));
//
//        mockMvc.perform(get("/json/students")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(2))
//                .andExpect(jsonPath("$[0].userName").value("qwe453"))
//                .andExpect(jsonPath("$[0].password").value("$2a$12$PGIDIEUTp2M/aoQ.NF7blO9th24vNu1YbamXdoZwy9iWinqHTMMH6"))
//                .andExpect(jsonPath("$[0].name").value("qwe"))
//                .andExpect(jsonPath("$[0].age").value(123))
//                .andExpect(jsonPath("$[0].roles[0].id").value(1))
//                .andExpect(jsonPath("$[0].roles[0].name").value("STUDENT"))
//                .andExpect(jsonPath("$[0].authorities").isEmpty())
//                .andExpect(jsonPath("$[0].groups[0].id").isNumber())
//                .andExpect(jsonPath("$[0].groups[1].id").isNumber())
//                .andExpect(jsonPath("$[0].grades[0].id").value(1))
//                .andExpect(jsonPath("$[0].grades[0].themeName").value("Math"))
//                .andExpect(jsonPath("$[0].grades[0].grade").value(55))
//                .andExpect(jsonPath("$[0].grades[1].id").value(2))
//                .andExpect(jsonPath("$[0].grades[1].themeName").value("Math"))
//                .andExpect(jsonPath("$[0].grades[1].grade").value(32))
//                .andExpect(jsonPath("$[0].grades[2].id").value(3))
//                .andExpect(jsonPath("$[0].grades[2].themeName").value("English"))
//                .andExpect(jsonPath("$[0].grades[2].grade").value(78))
//                .andExpect(jsonPath("$[0].grades[3].id").value(4))
//                .andExpect(jsonPath("$[0].grades[3].themeName").value("English"))
//                .andExpect(jsonPath("$[0].grades[3].grade").value(89))
//
//                .andExpect(jsonPath("$[1].id").value(6))
//                .andExpect(jsonPath("$[1].userName").value("student"))
//                .andExpect(jsonPath("$[1].password").value("$2a$12$iDPdhEo8ewcqwqagAVjYJ.SMES4piBWmusiZ76uoR.vKCI1aceYBW"))
//                .andExpect(jsonPath("$[1].name").value("Slavik"))
//                .andExpect(jsonPath("$[1].age").value(26))
//                .andExpect(jsonPath("$[1].roles[0].id").value(1))
//                .andExpect(jsonPath("$[1].roles[0].name").value("STUDENT"))
//                .andExpect(jsonPath("$[1].authorities").isEmpty())
//                .andExpect(jsonPath("$[1].groups[0].id").isNumber())
//                .andExpect(jsonPath("$[1].groups[1].id").isNumber())
//                .andExpect(jsonPath("$[1].grades[0].id").value(176))
//                .andExpect(jsonPath("$[1].grades[0].themeName").value("Math"))
//                .andExpect(jsonPath("$[1].grades[0].grade").value(55))
//                .andExpect(jsonPath("$[1].grades[1].id").value(177))
//                .andExpect(jsonPath("$[1].grades[1].themeName").value("Math"))
//                .andExpect(jsonPath("$[1].grades[1].grade").value(32))
//                .andExpect(jsonPath("$[1].grades[2].id").value(178))
//                .andExpect(jsonPath("$[1].grades[2].themeName").value("English"))
//                .andExpect(jsonPath("$[1].grades[2].grade").value(78))
//                .andExpect(jsonPath("$[1].grades[3].id").value(179))
//                .andExpect(jsonPath("$[1].grades[3].themeName").value("English"))
//                .andExpect(jsonPath("$[1].grades[3].grade").value(89))
//                .andExpect(jsonPath("$[1].grades[4].id").value(175))
//                .andExpect(jsonPath("$[1].grades[4].themeName").value("abs"))
//                .andExpect(jsonPath("$[1].grades[4].grade").value(54));
//    }

    @Test
    void getAllWhenListOfStudentsIsEmptyShouldReturnEmptyList() throws Exception {
        when(studentService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/json/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getStudentWhenUserIdIsCorrectShouldReturnStudent() throws Exception {
        when(studentService.findById(6)).thenReturn(Optional.of(slavik));

        mockMvc.perform(get("/json/students/{id}", 6)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.userName").value("student"))
                .andExpect(jsonPath("$.password").value("$2a$12$iDPdhEo8ewcqwqagAVjYJ.SMES4piBWmusiZ76uoR.vKCI1aceYBW"))
                .andExpect(jsonPath("$.name").value("Slavik"))
                .andExpect(jsonPath("$.age").value(26))
                .andExpect(jsonPath("$.roles[0].id").value(1))
                .andExpect(jsonPath("$.roles[0].name").value("STUDENT"))
                .andExpect(jsonPath("$.authorities").isEmpty())
                .andExpect(jsonPath("$.groups[0].id").isNumber())
                .andExpect(jsonPath("$.groups[1].id").isNumber())
                .andExpect(jsonPath("$.grades[0].id").value(176))
                .andExpect(jsonPath("$.grades[0].themeName").value("Math"))
                .andExpect(jsonPath("$.grades[0].grade").value(55))
                .andExpect(jsonPath("$.grades[1].id").value(177))
                .andExpect(jsonPath("$.grades[1].themeName").value("Math"))
                .andExpect(jsonPath("$.grades[1].grade").value(32))
                .andExpect(jsonPath("$.grades[2].id").value(178))
                .andExpect(jsonPath("$.grades[2].themeName").value("English"))
                .andExpect(jsonPath("$.grades[2].grade").value(78))
                .andExpect(jsonPath("$.grades[3].id").value(179))
                .andExpect(jsonPath("$.grades[3].themeName").value("English"))
                .andExpect(jsonPath("$.grades[3].grade").value(89))
                .andExpect(jsonPath("$.grades[4].id").value(175))
                .andExpect(jsonPath("$.grades[4].themeName").value("abs"))
                .andExpect(jsonPath("$.grades[4].grade").value(54));
    }

    @Test
    void getStudentWhenUserIdIsIncorrectShouldReturnStatusNotFound() throws Exception {
        when(studentService.findById(-1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/json/students/{id}", -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveStudentWhenStudentIsValidShouldReturnThisStudent() throws Exception {
        Student student = new Student()
                .withUserName("xcv");
        when(studentService.save(student)).thenReturn(student);

        mockMvc.perform(post("/json/students")
                        .content(new ObjectMapper().writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("xcv"))
                .andExpect(jsonPath("$.roles[0].id").value(1))
                .andExpect(jsonPath("$.roles[0].name").value("STUDENT"));
    }

    @Test
    void saveStudentWhenRequestBodyIsNotStudentShouldReturnStatusBadRequest() throws Exception {
        Teacher teacher = new Teacher().withUserName("asdf");

        mockMvc.perform(post("/json/students")
                        .content(new ObjectMapper().writeValueAsString(teacher))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveStudentWhenStudentIsAlreadyInTheDataBaseShouldReturnStatusBadRequest() throws Exception {
        when(studentService.save(slavik)).thenThrow(DataBaseException.class);

        mockMvc.perform(post("/json/students")
                        .content(new ObjectMapper().writeValueAsString(slavik))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateStudentWhenRequestBodyIsStudentAndIdIsCorrectShouldReturnStudent() throws Exception {
        when(studentService.update(slavik.getId(), slavik)).thenReturn(slavik);

        mockMvc.perform(put("/json/students/{id}", slavik.getId())
                        .content(new ObjectMapper().writeValueAsString(slavik))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.userName").value("student"))
                .andExpect(jsonPath("$.password").value("$2a$12$iDPdhEo8ewcqwqagAVjYJ.SMES4piBWmusiZ76uoR.vKCI1aceYBW"))
                .andExpect(jsonPath("$.name").value("Slavik"))
                .andExpect(jsonPath("$.age").value(26))
                .andExpect(jsonPath("$.roles[0].id").value(1))
                .andExpect(jsonPath("$.roles[0].name").value("STUDENT"))
                .andExpect(jsonPath("$.authorities").isEmpty())
                .andExpect(jsonPath("$.groups[0].id").isNumber())
                .andExpect(jsonPath("$.groups[1].id").isNumber())
                .andExpect(jsonPath("$.grades[0].id").value(176))
                .andExpect(jsonPath("$.grades[0].themeName").value("Math"))
                .andExpect(jsonPath("$.grades[0].grade").value(55))
                .andExpect(jsonPath("$.grades[1].id").value(177))
                .andExpect(jsonPath("$.grades[1].themeName").value("Math"))
                .andExpect(jsonPath("$.grades[1].grade").value(32))
                .andExpect(jsonPath("$.grades[2].id").value(178))
                .andExpect(jsonPath("$.grades[2].themeName").value("English"))
                .andExpect(jsonPath("$.grades[2].grade").value(78))
                .andExpect(jsonPath("$.grades[3].id").value(179))
                .andExpect(jsonPath("$.grades[3].themeName").value("English"))
                .andExpect(jsonPath("$.grades[3].grade").value(89))
                .andExpect(jsonPath("$.grades[4].id").value(175))
                .andExpect(jsonPath("$.grades[4].themeName").value("abs"))
                .andExpect(jsonPath("$.grades[4].grade").value(54));
    }

    @Test
    void updateStudentWhenStudentIdIsIncorrectShouldThrowException() throws Exception {
        slavik.setId(-1);
        when(studentService.update(slavik.getId(), slavik)).thenThrow(DataBaseException.class);

        mockMvc.perform(put("/json/students/{id}", -1)
                        .content(new ObjectMapper().writeValueAsString(slavik))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateStudentWhenStudentIdIsNotEqualPathVariableIdShouldReturnStatusBadRequest() throws Exception {
        int pathVariable = -1;
        mockMvc.perform(put("/json/students/{id}", pathVariable)
                        .content(new ObjectMapper().writeValueAsString(slavik))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$")
                        .value("Student id must be equal with id in path: "
                                + pathVariable + " != " + slavik.getId()));
    }

    @Test
    void updateStudentWhenRequestBodyIsNotStudentShouldReturnStatusBadRequest() throws Exception {
        slavik.setRoles(Set.of(new Role().withId(2).withName("Teacher").addPerson(slavik)));

        mockMvc.perform(put("/json/students/{id}", slavik.getId())
                        .content(new ObjectMapper().writeValueAsString(slavik))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Person is not student"));
    }

    @Test
    void deleteStudentWhenPathVariableIsIncorrectShouldReturnStatusNotFound() throws Exception {
        mockMvc.perform(delete("/json/students/{id}", -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStudentWhenFoundNotStudentShouldReturnStatusNotFound() throws Exception {
        slavik.setRoles(Set.of(new Role().withId(2).withName("Teacher").addPerson(slavik)));
        when(studentService.findById(slavik.getId())).thenReturn(Optional.of(slavik));

        mockMvc.perform(delete("/json/students/{id}", slavik.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStudentWhenPathVariableIsCorrectShouldReturnStudent() throws Exception {
        when(studentService.findById(slavik.getId())).thenReturn(Optional.of(slavik));

        mockMvc.perform(delete("/json/students/{id}", slavik.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}