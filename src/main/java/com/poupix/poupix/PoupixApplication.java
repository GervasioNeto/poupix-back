package com.poupix.poupix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class PoupixApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoupixApplication.class, args);
	}

}
