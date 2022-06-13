package eu.senla;

import eu.senla.dutov.util.ControllerConstantClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = ControllerConstantClass.EU_SENLA)
@EnableTransactionManagement
@EnableMongoRepositories(basePackages = MyFirstAppApplication.EU_SENLA_DUTOV_REPOSITORY_MONGO)
@EnableJpaRepositories(basePackages = MyFirstAppApplication.EU_SENLA_DUTOV_REPOSITORY_JPA)
public class MyFirstAppApplication {

    public static final String EU_SENLA_DUTOV_REPOSITORY_MONGO = "eu.senla.dutov.repository.mongo";
    public static final String EU_SENLA_DUTOV_REPOSITORY_JPA = "eu.senla.dutov.repository.jpa";

    public static void main(String[] args) {
        SpringApplication.run(MyFirstAppApplication.class, args);
    }
}