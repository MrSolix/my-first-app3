package eu.senla.dutov.controller.teacher;

import eu.senla.dutov.dto.RequestTeacherDto;
import eu.senla.dutov.dto.ResponseTeacherDto;
import eu.senla.dutov.service.user.TeacherService;
import eu.senla.dutov.util.ControllerConstantClass;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static eu.senla.dutov.util.ControllerConstantClass.MIN_VALUE;

@RestController
@RequestMapping(path = TeacherJsonController.URI_JSON_TEACHERS, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
@Validated
@SecurityRequirement(name = ControllerConstantClass.BEARER_AUTH)
public class TeacherJsonController {

    private final TeacherService teacherService;
    public static final String URI_JSON_TEACHERS = "/json/teachers";

    @GetMapping
    public List<ResponseTeacherDto> getAllTeachers() {
        return teacherService.findAll();
    }

    @GetMapping(ControllerConstantClass.URI_ID)
    public ResponseEntity<ResponseTeacherDto> getTeacher(@PathVariable @Min(MIN_VALUE) int id) {
        return ResponseEntity.ok(teacherService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseTeacherDto> saveTeacher(@Valid @RequestBody RequestTeacherDto teacherDto) {
        return ResponseEntity.ok(teacherService.save(teacherDto));
    }

    @PutMapping(ControllerConstantClass.URI_ID)
    public ResponseEntity<ResponseTeacherDto> updateTeacher(@PathVariable @Min(MIN_VALUE) int id,
                                                            @Valid @RequestBody RequestTeacherDto teacherDto) {
        return ResponseEntity.ok(teacherService.update(id, teacherDto));
    }

    @DeleteMapping(ControllerConstantClass.URI_ID)
    public ResponseEntity<String> deleteTeacher(@PathVariable @Min(MIN_VALUE) int id) {
        teacherService.remove(id);
        return ResponseEntity.ok().build();
    }
}
