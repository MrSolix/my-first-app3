package eu.senla.dutov.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.dutov.model.dto.ResponseStudentDto;
import eu.senla.dutov.service.person.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static eu.senla.dutov.controller.util.ConstantClass.INCORRECT_ID;
import static eu.senla.dutov.controller.util.ConstantClass.OTHER_STUDENT_ID;
import static eu.senla.dutov.controller.util.ConstantClass.STUDENT_ID;
import static eu.senla.dutov.controller.util.ConstantClass.TEACHER_ID;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin")
@TestPropertySource(value = "/application-test.properties")
@Sql(value = "/create-user-before.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(value = "/create-user-after.sql", executionPhase = AFTER_TEST_METHOD)
class StudentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    @Test
    void getAllStudents() throws Exception {
        mockMvc.perform(get("/json/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getStudentWhenIdIsCorrectShouldReturnStudent() throws Exception {
        mockMvc.perform(get("/json/students/{id}", STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getStudentWhenIdIsCorrectButNotStudentShouldReturnStatusNotFound() throws Exception {
        mockMvc.perform(get("/json/students/{id}", TEACHER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getStudentWhenIdIsIncorrectShouldReturnStatusNotFound() throws Exception {
        mockMvc.perform(get("/json/students/{id}", INCORRECT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveStudentWhenRequestBodyIsValidShouldReturnStudent() throws Exception {
        ResponseStudentDto responseStudentDto = new ResponseStudentDto();
        responseStudentDto.setUserName("testStudent");

        mockMvc.perform(post("/json/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(responseStudentDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void saveStudentWhenRequestBodyIsStudentWhichIsAlreadyExistShouldReturnStatusBadRequest() throws Exception {
        ResponseStudentDto student = studentService.findById(STUDENT_ID);
        student.setId(null);

        mockMvc.perform(post("/json/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(student)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveStudentWhenRequestBodyIsNotValidShouldReturnStatusBadRequest() throws Exception {
        ResponseStudentDto student = studentService.findById(STUDENT_ID);
        student.setId(null);
        student.setUserName("123");
        student.setAge(0);

        mockMvc.perform(post("/json/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(student)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateStudentWhenDataIsCorrectShouldReturnStudent() throws Exception {
        ResponseStudentDto student = studentService.findById(STUDENT_ID);
        student.setAge(99);

        mockMvc.perform(put("/json/students/{id}", student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(student)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateStudentWhenIdIsIncorrectShouldReturnStatusBadRequest() throws Exception {
        ResponseStudentDto student = studentService.findById(STUDENT_ID);

        mockMvc.perform(put("/json/students/{id}", INCORRECT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(student)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateStudentWhenRequestBodyIsNotValidShouldReturnStatusBadRequest() throws Exception {
        ResponseStudentDto student = studentService.findById(STUDENT_ID);
        student.setUserName("123");
        student.setAge(0);

        mockMvc.perform(put("/json/students/{id}", student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(student)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateStudentWhenPathVariableIdIsNotEqualStudentIdShouldReturnStatusBadRequest() throws Exception {
        ResponseStudentDto student = studentService.findById(STUDENT_ID);

        mockMvc.perform(put("/json/students/{id}", OTHER_STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(student)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateStudentWhenStudentIsNotFoundShouldReturnStatusNotFound() throws Exception {
        ResponseStudentDto student = studentService.findById(STUDENT_ID);
        student.setId(8);

        mockMvc.perform(put("/json/students/{id}", student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(student)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStudentWhenIdIsCorrectShouldReturnStatusOk() throws Exception {
        mockMvc.perform(delete("/json/students/{id}", STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteStudentWhenStudentIsNotFoundShouldReturnStatusNotFound() throws Exception {
        mockMvc.perform(delete("/json/students/{id}", TEACHER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStudentWhenIdIsIncorrectShouldReturnStatusBadRequest() throws Exception {
        mockMvc.perform(delete("/json/students/{id}", INCORRECT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
