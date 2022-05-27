package eu.senla.dutov.mapper;

import eu.senla.dutov.model.dto.RequestTeacherDto;
import eu.senla.dutov.model.dto.ResponseTeacherDto;
import eu.senla.dutov.model.people.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = AbstractMapper.SPRING)
public interface TeacherMapper extends AbstractMapper<RequestTeacherDto, ResponseTeacherDto, Teacher> {
}
