package com.oas.services;

import java.util.List;

import com.oas.model.Advertisement;
import com.oas.model.Images;
import com.oas.web.search.SearchRequest;

public interface IAdvertisementSearchService {

	public void save(Advertisement appartment);
	public Advertisement findById(Long id);
	public List<String> findCities(String searchStr);	
	public Advertisement update(Advertisement appartment);
	public void delete(Advertisement advert);
	public List<Advertisement> findRealStatetByZip(String zip);
	public List<Advertisement> findAdvertisement(SearchRequest searchRequest);
	
	public Images findRealStateImageById(long id);
	public void deleteImage(Images appartmentImage);
}
