package com.oas.web.panels;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.FormComponentLabel;
import org.apache.wicket.markup.html.form.SimpleFormComponentLabel;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.file.Folder;

import com.oas.common.utils.OASAplicationConfiguration;
import com.oas.model.Advertisement;
import com.oas.model.CategoryType;
import com.oas.model.HeatingType;
import com.oas.services.IAdvertisementSearchService;
import com.oas.services.ICommonService;
import com.oas.web.model.LoadableAdvertisementModel;

public class RealStateEditPanel extends Panel {

	private static final long serialVersionUID = 1519030982452789750L;
	private Advertisement realStateAdvert = null;
	
	@SpringBean
	private ICommonService commonService;

	@SpringBean
	private OASAplicationConfiguration appConfig;
	
	@SpringBean
	private IAdvertisementSearchService advertisementSearchService;
	
	public RealStateEditPanel(final String id, final Advertisement advert) {
		super(id);

		// labels

		final Form<Advertisement> editForm = new Form<Advertisement>("realStateAdvertForm");

		if (advert == null) {
			realStateAdvert = new Advertisement();	
		} else {
			realStateAdvert = advert;
		}
		final IModel loadableAppartmentModel = new LoadableAdvertisementModel(realStateAdvert.getId());
		CompoundPropertyModel realStateModel = new CompoundPropertyModel(loadableAppartmentModel);
		editForm.setModel(realStateModel);
		editForm.setMultiPart(true);

		TextField titleDescriptionText = new TextField("titleDescription");
		titleDescriptionText.setLabel(new StringResourceModel("advertPanelForm.title", this, null));
		editForm.add(new SimpleFormComponentLabel("titleDescriptionLabel", titleDescriptionText));
		editForm.add(titleDescriptionText);

		DateField availableFromDateText = new DateField("availableFrom");
		availableFromDateText.setLabel(new StringResourceModel("advertPanelForm.availableFrom", this, null));
		editForm.add(new SimpleFormComponentLabel("availableFromLabel", availableFromDateText));
		editForm.add(availableFromDateText);

		TextField builtYearText = new TextField("builtYear");
		builtYearText.setLabel(new StringResourceModel("advertPanelForm.builtYear", this, null));
		editForm.add(new SimpleFormComponentLabel("builtYearLabel", builtYearText));
		editForm.add(builtYearText);

		DateField activationDateText = new DateField("activationDate");
		activationDateText.setLabel(new StringResourceModel("advertPanelForm.activationDate", this, null));
		//activationDateText.add(new DatePicker());
		editForm.add(new SimpleFormComponentLabel("activationDateLabel", activationDateText));
		editForm.add(activationDateText);

		TextField cityText = new TextField("city");
		cityText.setLabel(new StringResourceModel("advertPanelForm.city", this, null));
		editForm.add(new SimpleFormComponentLabel("cityLabel", cityText));
		editForm.add(cityText);
		
		TextField areaCodeText = new TextField("areaCode");
		areaCodeText.setLabel(new StringResourceModel("advertPanelForm.areaCode", this, null));
		editForm.add(new SimpleFormComponentLabel("areaCodeLabel", areaCodeText));
		editForm.add(areaCodeText);

		TextField streetText = new TextField("street");
		streetText.setLabel(new StringResourceModel("advertPanelForm.street", this, null));
		editForm.add(new SimpleFormComponentLabel("streetLabel", streetText));
		editForm.add(streetText);
		
		TextField houseNoText = new TextField("houseNo");
		houseNoText.setLabel(new StringResourceModel("advertPanelForm.houseNo", this, null));
		editForm.add(new SimpleFormComponentLabel("houseNoLabel", houseNoText));
		editForm.add(houseNoText);
		
		TextField floorText = new TextField("floor");
		floorText.setLabel(new StringResourceModel("advertPanelForm.floor", this, null));
		editForm.add(new SimpleFormComponentLabel("floorLabel", floorText));
		editForm.add(floorText);		
		
		TextField totalFloorsText = new TextField("totalFloors");
		totalFloorsText.setLabel(new StringResourceModel("advertPanelForm.totalFloors", this, null));
		editForm.add(new SimpleFormComponentLabel("totalFloorsLabel", totalFloorsText));
		editForm.add(totalFloorsText);
		
		TextField sizeText = new TextField("size");
		sizeText.setLabel(new StringResourceModel("advertPanelForm.size", this, null));
		editForm.add(new SimpleFormComponentLabel("sizeLabel", sizeText));
		editForm.add(sizeText);

		TextField costText = new TextField("cost");
		costText.setLabel(new StringResourceModel("advertPanelForm.cost", this, null));
		editForm.add(new SimpleFormComponentLabel("costLabel", costText));
		editForm.add(costText);

		TextField additionalCostText = new TextField("additionalCost");
		additionalCostText.setLabel(new StringResourceModel("advertPanelForm.additionalCost", this, null));
		editForm.add(new SimpleFormComponentLabel("additionalCostLabel", additionalCostText));
		editForm.add(additionalCostText);
		
		TextField depositPeiodText = new TextField("depositPeriod");
		depositPeiodText.setLabel(new StringResourceModel("advertPanelForm.depositPeriod", this, null));
		editForm.add(new SimpleFormComponentLabel("depositPeriodLabel", depositPeiodText));
		editForm.add(depositPeiodText);

		CheckBox provisionFreeChk = new CheckBox("provisionFree");
		provisionFreeChk.setLabel(new StringResourceModel("advertPanelForm.provisionFree", this, null));
		editForm.add(new SimpleFormComponentLabel("provisionFreeLabel", provisionFreeChk));
		editForm.add(provisionFreeChk);
		
		TextField provisionConditionText = new TextField("provisionCondition");
		provisionConditionText.setLabel(new StringResourceModel("advertPanelForm.provisionCondition", this, null));
		editForm.add(new SimpleFormComponentLabel("provisionConditionLabel", provisionConditionText));
		editForm.add(provisionConditionText);
		
		TextField totalRoomsText = new TextField("totalRooms");
		totalRoomsText.setLabel(new StringResourceModel("advertPanelForm.totalRooms", this, null));
		editForm.add(new SimpleFormComponentLabel("totalRoomsLabel", totalRoomsText));
		editForm.add(totalRoomsText);

		TextField bedRoomsText = new TextField("bedRooms");
		bedRoomsText.setLabel(new StringResourceModel("advertPanelForm.bedRooms", this, null));
		editForm.add(new SimpleFormComponentLabel("bedRoomsLabel", bedRoomsText));
		editForm.add(bedRoomsText);

		TextField bathRoomsText = new TextField("bathRooms");
		bathRoomsText.setLabel(new StringResourceModel("advertPanelForm.bathRooms", this, null));
		editForm.add(new SimpleFormComponentLabel("bathRoomsLabel", bathRoomsText));
		editForm.add(bathRoomsText);

		CheckBox kitchenAvailChk = new CheckBox("kitchenAvailable");
		kitchenAvailChk.setLabel(new StringResourceModel("advertPanelForm.kitchenAvailable", this, null));
		editForm.add(new SimpleFormComponentLabel("kitchenAvailableLabel", kitchenAvailChk));
		editForm.add(kitchenAvailChk);
		
		CheckBox cellarAvailChk = new CheckBox("cellarAvailable");
		cellarAvailChk.setLabel(new StringResourceModel("advertPanelForm.cellarAvailable", this, null));
		editForm.add(new SimpleFormComponentLabel("cellarAvailableLabel", cellarAvailChk));
		editForm.add(cellarAvailChk);

		CheckBox baclconAvailChk = new CheckBox("balconyAvailable");
		baclconAvailChk.setLabel(new StringResourceModel("advertPanelForm.balconyAvailable", this, null));
		editForm.add(new SimpleFormComponentLabel("balconyAvailableLabel", baclconAvailChk));
		editForm.add(baclconAvailChk);

		CheckBox garageAvailChk = new CheckBox("garageAvailable");
		garageAvailChk.setLabel(new StringResourceModel("advertPanelForm.garageAvailable", this, null));
		editForm.add(new SimpleFormComponentLabel("garageAvailableLabel", garageAvailChk));
		editForm.add(garageAvailChk);

		CheckBox energyPassAvailChk = new CheckBox("energyPassAvailable");
		energyPassAvailChk.setLabel(new StringResourceModel("advertPanelForm.energyPassAvailable", this, null));
		editForm.add(new SimpleFormComponentLabel("energyPassAvailableLabel", energyPassAvailChk));
		editForm.add(energyPassAvailChk);

		CheckBox toiletWithBathRoomChk = new CheckBox("toiletWithBathRoom");
		toiletWithBathRoomChk.setLabel(new StringResourceModel("advertPanelForm.toiletWithBathRoom", this, null));
		editForm.add(new SimpleFormComponentLabel("toiletWithBathRoomLabel", toiletWithBathRoomChk));
		editForm.add(toiletWithBathRoomChk);
		
		CheckBox gardenAvailChk = new CheckBox("gardenAvailable");
		gardenAvailChk.setLabel(new StringResourceModel("advertPanelForm.gardenAvailable", this, null));
		editForm.add(new SimpleFormComponentLabel("gardenAvailableLabel", gardenAvailChk));
		editForm.add(gardenAvailChk);
		
		CheckBox liftAvailChk = new CheckBox("liftAvailable");
		liftAvailChk.setLabel(new StringResourceModel("advertPanelForm.liftAvailable", this, null));
		editForm.add(new SimpleFormComponentLabel("liftAvailableLabel", liftAvailChk));
		editForm.add(liftAvailChk);
		
		CheckBox furnishedChk = new CheckBox("furnished");
		furnishedChk.setLabel(new StringResourceModel("advertPanelForm.furnished", this, null));
		editForm.add(new SimpleFormComponentLabel("furnishedLabel", furnishedChk));
		editForm.add(furnishedChk);
		
		List<CategoryType> categoriesTypesList = commonService.getCategoryTypes();
		DropDownChoice<CategoryType> categoriesTypesListDropDown = new DropDownChoice("categoryTypes", realStateModel.bind("categoryType"),
				categoriesTypesList, new ChoiceRenderer("description", "id"));
		categoriesTypesListDropDown.setLabel(new StringResourceModel("advertPanelForm.categoryTypes", this, null));
		editForm.add(new SimpleFormComponentLabel("categoryTypesLabel", categoriesTypesListDropDown));
		editForm.add(categoriesTypesListDropDown);

		List<HeatingType> heatingTypesList = commonService.getHeatingTypes();
		DropDownChoice<HeatingType> heatingTypesTypesDropDown = new DropDownChoice("heatingTypes", realStateModel.bind("heatingType"),
				heatingTypesList, new ChoiceRenderer("description", "id"));
		heatingTypesTypesDropDown.setLabel(new StringResourceModel("advertPanelForm.heatingTypes", this, null));
		//heatingTypesTypesDropDown.setDefaultModel(new CompoundPropertyModel<HeatingType>(heatingTypesList.get(0)));
		editForm.add(new SimpleFormComponentLabel("heatingTypesLabel", heatingTypesTypesDropDown));
		editForm.add(heatingTypesTypesDropDown);

		TextArea descriptionText = new TextArea("description");
		descriptionText.setLabel(new StringResourceModel("advertPanelForm.description", this, null));
		editForm.add(new SimpleFormComponentLabel("descriptionLabel", descriptionText));
		editForm.add(descriptionText);

		TextArea areaDescriptionText = new TextArea("areaDescription");
		areaDescriptionText.setLabel(new StringResourceModel("advertPanelForm.areaDescription", this, null));
		editForm.add(new SimpleFormComponentLabel("areaDescriptionLabel", areaDescriptionText));
		editForm.add(areaDescriptionText);

		TextArea fittingsText = new TextArea("fittings");
		fittingsText.setLabel(new StringResourceModel("advertPanelForm.fittings", this, null));
		editForm.add(new SimpleFormComponentLabel("fittingsLabel", fittingsText));
		editForm.add(fittingsText);

		TextArea otherInformationText = new TextArea("otherInformation");
		otherInformationText.setLabel(new StringResourceModel("advertPanelForm.otherInformation", this, null));
		editForm.add(new SimpleFormComponentLabel("otherInformationLabel", otherInformationText));
		editForm.add(otherInformationText);
		
		TextField lastRenovatedYearText = new TextField("lastRenovatedYear");
		lastRenovatedYearText.setLabel(new StringResourceModel("advertPanelForm.lastRenovatedYear", this, null));
		editForm.add(new SimpleFormComponentLabel("lastRenovatedYearLabel", lastRenovatedYearText));
		editForm.add(lastRenovatedYearText);
		
		final Panel uploadPanel = new EmptyPanel("uploadPanel");
		
		editForm.add(new SubmitLink("submitAdvertisement") {
			@Override
			public void onSubmit() {
				
				editForm.setVisible(false);
				Advertisement advertModified = editForm.getModelObject();
				
				final String imageLocation = appConfig.getImageStore();
				final Folder uploadFolder;
				
				if (advertModified.getImageDir() != null) {
					uploadFolder = new Folder(imageLocation + File.separatorChar + advertModified.getImageDir() );
				} else {
					uploadFolder = new Folder(imageLocation + File.separatorChar + UUID.randomUUID().toString() );
				}
				
				if (!uploadFolder.exists()) {
					uploadFolder.mkdirs();
					advertModified.setImageDir(uploadFolder.getName());
				}
				advertModified = advertisementSearchService.update(advertModified);
				LoadableAdvertisementModel apptAdvertModel = new LoadableAdvertisementModel(advertModified.getId());
				uploadPanel.replaceWith(new UploadPanel("uploadPanel", uploadFolder, apptAdvertModel));
			}
			
		});

		this.add(editForm);
		this.add(uploadPanel);
	}

	protected FormComponentLabel createFieldLabel(final MarkupContainer container, final FormComponent formComponent) {
		FormComponentLabel labelForm = new FormComponentLabel(formComponent.getId() + "Label", formComponent);
		labelForm.add(formComponent);
		Label label = new Label(formComponent.getId() + "LabelSpan", new StringResourceModel("advertPanelForm." + formComponent.getId() + "Label",
				this, null));
		labelForm.add(label);
		container.add(labelForm);
		return labelForm;
	}
}
