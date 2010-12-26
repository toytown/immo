package com.oas.common.impl;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.oas.common.IDomainObject;
import com.oas.common.IGenericDAO;

public class GenericDataProvider<T extends IDomainObject> extends SortableDataProvider {

	@SpringBean
    private IGenericDAO<T, Long> genericDAO;
	
	public Iterator iterator(int arg0, int arg1) {
		return genericDAO.findAll().iterator();
	}

	public IModel model(Object object) {
		return new GenericLoadableDetachableModel<T>((T) object);
	}

	public int size() {
		return genericDAO.findAll().size();
	}

	public void detach() {
	}

}
