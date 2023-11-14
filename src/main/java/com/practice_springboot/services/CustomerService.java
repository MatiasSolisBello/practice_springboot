package com.practice_springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice_springboot.dao.ICustomerDao;
import com.practice_springboot.models.Customer;

@Service
public class CustomerService {
	@Autowired
	private ICustomerDao dao;
	
	
	public List<Customer> listAll(){
		return dao.findAll();
	}
	
	
	public void save(Customer customer) {
		dao.save(customer);
	}
	
	
	public Customer getById(Long id) {
		return dao.findById(id).get();
	}
	
	
	public void delete(Long id) {
		dao.deleteById(id);
	}
}
