package eu.senla.myfirstapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "eu.senla.myfirstapp.app.repository",
		entityManagerFactoryRef = "factoryBean",
		transactionManagerRef = "jpaTransactionManager"
)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyFirstAppApplication /*extends SpringBootServletInitializer*/ {

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(MyFirstAppApplication.class);
//	}

	public static void main(String[] args) {
		SpringApplication.run(MyFirstAppApplication.class, args);
	}

}
