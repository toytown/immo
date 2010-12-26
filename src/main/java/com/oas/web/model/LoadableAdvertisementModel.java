package com.oas.web.model;

import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.oas.model.Advertisement;
import com.oas.services.IAdvertisementSearchService;

public class LoadableAdvertisementModel extends LoadableDetachableModel {

	@SpringBean(name = "advertisementSearchService")
	private IAdvertisementSearchService advertisementSearchService;

	private Long realStateId;

	public LoadableAdvertisementModel(Long id) {
		InjectorHolder.getInjector().inject(this);
		this.realStateId = id;
	}

	@Override
	protected Object load() {
		if (realStateId == null)
			return new Advertisement();
		else
			return advertisementSearchService.findById(realStateId);
	}
}
