package eu.senla.dutov.controller.integration;

import eu.senla.dutov.service.Finance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin")
@TestPropertySource(value = "/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = AFTER_TEST_METHOD)
class SalaryTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getSalaryWhenIdIsCorrectShouldReturnSalary() throws Exception {
        mockMvc.perform(get("/json/salaries/{id}", 4)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(10000.0));
    }

    @Test
    void getSalaryWhenIdIsIncorrectShouldReturnStatusBadRequest() throws Exception {
        mockMvc.perform(get("/json/salaries/{id}", -1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getSalaryWhenIdIsCorrectButTeacherNotFoundShouldReturnStatusNotFound() throws Exception {
        mockMvc.perform(get("/json/salaries/{id}", 7)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAverageSalaryWhenParametersAreCorrectShouldReturnAverageSalary() throws Exception {
        mockMvc.perform(get("/json/salaries/{id}/average/minRange={min}&maxRange={max}", 4, 1, 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    void getAverageSalaryWhenIdIsIncorrectShouldReturnStatusBadRequest() throws Exception {
        mockMvc.perform(get("/json/salaries/{id}/average/minRange={min}&maxRange={max}", -1, -1, -2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAverageSalaryWhenMaxRangeMoreThanCurrentMonthShouldReturnStatusBadRequest() throws Exception {
        mockMvc.perform(get("/json/salaries/{id}/average/minRange={min}&maxRange={max}", 4, 1, Finance.CURRENT_MONTH + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAverageSalaryWhenTeacherIsNotFoundShouldReturnStatusNotFound() throws Exception {
        mockMvc.perform(get("/json/salaries/{id}/average/minRange={min}&maxRange={max}", 7, 1, 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
