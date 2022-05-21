package eu.senla.dutov.controller.salary;

import eu.senla.dutov.service.Finance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static eu.senla.dutov.util.ControllerConstantClass.ID_AVERAGE_MIN_RANGE_MIN_MAX_RANGE_MAX;
import static eu.senla.dutov.util.ControllerConstantClass.MAX_VALUE;
import static eu.senla.dutov.util.ControllerConstantClass.MIN_VALUE;
import static eu.senla.dutov.util.ControllerConstantClass.PATH_ID;
import static eu.senla.dutov.util.ControllerConstantClass.PATH_JSON_SALARIES;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@Slf4j
@RestController
@RequestMapping(path = PATH_JSON_SALARIES, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SalaryJsonController {

    private final Finance finance;

    @GetMapping(PATH_ID)
    public ResponseEntity<Double> getSalary(@PathVariable @Min(MIN_VALUE) int id) {
        return ResponseEntity.ok(finance.getSalary(id));
    }

    @GetMapping(ID_AVERAGE_MIN_RANGE_MIN_MAX_RANGE_MAX)
    public ResponseEntity<Double> averageSalary(@PathVariable @Min(MIN_VALUE) int id,
                                                @PathVariable @Min(MIN_VALUE) @Max(MAX_VALUE) int min,
                                                @PathVariable @Min(MIN_VALUE) @Max(MAX_VALUE) int max) {
        return ResponseEntity.ok(finance.getAverageSalary(id, min, max));
    }
}
