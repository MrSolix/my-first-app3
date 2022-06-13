package eu.senla.dutov.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.lang.NonNull;
import eu.senla.dutov.util.ControllerConstantClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Collection;
import java.util.Collections;

@Configuration
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    private static final String SPRING_DATA_MONGODB_URI = "${spring.data.mongodb.uri}";
    private static final String SPRING_DATA_MONGODB_DATABASE = "${spring.data.mongodb.database}";

    @Value(SPRING_DATA_MONGODB_URI)
    private String mongoUrl;

    @Value(SPRING_DATA_MONGODB_DATABASE)
    private String dbName;

    @Override
    @NonNull
    protected String getDatabaseName() {
        return dbName;
    }

    @Override
    @NonNull
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoUrl);
        return MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build());
    }

    @Override
    @NonNull
    protected Collection<String> getMappingBasePackages() {
        return Collections.singleton(ControllerConstantClass.EU_SENLA);
    }
}
