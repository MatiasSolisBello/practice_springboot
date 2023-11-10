package com.practice_springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.practice_springboot.models.entity.Customer;
import com.practice_springboot.services.ICustomerService;


//@CrossOrigin(origins=)
@RestController
@RequestMapping("/api")
public class CustomerController {
	
	@Autowired
	private ICustomerService customerService;
	
	@GetMapping("/customer")
	public List<Customer> index(){
		return customerService.findAll();
		
	}
}
