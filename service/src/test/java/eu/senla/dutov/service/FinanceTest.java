package eu.senla.dutov.service;

import eu.senla.dutov.exception.IncorrectValueException;
import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.model.people.Person;
import eu.senla.dutov.model.people.Student;
import eu.senla.dutov.model.people.Teacher;
import eu.senla.dutov.service.person.PersonService;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FinanceTest {
    public static final int CURRENT_MONTH = LocalDate.now().getMonthValue();
    static Map<Integer, Map<Integer, Double>> salaryHistory = new ConcurrentHashMap<>();
    static Teacher teacher;

    PersonService personService = mock(PersonService.class);

    @BeforeAll
    static void beforeAll() {
        teacher = new Teacher()
                .withId(1)
                .withSalary(1000);
        Map<Integer, Double> salaries = new HashMap<>();
        for (int i = 1; i < CURRENT_MONTH; i++) {
            salaries.put(i, i * 110.0);
        }
        salaries.put(CURRENT_MONTH, teacher.getSalary());
        salaryHistory.put(teacher.getId(), salaries);
    }

    @Test
    void GetSalary_WithIdEqualOne_ShouldReturnOneThousand() {
        Finance finance = getFinance(personService, 1, Optional.of(teacher));
        Double actual = finance.getSalary(1);
        Double expected = 1000.0;
        assertEquals(expected, actual);
    }

    @Test
    void GetSalary_WithIdInvalid_ShouldThrowException() {
        Finance finance = getFinance(personService, 0, Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            finance.getSalary(0);
        });
    }

    @Test
    void GetSalary_WithRoleNotTeacher_ShouldThrowException() {
        Finance finance = getFinance(personService, 1, Optional.of(new Student()));
        assertThrows(NotFoundException.class, () -> finance.getSalary(1));
    }

    private Finance getFinance(PersonService personService, int id, Optional<Person> person) {
        when(personService.findById(id)).thenReturn(person);
        return new Finance(personService);
    }

    @Test
    void GetAverageSalary_WithIdInvalid_ShouldThrowException() {
        when(personService.findById(0)).thenReturn(Optional.empty());
        Finance finance = new Finance(personService);
        assertThrows(NotFoundException.class, () -> finance.getAverageSalary(0, 1, 2));
    }

    @Test
    void GetAverageSalary_WithRoleNotTeacher_ShouldThrowException() {
        when(personService.findById(1)).thenReturn(Optional.of(new Student()));
        Finance finance = new Finance(personService);
        assertThrows(NotFoundException.class, () -> finance.getAverageSalary(1, 1, 2));
    }

    @Test
    void GetAverageSalary_WithMinRangeIsIncorrect_ShouldThrowException() {
        when(personService.findById(1)).thenReturn(Optional.of(teacher));
        Finance finance = new Finance(personService);
        assertThrows(IncorrectValueException.class, () -> finance.getAverageSalary(1, 0, 2));
    }

    @Test
    void GetAverageSalary_WithMinRangeMoreThanMaxRange_ShouldThrowException() {
        when(personService.findById(1)).thenReturn(Optional.of(teacher));
        Finance finance = new Finance(personService);
        assertThrows(IncorrectValueException.class, () -> finance.getAverageSalary(1, 2, 1));
    }

    @Test
    void GetAverageSalary_WithMaxRangeMoreThatCurrentMonth_ShouldThrowException() {
        when(personService.findById(1)).thenReturn(Optional.of(teacher));
        Finance finance = new Finance(personService);
        assertThrows(IncorrectValueException.class,
                () -> finance.getAverageSalary(1, 1, CURRENT_MONTH + 1));
    }

    @Test
    void GetAverageSalary_WithCorrectData_ShouldReturnAverageSalary() throws NoSuchFieldException, IllegalAccessException {
        when(personService.findById(1)).thenReturn(Optional.of(teacher));
        Finance finance = new Finance(personService);
        Field salaryHistory = finance.getClass().getDeclaredField("salaryHistory");
        salaryHistory.setAccessible(true);
        salaryHistory.set(finance, FinanceTest.salaryHistory);
        Double actual = finance.getAverageSalary(1, 1, CURRENT_MONTH);
        Double expected = FinanceTest.salaryHistory.get(1).values().stream().reduce(Double::sum).get() / FinanceTest.salaryHistory.get(1).size();
        assertEquals(expected, actual);
    }
}