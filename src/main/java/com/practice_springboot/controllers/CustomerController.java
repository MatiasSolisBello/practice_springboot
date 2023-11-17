package com.practice_springboot.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
		return service.findAll();
	}
	
	@GetMapping("/customer/page/{page}")
	public Page<Customer> listAll(@PathVariable Integer page){
		PageRequest pageable = PageRequest.of(page, 3);
		return service.findAll(pageable);
	}
	
	
	@GetMapping("/customer/{id}")
	public ResponseEntity<Customer> getById(@PathVariable Long id){
		try {
			Customer customer = service.findById(id);
			return new ResponseEntity<Customer>(customer, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PostMapping("/customer")
	@ResponseStatus(HttpStatus.CREATED)
	public Customer create(@RequestBody Customer customer) {
		customer.setCreatedAt(new Date());
		this.service.save(customer);
		return customer;
	}
	
	
	@PutMapping("/customer/{id}")
	public ResponseEntity<?> update(
			@RequestBody Customer customer, @PathVariable Long id){
		
		try {
			Customer getCustomerExist = service.findById(id);
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
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Customer currentCustomer = this.service.findById(id);
		this.service.delete(currentCustomer);	
	}
}
