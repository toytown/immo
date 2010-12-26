package com.oas.web.model;

import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.oas.model.Customer;
import com.oas.services.ICustomerService;

public class LoadableCustomerModel extends LoadableDetachableModel<Customer> {

	private long userId;

	public LoadableCustomerModel(Customer customer) {
		super(customer);
		InjectorHolder.getInjector().inject(this);
		userId = customer.getId();
	}
	
	public LoadableCustomerModel(Long id) {
		super();
		InjectorHolder.getInjector().inject(this);
		userId= id;
	}

	@SpringBean(name="customerService")
	private ICustomerService customerService;
	
	@Override
	protected Customer load() {
		// TODO Auto-generated method stub
		Customer customer = customerService.load(userId);
		return customer;
	}

}
