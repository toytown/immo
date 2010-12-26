package com.oas.web.panels;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;

import com.oas.model.Advertisement;
import com.oas.web.model.AdvertisementDataProvider;
import com.oas.web.pages.RealStateDetailView;
import com.oas.web.pages.RealStateEditPage;
import com.oas.web.pages.DeleteConfirmationPage;
import com.oas.web.pages.CustomerPage;
import com.oas.web.search.SearchRequest;

public class SearchResultPanelExt extends GenericSearchResultPanel {

	private CustomerPage page;
	
	public SearchResultPanelExt(String id, IModel<SearchRequest> searchRequestModel, CustomerPage customerPage) {
		super(id, searchRequestModel);
		IMAGE_STORE_LOCATION = appConfig.getImageStore();
		final WebMarkupContainer searchResultContainer = new WebMarkupContainer("searchResultContainer");
		searchResultContainer.setOutputMarkupId(true);
		this.add(searchResultContainer);
		searchAndRenderResults(searchResultContainer, searchRequestModel.getObject());
		page = customerPage;
		
	}

	@Override
	protected DataView getSearchResult(final SearchRequest searchReq) {
		final DataView<Advertisement> dataView = new DataView<Advertisement>("appartmentAdvertView",
				new AdvertisementDataProvider(searchReq)) {

			@Override
			protected void populateItem(final Item<Advertisement> item) {
				final Advertisement advert = (Advertisement) item.getModelObject();

				Link detailLink = new Link("showDetailLink"){
					@Override
					public void onClick() {
						setResponsePage(new RealStateDetailView(advert));
					}
				};
				
				//new links
				Link editLink = new Link("editLink"){
					@Override
					public void onClick() {
						setResponsePage(new RealStateEditPage(advert));
					}
				};
				

				Link deleteLink = new Link("deleteLink"){
					@Override
					public void onClick() {
						Map<String, Long> map = new HashMap<String, Long>(1);
						map.put("advertId", advert.getId());
						setResponsePage(new DeleteConfirmationPage(new PageParameters(map), page, searchReq));
					}
				};
				
				detailLink.add(getTitleImage(advert));
				item.add(detailLink);
				item.add(editLink);
				item.add(deleteLink);
				item.add(new MultiLineLabel("title", advert.getTitleDescription()));
				item.add(new Label("rooms", String.valueOf(advert.getTotalRooms())));
				item.add(new Label("size", String.valueOf(advert.getSize())));
				item.add(new Label("cost", String.valueOf(advert.getCost())));
				item.add(new Label("location", getLocationString(advert.getCity(), advert.getAreaCode(), advert.getStreet(), advert.getHouseNo())));
			}
		};	
		
		return dataView;
	}
}
