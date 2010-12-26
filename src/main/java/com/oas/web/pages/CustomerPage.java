package com.oas.web.pages;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.oas.model.Customer;
import com.oas.web.main.OASSession;
import com.oas.web.panels.SearchResultPanelExt;
import com.oas.web.panels.CustomerEditPanel;
import com.oas.web.search.SearchRequest;

public class CustomerPage extends BasePage {

	@Override
	public String getTitle() {
		return "customer settings";
	}
	private Panel emptyPanel = new EmptyPanel("userMainPanel"); //
	private CustomerEditPanel customerEditPanel = new CustomerEditPanel("userMainPanel");	
	private Panel currentPanel = emptyPanel;
	private Customer customer;
	
	public CustomerPage() {
		super();
		customer = ((OASSession)Session.get()).getUser();
		
		this.add(new Link("advertisements") {

			@Override
			public void onClick() {
				searchAndRenderResults();
				
			}
			
		});
		

		this.add(new Link("editProfile") {

			@Override
			public void onClick() {
				currentPanel.replaceWith(customerEditPanel);
				currentPanel = customerEditPanel;
			}
			
		});	
		
		this.add(currentPanel);
	
	}
	
	public void searchAndRenderResults() {
		SearchRequest req = new SearchRequest();
		req.setUserName(customer.getUserName());
		Panel resultPanel =  new SearchResultPanelExt("userMainPanel", new Model(req), CustomerPage.this);
		currentPanel.replaceWith(resultPanel);		
		currentPanel = resultPanel;
	}

}
