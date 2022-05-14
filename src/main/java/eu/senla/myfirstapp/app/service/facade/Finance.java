package eu.senla.myfirstapp.app.service.facade;

import eu.senla.myfirstapp.app.exception.IncorrectValueException;
import eu.senla.myfirstapp.app.exception.NotFoundException;
import eu.senla.myfirstapp.app.service.person.PersonService;
import eu.senla.myfirstapp.model.auth.Role;
import eu.senla.myfirstapp.model.people.Person;
import eu.senla.myfirstapp.model.people.Teacher;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
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
        List<? extends Person> all = personService.findAll();
        for (Person p : all) {
            if (getRolesName(p.getRoles()).contains(Role.ROLE_TEACHER)) {
                Optional<? extends Person> user = personService.find(p.getId());
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
        Optional<? extends Person> person = personService.find(id);
        if (person.isEmpty() || !getRolesName(person.get().getRoles()).contains(Role.ROLE_TEACHER)) {
            log.info("person == null or person role != \"TEACHER\"");
            throw new NotFoundException(PERSON_NOT_FOUND);
        }
        log.info("Salary = {}", ((Teacher) person.get()).getSalary());
        return ((Teacher) person.get()).getSalary();
    }

    public Double getAverageSalary(int id, int min, int max) {
        log.info("id = {}, minRange = {}, maxRange = {}", id, min, max);
        Optional<? extends Person> person = personService.find(id);
        log.info("Get person from db");
        if (person.isEmpty() || !getRolesName(person.get().getRoles()).contains(Role.ROLE_TEACHER)) {
            log.info("person == null or person role != \"TEACHER\"");
            throw new NotFoundException(PERSON_NOT_FOUND);
        }
        Double averageSalary = averageSalary(min, max, (Teacher) person.get());
        if (averageSalary <= 0) {
            log.info("incorrect value in fields \"minRange\" or \"maxRange\"");
            throw new IncorrectValueException(INCORRECT_VALUE);
        }
        log.info("Average Salary = {}", averageSalary);
        return averageSalary;
    }
}
