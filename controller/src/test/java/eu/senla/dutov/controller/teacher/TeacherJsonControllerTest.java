package eu.senla.dutov.controller.teacher;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.handler.ControllerExceptionHandler;
import eu.senla.dutov.mapper.TeacherMapper;
import eu.senla.dutov.model.dto.GroupDto;
import eu.senla.dutov.model.dto.RequestTeacherDto;
import eu.senla.dutov.model.people.Teacher;
import eu.senla.dutov.service.person.TeacherService;
import java.util.Collections;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
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

    private static final TeacherService teacherService = mock(TeacherService.class);
    private static final TeacherMapper teacherMapper = Mappers.getMapper(TeacherMapper.class);

    private static RequestTeacherDto requestTeacherDto;

    private static Teacher teacherOne;
    private static Teacher teacherTwo;
    private static Teacher teacherThree;

    @BeforeAll
    static void setUp() {
        mockMvc = standaloneSetup(new TeacherJsonController(teacherService))
                .setControllerAdvice(ControllerExceptionHandler.class)
                .build();
    }

    @AfterEach
    void clear() {
        Mockito.clearInvocations(teacherService);
    }

    @BeforeEach
    void setTeacher() {
        requestTeacherDto = new RequestTeacherDto();
        requestTeacherDto.setId(4);
        requestTeacherDto.setUserName("teacher");
        requestTeacherDto.setPassword("$2a$12$8RWIkuNE37xVh3Qj6qPZpOAENv1FPaffdtXpSNGrWJxCoe.PVk32q");
        requestTeacherDto.setName("Ychitel");
        requestTeacherDto.setAge(12);
        GroupDto groupDto = new GroupDto();
        groupDto.setId(1);
        requestTeacherDto.setGroup(groupDto);
        requestTeacherDto.setSalary(10000.0);

        RequestTeacherDto requestCheburatorDto = new RequestTeacherDto();
        requestCheburatorDto.setId(1);
        requestCheburatorDto.setUserName("teacher1");
        requestCheburatorDto.setPassword("$2a$12$3sntQS7oN3j/kLxHGtrUSOgXvd6USe3awF9.nLlTpgswWVSjjqHCG");
        requestCheburatorDto.setName("Cheburator");
        requestCheburatorDto.setAge(124);
        requestCheburatorDto.setSalary(2000.0);

        RequestTeacherDto requestKekDto = new RequestTeacherDto();
        requestKekDto.setId(3);
        requestKekDto.setUserName("teacher12");
        requestKekDto.setPassword("$2a$12$fRb6vuqoZlPp2bdWXd4TZ.Fj./Dx5Or47AU9xJc/TKlxZpy8YYG1u");
        requestKekDto.setName("kek");
        requestKekDto.setAge(12);
        GroupDto groupDto1 = new GroupDto();
        groupDto1.setId(2);
        requestKekDto.setGroup(groupDto1);
        requestKekDto.setSalary(5000.0);

        teacherOne = teacherMapper.toModel(requestTeacherDto);
        teacherTwo = teacherMapper.toModel(requestCheburatorDto);
        teacherThree = teacherMapper.toModel(requestKekDto);
    }

    @Test
    void getAllWhenListOfTeachersHasTeachersAndOneStudentShouldReturnAllTeachers() throws Exception {
        when(teacherService.findAll()).thenReturn(List.of(
                teacherMapper.toDTO(teacherTwo),
                teacherMapper.toDTO(teacherThree),
                teacherMapper.toDTO(teacherOne)));

        mockMvc.perform(get("/json/teachers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].userName").value("teacher1"))
                .andExpect(jsonPath("$[0].name").value("Cheburator"))
                .andExpect(jsonPath("$[0].age").value(124))
                .andExpect(jsonPath("$[0].salary").value(2000.0))

                .andExpect(jsonPath("$[1].id").value(3))
                .andExpect(jsonPath("$[1].userName").value("teacher12"))
                .andExpect(jsonPath("$[1].name").value("kek"))
                .andExpect(jsonPath("$[1].age").value(12))
                .andExpect(jsonPath("$[1].group.id").value(2))
                .andExpect(jsonPath("$[1].salary").value(5000.0))

                .andExpect(jsonPath("$[2].id").value(4))
                .andExpect(jsonPath("$[2].userName").value("teacher"))
                .andExpect(jsonPath("$[2].name").value("Ychitel"))
                .andExpect(jsonPath("$[2].age").value(12))
                .andExpect(jsonPath("$[2].group.id").value(1))
                .andExpect(jsonPath("$[2].salary").value(10000.0));
    }

    @Test
    void getAllWhenListOfTeachersIsEmptyShouldReturnEmptyList() throws Exception {
        when(teacherService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/json/teachers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getTeacherWhenPathVariableIsCorrectShouldReturnTeacher() throws Exception {
        when(teacherService.findById(requestTeacherDto.getId())).thenReturn(teacherMapper.toDTO(teacherOne));

        mockMvc.perform(get("/json/teachers/{id}", requestTeacherDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.userName").value("teacher"))
                .andExpect(jsonPath("$.name").value("Ychitel"))
                .andExpect(jsonPath("$.age").value(12))
                .andExpect(jsonPath("$.group.id").value(1))
                .andExpect(jsonPath("$.salary").value(10000.0));
    }

    @Test
    void getTeacherWhenPathVariableIsIncorrectShouldReturnStatusNotFound() throws Exception {
        when(teacherService.findById(-1)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/json/teachers/{id}", -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveTeacherWhenTeacherIsAlreadyInTheDataBaseShouldReturnStatusBadRequest() throws Exception {
        when(teacherService.save(requestTeacherDto)).thenThrow(new DataAccessException("...") {
        });

        mockMvc.perform(post("/json/teachers")
                        .content(new ObjectMapper().writeValueAsString(requestTeacherDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveTeacherWhenTeachersUserNameIsNullShouldReturnStatusBadRequest() throws Exception {
        requestTeacherDto.setUserName(null);
        when(teacherService.save(requestTeacherDto)).thenThrow(new DataAccessException("...") {
        });

        mockMvc.perform(post("/json/teachers")
                        .content(new ObjectMapper().writeValueAsString(requestTeacherDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveTeacherWhenTeacherIsValidShouldReturnTeacher() throws Exception {
        when(teacherService.save(requestTeacherDto)).thenReturn(teacherMapper.toDTO(teacherOne));

        mockMvc.perform(post("/json/teachers")
                        .content(new ObjectMapper().writeValueAsString(requestTeacherDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.userName").value("teacher"))
                .andExpect(jsonPath("$.name").value("Ychitel"))
                .andExpect(jsonPath("$.age").value(12))
                .andExpect(jsonPath("$.group.id").value(1))
                .andExpect(jsonPath("$.salary").value(10000.0));
    }

    @Test
    void updateTeacherWhenRequestBodyIsTeacherAndIdIsCorrectShouldReturnStudent() throws Exception {
        when(teacherService.update(requestTeacherDto.getId(), requestTeacherDto)).thenReturn(teacherMapper.toDTO(teacherOne));

        mockMvc.perform(put("/json/teachers/{id}", requestTeacherDto.getId())
                        .content(new ObjectMapper().writeValueAsString(requestTeacherDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.userName").value("teacher"))
                .andExpect(jsonPath("$.name").value("Ychitel"))
                .andExpect(jsonPath("$.age").value(12))
                .andExpect(jsonPath("$.group.id").value(1))
                .andExpect(jsonPath("$.salary").value(10000.0));
    }

    @Test
    void updateTeacherWhenIdIsNotEqualUserIdShouldReturnStatusBadRequest() throws Exception {
        requestTeacherDto.setId(-1);
        when(teacherService.update(requestTeacherDto.getId(), requestTeacherDto)).thenThrow(ConstraintViolationException.class);

        mockMvc.perform(put("/json/teachers/{id}", -1)
                        .content(new ObjectMapper().writeValueAsString(requestTeacherDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteTeacherWhenIdIsCorrectShouldReturnTeacher() throws Exception {
        when(teacherService.findById(requestTeacherDto.getId())).thenReturn(teacherMapper.toDTO(teacherOne));

        mockMvc.perform(delete("/json/teachers/{id}", requestTeacherDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTeacherWhenIdIsIncorrectShouldReturnStatusNotFound() throws Exception {
        doThrow(ConstraintViolationException.class).when(teacherService).remove(-1);

        mockMvc.perform(delete("/json/teachers/{id}", -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}