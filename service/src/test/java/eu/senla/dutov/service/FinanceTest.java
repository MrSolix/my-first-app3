package eu.senla.dutov.service;

import eu.senla.dutov.exception.IncorrectValueException;
import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.model.people.Teacher;
import eu.senla.dutov.service.person.TeacherService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FinanceTest {
    public static final int CURRENT_MONTH = LocalDate.now().getMonthValue();
    static Map<Integer, Map<Integer, Double>> salaryHistory = new ConcurrentHashMap<>();
    static Teacher teacher;

    TeacherService teacherService = mock(TeacherService.class);

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
        Finance finance = getFinance(teacherService, 1, teacher);
        Double actual = finance.getSalary(1);
        Double expected = 1000.0;
        assertEquals(expected, actual);
    }

    @Test
    void GetSalary_WithIdInvalid_ShouldThrowException() {
        Finance finance = getFinance(teacherService, 0, null);
        assertThrows(NotFoundException.class, () -> finance.getSalary(0));
    }

    private Finance getFinance(TeacherService teacherService, int id, Teacher teacher) {
        when(teacherService.findById(id)).thenReturn(Optional.ofNullable(teacher));
        return new Finance(teacherService);
    }

    @Test
    void GetAverageSalary_WithIdInvalid_ShouldThrowException() {
        when(teacherService.findById(0)).thenReturn(Optional.empty());
        Finance finance = new Finance(teacherService);
        assertThrows(NotFoundException.class, () -> finance.getAverageSalary(0, 1, 2));
    }

    @Test
    void GetAverageSalary_WithMinRangeIsIncorrect_ShouldThrowException() {
        when(teacherService.findById(1)).thenReturn(Optional.of(teacher));
        Finance finance = new Finance(teacherService);
        assertThrows(IncorrectValueException.class, () -> finance.getAverageSalary(1, 0, 2));
    }

    @Test
    void GetAverageSalary_WithMinRangeMoreThanMaxRange_ShouldThrowException() {
        when(teacherService.findById(1)).thenReturn(Optional.of(teacher));
        Finance finance = new Finance(teacherService);
        assertThrows(IncorrectValueException.class, () -> finance.getAverageSalary(1, 2, 1));
    }

    @Test
    void GetAverageSalary_WithMaxRangeMoreThatCurrentMonth_ShouldThrowException() {
        when(teacherService.findById(1)).thenReturn(Optional.of(teacher));
        Finance finance = new Finance(teacherService);
        assertThrows(IncorrectValueException.class,
                () -> finance.getAverageSalary(1, 1, CURRENT_MONTH + 1));
    }

    @Test
    void GetAverageSalary_WithCorrectData_ShouldReturnAverageSalary() throws NoSuchFieldException, IllegalAccessException {
        when(teacherService.findById(1)).thenReturn(Optional.of(teacher));
        Finance finance = new Finance(teacherService);
        Field salaryHistory = finance.getClass().getDeclaredField("salaryHistory");
        salaryHistory.setAccessible(true);
        salaryHistory.set(finance, FinanceTest.salaryHistory);
        Double actual = finance.getAverageSalary(1, 1, CURRENT_MONTH);
        Double expected = FinanceTest.salaryHistory
                .get(1)
                .values()
                .stream()
                .reduce(Double::sum).orElseThrow() / FinanceTest.salaryHistory.get(1).size();
        assertEquals(expected, actual);
    }
}