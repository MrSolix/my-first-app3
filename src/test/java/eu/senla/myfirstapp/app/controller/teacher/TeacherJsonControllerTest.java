package eu.senla.myfirstapp.app.controller.teacher;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.myfirstapp.app.exception.DataBaseException;
import eu.senla.myfirstapp.app.service.person.PersonService;
import eu.senla.myfirstapp.model.auth.Role;
import eu.senla.myfirstapp.model.group.Group;
import eu.senla.myfirstapp.model.people.Student;
import eu.senla.myfirstapp.model.people.Teacher;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class TeacherJsonControllerTest {

    private static MockMvc mockMvc;

    private static final PersonService personService = mock(PersonService.class);

    private static Teacher teacher;
    private static Teacher teacher1;
    private static Teacher teacher12;

    @BeforeAll
    static void setUp() {
        mockMvc = standaloneSetup(new TeacherJsonController(personService)).build();
    }

    @AfterEach
    void clear() {
        Mockito.clearInvocations(personService);
    }

    @BeforeEach
    void setTeacher() {

        teacher = new Teacher()
                .withId(4)
                .withUserName("teacher")
                .withPassword("$2a$12$8RWIkuNE37xVh3Qj6qPZpOAENv1FPaffdtXpSNGrWJxCoe.PVk32q")
                .withName("Ychitel")
                .withAge(12)
                .withGroup(new Group().withId(1))
                .withSalary(10000.0);
        teacher1 = new Teacher()
                .withId(1)
                .withUserName("teacher1")
                .withPassword("$2a$12$3sntQS7oN3j/kLxHGtrUSOgXvd6USe3awF9.nLlTpgswWVSjjqHCG")
                .withName("Cheburator")
                .withAge(124)
                .withSalary(2000.0);
        teacher12 = new Teacher()
                .withId(3)
                .withUserName("teacher12")
                .withPassword("$2a$12$fRb6vuqoZlPp2bdWXd4TZ.Fj./Dx5Or47AU9xJc/TKlxZpy8YYG1u")
                .withName("kek")
                .withAge(12)
                .withGroup(new Group().withId(2))
                .withSalary(5000.0);
    }

    @Test
    void getAllWhenListOfTeachersHasTeachersAndOneStudentShouldReturnAllTeachers() throws Exception {
        when(personService.findAll()).thenReturn(List.of(
                teacher1,
                teacher12,
                teacher,
                new Student()));

        mockMvc.perform(get("/json/teachers")
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].userName").value("teacher1"))
                .andExpect(jsonPath("$[0].password").value("$2a$12$3sntQS7oN3j/kLxHGtrUSOgXvd6USe3awF9.nLlTpgswWVSjjqHCG"))
                .andExpect(jsonPath("$[0].name").value("Cheburator"))
                .andExpect(jsonPath("$[0].age").value(124))
                .andExpect(jsonPath("$[0].roles[0].id").value(2))
                .andExpect(jsonPath("$[0].roles[0].name").value("TEACHER"))
                .andExpect(jsonPath("$[0].authorities").isEmpty())
                .andExpect(jsonPath("$[0].salary").value(2000.0))

                .andExpect(jsonPath("$[1].id").value(3))
                .andExpect(jsonPath("$[1].userName").value("teacher12"))
                .andExpect(jsonPath("$[1].password").value("$2a$12$fRb6vuqoZlPp2bdWXd4TZ.Fj./Dx5Or47AU9xJc/TKlxZpy8YYG1u"))
                .andExpect(jsonPath("$[1].name").value("kek"))
                .andExpect(jsonPath("$[1].age").value(12))
                .andExpect(jsonPath("$[1].roles[0].id").value(2))
                .andExpect(jsonPath("$[1].roles[0].name").value("TEACHER"))
                .andExpect(jsonPath("$[1].authorities").isEmpty())
                .andExpect(jsonPath("$[1].group.id").value(2))
                .andExpect(jsonPath("$[1].salary").value(5000.0))

                .andExpect(jsonPath("$[2].id").value(4))
                .andExpect(jsonPath("$[2].userName").value("teacher"))
                .andExpect(jsonPath("$[2].password").value("$2a$12$8RWIkuNE37xVh3Qj6qPZpOAENv1FPaffdtXpSNGrWJxCoe.PVk32q"))
                .andExpect(jsonPath("$[2].name").value("Ychitel"))
                .andExpect(jsonPath("$[2].age").value(12))
                .andExpect(jsonPath("$[2].roles[0].id").value(2))
                .andExpect(jsonPath("$[2].roles[0].name").value("TEACHER"))
                .andExpect(jsonPath("$[2].authorities").isEmpty())
                .andExpect(jsonPath("$[2].group.id").value(1))
                .andExpect(jsonPath("$[2].salary").value(10000.0));
    }

    @Test
    void getAllWhenListOfTeachersIsEmptyShouldReturnEmptyList() throws Exception {
        when(personService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/json/teachers")
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getTeacherWhenPathVariableIsCorrectShouldReturnTeacher() throws Exception {
        when(personService.find(teacher.getId())).thenReturn(Optional.of(teacher));

        mockMvc.perform(get("/json/teachers/{id}", teacher.getId())
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.userName").value("teacher"))
                .andExpect(jsonPath("$.password").value("$2a$12$8RWIkuNE37xVh3Qj6qPZpOAENv1FPaffdtXpSNGrWJxCoe.PVk32q"))
                .andExpect(jsonPath("$.name").value("Ychitel"))
                .andExpect(jsonPath("$.age").value(12))
                .andExpect(jsonPath("$.roles[0].id").value(2))
                .andExpect(jsonPath("$.roles[0].name").value("TEACHER"))
                .andExpect(jsonPath("$.authorities").isEmpty())
                .andExpect(jsonPath("$.group.id").value(1))
                .andExpect(jsonPath("$.salary").value(10000.0));
    }

    @Test
    void getTeacherWhenPathVariableIsIncorrectShouldReturnStatusNotFound() throws Exception {
        when(personService.find(-1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/json/teachers/{id}", -1)
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTeacherWhenFoundNotTeacherShouldReturnStatusNotFound() throws Exception {
        teacher.setRoles(Set.of(new Role().withId(1).withName("STUDENT").addPerson(teacher)));
        when(personService.find(4)).thenReturn(Optional.of(teacher));

        mockMvc.perform(get("/json/teachers/{id}", 4)
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveTeacherWhenTeacherIsAlreadyInTheDataBaseShouldReturnStatusBadRequest() throws Exception {
        when(personService.save(teacher)).thenThrow(DataBaseException.class);

        mockMvc.perform(post("/json/teachers")
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .content(new ObjectMapper().writeValueAsString(teacher))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveTeacherWhenRequestBodyIsNotTeacherShouldReturnStatusBadRequest() throws Exception {
        teacher.setRoles(Set.of(new Role().withId(1).withName("STUDENT").addPerson(teacher)));

        mockMvc.perform(post("/json/teachers")
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .content(new ObjectMapper().writeValueAsString(teacher))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveTeacherWhenTeachersUserNameIsNullShouldReturnStatusBadRequest() throws Exception {
        teacher.setUserName(null);
        when(personService.save(teacher)).thenThrow(DataBaseException.class);

        mockMvc.perform(post("/json/teachers")
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .content(new ObjectMapper().writeValueAsString(teacher))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveTeacherWhenTeacherIsValidShouldReturnTeacher() throws Exception {
        when(personService.save(teacher)).thenReturn(teacher);

        mockMvc.perform(post("/json/teachers")
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .content(new ObjectMapper().writeValueAsString(teacher))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.userName").value("teacher"))
                .andExpect(jsonPath("$.password").value("$2a$12$8RWIkuNE37xVh3Qj6qPZpOAENv1FPaffdtXpSNGrWJxCoe.PVk32q"))
                .andExpect(jsonPath("$.name").value("Ychitel"))
                .andExpect(jsonPath("$.age").value(12))
                .andExpect(jsonPath("$.roles[0].id").value(2))
                .andExpect(jsonPath("$.roles[0].name").value("TEACHER"))
                .andExpect(jsonPath("$.authorities").isEmpty())
                .andExpect(jsonPath("$.group.id").value(1))
                .andExpect(jsonPath("$.salary").value(10000.0));
    }

    @Test
    void updateTeacherWhenRequestBodyIsTeacherAndIdIsCorrectShouldReturnStudent() throws Exception {
        when(personService.update(teacher.getId(), teacher)).thenReturn(teacher);

        mockMvc.perform(put("/json/teachers/{id}", teacher.getId())
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .content(new ObjectMapper().writeValueAsString(teacher))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.userName").value("teacher"))
                .andExpect(jsonPath("$.password").value("$2a$12$8RWIkuNE37xVh3Qj6qPZpOAENv1FPaffdtXpSNGrWJxCoe.PVk32q"))
                .andExpect(jsonPath("$.name").value("Ychitel"))
                .andExpect(jsonPath("$.age").value(12))
                .andExpect(jsonPath("$.roles[0].id").value(2))
                .andExpect(jsonPath("$.roles[0].name").value("TEACHER"))
                .andExpect(jsonPath("$.authorities").isEmpty())
                .andExpect(jsonPath("$.group.id").value(1))
                .andExpect(jsonPath("$.salary").value(10000.0));
    }

    @Test
    void updateTeacherWhenIdIsNotEqualUserIdShouldReturnStatusBadRequest() throws Exception {
        mockMvc.perform(put("/json/teachers/{id}", -1)
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .content(new ObjectMapper().writeValueAsString(teacher))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Teacher id must be equal with id in path: " + -1 + " != " + teacher.getId()));
    }

    @Test
    void updateTeacherWhenUserIdAndPathVariableIdIsIncorrectShouldReturnStatusBadRequest() throws Exception {
        teacher.setId(-1);
        int pathVariable = -1;
        when(personService.update(pathVariable, teacher)).thenThrow(DataBaseException.class);

        mockMvc.perform(put("/json/teachers/{id}", pathVariable)
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .content(new ObjectMapper().writeValueAsString(teacher))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateTeacherWhenRequestBodyHasNotTeacherShouldReturnStatusBadRequest() throws Exception {
        teacher.setRoles(Set.of(new Role().withId(1).withName("STUDENT").addPerson(teacher)));

        mockMvc.perform(put("/json/teachers/{id}", teacher.getId())
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .content(new ObjectMapper().writeValueAsString(teacher))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Person is not teacher"));
    }

    @Test
    void deleteTeacherWhenIdIsCorrectShouldReturnTeacher() throws Exception {
        when(personService.find(teacher.getId())).thenReturn(Optional.of(teacher));
        when(personService.remove(teacher)).thenReturn(teacher);

        mockMvc.perform(delete("/json/teachers/{id}", teacher.getId())
                .header("Authorization", "Basic YWRtaW46MTIz")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.userName").value("teacher"))
                .andExpect(jsonPath("$.password").value("$2a$12$8RWIkuNE37xVh3Qj6qPZpOAENv1FPaffdtXpSNGrWJxCoe.PVk32q"))
                .andExpect(jsonPath("$.name").value("Ychitel"))
                .andExpect(jsonPath("$.age").value(12))
                .andExpect(jsonPath("$.roles[0].id").value(2))
                .andExpect(jsonPath("$.roles[0].name").value("TEACHER"))
                .andExpect(jsonPath("$.authorities").isEmpty())
                .andExpect(jsonPath("$.group.id").value(1))
                .andExpect(jsonPath("$.salary").value(10000.0));
    }

    @Test
    void deleteTeacherWhenIdIsIncorrectShouldReturnStatusNotFound() throws Exception {
        when(personService.find(-1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/json/teachers/{id}", -1)
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTeacherWhenFoundNotTeacherShouldReturnStatusNotFound() throws Exception {
        teacher.setRoles(Set.of(new Role().withId(1).withName("STUDENT").addPerson(teacher)));
        when(personService.find(teacher.getId())).thenReturn(Optional.of(teacher));

        mockMvc.perform(delete("/json/teachers/{id}", teacher.getId())
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}