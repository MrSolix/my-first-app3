package eu.senla.myfirstapp.app.controller.salary;

import eu.senla.myfirstapp.app.exception.IncorrectValueException;
import eu.senla.myfirstapp.app.exception.NotFoundException;
import eu.senla.myfirstapp.app.service.facade.Finance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SalaryJsonControllerTest {

    private static MockMvc mockMvc;

    private static final Finance finance = mock(Finance.class);

    @BeforeAll
    static void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new SalaryJsonController(finance)).build();
    }

    @Test
    void getSalary_WithUserIdIsThree_ShouldReturnFiveThousand() throws Exception {
        when(finance.getSalary(3)).thenReturn(5000.0);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/json/salaries/{id}", 3)
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(5000.0));
    }

    @Test
    void getSalary_WhenUserIdIsIncorrect_ShouldThrowException() throws Exception {
        when(finance.getSalary(-1)).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/json/salaries/{id}", -1)
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void averageSalary_WhenDataIsCorrect_ShouldReturnDouble() throws Exception {
        when(finance.getAverageSalary(3, 1, 5)).thenReturn(1220.0);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/json/salaries/{id}/average/minRange={min}&maxRange={max}", 3, 1, 5)
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1220.0));
    }

    @Test
    void averageSalary_WhenUserIdIsIncorrect_ShouldThrowException() throws Exception {
        when(finance.getAverageSalary(-1, 1, 5)).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/json/salaries/{id}/average/minRange={min}&maxRange={max}", -1, 1, 5)
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void averageSalary_WhenRangeIsIncorrect_ShouldThrowException() throws Exception {
        when(finance.getAverageSalary(3, -1, 5)).thenThrow(IncorrectValueException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/json/salaries/{id}/average/minRange={min}&maxRange={max}", 3, -1, 5)
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}