package com.oas.common.impl;

import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.oas.common.IDomainObject;
import com.oas.common.IGenericDAO;

public class GenericLoadableDetachableModel<T extends IDomainObject> extends LoadableDetachableModel<T> {

	@SpringBean 
    private IGenericDAO<T, Long> genericDAO;

    private Long entityId;

    public GenericLoadableDetachableModel(T entity) {
        super(entity);
        entityId = entity.getId();
        InjectorHolder.getInjector().inject(this);
    }

    public GenericLoadableDetachableModel(Class<T> entityClass, 
            Long entityId) {
        super();
        this.entityId = entityId;
        InjectorHolder.getInjector().inject(this);
    }

    protected T load() {
        return (T) genericDAO.findById(entityId);
    }

}
