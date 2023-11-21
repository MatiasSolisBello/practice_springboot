package com.practice_springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice_springboot.dao.ICustomerDao;
import com.practice_springboot.models.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private ICustomerDao dao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Customer> findAll() {
		return (List<Customer>) dao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Customer> findAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	
	@Override
	@Transactional
	public Customer save(Customer customer) {
		return dao.save(customer);
	}

	
	@Override
	@Transactional(readOnly = true)
	public Customer findById(Long id) {
		return dao.findById(id).orElse(null);
	}


	@Override
	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);
	}
	
}
