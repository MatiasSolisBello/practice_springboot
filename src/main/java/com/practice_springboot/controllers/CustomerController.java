package com.practice_springboot.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice_springboot.dao.ICustomerDao;
import com.practice_springboot.models.entity.Customer;


//@CrossOrigin(origins=)
@RestController
@RequestMapping("/api")
public class CustomerController {
	
	@Autowired
	private ICustomerDao customerDao;
	
	@GetMapping("/customer")
	public List<Customer> listAll(){
		return customerDao.findAll();
	}
	
	@GetMapping("/customer/{id}")
	public Optional<Customer> listById(@PathVariable("id") Long id) {
		return customerDao.findById(id);
	}
	
	@PostMapping("/customer")
	public void create(@RequestBody Customer customer) {
		customerDao.save(customer);
	}
	
	@DeleteMapping("/customer/{id}")
	public void delete(@PathVariable("id") Long id) {
		customerDao.deleteById(id);
	}
	
	@PutMapping("/customer")
	public void update(@RequestBody Customer customer) {
		customerDao.save(customer);
	}
}
