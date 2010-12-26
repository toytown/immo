package com.oas.web.panels;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

import com.oas.model.Advertisement;
import com.oas.web.main.OASSession;
import com.oas.web.model.AdvertisementDataProvider;
import com.oas.web.model.SortingType;
import com.oas.web.pages.RealStateDetailView;
import com.oas.web.search.FavouriteAdvert;
import com.oas.web.search.SearchRequest;

public class GalleryViewPanel extends GenericSearchResultPanel {

	public GalleryViewPanel(String id, final IModel<SearchRequest> searchRequestModel) {
		super(id, searchRequestModel);
		
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

	public void searchAndRenderResults(final WebMarkupContainer  searchResultContainer, final SearchRequest searchReq) {

		GridView<Advertisement> resultView =  (GridView<Advertisement>) getResult(searchReq); 
		resultView.setRows(5);
		resultView.setColumns(3);
		resultView.setOutputMarkupId(true);		
		
		searchResultContainer.addOrReplace(new PagingNavigator("navigator", resultView));
		searchResultContainer.addOrReplace(resultView);		
	}

	protected GridView getResult(final SearchRequest searchReq) {
		IDataProvider dataProvider = new AdvertisementDataProvider(searchReq); 
		final GridView<Advertisement> dataGrid = new GridView<Advertisement>("appartmentAdvertView", dataProvider) {

			@Override
			protected void populateItem(final Item<Advertisement> item) {
				final Advertisement advert = (Advertisement) item.getModelObject();

				Link detailImageLink = new Link("detailImageLink"){
					@Override
					public void onClick() {
						setResponsePage(new RealStateDetailView(advert));
					}
				};
				
				Link detailLink = new Link("detailLink"){
					@Override
					public void onClick() {
						setResponsePage(new RealStateDetailView(advert));
					}
				};
				
				AjaxLink favouritesLink = new AjaxLink("favouritesLink"){

					@Override
					public void onClick(AjaxRequestTarget target) {
						OASSession sess = (OASSession) Session.get();
						FavouriteAdvert favouriteAdvert = new FavouriteAdvert();
						favouriteAdvert.setId(advert.getId());
						favouriteAdvert.setTitle(advert.getTitleDescription());						
						sess.addToFavourite(favouriteAdvert);
						favoritesContainer.addOrReplace(new ListView<FavouriteAdvert>("favouriteList", sess.getFavourites()){

							@Override
							protected void populateItem(ListItem<FavouriteAdvert> item) {
								FavouriteAdvert favAdvert = item.getModelObject();
								Link favouriteLink = new Link("favouritesDetailLink") {

									@Override
									public void onClick() {
										setResponsePage(new RealStateDetailView(advert));
									}
								};
								item.add(favouriteLink);
								favouriteLink.add(new Label("title", favAdvert.getTitle()));
								
							}
							
						});
						favoritesContainer.setOutputMarkupId(true);
						target.addComponent(favoritesContainer);
					}

				};
				
				detailImageLink.add(getTitleImage(advert));
				item.add(favouritesLink);
				item.add(detailImageLink);
				item.add(detailLink);
				item.add(new MultiLineLabel("title", advert.getTitleDescription()));
				item.add(new Label("rooms", String.valueOf(advert.getTotalRooms())));
				item.add(new Label("size", String.valueOf(advert.getSize())));
				item.add(new Label("cost", String.valueOf(advert.getCost())));
				item.add(new Label("location", getLocationString(advert.getCity(), advert.getAreaCode(), advert.getStreet(), advert.getHouseNo())));
				
				
			}

			@Override
			protected void populateEmptyItem(Item<Advertisement> item) {

				final Advertisement advert = (Advertisement) item.getModelObject();

				Link detailImageLink = new Link("detailImageLink"){
					@Override
					public void onClick() {
						setResponsePage(new RealStateDetailView(advert));
					}
				};
				
				Link detailLink = new Link("detailLink"){
					@Override
					public void onClick() {
						setResponsePage(new RealStateDetailView(advert));
					}
				};
				
				AjaxLink favouritesLink = new AjaxLink("favouritesLink"){

					@Override
					public void onClick(AjaxRequestTarget target) {
						OASSession sess = (OASSession) Session.get();
						FavouriteAdvert favouriteAdvert = new FavouriteAdvert();
						favouriteAdvert.setId(advert.getId());
						favouriteAdvert.setTitle(advert.getTitleDescription());						
						sess.addToFavourite(favouriteAdvert);
						favoritesContainer.addOrReplace(new ListView<FavouriteAdvert>("favouriteList", sess.getFavourites()){

							@Override
							protected void populateItem(ListItem<FavouriteAdvert> item) {
								FavouriteAdvert favAdvert = item.getModelObject();
								Link favouriteLink = new Link("favouritesDetailLink") {

									@Override
									public void onClick() {
										setResponsePage(new RealStateDetailView(advert));
									}
								};
								item.add(favouriteLink);
								favouriteLink.add(new Label("title", favAdvert.getTitle()));
								
							}
							
						});
						favoritesContainer.setOutputMarkupId(true);
						target.addComponent(favoritesContainer);
					}

				};
				
				detailImageLink.add(getTitleImage(advert));
				item.add(favouritesLink.setVisible(false));
				item.add(detailImageLink.setVisible(false));
				item.add(detailLink.setVisible(false));
				item.add(new MultiLineLabel("title", "").setVisible(false));
				item.add(new Label("rooms", "").setVisible(false));
				item.add(new Label("size", "").setVisible(false));
				item.add(new Label("cost", "").setVisible(false));
				item.add(new Label("location", "").setVisible(false));
								
			}
		};	
		
		return dataGrid;
	}	
}
