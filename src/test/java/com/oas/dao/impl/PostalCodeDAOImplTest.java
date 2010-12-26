package com.oas.dao.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.oas.model.PostalCode;
import com.oas.test.utils.OASTestCase;

public class PostalCodeDAOImplTest extends OASTestCase {

	@Autowired
	private PostalCodeDAOImpl postalDAO;
	
	@Test
	public void testFindCityByZipCode() {
		List<PostalCode> postalCode = postalDAO.findCityByZipCode("81669");
		Assert.assertTrue(!postalCode.isEmpty());
		postalDAO.findCityByZipCode("81669");
	}

}
