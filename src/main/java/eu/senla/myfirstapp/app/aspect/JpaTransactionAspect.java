package eu.senla.myfirstapp.app.aspect;

import eu.senla.myfirstapp.app.exception.DataBaseException;
import eu.senla.myfirstapp.app.repository.AbstractGeneralTransaction;
import eu.senla.myfirstapp.app.repository.ConnectionType;
import eu.senla.myfirstapp.app.repository.EntityManagerHelper;
import eu.senla.myfirstapp.app.service.AbstractDaoInstance;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Optional;

import static eu.senla.myfirstapp.app.repository.AbstractGeneralTransaction.connectionType;


@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class JpaTransactionAspect extends AbstractDaoInstance {

    private final EntityManagerHelper helper;

    @SneakyThrows
    @Around("@annotation(JpaTransaction)")
    public Object transaction(ProceedingJoinPoint jp) {
        String methodName = jp.getSignature().getName();
        log.info("Transaction : {}", methodName);
        Object result;
        connectionType = ConnectionType.MANY;
        EntityManager em = null;
        try {
            em = helper.getObject();
            helper.begin(em);

            result = jp.proceed();

            if (result instanceof Optional) {
                if (((Optional) result).isEmpty()) {
                    return Optional.empty();
                }
            }
            helper.commitMany(em);
        } catch (Exception e) {
            helper.rollBack(em);
            log.error("Error from " + methodName);
            throw new DataBaseException("Error from " + methodName, e);
        } finally {
            helper.close(em);
        }
        return result;
    }
}
