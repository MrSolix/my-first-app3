package eu.senla.myfirstapp.app.repository.group.jpa;

import eu.senla.myfirstapp.app.exception.DataBaseException;
import eu.senla.myfirstapp.app.repository.EntityManagerHelper;
import eu.senla.myfirstapp.app.repository.group.GroupDAOInterface;
import eu.senla.myfirstapp.model.group.Group;
import eu.senla.myfirstapp.model.people.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static eu.senla.myfirstapp.app.util.ConstantsClass.*;


@Slf4j
@Repository("jpaGroup")
@Lazy
public class GroupDaoJpa implements GroupDAOInterface {

    protected final EntityManagerHelper helper;

    @Autowired
    public GroupDaoJpa(EntityManagerHelper jpaEntityManager) {
        this.helper = jpaEntityManager;
    }


    @Override
    public Group save(Group group) {
        EntityManager em = null;
        try {
            em = helper.getObject();
            helper.begin(em);

            if (group.getId() == null) {
                em.persist(group);
            } else {
                update(group.getId(), group);
            }

            helper.commitSingle(em);
            return group;
        } catch (Exception e) {
            helper.rollBack(em);
            log.error(ERROR_FROM_SAVE);
            throw new DataBaseException(ERROR_FROM_SAVE, e);
        } finally {
            helper.closeQuietly(em);
        }
    }

    @Override
    public Optional<Group> find(Integer id) {
        EntityManager em = null;
        try {
            em = helper.getObject();
            helper.begin(em);

            Group entity = em.find(Group.class, id);

            helper.commitSingle(em);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            helper.rollBack(em);
            log.error(ERROR_FROM_FIND);
            return Optional.empty();
        } finally {
            helper.closeQuietly(em);
        }
    }

    @Override
    public Group update(Integer id, Group group) {
        EntityManager em = null;
        try {
            em = helper.getObject();
            helper.begin(em);

            em.merge(group);

            helper.commitSingle(em);
            return group;
        } catch (Exception e) {
            helper.rollBack(em);
            log.error(ERROR_FROM_UPDATE);
            throw new DataBaseException(ERROR_FROM_UPDATE);
        } finally {
            helper.closeQuietly(em);
        }
    }

    @Override
    public Group remove(Group group) {
        EntityManager em = null;
        try {
            em = helper.getObject();
            helper.begin(em);

            Group naturalGroup = em.find(Group.class, group.getId());
            for (int i = 0; i < naturalGroup.getStudents().size(); i++) {
                Optional<Student> first = naturalGroup.getStudents().stream().findFirst();
                first.ifPresent(naturalGroup::removeStudent);
            }

            em.remove(naturalGroup);

            helper.commitSingle(em);
            return naturalGroup;
        } catch (Exception e) {
            helper.rollBack(em);
            log.error(ERROR_FROM_REMOVE);
            throw new DataBaseException(ERROR_FROM_REMOVE);
        } finally {
            helper.closeQuietly(em);
        }
    }

    @Override
    public List<Group> findAll() {
        List<Group> entities;
        EntityManager em = null;
        try {
            em = helper.getObject();
            helper.begin(em);

            TypedQuery<Group> query = em.createQuery("from Group ", Group.class);
            entities = query.getResultList();

            helper.commitSingle(em);
        } catch (Exception e) {
            helper.rollBack(em);
            log.error(ERROR_FROM_FIND_ALL);
            throw new DataBaseException(ERROR_FROM_FIND_ALL);
        } finally {
            helper.closeQuietly(em);
        }
        return entities;
    }
}
