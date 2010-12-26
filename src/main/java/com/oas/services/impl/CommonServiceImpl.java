package com.oas.services.impl;

import java.util.List;

import com.oas.dao.ICommonDAO;
import com.oas.model.CategoryType;
import com.oas.model.HeatingType;
import com.oas.services.ICommonService;

public class CommonServiceImpl implements ICommonService {

	public ICommonDAO getCommonDAO() {
		return commonDAO;
	}

	public void setCommonDAO(ICommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	private ICommonDAO commonDAO;

	public List<HeatingType> getHeatingTypes() {
		return commonDAO.getHeatingTypes();
	}

	public List<CategoryType> getCategoryTypes() {
		return commonDAO.getTypes();
	}

}
