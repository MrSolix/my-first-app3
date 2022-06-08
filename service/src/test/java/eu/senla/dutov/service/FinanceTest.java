package eu.senla.dutov.service;

import eu.senla.dutov.exception.IncorrectValueException;
import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.model.people.Teacher;
import eu.senla.dutov.repository.jpa.user.TeacherRepository;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FinanceTest {

    private static final int CURRENT_MONTH = LocalDate.now().getMonthValue();
    private static final Map<Integer, Map<Integer, Double>> salaryHistory = new ConcurrentHashMap<>();
    private static Teacher teacher;
    private final TeacherRepository teacherRepository = Mockito.mock(TeacherRepository.class);

    @BeforeAll
    static void beforeAll() {
        teacher = new Teacher();
        teacher.setId(1);
        teacher.setSalary(10000.0);

        Map<Integer, Double> salaries = new HashMap<>();
        for (int i = 1; i < CURRENT_MONTH; i++) {
            salaries.put(i, 1000.0 + Math.round(Math.random() * 10000));
        }
        salaries.put(CURRENT_MONTH, teacher.getSalary());
        salaryHistory.put(teacher.getId(), salaries);
    }

    @Test
    void GetSalary_WithIdEqualOne_ShouldReturnOneThousand() {
        Finance finance = getFinance(teacherRepository, 1, teacher);
        Double actual = finance.getSalary(1);
        Double expected = 10000.0;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void GetSalary_WithIdInvalid_ShouldThrowException() {
        Finance finance = getFinance(teacherRepository, 0, null);
        Assertions.assertThrows(NotFoundException.class, () -> finance.getSalary(0));
    }

    private Finance getFinance(TeacherRepository teacherRepository, int id, Teacher teacher) {
        Mockito.when(teacherRepository.findById(id)).thenReturn(Optional.ofNullable(teacher));
        return new Finance(teacherRepository);
    }

    @Test
    void GetAverageSalary_WithIdInvalid_ShouldThrowException() {
        Mockito.when(teacherRepository.findById(0)).thenReturn(Optional.empty());
        Finance finance = new Finance(teacherRepository);
        Assertions.assertThrows(NotFoundException.class, () -> finance.getAverageSalary(0, 1, 2));
    }

    @Test
    void GetAverageSalary_WithMinRangeMoreThanMaxRange_ShouldThrowException() {
        Mockito.when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher));
        Finance finance = new Finance(teacherRepository);
        Assertions.assertThrows(IncorrectValueException.class, () -> finance.getAverageSalary(1, 2, 1));
    }

    @Test
    void GetAverageSalary_WithMaxRangeMoreThatCurrentMonth_ShouldThrowException() {
        Mockito.when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher));
        Finance finance = new Finance(teacherRepository);
        Assertions.assertThrows(IncorrectValueException.class,
                () -> finance.getAverageSalary(1, 1, CURRENT_MONTH + 1));
    }

    @Test
    void GetAverageSalary_WithCorrectData_ShouldReturnAverageSalary() throws NoSuchFieldException, IllegalAccessException {
        Mockito.when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher));
        Finance finance = new Finance(teacherRepository);
        Field salaryHistory = finance.getClass().getDeclaredField("salaryHistory");
        salaryHistory.setAccessible(true);
        salaryHistory.set(finance, FinanceTest.salaryHistory);
        Double actual = finance.getAverageSalary(1, 1, CURRENT_MONTH);
        Double expected = FinanceTest.salaryHistory
                .get(1)
                .values()
                .stream()
                .reduce(Double::sum).orElseThrow() / FinanceTest.salaryHistory.get(1).size();
        Assertions.assertEquals(expected, actual);
    }
}