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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static eu.senla.myfirstapp.model.auth.Role.getRolesName;

@RestController
@RequestMapping(path = "/json/students", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class StudentJsonController {

    private final PersonService personService;

    @GetMapping
    public List<Student> getAll() {
        List<Person> all = personService.findAll();
        List<Student> students = new ArrayList<>();
        for (Person person :
                all) {
            if (getRolesName(person.getRoles()).contains(Role.ROLE_STUDENT)) {
                students.add(((Student) person));
            }
        }
        return students;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable int id) {
        Optional<Person> personOptional = personService.find(id);
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
            } catch (DataBaseException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable int id, @RequestBody Student student) {
        if (id != student.getId()) {
            return ResponseEntity
                    .badRequest()
                    .body("Student id must be equal with id in path: " + id + " != " + student.getId());
        }
        if (!getRolesName(student.getRoles()).contains(Role.ROLE_STUDENT)) {
            return ResponseEntity
                    .badRequest()
                    .body("Person is not student");
        }
        try {
            return ResponseEntity.ok(personService.update(id, student));
        } catch (DataBaseException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable int id) {
        Optional<Person> optionalPerson = personService.find(id);
        if (optionalPerson.isPresent() && getRolesName(optionalPerson.get().getRoles()).contains(Role.ROLE_STUDENT)) {
            return ResponseEntity.of(Optional.of(((Student) personService.remove(optionalPerson.get()))));
        }
        return ResponseEntity.notFound().build();
    }
}
