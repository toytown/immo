package com.oas.web.pages;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;

import com.oas.web.main.OASSession;

public class BasePage extends WebPage {
	
	
	public BasePage() {
		add(new Label("title", getTitle()));
	
		//navigation tool
		add( new BookmarkablePageLink("Suchen", MainPage.class));
		add( new BookmarkablePageLink("Inserieren", EditPage.class));
		
		
		WebMarkupContainer authenticatedMenu = new WebMarkupContainer("authenticatedMenu") {
			
			public boolean isVisible() {
				OASSession sessionNew = (OASSession) Session.get();
				return (sessionNew.getUser() != null);
			}
		};
		
		authenticatedMenu.add(new Label("loginName", "test"));
		authenticatedMenu.add(new BookmarkablePageLink("userDetailLink", CustomerPage.class));
		
		authenticatedMenu.add(new Link("logoffLink") {
			public void onClick() {
				OASSession session = (OASSession) Session.get();
				session.invalidate();
				setResponsePage(MainPage.class);
			}
		});		
		
		add(authenticatedMenu);

		
	}
	
	public String getTitle() {
		return "";
	}
}
