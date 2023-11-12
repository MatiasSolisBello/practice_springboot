package com.practice_springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.practice_springboot.models.entity.Customer;

public interface ICustomerDao extends JpaRepository<Customer, Long>{

}
