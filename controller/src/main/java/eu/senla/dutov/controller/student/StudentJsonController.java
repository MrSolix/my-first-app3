package eu.senla.dutov.controller.student;

import eu.senla.dutov.exception.DataBaseException;
import eu.senla.dutov.model.people.Student;
import eu.senla.dutov.service.person.StudentService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
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

import static eu.senla.dutov.controller.student.StudentJsonController.JSON_STUDENTS;
import static eu.senla.dutov.model.auth.Role.ROLE_STUDENT;
import static eu.senla.dutov.model.auth.Role.getRolesName;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
@RequestMapping(path = JSON_STUDENTS, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class StudentJsonController {

    public static final String STUDENT_ID_MUST_BE_EQUAL_WITH_ID_IN_PATH = "Student id must be equal with id in path: ";
    public static final String NOT_EQUAL = " != ";
    public static final String PERSON_IS_NOT_STUDENT = "Person is not student";
    public static final String ID = "/{id}";
    public static final String JSON_STUDENTS = "/json/students";
    private final StudentService studentService;

    @GetMapping
    public List<Student> getAll() {
        return studentService.findAll();
    }

    @GetMapping(ID)
    public ResponseEntity<Student> getStudent(@PathVariable @Min(1) int id) {
        return ResponseEntity.of(studentService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Student> saveStudent(@Valid @RequestBody Student student) {
        return ResponseEntity.ok(studentService.save(student));
    }

    @PutMapping(ID)
    public ResponseEntity<?> updateStudent(@PathVariable @Min(1) int id,
                                           @RequestBody Student student) {
        try {
            return ResponseEntity.ok(studentService.update(id, student));
        } catch (DataBaseException dataBaseException) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(ID)
    public ResponseEntity<?> deleteStudent(@PathVariable @Min(1) int id) {
        studentService.remove(id);
        return ResponseEntity.ok().build();
    }
}
