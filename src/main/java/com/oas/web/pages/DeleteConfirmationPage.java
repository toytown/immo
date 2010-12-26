package com.oas.web.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.oas.model.Advertisement;
import com.oas.services.IAdvertisementSearchService;
import com.oas.web.search.SearchRequest;

public class DeleteConfirmationPage extends BasePage {

	@SpringBean
	private IAdvertisementSearchService advertisementSearchService;
	
	@Override
	public String getTitle() {
		return "Delete confirmation";
	}

	public DeleteConfirmationPage(final PageParameters p, final CustomerPage page, final SearchRequest req) {
		super();

		Form<Advertisement> deleteForm = new Form("deleteForm");
		deleteForm.add(new Button("delete", new Model("delete")) {
			
			@Override
			public void onSubmit() {
				long advertId = p.getAsLong("advertId");
				Advertisement advert = advertisementSearchService.findById(advertId);
				advertisementSearchService.delete(advert);
				page.searchAndRenderResults();
				setResponsePage(page);
			}
		});
		
		deleteForm.add(new Button("cancel", new Model("cancel")) {
			@Override
			public void onSubmit() {
				
			}			
		});
		
		this.add(deleteForm);
	}
}
