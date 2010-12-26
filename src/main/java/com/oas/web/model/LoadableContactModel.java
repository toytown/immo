package com.oas.web.model;

import java.util.List;

import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.oas.model.CustomerContactDetails;
import com.oas.model.Customer;
import com.oas.services.ICustomerService;

public class LoadableContactModel extends LoadableDetachableModel {

	@SpringBean(name="customerService")
	private ICustomerService customerService;
	
	private long userId;

	public LoadableContactModel(Customer customer) {
		super(customer);
		InjectorHolder.getInjector().inject(this);
		userId = customer.getId();
	}
	
	public LoadableContactModel(long userId) {
		super();
		this.userId = userId;
		InjectorHolder.getInjector().inject(this);
	}

	@Override
	protected CustomerContactDetails load() {
		Customer customer = customerService.load(userId);
		List<CustomerContactDetails> customerContactDetails = customer.getContactDetails();
		return customerContactDetails.get(0);
	}

}
