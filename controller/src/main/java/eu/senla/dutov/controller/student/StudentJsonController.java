package eu.senla.dutov.controller.student;

import eu.senla.dutov.model.dto.RequestStudentDto;
import eu.senla.dutov.model.dto.ResponseStudentDto;
import eu.senla.dutov.service.person.StudentService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

import static eu.senla.dutov.util.ControllerConstantClass.MIN_VALUE;

@RestController
@Validated
@RequestMapping(path = "/json/students", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class StudentJsonController {

    private final StudentService studentService;

    @GetMapping
    public List<ResponseStudentDto> getAllStudents() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStudentDto> getStudent(@PathVariable @Min(MIN_VALUE) int id) {
        return ResponseEntity.ok(studentService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<ResponseStudentDto> saveStudent(@Valid @RequestBody RequestStudentDto student) {
        return ResponseEntity.ok(studentService.save(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStudentDto> updateStudent(@PathVariable @Min(MIN_VALUE) int id,
                                                            @Valid @RequestBody RequestStudentDto student) {
        return ResponseEntity.ok(studentService.update(id, student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable @Min(MIN_VALUE) int id) {
        studentService.remove(id);
        return ResponseEntity.ok().build();
    }
}
