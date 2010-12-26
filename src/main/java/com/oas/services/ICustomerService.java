package com.oas.services;

import com.oas.model.Customer;
import com.oas.web.search.ContactRequest;

public interface ICustomerService {

	public Customer load(long userId);
	
	public Customer getCustomer(String userName, String password);

	public void save(Customer aUser);
	
	public Customer update(Customer aUser);
	
	public boolean sendActivationEmail(Customer customer) ;
	
	public boolean sendPasswordEmail(String user, String oldPassword) ;
	
	public Customer findCustomerById(Long userId);
	
	public void sendContactMessage(Customer customer, ContactRequest contactRequest);
}