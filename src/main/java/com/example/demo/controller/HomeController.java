package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

	@RequestMapping("/test")
	public String getString() {
		return "Hello everyone";
	}
	
	
	@RequestMapping("/home")
	public String getHomeScreen() {
		return "Welcome to Home";
	}
	
	@RequestMapping("/app")
	public String getApplication() {
		return "Welcome to Yash family";
	}
}
