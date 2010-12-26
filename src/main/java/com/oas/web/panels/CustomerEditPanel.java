package com.oas.web.panels;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.SimpleFormComponentLabel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.oas.model.Customer;
import com.oas.services.ICustomerService;
import com.oas.web.main.OASSession;
import com.oas.web.model.LoadableCustomerModel;
import com.oas.web.pages.CustomerPage;

public class CustomerEditPanel extends Panel {

	//private static final List<String> NUMBERS = Arrays.asList(new String[] { "1", "2" });

	
	@SpringBean
	private ICustomerService customerService;
	
	public CustomerEditPanel(String id) {
		super(id);

		final Customer userSess = ((OASSession)Session.get()).getUser();

		CompoundPropertyModel userModel = new CompoundPropertyModel(new LoadableCustomerModel(userSess.getId()));	
		
		final Form userRegistrationForm = new Form("userRegistrationForm", userModel) {
			@Override
			public final void onSubmit() {
				Customer newUser = (Customer)getModelObject();
				customerService.update(newUser);
				OASSession sess = (OASSession)Session.get();
				sess.setUser(newUser);
				setResponsePage(CustomerPage.class);
			}

		};

		TextField userNameText = new TextField("userName");
		userNameText.setLabel(new StringResourceModel("userRegistrationForm.userName", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("userNameLabel", userNameText));
		userRegistrationForm.add(userNameText);
		userNameText.setEnabled(false);
		

		TextField titleText = new TextField("title");
		titleText.setLabel(new StringResourceModel("userRegistrationForm.title", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("titleLabel", titleText));
		userRegistrationForm.add(titleText);

		TextField firstNameText = new TextField("firstName");
		firstNameText.setLabel(new StringResourceModel("userRegistrationForm.firstName", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("firstNameLabel", firstNameText));
		userRegistrationForm.add(firstNameText);

		TextField lastNameText = new TextField("lastName");
		lastNameText.setLabel(new StringResourceModel("userRegistrationForm.lastName", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("lastNameLabel", lastNameText));
		userRegistrationForm.add(lastNameText);

		TextField companyNameText = new TextField("companyName");
		companyNameText.setLabel(new StringResourceModel("userRegistrationForm.companyName", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("companyNameLabel", companyNameText));
		userRegistrationForm.add(companyNameText);

		PasswordTextField passwordText = new PasswordTextField("password");
		passwordText.setLabel(new StringResourceModel("userRegistrationForm.password", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("passwordLabel", passwordText));
		userRegistrationForm.add(passwordText);

		/*
		RadioGroup<String> group = new RadioGroup<String>("userCategoryGroup", userModel);
		ListView<String> categories = new ListView<String>("userCategory", NUMBERS) {
			@Override
			protected void populateItem(ListItem<String> item) {
				item.add(new Radio<String>("userCategory", item.getModel()));
			};
		}.setReuseItems(true);
		group.add(categories);
		*/


		TextField cityText = new TextField("city", userModel.bind("contactDetails[0].city"));
		cityText.setLabel(new StringResourceModel("userRegistrationForm.city", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("cityLabel", cityText));
		userRegistrationForm.add(cityText);
		
		TextField zipText = new TextField("zip", userModel.bind("contactDetails[0].zip"));
		zipText.setLabel(new StringResourceModel("userRegistrationForm.zip", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("zipLabel", zipText));
		userRegistrationForm.add(zipText);

		TextField streetText = new TextField("street", userModel.bind("contactDetails[0].street"));
		streetText.setLabel(new StringResourceModel("userRegistrationForm.street", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("streetLabel", streetText));
		userRegistrationForm.add(streetText);
		
		TextField houseNumberText = new TextField("houseNumber", userModel.bind("contactDetails[0].houseNumber"));
		houseNumberText.setLabel(new StringResourceModel("userRegistrationForm.houseNumber", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("houseNumberLabel", houseNumberText));
		userRegistrationForm.add(houseNumberText);		

		TextField emailText = new TextField("email", userModel.bind("contactDetails[0].email"));
		emailText.setLabel(new StringResourceModel("userRegistrationForm.email", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("emailLabel", emailText));
		userRegistrationForm.add(emailText);
		
		TextField faxText = new TextField("fax", userModel.bind("contactDetails[0].fax"));
		faxText.setLabel(new StringResourceModel("userRegistrationForm.fax", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("faxLabel", faxText));
		userRegistrationForm.add(faxText);
		
		TextField phone1Text = new TextField("phone1", userModel.bind("contactDetails[0].phone1"));
		phone1Text.setLabel(new StringResourceModel("userRegistrationForm.phone1", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("phone1Label", phone1Text));
		userRegistrationForm.add(phone1Text);
		
		TextField mobileText = new TextField("mobile", userModel.bind("contactDetails[0].mobile"));
		mobileText.setLabel(new StringResourceModel("userRegistrationForm.mobile", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("mobileLabel", mobileText));
		userRegistrationForm.add(mobileText);		

		TextField homePageURLText = new TextField("homePageURL", userModel.bind("contactDetails[0].homePageURL"));
		homePageURLText.setLabel(new StringResourceModel("userRegistrationForm.homePageURL", this, null));
		userRegistrationForm.add(new SimpleFormComponentLabel("homePageURLLabel", homePageURLText));
		userRegistrationForm.add(homePageURLText);	
		
		//userRegistrationForm.add(group);
		
		userRegistrationForm.add(new Button("register"));
		add(userRegistrationForm);	
	}

}
