package eu.senla.dutov.repository.mongo;

import eu.senla.dutov.dto.UserTimeStamp;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserTimeStampRepository extends MongoRepository<UserTimeStamp, String> {
}
