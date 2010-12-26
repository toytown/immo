package com.oas.web.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.SimpleFormComponentLabel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.oas.model.Customer;
import com.oas.services.ICustomerService;
import com.oas.web.model.LoadableCustomerModel;

public class ModelTestPage extends WebPage {

	@SpringBean
	private ICustomerService customerService;
	
	private Customer oldUser;
	
	public ModelTestPage() {
		super();

		oldUser = customerService.findCustomerById(1l);
		CompoundPropertyModel userModel = new CompoundPropertyModel(new LoadableCustomerModel(oldUser.getId()));	
		
		final Form userRegistrationForm = new Form("userRegistrationForm", userModel) {
			@Override
			public final void onSubmit() {
				Customer newUser = (Customer)getModelObject();
				customerService.update(newUser);
			}

		};
		
		userRegistrationForm.setModel(userModel);

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

		userRegistrationForm.add(new Button("register"));
		add(userRegistrationForm);			
	}

}
