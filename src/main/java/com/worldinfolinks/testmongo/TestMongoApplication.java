package com.worldinfolinks.testmongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages={"com.worldinfolinks"})
@EnableMongoRepositories(basePackages={"com.worldinfolinks.repository"})
public class TestMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestMongoApplication.class, args);
	}

}
