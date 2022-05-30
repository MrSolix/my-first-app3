package eu.senla.dutov.mapper;

import eu.senla.dutov.dto.RequestStudentDto;
import eu.senla.dutov.dto.ResponseStudentDto;
import eu.senla.dutov.model.people.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = AbstractMapper.SPRING)
public interface StudentMapper extends AbstractMapper<RequestStudentDto, ResponseStudentDto, Student> {
}
