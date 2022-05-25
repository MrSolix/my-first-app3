package eu.senla.dutov.service.person;

import eu.senla.dutov.exception.IncorrectValueException;
import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.mapper.AbstractMapper;
import eu.senla.dutov.model.dto.RequestUserDto;
import eu.senla.dutov.model.dto.ResponseUserDto;
import eu.senla.dutov.model.people.User;
import eu.senla.dutov.repository.base.CommonRepository;
import eu.senla.dutov.service.ServiceCRUDInterfaceDuo;
import eu.senla.dutov.service.util.PersonUtil;
import eu.senla.dutov.service.util.ServiceConstantClass;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractUserService<R extends RequestUserDto, T extends ResponseUserDto, U extends User> implements ServiceCRUDInterfaceDuo<R, T> {

    protected CommonRepository<U, Integer> commonRepository;
    protected AbstractMapper<R, T, U> abstractMapper;
    protected String role;

    @Override
    public T save(R dto) {
        return abstractMapper
                .toDTO(commonRepository
                        .save(abstractMapper
                                .toModel(dto)));
    }

    @Override
    @Transactional(readOnly = true)
    public T findById(Integer id) {
        return abstractMapper
                .toDTO(commonRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(String
                                .format(ServiceConstantClass.USER_IS_NOT_FOUND, role))));
    }

    @Override
    public T update(Integer id, R dto) {
        if (!id.equals(dto.getId())) {
            throw new IncorrectValueException(String
                    .format(ServiceConstantClass.PASSED_ID_IS_NOT_EQUAL_TO_USER_ID, role));
        }

        U oldUser = commonRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String
                        .format(ServiceConstantClass.USER_IS_NOT_FOUND, role)));
        PersonUtil.setPersonFields(oldUser, abstractMapper.toModel(dto));
        commonRepository.update(oldUser.getUserName(), oldUser.getPassword(),
                oldUser.getName(), oldUser.getAge(), oldUser.getId());
        return abstractMapper.toDTO(oldUser);
    }

    @Override
    public void remove(int id) {
        commonRepository
                .delete(commonRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(String
                                .format(ServiceConstantClass.USER_IS_NOT_FOUND, role))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return abstractMapper.toDTOList(commonRepository.findAll());
    }
}
