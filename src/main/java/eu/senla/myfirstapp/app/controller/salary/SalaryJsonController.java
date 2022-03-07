package eu.senla.myfirstapp.app.controller.salary;

import eu.senla.myfirstapp.app.exception.IncorrectValueException;
import eu.senla.myfirstapp.app.exception.NotFoundException;
import eu.senla.myfirstapp.app.service.facade.Finance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/json/salaries", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SalaryJsonController {

    private final Finance finance;

    @GetMapping("/{id}")
    public ResponseEntity<Double> getSalary(@PathVariable int id) {
        log.info("Get parameters");
        log.info("id = {}", id);
        log.info("Get person from db");
        try {
            return ResponseEntity.ok(finance.getSalary(id));
        } catch (NotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/average/minRange={min}&maxRange={max}")
    public ResponseEntity<Double> averageSalary(@PathVariable int id, @PathVariable int min, @PathVariable int max) {
        log.info("Get parameters");
        log.info("id = {}", id);
        log.info("Get person from db");
        try {
            return ResponseEntity.ok(finance.getAverageSalary(id, min, max));
        } catch (NotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IncorrectValueException e1) {
            log.info(e1.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
