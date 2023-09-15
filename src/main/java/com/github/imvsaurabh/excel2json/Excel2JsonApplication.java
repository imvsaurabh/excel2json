package com.github.imvsaurabh.excel2json;

import com.github.imvsaurabh.excel2json.service.Excel2JsonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Excel2JsonApplication {

	private static final Logger log = LoggerFactory.getLogger(Excel2JsonApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(Excel2JsonApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
			log.info("Application started...");
		};
	}

}
