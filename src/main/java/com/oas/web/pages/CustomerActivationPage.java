package com.oas.web.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.oas.model.Customer;
import com.oas.services.ICustomerService;

public class CustomerActivationPage extends BasePage {

	@SpringBean
	private ICustomerService customerService;
	
	@Override
	public String getTitle() {
		return "Activation Page";
	}

	public CustomerActivationPage() {
		super();
		setStatelessHint(true);
	}

	public CustomerActivationPage(PageParameters pm) {
		super();
		setStatelessHint(true);		
		
		String userId = (String) pm.get("userId");
		String activationCode = (String) pm.get("activationCode");
		if ( userId != null && activationCode != null) {
			Customer customer = customerService.findCustomerById(Long.valueOf(userId));
			customer.setUserStatus(Short.valueOf("1"));
			Customer userUpdated = customerService.update(customer);
			
			if (userUpdated != null && userUpdated.getUserStatus() == Short.valueOf("1")) {
				setResponsePage(new LoginPage());
			}
		}
	}	
}
