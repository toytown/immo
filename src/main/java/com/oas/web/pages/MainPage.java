package com.oas.web.pages;

import com.oas.web.panels.SearchTypePanel;



public class MainPage extends BasePage {

	public MainPage() {
		super();
		
		this.add(new SearchTypePanel("searchTypePanel"));

	}
	

}
