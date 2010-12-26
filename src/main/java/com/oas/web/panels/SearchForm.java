package com.oas.web.panels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.Strings;

import com.oas.services.IAdvertisementSearchService;
import com.oas.web.search.SearchRequest;

public class SearchForm extends Form<SearchRequest> {


	@SpringBean
	private IAdvertisementSearchService advertSearch;
	
	public SearchForm(String id, IModel<SearchRequest> aSearchRequest) {
		super(id, aSearchRequest);
		
		CompoundPropertyModel<SearchRequest> searchReq = new CompoundPropertyModel<SearchRequest>(aSearchRequest);
		this.setModel(searchReq);
		
		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);


		// labels
		add(new Label("cityLabel", new StringResourceModel("city", this, null)));
		add(new Label("sizeLabel", new StringResourceModel("size", this, null)));
		add(new Label("roomsLabel", new StringResourceModel("rooms", this, null)));
		add(new Label("costLabel", new StringResourceModel("cost", this, null)));

		AutoCompleteTextField<String> cityText = new AutoCompleteTextField<String>("city") {

			@Override
			protected Iterator<String> getChoices(String input) {
				if (Strings.isEmpty(input)) {
					List<String> emptyList = Collections.emptyList();
					return emptyList.iterator();
				}

				List<String> cities = advertSearch.findCities(input);
				List<String> choices = new ArrayList<String>(10);
				
				for (final String city : cities) {

					if (city.toUpperCase().startsWith(input.toUpperCase())) {
						choices.add(city);
						if (choices.size() == 10) {
							break;
						}
					}
				}

				return choices.iterator();
			}

		};
		cityText.setPersistent(true);
		cityText.setLabel(new StringResourceModel("city", this, null));
		add(cityText);

		TextField sizeFromText = new TextField("sizeFrom", Double.class);		
		sizeFromText.setPersistent(true);
		sizeFromText.setLabel(new StringResourceModel("sizeFrom", this, null));
		add(sizeFromText);

		TextField sizeToText = new TextField("sizeTo", Double.class);
		sizeToText.setPersistent(true);
		sizeToText.setLabel(new StringResourceModel("sizeTo", this, null));
		add(sizeToText);

		TextField roomsFromText = new TextField("roomsFrom", Double.class);
		roomsFromText.setPersistent(true);
		roomsFromText.setLabel(new StringResourceModel("roomsFrom", this, null));
		add(roomsFromText);

		TextField roomsToText = new TextField("roomsTo", Double.class);
		roomsToText.setPersistent(true);
		roomsToText.setLabel(new StringResourceModel("roomsTo", this, null));
		add(roomsToText);

		TextField costFromText = new TextField("costFrom", Double.class);
		costFromText.setPersistent(true);
		costFromText.setLabel(new StringResourceModel("costFrom", this, null));
		add(costFromText);

		TextField costToText = new TextField("costTo", Double.class);
		costToText.setPersistent(true);
		costToText.setLabel(new StringResourceModel("costTo", this, null));
		add(costToText);

		add(feedback);
		setOutputMarkupId(true);
	}

}
