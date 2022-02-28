package eu.senla.myfirstapp.app.service.group;

import eu.senla.myfirstapp.model.group.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService implements GroupServiceInterface {

    private final GroupDaoInstance groupDaoInstance;

    @Override
    public Group save(Group group) {
        return groupDaoInstance.getRepository().save(group);
    }

    @Override
    public Optional<Group> find(Integer id) {
        return groupDaoInstance.getRepository().find(id);
    }

    @Override
    public Group update(Integer id, Group group) {
        group.setId(id);
        return groupDaoInstance.getRepository().save(group);
    }

    @Override
    public Group remove(Group group) {
        return groupDaoInstance.getRepository().remove(group);
    }

    @Override
    public List<Group> findAll() {
        return groupDaoInstance.getRepository().findAll();
    }
}
