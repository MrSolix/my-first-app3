package eu.senla.myfirstapp.app.controller.teacher;

import eu.senla.myfirstapp.app.exception.DataBaseException;
import eu.senla.myfirstapp.app.service.person.PersonService;
import eu.senla.myfirstapp.model.auth.Role;
import eu.senla.myfirstapp.model.people.Person;
import eu.senla.myfirstapp.model.people.Teacher;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static eu.senla.myfirstapp.app.controller.teacher.TeacherJsonController.JSON_TEACHERS;
import static eu.senla.myfirstapp.model.auth.Role.getRolesName;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = JSON_TEACHERS, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class TeacherJsonController {

    public static final String TEACHER_ID_MUST_BE_EQUAL_WITH_ID_IN_PATH = "Teacher id must be equal with id in path: ";
    public static final String NOT_EQUAL = " != ";
    public static final String PERSON_IS_NOT_TEACHER = "Person is not teacher";
    public static final String ID = "/{id}";
    public static final String JSON_TEACHERS = "/json/teachers";
    private final PersonService personService;

    @GetMapping
    public List<Teacher> getAll() {
        List<Person> allPersons = personService.findAll();
        List<Teacher> teachers = new ArrayList<>();
        for (Person person : allPersons) {
            if (getRolesName(person.getRoles()).contains(Role.ROLE_TEACHER)) {
                teachers.add((Teacher) person);
            }
        }
        return teachers;
    }

    @GetMapping(ID)
    public ResponseEntity<Teacher> getTeacher(@PathVariable int id) {
        Optional<Person> personOptional = personService.findById(id);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            if (getRolesName(person.getRoles()).contains(Role.ROLE_TEACHER)) {
                return ResponseEntity.ok(((Teacher) person));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Teacher> saveTeacher(@RequestBody Teacher teacher) {
        if (getRolesName(teacher.getRoles()).contains(Role.ROLE_TEACHER)) {
            try {
                return ResponseEntity.ok((Teacher) personService.save(teacher));
            } catch (DataBaseException dataBaseException) {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping(ID)
    public ResponseEntity<?> updateTeacher(@PathVariable int id, @RequestBody Teacher teacher) {
        if (id != teacher.getId()) {
            return ResponseEntity
                    .badRequest()
                    .body(TEACHER_ID_MUST_BE_EQUAL_WITH_ID_IN_PATH + id + NOT_EQUAL + teacher.getId());
        }
        if (!getRolesName(teacher.getRoles()).contains(Role.ROLE_TEACHER)) {
            return ResponseEntity
                    .badRequest()
                    .body(PERSON_IS_NOT_TEACHER);
        }
        try {
            return ResponseEntity.ok(personService.update(id, teacher));
        } catch (DataBaseException dataBaseException) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(ID)
    public ResponseEntity<?> deleteTeacher(@PathVariable int id) {
        Optional<Person> optionalPerson = personService.findById(id);
        if (optionalPerson.isPresent() && getRolesName(optionalPerson.get().getRoles()).contains(Role.ROLE_TEACHER)) {
            personService.remove(optionalPerson.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
