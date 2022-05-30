package eu.senla.dutov.mapper;

import eu.senla.dutov.dto.RequestTeacherDto;
import eu.senla.dutov.dto.ResponseTeacherDto;
import eu.senla.dutov.model.people.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = AbstractMapper.SPRING)
public interface TeacherMapper extends AbstractMapper<RequestTeacherDto, ResponseTeacherDto, Teacher> {
}
