package eu.senla.dutov.controller.salary;

import eu.senla.dutov.exception.IncorrectValueException;
import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.service.Finance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static eu.senla.dutov.controller.salary.SalaryJsonController.JSON_SALARIES;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(path = JSON_SALARIES, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SalaryJsonController {

    public static final String ID = "/{id}";
    public static final String ID_AVERAGE_MIN_RANGE_MIN_MAX_RANGE_MAX = "/{id}/average/minRange={min}&maxRange={max}";
    public static final String JSON_SALARIES = "/json/salaries";
    private final Finance finance;

    @GetMapping(ID)
    public ResponseEntity<Double> getSalary(@PathVariable int id) {
        try {
            return ResponseEntity.ok(finance.getSalary(id));
        } catch (NotFoundException notFoundException) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(ID_AVERAGE_MIN_RANGE_MIN_MAX_RANGE_MAX)
    public ResponseEntity<Double> averageSalary(@PathVariable int id, @PathVariable int min, @PathVariable int max) {
        try {
            return ResponseEntity.ok(finance.getAverageSalary(id, min, max));
        } catch (NotFoundException notFoundException) {
            return ResponseEntity.notFound().build();
        } catch (IncorrectValueException incorrectValueException) {
            return ResponseEntity.badRequest().build();
        }
    }
}
