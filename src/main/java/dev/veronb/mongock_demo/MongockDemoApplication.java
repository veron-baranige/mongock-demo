package dev.veronb.mongock_demo;

import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableMongock
@EnableTransactionManagement
public class MongockDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongockDemoApplication.class, args);
	}
}
