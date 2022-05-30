package eu.senla.dutov.controller.salary;

import eu.senla.dutov.exception.IncorrectValueException;
import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.handler.ControllerExceptionHandler;
import eu.senla.dutov.service.Finance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SalaryJsonControllerTest {

    private static MockMvc mockMvc;

    private static final Finance finance = mock(Finance.class);

    @BeforeAll
    static void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new SalaryJsonController(finance))
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void getSalaryWithUserIdIsThreeShouldReturnFiveThousand() throws Exception {
        when(finance.getSalary(3)).thenReturn(5000.0);

        mockMvc.perform(get("/json/salaries/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(5000.0));
    }

    @Test
    void getSalaryWhenUserIdIsIncorrectShouldThrowException() throws Exception {
        when(finance.getSalary(-1)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/json/salaries/{id}", -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void averageSalaryWhenDataIsCorrectShouldReturnDouble() throws Exception {
        when(finance.getAverageSalary(3, 1, 5)).thenReturn(1220.0);

        mockMvc.perform(get("/json/salaries/{id}/average/minRange={min}&maxRange={max}", 3, 1, 5)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1220.0));
    }

    @Test
    void averageSalaryWhenUserIdIsIncorrectShouldThrowException() throws Exception {
        when(finance.getAverageSalary(-1, 1, 5)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/json/salaries/{id}/average/minRange={min}&maxRange={max}", -1, 1, 5)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void averageSalaryWhenRangeIsIncorrectShouldThrowException() throws Exception {
        when(finance.getAverageSalary(3, -1, 5)).thenThrow(IncorrectValueException.class);

        mockMvc.perform(get("/json/salaries/{id}/average/minRange={min}&maxRange={max}", 3, -1, 5)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}