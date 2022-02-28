package eu.senla.myfirstapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
//@EntityScan("eu.senla.myfirstapp.model")
//@ComponentScan("eu.senla.myfirstapp.app")
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "eu.senla.myfirstapp.app.repository",
//		entityManagerFactoryRef = "factoryBean",
//		transactionManagerRef = "jpaTransactionManager"
//)
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class MyFirstAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyFirstAppApplication.class, args);
	}

}
