package com.oas.web.pages;

import com.oas.model.Advertisement;
import com.oas.web.panels.RealStatePreviewPanel;

public class RealStateDetailView extends BasePage{

	public RealStateDetailView() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RealStateDetailView(Advertisement advert) {
		super();
		add(new RealStatePreviewPanel("detailViewPanel", advert));
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "detailv";
	}
	


}
