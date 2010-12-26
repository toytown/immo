package com.oas.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;

import com.oas.common.impl.GenericDAOImpl;
import com.oas.dao.ICustomerDAO;
import com.oas.model.Customer;

public class CustomerDAOImpl extends GenericDAOImpl<Customer, Long> implements ICustomerDAO {

	public Customer getCustomer(final String userName, final String password) {
		
    
		List list = getJpaTemplate().executeFind(new JpaCallback() {

			public Object doInJpa(EntityManager em) throws PersistenceException {				
				Query query = em.createQuery("select u from com.oas.model.Customer u where u.userName=:userName and u.password=:password");
                query.setParameter("userName", userName);
                query.setParameter("password", password);
                List result = query.getResultList(); 
                return result;
			}});
		Customer customer = (Customer) list.get(0);
		return customer;
	}
	
	
	public Customer findCustomerById(Long userId) {
		return super.findById(userId);
	}	
}
