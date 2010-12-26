package com.oas.web.pages;

import com.oas.web.panels.CustomerRegistrationPanel;

public class CustomerRegistrationPage extends BasePage {

	public CustomerRegistrationPage() {
		super();
		setStatelessHint(true);
		add(new CustomerRegistrationPanel("customerRegistrationPanel"));
	}

	@Override
	public String getTitle() {
		return "Registration";
	}

}
