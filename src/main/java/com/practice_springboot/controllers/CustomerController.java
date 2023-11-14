package com.practice_springboot.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.practice_springboot.dao.ICustomerDao;
import com.practice_springboot.models.Customer;
import com.practice_springboot.services.CustomerService;


//@CrossOrigin(origins=)
@RestController
@RequestMapping("/api")
public class CustomerController {
	
	@Autowired
	private CustomerService service;
	
	
	@GetMapping("/customer")
	public List<Customer> listAll(){
		return service.listAll();
	}
	
	
	@GetMapping("/customer/{id}")
	public ResponseEntity<Customer> getById(@PathVariable Long id){
		try {
			Customer customer = service.getById(id);
			return new ResponseEntity<Customer>(customer, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PostMapping("/customer")
	public void save(@RequestBody Customer customer) {
		service.save(customer);
	}
	
	
	@PutMapping("/customer/{id}")
	public ResponseEntity<?> update(
			@RequestBody Customer customer, @PathVariable Long id){
		
		try {
			Customer getCustomerExist = service.getById(id);
			System.out.print(getCustomerExist);
			
			getCustomerExist.setName(customer.getName());
			getCustomerExist.setLast_name(customer.getLast_name());
			getCustomerExist.setEmail(customer.getEmail());
			getCustomerExist.setCreatedAt(customer.getCreatedAt());
			
			service.save(getCustomerExist);
			return new ResponseEntity<Customer>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@DeleteMapping("/customer/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);		
	}
}
