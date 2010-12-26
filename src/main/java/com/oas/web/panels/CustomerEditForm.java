package com.oas.web.panels;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.oas.model.Customer;
import com.oas.services.ICustomerService;
import com.oas.web.main.OASSession;
import com.oas.web.pages.CustomerPage;

public class CustomerEditForm extends Form {

	@SpringBean
	private ICustomerService customerService;
	
	public CustomerEditForm(String id, IModel model) {
		super(id, model);
	}

	public CustomerEditForm(String id) {
		super(id);
	}

	
	@Override
	public final void onSubmit() {
		Customer customer = (Customer) this.getModelObject();
		customerService.update(customer);
		OASSession sess = (OASSession)Session.get();
		sess.setUser(customer);
		setResponsePage(CustomerPage.class);
	}	
}
