package eu.senla.dutov.service;

import eu.senla.dutov.exception.IncorrectValueException;
import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.model.auth.Role;
import eu.senla.dutov.model.people.Teacher;
import eu.senla.dutov.repository.user.TeacherRepository;
import eu.senla.dutov.service.util.ServiceConstantClass;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Finance {

    public static final int CURRENT_MONTH = LocalDate.now().getMonthValue();
    private final Map<Integer, Map<Integer, Double>> salaryHistory = new ConcurrentHashMap<>();
    private final TeacherRepository teacherRepository;

    @PostConstruct
    private void init() {
        List<Teacher> all = teacherRepository.findAll();
        for (Teacher teacher : all) {
            initSalaryHistory(teacher);
        }
    }

    private void initSalaryHistory(Teacher teacher) {
        for (int i = 1; i < CURRENT_MONTH; i++) {
            double randomSalary = 1000.0 + Math.round(Math.random() * 10000);
            saveSalary(teacher, i, randomSalary);
        }
        saveSalary(teacher, CURRENT_MONTH, teacher.getSalary());
    }

    private void saveSalary(Teacher teacher, int month, double salary) {
        if (month >= 1 && month <= CURRENT_MONTH) {
            getSalaryHistory().putIfAbsent(teacher.getId(), new HashMap<>());
            getSalaryHistory().get(teacher.getId()).putIfAbsent(month, salary);
        }
    }

    public Map<Integer, Map<Integer, Double>> getSalaryHistory() {
        return salaryHistory;
    }

    public Double getSalary(int id) {
        return teacherRepository.findById(id).orElseThrow(() -> new NotFoundException(String
                .format(ServiceConstantClass.USER_IS_NOT_FOUND, Role.ROLE_TEACHER))).getSalary();
    }

    public Double getAverageSalary(int id, int min, int max) {
        return averageSalary(min, max, teacherRepository.findById(id).orElseThrow(() -> new NotFoundException(String
                .format(ServiceConstantClass.USER_IS_NOT_FOUND, Role.ROLE_TEACHER))));
    }

    private double averageSalary(int minRange, int maxRange, Teacher teacher) {
        int rangeCount = maxRange - minRange + 1;
        int zero = 0;
        if (maxRange > CURRENT_MONTH || rangeCount <= zero) {
            throw new IncorrectValueException(ServiceConstantClass.INCORRECT_VALUE);
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
