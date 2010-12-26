package com.oas.web.pages;

import com.oas.web.panels.EditTypePanel;

public class EditPage extends BasePage {

	public EditPage() {
		super();
		this.add(new EditTypePanel("editTypePanel"));
	}
}
