package com.pelletier.rashall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class RashallApplication implements CommandLineRunner {

	Logger log = LoggerFactory.getLogger(RashallApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RashallApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		//I like having this to test out code
	}
}
