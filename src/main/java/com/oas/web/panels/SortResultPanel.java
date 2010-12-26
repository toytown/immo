package com.oas.web.panels;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

import com.oas.web.model.SortingType;
import com.oas.web.search.SearchRequest;

public class SortResultPanel extends Panel {

	private static final List<SortingType> sortingTypeList = new ArrayList<SortingType>();
	
	static {
		SortingType sortType0 = new SortingType();		
		sortType0.setId(new Integer(0));
		sortType0.setDescription("sort by relevance (date, price, rooms, size)");
		sortingTypeList.add(sortType0);
		
		SortingType sortType1 = new SortingType();	
		sortType1.setId(new Integer(1));
		sortType1.setDescription("sort by preis asc");
		sortingTypeList.add(sortType1);
		
		SortingType sortType2 = new SortingType();
		sortType2.setId(new Integer(2));
		sortType2.setDescription("sort by preis desc");
		sortingTypeList.add(sortType2);		
		
		SortingType sortType3 = new SortingType();
		sortType3.setId(new Integer(3));
		sortType3.setDescription("sort by size asc");
		sortingTypeList.add(sortType3);
		
		SortingType sortType4 = new SortingType();
		sortType4.setId(new Integer(4));
		sortType4.setDescription("sort by size desc");
		sortingTypeList.add(sortType4);	
		
		SortingType sortType5 = new SortingType();
		sortType5.setId(new Integer(5));
		sortType5.setDescription("sort by room asc");
		sortingTypeList.add(sortType5);
		
		SortingType sortType6 = new SortingType();
		sortType6.setId(new Integer(6));
		sortType6.setDescription("sort by room desc");
		sortingTypeList.add(sortType6);		
	}
	
	public SortResultPanel(String id, IModel<SearchRequest> requestModel) {
		super(id, requestModel);
		
		CompoundPropertyModel<SortingType> sortingModel = new CompoundPropertyModel<SortingType>(requestModel.getObject());
		
		DropDownChoice<SortingType> sortingTypesTypesDropDown = new DropDownChoice("sortingTypes", sortingModel.bind("sortType"),
				sortingTypeList, new ChoiceRenderer("description", "id"));
		sortingTypesTypesDropDown.setLabel(new StringResourceModel("advertPanelForm.heatingTypes", this, null));

		sortingTypesTypesDropDown.setDefaultModelObject(sortingTypeList.get(0));
		this.add(sortingTypesTypesDropDown);
	}
	

}
