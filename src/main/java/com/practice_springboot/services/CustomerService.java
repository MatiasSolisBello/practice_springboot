package com.practice_springboot.services;


import java.util.List;

import org.springframework.data.domain.*;

import com.practice_springboot.models.Customer;

public interface CustomerService {
	public List<Customer> findAll();
	public Page<Customer> findAll(Pageable pageable);
	public Customer save(Customer customer);
	public Customer findById(Long id);
	public void delete(Long id);
}
