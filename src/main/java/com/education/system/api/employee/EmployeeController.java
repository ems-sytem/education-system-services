package com.education.system.api.employee;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

	@RequestMapping("/")
	public String greet() {
		return "Greetings from Education Management System!";
	}
}
