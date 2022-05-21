package eu.senla.dutov.controller.student;

import eu.senla.dutov.model.people.Student;
import eu.senla.dutov.service.person.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static eu.senla.dutov.util.ControllerConstantClass.JSON_STUDENTS;
import static eu.senla.dutov.util.ControllerConstantClass.MIN_VALUE;
import static eu.senla.dutov.util.ControllerConstantClass.PATH_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
@RequestMapping(path = JSON_STUDENTS, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class StudentJsonController {

    private final StudentService studentService;

    @GetMapping
    public List<Student> getAll() {
        return studentService.findAll();
    }

    @GetMapping(PATH_ID)
    public ResponseEntity<Student> getStudent(@PathVariable @Min(MIN_VALUE) int id) {
        return ResponseEntity.of(studentService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Student> saveStudent(@Valid @RequestBody Student student) {
        return ResponseEntity.ok(studentService.save(student));
    }

    @PutMapping(PATH_ID)
    public ResponseEntity<?> updateStudent(@PathVariable @Min(MIN_VALUE) int id,
                                           @RequestBody Student student) {
        return ResponseEntity.ok(studentService.update(id, student));
    }

    @DeleteMapping(PATH_ID)
    public ResponseEntity<?> deleteStudent(@PathVariable @Min(MIN_VALUE) int id) {
        studentService.remove(id);
        return ResponseEntity.ok().build();
    }
}
