package com.oas.web.pages;

import com.oas.model.Advertisement;
import com.oas.web.panels.RealStateEditPanel;

public class RealStateEditPage extends BasePage {

	public RealStateEditPage(Advertisement advert) {
		super();

		RealStateEditPanel editPanel = new RealStateEditPanel("editPanel", advert);
		
		this.add(editPanel);
		
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Edit Page";
	}

}
