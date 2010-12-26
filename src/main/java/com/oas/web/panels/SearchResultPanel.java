package com.oas.web.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

import com.oas.web.model.SortingType;
import com.oas.web.search.SearchRequest;

public class SearchResultPanel extends GenericSearchResultPanel {

	public SearchResultPanel(final String id, final IModel<SearchRequest> searchRequestModel) {
		super(id, searchRequestModel);
		InjectorHolder.getInjector().inject(this);

		final SearchForm searchForm = new SearchForm("searchForm", searchRequestModel);
		this.add(searchForm);
		final WebMarkupContainer searchResultContainer = new WebMarkupContainer("searchResultContainer");
		searchResultContainer.setOutputMarkupId(true);
		this.add(searchResultContainer);
		SortResultPanel sortResultPanel = new SortResultPanel("sortingPanel", searchRequestModel);
		add(sortResultPanel);
		DropDownChoice<SortingType> sortingTypesTypesDropDown = (DropDownChoice<SortingType>) sortResultPanel.get("sortingTypes");
		sortingTypesTypesDropDown.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				searchAndRenderResults(searchResultContainer, searchRequestModel.getObject());
				target.addComponent(searchResultContainer);
			}
		});

		searchAndRenderResults(searchResultContainer, searchRequestModel.getObject());

		searchForm.add(new IndicatingAjaxButton("searchButton") {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> searchForm) {
				SearchRequest req = (SearchRequest) searchForm.getModelObject();
				
				searchAndRenderResults(searchResultContainer, req);
				target.addComponent(searchResultContainer);
			}

		});


		final RefinedSearchPanel refinedSearchPanel = new RefinedSearchPanel("refinedSearchPanel", searchRequestModel);
		add(refinedSearchPanel);

		Form refinedSearchForm =  (Form) refinedSearchPanel.get("refinedSearchForm");
		refinedSearchForm.add(new IndicatingAjaxButton("submit"){

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				SearchRequest req = (SearchRequest) form.getModelObject();
				searchAndRenderResults(searchResultContainer, req);
				target.addComponent(searchResultContainer);
				
			}
			
		});
		
		this.setOutputMarkupId(true);
	}

}
