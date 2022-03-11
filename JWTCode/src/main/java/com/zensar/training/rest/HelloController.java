package com.zensar.training.rest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@RequestMapping("/greet")
	public String syaHello() {
		return "Welcome to REST APIs";
	}
}
