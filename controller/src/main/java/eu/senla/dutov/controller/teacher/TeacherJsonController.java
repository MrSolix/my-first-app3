package eu.senla.dutov.controller.teacher;

import eu.senla.dutov.model.people.Teacher;
import eu.senla.dutov.service.person.TeacherService;
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

import static eu.senla.dutov.util.ControllerConstantClass.JSON_TEACHERS;
import static eu.senla.dutov.util.ControllerConstantClass.MIN_VALUE;
import static eu.senla.dutov.util.ControllerConstantClass.PATH_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = JSON_TEACHERS, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
@Validated
public class TeacherJsonController {

    private final TeacherService teacherService;

    @GetMapping
    public List<Teacher> getAll() {
        return teacherService.findAll();
    }

    @GetMapping(PATH_ID)
    public ResponseEntity<Teacher> getTeacher(@PathVariable @Min(MIN_VALUE) int id) {
        return ResponseEntity.of(teacherService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Teacher> saveTeacher(@Valid @RequestBody Teacher teacher) {
        return ResponseEntity.ok(teacherService.save(teacher));
    }

    @PutMapping(PATH_ID)
    public ResponseEntity<?> updateTeacher(@PathVariable @Min(MIN_VALUE) int id,
                                           @Valid @RequestBody Teacher teacher) {
        return ResponseEntity.ok(teacherService.update(id, teacher));
    }

    @DeleteMapping(PATH_ID)
    public ResponseEntity<?> deleteTeacher(@PathVariable @Min(MIN_VALUE) int id) {
        teacherService.remove(id);
        return ResponseEntity.ok().build();
    }
}
