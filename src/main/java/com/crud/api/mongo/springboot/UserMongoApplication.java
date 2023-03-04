package com.crud.api.mongo.springboot;

import com.crud.api.mongo.springboot.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories
//@EnableMongoRepositories(basePackageClasses = UserRepository.class)
//@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
//@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@SpringBootApplication
//@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
public class UserMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMongoApplication.class, args);
	}

}
