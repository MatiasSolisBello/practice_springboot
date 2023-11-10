package com.practice_springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice_springboot.dao.ICustomerDao;
import com.practice_springboot.models.entity.Customer;


@Service
public class CustomerServiceImpl implements ICustomerService {
	
	@Autowired
	private ICustomerDao customerDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Customer> findAll(){
		return (List<Customer>)customerDao.findAll();
	}
}
