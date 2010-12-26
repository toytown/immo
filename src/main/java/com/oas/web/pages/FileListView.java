package com.oas.web.pages;

import java.io.File;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.file.Files;

/**
 * List view for files in upload folder.
 */
public class FileListView extends ListView<File> {
	/**
	 * Construct.
	 * 
	 * @param name
	 *            Component name
	 * @param files
	 *            The file list model
	 */
	public FileListView(String name, final IModel<List<File>> files) {
		super(name, files);
	}

	/**
	 * @see ListView#populateItem(ListItem)
	 */
	@Override
	protected void populateItem(ListItem<File> listItem) {
		final File file = listItem.getModelObject();
		listItem.add(new Label("file", file.getName()));
		listItem.add(new Link("delete") {
			@Override
			public void onClick() {
				Files.remove(file);
				info("Deleted " + file);
			}
		});
	}
}
