package eu.senla.dutov.service;

import eu.senla.dutov.exception.IncorrectValueException;
import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.model.people.Teacher;
import eu.senla.dutov.service.person.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static eu.senla.dutov.model.auth.Role.ROLE_TEACHER;
import static eu.senla.dutov.service.util.ServiceConstantClass.INCORRECT_VALUE;
import static eu.senla.dutov.service.util.ServiceConstantClass.USER_IS_NOT_FOUND;
import static java.lang.String.format;

@Slf4j
@Component
@RequiredArgsConstructor
public class Finance {

    private final Map<Integer, Map<Integer, Double>> salaryHistory = new ConcurrentHashMap<>();
    private static final int CURRENT_MONTH = LocalDate.now().getMonthValue();
    private final TeacherService teacherService;

    @PostConstruct
    private void init() {
        for (Teacher teacher : teacherService.findAll()) {
            initSalaryHistory(teacher);
        }
    }

    private void initSalaryHistory(Teacher teacher) {
        for (int i = 1; i < CURRENT_MONTH; i++) {
            saveSalary(teacher, i, i * Math.random());
        }
        saveSalary(teacher, CURRENT_MONTH, teacher.getSalary());
    }

    public Map<Integer, Map<Integer, Double>> getSalaryHistory() {
        return salaryHistory;
    }

    private void saveSalary(Teacher teacher, int month, double salary) {
        if (month >= 1 && month <= CURRENT_MONTH) {
            getSalaryHistory().putIfAbsent(teacher.getId(), new HashMap<>());
            getSalaryHistory().get(teacher.getId()).putIfAbsent(month, salary);
        }
    }

    public Double getSalary(int id) {
        return teacherService.findById(id)
                .orElseThrow(() -> new NotFoundException(format(USER_IS_NOT_FOUND, ROLE_TEACHER)))
                .getSalary();
    }

    public Double getAverageSalary(int id, int min, int max) {
        return averageSalary(min, max, teacherService
                .findById(id)
                .orElseThrow(() -> new NotFoundException(format(USER_IS_NOT_FOUND, ROLE_TEACHER))));
    }

    private double averageSalary(int minRange, int maxRange, Teacher teacher) {
        int rangeCount = maxRange - minRange + 1;
        boolean isCorrectMinRange = minRange < 1;
        boolean isCorrectMaxRange = maxRange > CURRENT_MONTH;
        boolean isCorrectRangeCount = rangeCount <= 0;
        boolean containsKeyInTheMap = !getSalaryHistory().containsKey(teacher.getId());
        if (isCorrectMinRange
                || isCorrectMaxRange
                || isCorrectRangeCount
                || containsKeyInTheMap) {
            throw new IncorrectValueException(INCORRECT_VALUE);
        }
        return getAvgSal(teacher, minRange, maxRange) / rangeCount;
    }

    private double getAvgSal(Teacher teacher, int minRange, int maxRange) {
        double avgSal = 0.0;
        Map<Integer, Double> integerDoubleMap = getSalaryHistory().get(teacher.getId());
        for (int i = minRange; i <= maxRange; i++) {
            avgSal += integerDoubleMap.get(i);
        }
        return avgSal;
    }
}
