package com.oas.services;

import java.util.List;

import com.oas.model.CategoryType;
import com.oas.model.HeatingType;

public interface ICommonService {

	public List<CategoryType> getCategoryTypes();
	
	public List<HeatingType> getHeatingTypes();
	
	
}
