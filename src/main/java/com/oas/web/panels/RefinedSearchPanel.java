package com.oas.web.panels;

import java.util.List;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SimpleFormComponentLabel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.oas.model.CategoryType;
import com.oas.services.ICommonService;
import com.oas.web.search.SearchRequest;

public class RefinedSearchPanel extends Panel {

	@SpringBean
	private ICommonService commonService;
	
	public RefinedSearchPanel(String id, IModel<SearchRequest> model) {
		super(id, model);
		
		List<CategoryType> appartmentTypeChoices = commonService.getCategoryTypes();
		
		final CompoundPropertyModel<SearchRequest> searchReqModel = new CompoundPropertyModel<SearchRequest>(model.getObject());
		final Form<SearchRequest> refinedSearchForm = new Form<SearchRequest>("refinedSearchForm", searchReqModel);
		ChoiceRenderer renderer = new ChoiceRenderer("description", "id");
		final CheckBoxMultipleChoice appartmentTypeChoice = new CheckBoxMultipleChoice("appartmentTypesChoice",  searchReqModel.bind("categoryTypes"), appartmentTypeChoices, renderer);
		refinedSearchForm.add(appartmentTypeChoice);
	

		CheckBox kitchenAvailChk = new CheckBox("kitchenAvailable");
		kitchenAvailChk.setLabel(new StringResourceModel("refinedSearchForm.kitchenAvailable", this, null));
		refinedSearchForm.add(new SimpleFormComponentLabel("kitchenAvailableLabel", kitchenAvailChk));
		refinedSearchForm.add(kitchenAvailChk);
		
		CheckBox cellarAvailChk = new CheckBox("cellarAvailable");
		cellarAvailChk.setLabel(new StringResourceModel("refinedSearchForm.cellarAvailable", this, null));
		refinedSearchForm.add(new SimpleFormComponentLabel("cellarAvailableLabel", cellarAvailChk));
		refinedSearchForm.add(cellarAvailChk);

		CheckBox baclconAvailChk = new CheckBox("balconyAvailable");
		baclconAvailChk.setLabel(new StringResourceModel("refinedSearchForm.balconyAvailable", this, null));
		refinedSearchForm.add(new SimpleFormComponentLabel("balconyAvailableLabel", baclconAvailChk));
		refinedSearchForm.add(baclconAvailChk);

		CheckBox garageAvailChk = new CheckBox("garageAvailable");
		garageAvailChk.setLabel(new StringResourceModel("refinedSearchForm.garageAvailable", this, null));
		refinedSearchForm.add(new SimpleFormComponentLabel("garageAvailableLabel", garageAvailChk));
		refinedSearchForm.add(garageAvailChk);
		
		CheckBox gardenAvailChk = new CheckBox("gardenAvailable");
		gardenAvailChk.setLabel(new StringResourceModel("refinedSearchForm.gardenAvailable", this, null));
		refinedSearchForm.add(new SimpleFormComponentLabel("gardenAvailableLabel", gardenAvailChk));
		refinedSearchForm.add(gardenAvailChk);		

		CheckBox toiletWithBathRoomChk = new CheckBox("toiletWithBathRoom");
		toiletWithBathRoomChk.setLabel(new StringResourceModel("refinedSearchForm.toiletWithBathRoom", this, null));
		refinedSearchForm.add(new SimpleFormComponentLabel("toiletWithBathRoomLabel", toiletWithBathRoomChk));
		refinedSearchForm.add(toiletWithBathRoomChk);
		
		CheckBox liftAvailChk = new CheckBox("liftAvailable");
		liftAvailChk.setLabel(new StringResourceModel("refinedSearchForm.liftAvailable", this, null));
		refinedSearchForm.add(new SimpleFormComponentLabel("liftAvailableLabel", liftAvailChk));
		refinedSearchForm.add(liftAvailChk);
		
		CheckBox furnishedChk = new CheckBox("furnished");
		furnishedChk.setLabel(new StringResourceModel("refinedSearchForm.furnished", this, null));
		refinedSearchForm.add(new SimpleFormComponentLabel("furnishedLabel", furnishedChk));
		refinedSearchForm.add(furnishedChk);
		
		
		CheckBox energyPassAvailChk = new CheckBox("energyPassAvailable");
		energyPassAvailChk.setLabel(new StringResourceModel("refinedSearchForm.energyPassAvailable", this, null));
		refinedSearchForm.add(new SimpleFormComponentLabel("energyPassAvailableLabel", energyPassAvailChk));
		refinedSearchForm.add(energyPassAvailChk);		

		CheckBox barrierFreeChk = new CheckBox("barrierFree");
		barrierFreeChk.setLabel(new StringResourceModel("refinedSearchForm.barrierFree", this, null));
		refinedSearchForm.add(new SimpleFormComponentLabel("barrierFreeLabel", barrierFreeChk));
		refinedSearchForm.add(barrierFreeChk);
		
		CheckBox provisionFreeChk = new CheckBox("provisionFree");
		provisionFreeChk.setLabel(new StringResourceModel("refinedSearchForm.provisionFree", this, null));
		refinedSearchForm.add(new SimpleFormComponentLabel("provisionFreeLabel", provisionFreeChk));
		refinedSearchForm.add(provisionFreeChk);
		
		CheckBox seniorAppartmentChk = new CheckBox("seniorAppartment");
		seniorAppartmentChk.setLabel(new StringResourceModel("refinedSearchForm.seniorAppartment", this, null));
		refinedSearchForm.add(new SimpleFormComponentLabel("seniorAppartmentLabel", energyPassAvailChk));
		refinedSearchForm.add(seniorAppartmentChk);
		
		TextField<Integer> builtYearFromTxt = new TextField<Integer>("builtYearFrom");
		builtYearFromTxt.setLabel(new StringResourceModel("refinedSearchForm.builtYear", this, null));
		refinedSearchForm.add(new SimpleFormComponentLabel("builtYearLabel", builtYearFromTxt));
		refinedSearchForm.add(builtYearFromTxt);
		
		TextField<Integer> builtYearToTxt = new TextField<Integer>("builtYearTo");
		refinedSearchForm.add(builtYearToTxt);
		
		this.add(refinedSearchForm);
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return "detailSearch";
	}

}
