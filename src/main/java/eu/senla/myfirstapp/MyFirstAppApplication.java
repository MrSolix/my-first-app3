package eu.senla.myfirstapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "eu.senla.myfirstapp.app.repository",
		entityManagerFactoryRef = "factoryBean",
		transactionManagerRef = "jpaTransactionManager"
)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyFirstAppApplication  {

	public static void main(String[] args) {
		SpringApplication.run(MyFirstAppApplication.class, args);
	}

}
