package com.oas.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import com.oas.dao.ICommonDAO;
import com.oas.model.Category;
import com.oas.model.CategoryType;
import com.oas.model.HeatingType;

public class CommonDAOImpl  extends JpaDaoSupport implements ICommonDAO {
	
	public List<CategoryType> getTypes() {
		return getJpaTemplate().executeFind(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query queryObject = em.createQuery("select t from com.oas.model.CategoryType t");
				queryObject.setHint("org.hibernate.cacheable", true);
				return queryObject.getResultList();
			}
		});
	}

	public List<HeatingType> getHeatingTypes() {
		return getJpaTemplate().executeFind(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query queryObject = em.createQuery("select t from com.oas.model.HeatingType t");
				queryObject.setHint("org.hibernate.cacheable", true);
				return queryObject.getResultList();
			}
		});
	}
	
	public List<Category> getCategories() {
		return getJpaTemplate().executeFind(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query queryObject = em.createQuery("select t from com.oas.model.Category t");
				queryObject.setHint("org.hibernate.cacheable", true);
				return queryObject.getResultList();
			}
		});
	}	
}
