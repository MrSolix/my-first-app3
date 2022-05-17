package eu.senla.myfirstapp.app.service.group;

import eu.senla.myfirstapp.app.repository.group.SpringDataGroupRepository;
import eu.senla.myfirstapp.model.group.Group;
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
    public void remove(Group group) {
        springDataGroupRepository.delete(group);
    }

    @Override
    public List<Group> findAll() {
        return springDataGroupRepository.findAll();
    }
}
