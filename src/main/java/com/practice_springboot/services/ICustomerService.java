package com.practice_springboot.services;

import java.util.List;

import com.practice_springboot.models.entity.Customer;

public interface ICustomerService {
	public List<Customer> findAll();
}
