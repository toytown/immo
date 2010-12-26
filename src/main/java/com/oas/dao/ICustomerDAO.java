package com.oas.dao;

import com.oas.common.IGenericDAO;
import com.oas.model.Customer;

public interface ICustomerDAO extends IGenericDAO<Customer, Long>{

	public Customer getCustomer(String userName, String password);
	
	public Customer findCustomerById(Long userId);
}
