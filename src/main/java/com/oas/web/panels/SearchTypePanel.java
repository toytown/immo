package com.oas.web.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxFallbackLink;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.oas.services.ICustomerService;
import com.oas.web.main.OASSession;

public class SearchTypePanel extends Panel {

	@SpringBean
	private ICustomerService customerService;

	public enum CATEGORY {
		APPARTMENT(1), HOUSE(2), GARAGE(3), LAND(4), OTHERS(5);

		private int code;

		private CATEGORY(int c) {
			code = c;
		}

		public int getCode() {
			return code;
		}
	}

	public SearchTypePanel(final String id) {
		super(id);
		this.setOutputMarkupId(true);
		
		this.add(new IndicatingAjaxFallbackLink("appartmentSearchLink") {

			@Override
			public void onClick(AjaxRequestTarget target) {
				SearchPanel searchPanel = new SearchPanel(id);
				SearchTypePanel.this.replaceWith(searchPanel);
				target.addComponent(searchPanel);
			}
		});

		final SignInPanel signInPanel = new SignInPanel("signInPanel") {
			@Override
			public boolean signIn(String username, String password) {
				OASSession session = ((OASSession) getSession());
				session.setCustomerService(customerService);
				return session.authenticate(username, password);
			}
		};

		add(signInPanel);
		OASSession session = ((OASSession) getSession());

		if (session.getUser() != null) {
			EmptyPanel emptyPanel = new EmptyPanel("signInPanel");
			signInPanel.replaceWith(emptyPanel);
		}
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return "main";
	}

}
