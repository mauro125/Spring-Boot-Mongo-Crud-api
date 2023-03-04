package com.crud.api.mongo.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class UserMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMongoApplication.class, args);
	}

}
