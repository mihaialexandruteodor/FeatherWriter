package com.mihaialexandruteodor.FeatherWriter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@SpringBootApplication
public class FeatherWriterApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeatherWriterApplication.class, args);
	}

}
