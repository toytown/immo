package com.oas.web.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebSession;

import com.oas.model.Customer;
import com.oas.services.ICustomerService;
import com.oas.web.search.FavouriteAdvert;

public class OASSession extends WebSession {

	private Customer customer = null;
	
	private ICustomerService customerService;
	
	private List<FavouriteAdvert> favoutites = new ArrayList<FavouriteAdvert>();
	
	public OASSession(Request request) {
		super(request);
	}

	/**
	 * Checks the given customer name and password, returning a Customer object if if the
	 * customer name and password identify a valid customer.
	 * 
	 * @param username
	 *            The customer name
	 * @param password
	 *            The password
	 * @return True if the customer was authenticated
	 */
	public synchronized final boolean authenticate(final String userName, final String password) {
		
		if (this.customer == null) {
			Customer aUser = customerService.getCustomer(userName, password);
			
			if (aUser != null) {
				this.customer = aUser;
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}	
	}
	
	/**
	 * @return True if customer is signed in
	 */
	public boolean isSignedIn() {
		return customer != null;
	}

	/**
	 * @return Customer
	 */
	public Customer getUser() {
		return customer;
	}

	/**
	 * @param customer
	 *            New customer
	 */
	public void setUser(final Customer customer) {
		this.customer = customer;
	}

	public ICustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	
	public List<FavouriteAdvert> getFavourites() {
		return favoutites;
	}

	public void setFavoutites(List<FavouriteAdvert> favoutites) {
		this.favoutites = favoutites;
	}

	public synchronized void addToFavourite(FavouriteAdvert favouriteAdvert) {
		getFavourites().add(favouriteAdvert);
	}
}
