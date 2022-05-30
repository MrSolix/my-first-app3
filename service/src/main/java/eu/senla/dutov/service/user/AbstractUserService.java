package eu.senla.dutov.service.user;

import eu.senla.dutov.dto.RequestUserDto;
import eu.senla.dutov.dto.ResponseUserDto;
import eu.senla.dutov.exception.IncorrectValueException;
import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.mapper.AbstractMapper;
import eu.senla.dutov.model.people.User;
import eu.senla.dutov.service.ServiceCRUDInterfaceDuo;
import eu.senla.dutov.service.util.ServiceConstantClass;
import eu.senla.dutov.service.util.UserUtil;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractUserService<R extends RequestUserDto, T extends ResponseUserDto, U extends User>
        implements ServiceCRUDInterfaceDuo<R, T> {

    protected JpaRepository<U, Integer> jpaRepository;
    protected AbstractMapper<R, T, U> abstractMapper;
    protected String role;

    @Override
    public T save(R dto) {
        return abstractMapper.toDTO(jpaRepository.save(abstractMapper.toModel(dto)));
    }

    @Override
    @Transactional(readOnly = true)
    public T findById(Integer id) {
        return abstractMapper.toDTO(jpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ServiceConstantClass.USER_IS_NOT_FOUND, role))));
    }

    @Override
    public T update(Integer id, R dto) {
        if (!id.equals(dto.getId())) {
            throw new IncorrectValueException(String
                    .format(ServiceConstantClass.PASSED_ID_IS_NOT_EQUAL_TO_USER_ID, role));
        }

        U oldUser = jpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ServiceConstantClass.USER_IS_NOT_FOUND, role)));
        UserUtil.setUserFields(oldUser, abstractMapper.toModel(dto));
        jpaRepository.save(oldUser);
        return abstractMapper.toDTO(oldUser);
    }

    @Override
    public void remove(int id) {
        jpaRepository.delete(jpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ServiceConstantClass.USER_IS_NOT_FOUND, role))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return abstractMapper.toDTOList(jpaRepository.findAll());
    }
}
