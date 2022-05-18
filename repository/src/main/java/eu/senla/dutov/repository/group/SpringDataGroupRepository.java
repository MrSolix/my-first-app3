package eu.senla.dutov.repository.group;

import eu.senla.dutov.model.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataGroupRepository extends JpaRepository<Group, Integer> {
}