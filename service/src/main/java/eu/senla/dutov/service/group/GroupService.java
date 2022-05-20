package eu.senla.dutov.service.group;

import eu.senla.dutov.exception.NotFoundException;
import eu.senla.dutov.model.group.Group;
import eu.senla.dutov.repository.group.SpringDataGroupRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService implements GroupServiceInterface {

    private final SpringDataGroupRepository springDataGroupRepository;

    @Override
    public Group save(Group group) {
        return springDataGroupRepository.save(group);
    }

    @Override
    public Optional<Group> findById(Integer id) {
        return springDataGroupRepository.findById(id);
    }

    @Override
    public Group update(Integer id, Group group) {
        group.setId(id);
        return springDataGroupRepository.save(group);
    }

    @Override
    public void remove(int id) {
        Optional<Group> optionalGroup = springDataGroupRepository.findById(id);
        if (optionalGroup.isEmpty()) {
            throw new NotFoundException("Group is not found");
        }
        springDataGroupRepository.delete(optionalGroup.get());
    }

    @Override
    public List<Group> findAll() {
        return springDataGroupRepository.findAll();
    }
}
