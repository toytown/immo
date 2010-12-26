package com.oas.dao;

import java.util.List;

import com.oas.common.IGenericDAO;
import com.oas.model.PostalCode;

public interface IPostalCodeDAO extends IGenericDAO<PostalCode, Long>{

	public List findCityByZipCode(String zipCode);
	
	
}
