package com.practice_springboot.dao;

import org.springframework.data.repository.CrudRepository;
import com.practice_springboot.models.entity.Customer;

public interface ICustomerDao extends CrudRepository<Customer, Long>{

}
