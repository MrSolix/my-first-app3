package eu.senla.dutov.mapper;

import eu.senla.dutov.model.dto.RequestStudentDto;
import eu.senla.dutov.model.dto.ResponseStudentDto;
import eu.senla.dutov.model.people.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper extends AbstractMapper<RequestStudentDto, ResponseStudentDto, Student> {
}
