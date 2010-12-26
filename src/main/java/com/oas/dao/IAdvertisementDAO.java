package com.oas.dao;

import java.util.List;

import com.oas.common.IGenericDAO;
import com.oas.model.Advertisement;
import com.oas.model.Images;
import com.oas.web.search.SearchRequest;

public interface IAdvertisementDAO extends IGenericDAO<Advertisement, Long> {

	public Advertisement findRealStateAdvertById(Long appartmentId);
	public List<Advertisement> findAllAppartments(String zipCode, String city);
	public List<String> findCities(String searchStr);
	public void save(Advertisement advert);
	public Advertisement update(Advertisement advert);
	public void delete(Advertisement advert);
	public List<Advertisement> findRealStateBySearchCriteria(SearchRequest searchRequest);	
	public Images findImageById(long id);
	public void deleteImage(Images appartmentImage);
}
