package eu.senla.dutov.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.dutov.model.dto.RequestTeacherDto;
import eu.senla.dutov.model.dto.ResponseTeacherDto;
import eu.senla.dutov.service.person.TeacherService;
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
import static eu.senla.dutov.controller.util.ConstantClass.OTHER_TEACHER_ID;
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
class TeacherTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherService teacherService;

    @Test
    void getAllTeachers() throws Exception {
        mockMvc.perform(get("/json/teachers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getTeacherWhenIdIsCorrectShouldReturnTeacher() throws Exception {
        mockMvc.perform(get("/json/teachers/{id}", TEACHER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getTeacherWhenIdIsCorrectButNotTeacherShouldReturnStatusNotFound() throws Exception {
        mockMvc.perform(get("/json/teachers/{id}", STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getTeacherWhenIdIsIncorrectShouldReturnStatusNotFound() throws Exception {
        mockMvc.perform(get("/json/teachers/{id}", INCORRECT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveTeacherWhenRequestBodyIsValidShouldReturnTeacher() throws Exception {
        RequestTeacherDto requestTeacherDto = new RequestTeacherDto();
        requestTeacherDto.setUserName("testTeacher");

        mockMvc.perform(post("/json/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestTeacherDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void saveTeacherWhenRequestBodyIsTeacherWhichIsAlreadyExistShouldReturnStatusBadRequest() throws Exception {
        ResponseTeacherDto teacher = teacherService.findById(TEACHER_ID);
        teacher.setId(null);

        mockMvc.perform(post("/json/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(teacher)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveTeacherWhenRequestBodyIsNotValidShouldReturnStatusBadRequest() throws Exception {
        ResponseTeacherDto teacher = teacherService.findById(TEACHER_ID);
        teacher.setId(null);
        teacher.setUserName("123");
        teacher.setAge(0);

        mockMvc.perform(post("/json/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(teacher)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateTeacherWhenDataIsCorrectShouldReturnTeacher() throws Exception {
        ResponseTeacherDto teacher = teacherService.findById(TEACHER_ID);
        teacher.setAge(99);

        mockMvc.perform(put("/json/teachers/{id}", teacher.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(teacher)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateTeacherWhenIdIsIncorrectShouldReturnStatusBadRequest() throws Exception {
        ResponseTeacherDto teacher = teacherService.findById(TEACHER_ID);

        mockMvc.perform(put("/json/teachers/{id}", INCORRECT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(teacher)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateTeacherWhenRequestBodyIsNotValidShouldReturnStatusBadRequest() throws Exception {
        ResponseTeacherDto teacher = teacherService.findById(TEACHER_ID);
        teacher.setUserName("123");
        teacher.setAge(0);

        mockMvc.perform(put("/json/teachers/{id}", teacher.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(teacher)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateTeacherWhenPathVariableIdIsNotEqualTeacherIdShouldReturnStatusBadRequest() throws Exception {
        ResponseTeacherDto teacher = teacherService.findById(TEACHER_ID);

        mockMvc.perform(put("/json/teachers/{id}", OTHER_TEACHER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(teacher)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateTeacherWhenTeacherIsNotFoundShouldReturnStatusNotFound() throws Exception {
        ResponseTeacherDto teacher = teacherService.findById(TEACHER_ID);
        teacher.setId(8);

        mockMvc.perform(put("/json/teachers/{id}", teacher.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(teacher)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTeacherWhenIdIsCorrectShouldReturnStatusOk() throws Exception {
        mockMvc.perform(delete("/json/teachers/{id}", TEACHER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteTeacherWhenTeacherIsNotFoundShouldReturnStatusNotFound() throws Exception {
        mockMvc.perform(delete("/json/teachers/{id}", STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTeacherWhenIdIsIncorrectShouldReturnStatusBadRequest() throws Exception {
        mockMvc.perform(delete("/json/teachers/{id}", INCORRECT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}