package com.example.security;

import com.example.security.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityApplication {
		@Autowired
		UserRepository userRepositary;

	public static void main(String[] args) {

		SpringApplication.run(SecurityApplication.class, args);


	}

}
