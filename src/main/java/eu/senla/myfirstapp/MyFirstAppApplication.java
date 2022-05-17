package eu.senla.myfirstapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class MyFirstAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyFirstAppApplication.class, args);
    }

}