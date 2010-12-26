package com.oas.dao.impl;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.oas.model.CustomerContactDetails;
import com.oas.model.Customer;
import com.oas.test.utils.OASTestCase;

public class CustomerDAOImplTest extends OASTestCase {

	@Autowired
	private CustomerDAOImpl userDAO;
	
	@Test
	@Transactional
	@Rollback(false)
	public void testSave() {
	
		CustomerContactDetails customerContactDetails = new CustomerContactDetails();
		customerContactDetails.setStreet("Schlesierstr");
		customerContactDetails.setHouseNumber("4");
		customerContactDetails.setEmail("ptuladhar@gmx.net");
		
		Customer customer = new Customer();
		customer.setUserName("test-username");
		customer.setPassword("password");
		customer.setInsertTs(new Date());
		
		customer.getContactDetails().add(customerContactDetails);
		
		userDAO.save(customer);
	}

	
	@Test
	@Transactional
	@Rollback(false)
	public void testUserValidation() {
	
		CustomerContactDetails customerContactDetails = new CustomerContactDetails();
		customerContactDetails.setStreet("Schlesierstr");
		customerContactDetails.setHouseNumber("4");
		customerContactDetails.setEmail("ptuladhar@gmx.net");
		
		Customer customer = new Customer();
		customer.setUserName("test-username-10000");
		customer.setPassword("password-1000");
		customer.setInsertTs(new Date());
		
		customer.getContactDetails().add(customerContactDetails);
		
		userDAO.save(customer);
		
		Customer aUser = userDAO.getCustomer("test-username-10000", "password-1000");
		assertNotNull(aUser);
	}	
}
