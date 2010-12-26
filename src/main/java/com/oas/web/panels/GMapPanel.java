package com.oas.web.panels;

import org.apache.wicket.markup.html.panel.Panel;

import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMapType;

import com.oas.web.main.OASApplication;

public class GMapPanel extends Panel {


	public GMapPanel(String id) {
		super(id);

		final GMap2 gmap = new GMap2("gmap", OASApplication.get().getGoogleMapsAPIkey());
		gmap.setOutputMarkupId(true);
		gmap.setMapType(GMapType.G_NORMAL_MAP);
		gmap.addControl(GControl.GSmallMapControl);
		gmap.setCenter(new GLatLng(51.0600, 13.7210));
		add(gmap);
	}	 
}
