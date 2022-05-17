package eu.senla.myfirstapp.app.repository.group;

import eu.senla.myfirstapp.model.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataGroupRepository extends JpaRepository<Group, Integer> {
}