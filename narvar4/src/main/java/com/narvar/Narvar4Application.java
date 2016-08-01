package com.narvar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.narvar.domain.Contacts;
import com.narvar.domain.ContactRepository;

@SpringBootApplication
@EnableCaching
public class Narvar4Application {
	@Autowired
    private ContactRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(Narvar4Application.class, args);
	}

	
}
