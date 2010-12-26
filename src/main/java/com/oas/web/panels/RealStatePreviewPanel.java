package com.oas.web.panels;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;

import com.oas.common.utils.DateTools;
import com.oas.model.Advertisement;
import com.oas.shared.resources.OASSharedResources;

public class RealStatePreviewPanel extends Panel {

	public RealStatePreviewPanel(String id, final Advertisement realStateAdvert) {
		super(id);
	
		final DateTools dateTools = DateTools.getInstance(); 
		add(new Label("title", realStateAdvert.getTitleDescription()));
		addImage("print");
		addImage("email");
		addImage("germany");
		
		add(new ImageNavigationPanel("imageNavPanel", realStateAdvert.getId(), realStateAdvert.getImageDir(), false));
		add(new Label("availableFromLabel", "Available From"));
		add(new Label("availableFrom", dateTools.toString(realStateAdvert.getAvailableFrom())));
	
		add(new Label("builtYearLabel", "Bau Jahr :"));
		add(new Label("builtYear", getFormattedValue(realStateAdvert.getBuiltYear())));
		
		add(new Label("city", realStateAdvert.getCity()));

		add(new Label("areaCode", realStateAdvert.getAreaCode()));
		
		add(new Label("street", realStateAdvert.getStreet()));
		
		add(new Label("houseNo", realStateAdvert.getHouseNo()));
		
		add(new Label("floorLabel", "Floor"));
		add(new Label("floor", getFormattedValue(realStateAdvert.getFloor())));
		
		add(new Label("totalFloorsLabel", "Total Floors"));
		add(new Label("totalFloors", getFormattedValue(realStateAdvert.getTotalFloors())));
		
		add(new Label("sizeLabel", "Size"));
		add(new Label("size", getFormattedValue(realStateAdvert.getSize())));
		
		add(new Label("costLabel", "Cost"));
		add(new Label("cost", getFormattedValue(realStateAdvert.getCost())));

		add(new Label("additionalCostLabel", "Zusatz Kosten :"));
		add(new Label("additionalCost", getFormattedValue(realStateAdvert.getAdditionalCost())));
		
		add(new Label("provisionLabel", "Provision"));
		add(new Label("provision", realStateAdvert.getProvisionCondition()));
		
		add(new Label("depositPeriodLabel", "Kaution :"));
		add(new Label("depositPeriod", getFormattedValue(realStateAdvert.getDepositPeriod())));
		
		add(new Label("totalRoomsLabel", "Total Rooms :"));
		add(new Label("totalRooms", getFormattedValue(realStateAdvert.getTotalRooms())));
		
		add(new Label("bedRoomsLabel", "Bed Rooms :"));
		add(new Label("bedRooms", getFormattedValue(realStateAdvert.getBedRooms())));
		
		add(new Label("bathRoomsLabel", "Bath Rooms :"));
		add(new Label("bathRooms", getFormattedValue(realStateAdvert.getBathRooms())));
		
		add(new Label("kitchen", "kitchen"));
		addImage("kitchenImg", realStateAdvert.isKitchenAvailable());
		
		add(new Label("cellar", "cellar"));
		addImage("cellarImg", realStateAdvert.isCellarAvailable());
		
		add(new Label("balcony", "balcony"));
		addImage("balconyImg", realStateAdvert.isBalconyAvailable());

		add(new Label("garage", "garage"));
		addImage("garageImg", realStateAdvert.isGarageAvailable());

		add(new Label("energyPass", "energyPass"));
		addImage("energyPassImg", realStateAdvert.isEnergyPassAvailable());
		
		add(new Label("toiletWithBathRoom", "toiletWithBathRoom"));
		addImage("toiletWithBathRoomImg", realStateAdvert.isToiletWithBathRoom());
		
		add(new Label("lift", "lift"));
		addImage("liftImg", realStateAdvert.isLiftAvailable());

		add(new Label("furnished", "furnished"));
		addImage("furnishedImg", realStateAdvert.isFurnished());
		
		add(new Label("appartmentTypeLabel", "Appartment CategoryType"));
		add(new Label("appartmentType", realStateAdvert.getCategoryType().getDescription()));
		
		add(new Label("heatingTypeLabel", "Heating CategoryType"));
		add(new Label("heatingType", realStateAdvert.getHeatingType().getDescription()));
		
		add(new Label("descriptionLabel", "Beschreibung :"));
		add(new MultiLineLabel("description", realStateAdvert.getDescription()));
		
		add(new Label("areaDescriptionLabel", "Umgebung :"));
		add(new MultiLineLabel("areaDescription", realStateAdvert.getAreaDescription()));
		
		add(new Label("fittingsLabel", "Sonstiges"));
		add(new MultiLineLabel("fittings", realStateAdvert.getFittings()));
		
		add(new Label("otherInformationLabel", "Note :"));
		add(new MultiLineLabel("otherInformation", realStateAdvert.getOtherInformation()));
		
		//contactPanel
		add(new ContactPanel("contactPanel"));


		
	}
	
	private String getFormattedValue(Object params) {
		if (params != null) {
			return String.valueOf(params);
		} else {
			return "";
		}
	}
	
	private void addImage(String imageId, boolean available) {
		Image accept = new Image(imageId, new ResourceReference(OASSharedResources.class, "check.png"));
		Image cross = new Image(imageId, new ResourceReference(OASSharedResources.class, "cross.png"));		
		if (available) {
			add(accept);
		} else {
			add(cross);
		}		
	}
	
	private void addImage(String imageId) {
		Image imageicon = new Image(imageId, new ResourceReference(OASSharedResources.class, imageId +".png"));
		add(imageicon);
	}	

}
