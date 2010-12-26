package com.oas.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;

import com.oas.common.impl.GenericDAOImpl;
import com.oas.dao.IPostalCodeDAO;
import com.oas.model.PostalCode;

public class PostalCodeDAOImpl extends GenericDAOImpl<PostalCode, Long> implements IPostalCodeDAO {

	public List<PostalCode> findCityByZipCode(final String zipCode) {

		Map<String, String> params = new HashMap<String, String>();
	    params.put("zipCode", zipCode);

		return getJpaTemplate().executeFind(new JpaCallback() {

			public Object doInJpa(EntityManager em) throws PersistenceException {				
				Query query = em.createQuery("select p from com.oas.model.PostalCode p where p.zipCode=:zipCode");
                query.setParameter("zipCode", zipCode);
                List result = query.getResultList(); 
                return result;
			}});
	}


}
