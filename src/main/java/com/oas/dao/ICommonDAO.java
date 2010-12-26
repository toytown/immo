package com.oas.dao;

import java.util.List;

import com.oas.model.Category;
import com.oas.model.CategoryType;
import com.oas.model.HeatingType;

public interface ICommonDAO {
	
	public List<CategoryType> getTypes();

	public List<HeatingType> getHeatingTypes();
	
	public List<Category> getCategories();
}
