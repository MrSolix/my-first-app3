package eu.senla.myfirstapp.app.controller.student;

import eu.senla.myfirstapp.app.exception.DataBaseException;
import eu.senla.myfirstapp.app.service.person.PersonService;
import eu.senla.myfirstapp.model.auth.Role;
import eu.senla.myfirstapp.model.people.Person;
import eu.senla.myfirstapp.model.people.Student;
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

import static eu.senla.myfirstapp.app.controller.student.StudentJsonController.JSON_STUDENTS;
import static eu.senla.myfirstapp.model.auth.Role.getRolesName;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = JSON_STUDENTS, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class StudentJsonController {

    public static final String STUDENT_ID_MUST_BE_EQUAL_WITH_ID_IN_PATH = "Student id must be equal with id in path: ";
    public static final String NOT_EQUAL = " != ";
    public static final String PERSON_IS_NOT_STUDENT = "Person is not student";
    public static final String ID = "/{id}";
    public static final String JSON_STUDENTS = "/json/students";
    private final PersonService personService;

    @GetMapping
    public List<Student> getAll() {
        List<Person> allPersons = personService.findAll();
        List<Student> students = new ArrayList<>();
        for (Person person : allPersons) {
            if (getRolesName(person.getRoles()).contains(Role.ROLE_STUDENT)) {
                students.add(((Student) person));
            }
        }
        return students;
    }

    @GetMapping(ID)
    public ResponseEntity<Student> getStudent(@PathVariable int id) {
        Optional<Person> personOptional = personService.findById(id);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            if (getRolesName(person.getRoles()).contains(Role.ROLE_STUDENT)) {
                return ResponseEntity.ok(((Student) person));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
        if (getRolesName(student.getRoles()).contains(Role.ROLE_STUDENT)) {
            try {
                return ResponseEntity.ok((Student) personService.save(student));
            } catch (DataBaseException dataBaseException) {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping(ID)
    public ResponseEntity<?> updateStudent(@PathVariable int id, @RequestBody Student student) {
        if (id != student.getId()) {
            return ResponseEntity
                    .badRequest()
                    .body(STUDENT_ID_MUST_BE_EQUAL_WITH_ID_IN_PATH + id + NOT_EQUAL + student.getId());
        }
        if (!getRolesName(student.getRoles()).contains(Role.ROLE_STUDENT)) {
            return ResponseEntity
                    .badRequest()
                    .body(PERSON_IS_NOT_STUDENT);
        }
        try {
            return ResponseEntity.ok(personService.update(id, student));
        } catch (DataBaseException dataBaseException) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(ID)
    public ResponseEntity<?> deleteStudent(@PathVariable int id) {
        Optional<Person> optionalPerson = personService.findById(id);
        if (optionalPerson.isPresent() && getRolesName(optionalPerson.get().getRoles()).contains(Role.ROLE_STUDENT)) {
            personService.remove(optionalPerson.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
