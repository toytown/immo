package com.oas.web.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.oas.web.search.SearchRequest;

public class SearchPanel extends Panel {

	private static final long serialVersionUID = 4985559346626325515L;
	
	public SearchPanel(final String id) {
		super(id);

		final SearchRequest searchReq = new SearchRequest();
		final SearchForm searchForm = new SearchForm("searchForm", new Model(searchReq));
	
		searchForm.add(new IndicatingAjaxButton("searchButton") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				SearchRequest req = (SearchRequest) form.getModelObject();
				final GalleryViewPanel result = new GalleryViewPanel(id, new Model(req));
				SearchPanel.this.replaceWith(result);
				target.addComponent(result);				
			}
		});
		
		this.add(searchForm);
		this.setOutputMarkupId(true);
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return "search basic";
	}

}
