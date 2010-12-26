package com.oas.web.panels;


import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SimpleFormComponentLabel;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.StringResourceModel;

public class ContactPanel extends Panel {

	public ContactPanel(String id) {
		super(id);
		Form contactForm = new Form("contactForm");
		TextField firstNameText = new TextField("firstName");
		firstNameText.setLabel(new StringResourceModel("contactForm.firstName", this, null));
		contactForm.add(new SimpleFormComponentLabel("firstNameLabel", firstNameText));
		
		TextField lastNameText = new TextField("lastName");
		lastNameText.setLabel(new StringResourceModel("contactForm.lastName", this, null));
		contactForm.add(new SimpleFormComponentLabel("lastNameLabel", lastNameText));
		
		TextField phoneText = new TextField("phone");
		phoneText.setLabel(new StringResourceModel("contactForm.phone", this, null));
		contactForm.add(new SimpleFormComponentLabel("phoneLabel", phoneText));
		
		TextField emailText = new TextField("email");
		emailText.setLabel(new StringResourceModel("contactForm.email", this, null));
		contactForm.add(new SimpleFormComponentLabel("emailLabel", emailText));
		
		TextArea messageText = new TextArea("message");
		messageText.setLabel(new StringResourceModel("contactForm.message", this, null));
		contactForm.add(new SimpleFormComponentLabel("messageLabel", messageText));
		
		contactForm.add(firstNameText);		
		contactForm.add(lastNameText);
		contactForm.add(messageText);
		contactForm.add(phoneText);
		contactForm.add(emailText);
		contactForm.add(new SubmitLink("submitMesssage"){
			@Override
			public void onSubmit() {
				
			}
		});
		add(contactForm);
	}

}
