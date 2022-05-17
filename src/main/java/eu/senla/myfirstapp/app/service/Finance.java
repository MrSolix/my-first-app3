package eu.senla.myfirstapp.app.service;

import eu.senla.myfirstapp.app.exception.IncorrectValueException;
import eu.senla.myfirstapp.app.exception.NotFoundException;
import eu.senla.myfirstapp.app.service.person.PersonService;
import eu.senla.myfirstapp.model.auth.Role;
import eu.senla.myfirstapp.model.people.Person;
import eu.senla.myfirstapp.model.people.Teacher;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static eu.senla.myfirstapp.model.auth.Role.getRolesName;

@Slf4j
@Component
public class Finance {

    public static final String PERSON_NOT_FOUND = "Person not found";
    public static final String INCORRECT_VALUE = "Incorrect value";
    private final Map<Integer, Map<Integer, Double>> salaryHistory;
    private static final int CURRENT_MONTH = LocalDate.now().getMonthValue();
    private final PersonService personService;

    @Autowired
    public Finance(PersonService personService) {
        this.personService = personService;
        salaryHistory = new ConcurrentHashMap<>();
    }

    @PostConstruct
    private void init() {
        for (Person person : personService.findAll()) {
            if (getRolesName(person.getRoles()).contains(Role.ROLE_TEACHER)) {
                Optional<? extends Person> user = personService.findById(person.getId());
                if (user.isPresent()) {
                    Teacher teacher = (Teacher) user.get();
                    for (int i = 1; i < CURRENT_MONTH; i++) {
                        saveSalary(teacher, i, i * 110.0);
                    }
                    saveSalary(teacher, CURRENT_MONTH, teacher.getSalary());
                }
            }
        }
    }

    public Map<Integer, Map<Integer, Double>> getSalaryHistory() {
        return salaryHistory;
    }

    private double averageSalary(Integer minRange, Integer maxRange, Teacher teacher) {
        int rangeCount = maxRange - minRange + 1;
        double avgSal = 0.0;
        if (minRange < 1 || maxRange > CURRENT_MONTH
                || rangeCount <= 0
                || teacher == null
                || !getSalaryHistory().containsKey(teacher.getId())) {
            return -1;
        }
        avgSal = getAvgSal(teacher, minRange, maxRange, avgSal);
        return avgSal / rangeCount;
    }

    private double getAvgSal(Teacher teacher, int minRange, int maxRange, double avgSal) {
        Map<Integer, Double> integerDoubleMap = getSalaryHistory().get(teacher.getId());
        for (int i = minRange; i <= maxRange; i++) {
            avgSal += integerDoubleMap.get(i);
        }
        return avgSal;
    }

    private void saveSalary(Teacher teacher, Integer month, Double salary) {
        if (month >= 1 && month <= CURRENT_MONTH) {
            getSalaryHistory().putIfAbsent(teacher.getId(), new HashMap<>());
            getSalaryHistory().get(teacher.getId()).putIfAbsent(month, salary);
        }
    }

    public Double getSalary(int id) {
        Optional<? extends Person> person = personService.findById(id);
        if (person.isEmpty() || !getRolesName(person.get().getRoles()).contains(Role.ROLE_TEACHER)) {
            throw new NotFoundException(PERSON_NOT_FOUND);
        }
        return ((Teacher) person.get()).getSalary();
    }

    public Double getAverageSalary(int id, int min, int max) {
        Optional<? extends Person> person = personService.findById(id);
        if (person.isEmpty() || !getRolesName(person.get().getRoles()).contains(Role.ROLE_TEACHER)) {
            throw new NotFoundException(PERSON_NOT_FOUND);
        }
        double averageSalary = averageSalary(min, max, (Teacher) person.get());
        if (averageSalary <= 0) {
            throw new IncorrectValueException(INCORRECT_VALUE);
        }
        return averageSalary;
    }
}
