package eu.senla.dutov.controller.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.dutov.dto.GradeDto;
import eu.senla.dutov.dto.GroupDto;
import eu.senla.dutov.dto.RequestStudentDto;
import eu.senla.dutov.exception.IncorrectValueException;
import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.handler.ControllerExceptionHandler;
import eu.senla.dutov.mapper.StudentMapper;
import eu.senla.dutov.model.people.Student;
import eu.senla.dutov.service.user.StudentService;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudentJsonControllerTest {

    private static MockMvc mockMvc;
    private static final StudentMapper studentMapper = Mappers.getMapper(StudentMapper.class);
    private static final StudentService studentService = mock(StudentService.class);

    private static RequestStudentDto requestSlavikDto;
    private static Student student;

    @BeforeAll
    static void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new StudentJsonController(studentService))
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @BeforeEach
    void setStudent() {
        GroupDto GroupDtoOneForSlavik = new GroupDto();
        GroupDtoOneForSlavik.setId(19);

        GroupDto GroupDtoTwoForSlavik = new GroupDto();
        GroupDtoTwoForSlavik.setId(29);

        GradeDto gradeDtoOneForSlavik = new GradeDto();
        gradeDtoOneForSlavik.setId(176);
        gradeDtoOneForSlavik.setThemeName("Math");
        gradeDtoOneForSlavik.setGrade(55);

        GradeDto gradeDtoTwoForSlavik = new GradeDto();
        gradeDtoTwoForSlavik.setId(177);
        gradeDtoTwoForSlavik.setThemeName("Math");
        gradeDtoTwoForSlavik.setGrade(32);

        GradeDto gradeDtoThreeForSlavik = new GradeDto();
        gradeDtoThreeForSlavik.setId(178);
        gradeDtoThreeForSlavik.setThemeName("English");
        gradeDtoThreeForSlavik.setGrade(78);

        GradeDto gradeDtoFourForSlavik = new GradeDto();
        gradeDtoFourForSlavik.setId(179);
        gradeDtoFourForSlavik.setThemeName("English");
        gradeDtoFourForSlavik.setGrade(89);

        GradeDto gradeDtoFiveForSlavik = new GradeDto();
        gradeDtoFiveForSlavik.setId(175);
        gradeDtoFiveForSlavik.setThemeName("abs");
        gradeDtoFiveForSlavik.setGrade(54);

        requestSlavikDto = new RequestStudentDto();
        requestSlavikDto.setId(6);
        requestSlavikDto.setUserName("student");
        requestSlavikDto.setPassword("$2a$12$iDPdhEo8ewcqwqagAVjYJ.SMES4piBWmusiZ76uoR.vKCI1aceYBW");
        requestSlavikDto.setName("Slavik");
        requestSlavikDto.setAge(26);
        requestSlavikDto.setGroups(Set.of(
                GroupDtoOneForSlavik,
                GroupDtoTwoForSlavik));
        requestSlavikDto.setGrades(List.of(
                gradeDtoOneForSlavik,
                gradeDtoTwoForSlavik,
                gradeDtoThreeForSlavik,
                gradeDtoFourForSlavik,
                gradeDtoFiveForSlavik
        ));

        student = studentMapper.toModel(requestSlavikDto);
    }

    @Test
    void getAllWhenListOfStudentsAreEmptyShouldReturnEmptyList() throws Exception {
        when(studentService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/json/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getAllWhenListOfStudentsAreNotEmptyShouldReturnStudentList() throws Exception {
        when(studentService.findAll()).thenReturn(studentMapper.toDTOList(List.of(student)));

        mockMvc.perform(get("/json/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(6))
                .andExpect(jsonPath("$[0].userName").value("student"))
                .andExpect(jsonPath("$[0].name").value("Slavik"))
                .andExpect(jsonPath("$[0].age").value(26))
                .andExpect(jsonPath("$[0].groups[0].id").isNumber())
                .andExpect(jsonPath("$[0].groups[1].id").isNumber())
                .andExpect(jsonPath("$[0].grades[0].id").value(176))
                .andExpect(jsonPath("$[0].grades[0].themeName").value("Math"))
                .andExpect(jsonPath("$[0].grades[0].grade").value(55))
                .andExpect(jsonPath("$[0].grades[1].id").value(177))
                .andExpect(jsonPath("$[0].grades[1].themeName").value("Math"))
                .andExpect(jsonPath("$[0].grades[1].grade").value(32))
                .andExpect(jsonPath("$[0].grades[2].id").value(178))
                .andExpect(jsonPath("$[0].grades[2].themeName").value("English"))
                .andExpect(jsonPath("$[0].grades[2].grade").value(78))
                .andExpect(jsonPath("$[0].grades[3].id").value(179))
                .andExpect(jsonPath("$[0].grades[3].themeName").value("English"))
                .andExpect(jsonPath("$[0].grades[3].grade").value(89))
                .andExpect(jsonPath("$[0].grades[4].id").value(175))
                .andExpect(jsonPath("$[0].grades[4].themeName").value("abs"))
                .andExpect(jsonPath("$[0].grades[4].grade").value(54));
    }

    @Test
    void getStudentWhenUserIdIsCorrectShouldReturnStudent() throws Exception {
        when(studentService.findById(6)).thenReturn(studentMapper.toDTO(student));

        mockMvc.perform(get("/json/students/{id}", 6)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.userName").value("student"))
                .andExpect(jsonPath("$.name").value("Slavik"))
                .andExpect(jsonPath("$.age").value(26))
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
        when(studentService.findById(-1)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/json/students/{id}", -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void saveStudentWhenStudentIsValidShouldReturnThisStudent() throws Exception {
        RequestStudentDto student = new RequestStudentDto();
        student.setUserName("xcv");
        when(studentService.save(student)).thenReturn(studentMapper.toDTO(studentMapper.toModel(student)));

        mockMvc.perform(post("/json/students")
                        .content(new ObjectMapper().writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("xcv"));
    }

    @Test
    void saveStudentWhenStudentIsAlreadyInTheDataBaseShouldReturnStatusBadRequest() throws Exception {
        when(studentService.save(requestSlavikDto)).thenThrow(new DataAccessException("...") {
        });

        mockMvc.perform(post("/json/students")
                        .content(new ObjectMapper().writeValueAsString(requestSlavikDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateStudentWhenRequestBodyIsStudentAndIdIsCorrectShouldReturnStudent() throws Exception {
        when(studentService.update(requestSlavikDto.getId(), requestSlavikDto)).thenReturn(studentMapper.toDTO(student));

        mockMvc.perform(put("/json/students/{id}", requestSlavikDto.getId())
                        .content(new ObjectMapper().writeValueAsString(requestSlavikDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.userName").value("student"))
                .andExpect(jsonPath("$.name").value("Slavik"))
                .andExpect(jsonPath("$.age").value(26))
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
        requestSlavikDto.setId(-1);
        when(studentService.update(requestSlavikDto.getId(), requestSlavikDto)).thenThrow(ConstraintViolationException.class);

        mockMvc.perform(put("/json/students/{id}", -1)
                        .content(new ObjectMapper().writeValueAsString(requestSlavikDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateStudentWhenStudentIdIsNotEqualPathVariableIdShouldReturnStatusBadRequest() throws Exception {
        int pathVariable = -1;
        when(studentService.update(pathVariable, requestSlavikDto)).thenThrow(IncorrectValueException.class);

        mockMvc.perform(put("/json/students/{id}", pathVariable)
                        .content(new ObjectMapper().writeValueAsString(requestSlavikDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteStudentWhenPathVariableIsIncorrectShouldReturnStatusNotFound() throws Exception {
        doThrow(ConstraintViolationException.class).when(studentService).remove(-1);
        mockMvc.perform(delete("/json/students/{id}", -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteStudentWhenPathVariableIsCorrectShouldReturnStudent() throws Exception {
        when(studentService.findById(requestSlavikDto.getId())).thenReturn(studentMapper.toDTO(student));

        mockMvc.perform(delete("/json/students/{id}", requestSlavikDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}