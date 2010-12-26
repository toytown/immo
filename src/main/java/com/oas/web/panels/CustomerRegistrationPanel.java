package com.oas.web.panels;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.SimpleFormComponentLabel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.oas.model.CustomerContactDetails;
import com.oas.model.Customer;
import com.oas.services.ICustomerService;

public class CustomerRegistrationPanel extends Panel {
	static final List<String> NUMBERS = Arrays.asList(new String[] { "1", "2" });

	@SpringBean
	private ICustomerService customerService;

	public ICustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public CustomerRegistrationPanel(String id) {
		super(id);
		final Customer customer = new Customer();
		final CustomerContactDetails contaclDetails = new CustomerContactDetails();
		Form userRegistrationForm = new Form("customerRegistrationForm") {
			@Override
			public final void onSubmit() {
				customer.getContactDetails().add(contaclDetails);
				customerService.save(customer);
			}

		};

		CompoundPropertyModel userModel = new CompoundPropertyModel(customer);
		userRegistrationForm.setModel(userModel);

		CompoundPropertyModel contactDetailsModel = new CompoundPropertyModel(contaclDetails);

		
		TextField titleText = new TextField("title");
		titleText.setLabel(new StringResourceModel("customerRegistrationForm.title", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("titleLabel", titleText));
		userRegistrationForm.add(titleText);

		TextField firstNameText = new TextField("firstName");
		firstNameText.setLabel(new StringResourceModel("customerRegistrationForm.firstName", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("firstNameLabel", firstNameText));
		userRegistrationForm.add(firstNameText);

		TextField lastNameText = new TextField("lastName");
		lastNameText.setLabel(new StringResourceModel("customerRegistrationForm.lastName", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("lastNameLabel", lastNameText));
		userRegistrationForm.add(lastNameText);

		TextField companyNameText = new TextField("companyName");
		companyNameText.setLabel(new StringResourceModel("customerRegistrationForm.companyName", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("companyNameLabel", companyNameText));
		userRegistrationForm.add(companyNameText);

		TextField userNameText = new TextField("userName");
		userNameText.setLabel(new StringResourceModel("customerRegistrationForm.userName", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("userNameLabel", userNameText));
		userRegistrationForm.add(userNameText);

		PasswordTextField passwordText = new PasswordTextField("password");
		passwordText.setLabel(new StringResourceModel("customerRegistrationForm.password", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("passwordLabel", passwordText));
		userRegistrationForm.add(passwordText);

		RadioGroup<String> group = new RadioGroup<String>("customerCategoryGroup", userModel);
		ListView<String> categories = new ListView<String>("customerCategory", NUMBERS) {
			@Override
			protected void populateItem(ListItem<String> item) {
				item.add(new Radio<String>("customerCategory", item.getModel()));
			};
		}.setReuseItems(true);
		group.add(categories);
		
		TextField cityText = new TextField("city", contactDetailsModel.bind("city"));
		cityText.setLabel(new StringResourceModel("customerRegistrationForm.city", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("cityLabel", cityText));
		userRegistrationForm.add(cityText);
		
		TextField zipText = new TextField("zip", contactDetailsModel.bind("zip"));
		zipText.setLabel(new StringResourceModel("customerRegistrationForm.zip", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("zipLabel", zipText));
		userRegistrationForm.add(zipText);

		TextField streetText = new TextField("street", contactDetailsModel.bind("street"));
		streetText.setLabel(new StringResourceModel("customerRegistrationForm.street", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("streetLabel", streetText));
		userRegistrationForm.add(streetText);
		
		TextField houseNumberText = new TextField("houseNumber", contactDetailsModel.bind("houseNumber"));
		houseNumberText.setLabel(new StringResourceModel("customerRegistrationForm.houseNumber", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("houseNumberLabel", houseNumberText));
		userRegistrationForm.add(houseNumberText);		

		TextField emailText = new TextField("email", contactDetailsModel.bind("email"));
		emailText.setLabel(new StringResourceModel("customerRegistrationForm.email", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("emailLabel", emailText));
		userRegistrationForm.add(emailText);
		
		TextField faxText = new TextField("fax", contactDetailsModel.bind("fax"));
		faxText.setLabel(new StringResourceModel("customerRegistrationForm.fax", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("faxLabel", faxText));
		userRegistrationForm.add(faxText);
		
		TextField mobileText = new TextField("mobile", contactDetailsModel.bind("mobile"));
		mobileText.setLabel(new StringResourceModel("customerRegistrationForm.mobile", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("mobileLabel", mobileText));
		userRegistrationForm.add(mobileText);		

		TextField homePageURLText = new TextField("homePageURL", contactDetailsModel.bind("homePageURL"));
		homePageURLText.setLabel(new StringResourceModel("customerRegistrationForm.homePageURL", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("homePageURLLabel", homePageURLText));
		userRegistrationForm.add(homePageURLText);	
		
		userRegistrationForm.add(group);
		userRegistrationForm.add(new Button("register"));
		add(userRegistrationForm);

	}

}
