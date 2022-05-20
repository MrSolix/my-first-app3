package eu.senla.dutov.service.person;


import eu.senla.dutov.exception.IncorrectValueException;
import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.model.people.Person;
import eu.senla.dutov.model.people.Student;
import eu.senla.dutov.repository.DAOInterface;
import eu.senla.dutov.repository.person.SpringDataStudentRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService implements DAOInterface<Student> {

    private final SpringDataStudentRepository springDataStudentRepository;

    @Override
    public Student save(Student student) {
        return springDataStudentRepository.save(student);
    }

    @Override
    public Optional<Student> findById(Integer id) {
        return springDataStudentRepository.findById(id);
    }

    @Override
    public Student update(Integer id, Student student) {
        if (!id.equals(student.getId())) {
            throw new IncorrectValueException("Passed id is not equal to student id");
        }
        Optional<Student> oldStudentOptional = springDataStudentRepository.findById(id);
        if (oldStudentOptional.isEmpty()) {
            throw new NotFoundException("Student is not found");
        }
        Student oldStudent = oldStudentOptional.get();
        setPersonFields(oldStudent, student);
        springDataStudentRepository.update(oldStudent.getUserName(), oldStudent.getPassword(),
                oldStudent.getName(), oldStudent.getAge(), oldStudent.getId());
        return oldStudent;
    }

    @Override
    public void remove(int id) {
        Optional<Student> optionalStudent = springDataStudentRepository.findById(id);
        if (optionalStudent.isEmpty()) {
            throw new NotFoundException("Student is not found");
        }
        springDataStudentRepository.delete(optionalStudent.get());
    }

    @Override
    public List<Student> findAll() {
        return springDataStudentRepository.findAll();
    }

    private void setPersonFields(Person oldPerson, Person newPerson) {
        String userName = newPerson.getUserName();
        String password = newPerson.getPassword();
        String name = newPerson.getName();
        Integer age = newPerson.getAge();
        if (userName != null) oldPerson.setUserName(userName);
        if (password != null) oldPerson.setPassword(password);
        if (name != null) oldPerson.setName(name);
        if (age != null) oldPerson.setAge(age);
    }
}
