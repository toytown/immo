 package com.oas.web.pages;

import com.oas.web.panels.SearchResultPanel;

public class SearchResultPage extends MainPage {

	public SearchResultPage() {
		super();
		add(new SearchResultPanel("searchResultPanel", null));	
	}
	

	public String getTitle() {
		return "search results";
	}

}
