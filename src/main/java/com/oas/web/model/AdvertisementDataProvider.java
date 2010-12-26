package com.oas.web.model;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.oas.model.Advertisement;
import com.oas.services.IAdvertisementSearchService;
import com.oas.web.search.SearchRequest;

public class AdvertisementDataProvider extends SortableDataProvider<Advertisement>{

	@SpringBean(name="advertisementSearchService")
	private IAdvertisementSearchService advertisementSearchService;
	private SearchRequest searchRequest;
	List<Advertisement> searchResultList = null;
	
	public AdvertisementDataProvider(SearchRequest req) {
		super();
		InjectorHolder.getInjector().inject(this);
		searchRequest = req;
		this.searchResultList = advertisementSearchService.findAdvertisement(searchRequest);
	}
	
	public Iterator<? extends Advertisement> iterator(int first, int count) {
		return searchResultList.subList(first, first + count).iterator();
	}

	public IModel<Advertisement> model(Advertisement advert) {
		return new LoadableAdvertisementModel(advert.getId());
	}

	public int size() {
		return searchResultList.size();
	}
	
}
