package com.oas.web.pages;

import org.apache.wicket.spring.injection.annot.SpringBean;

import com.oas.services.ICustomerService;
import com.oas.web.main.OASSession;
import com.oas.web.panels.SignInPanel;

public class LoginPage extends BasePage {
	
	@SpringBean
	private ICustomerService customerService;
	
	public ICustomerService getUserService() {
		return customerService;
	}

	public void setUserService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public LoginPage() {
		add(new SignInPanel("signInPanel")
		{
			@Override
			public boolean signIn(String username, String password)
			{
				OASSession session = ((OASSession)getSession());
				session.setCustomerService(customerService);
				return session.authenticate(username, password);
			}
		});
		
		//add(new GMapPanel("gmapPanel"));
	}

	@Override
	public String getTitle() {
		return "login";
	}


}