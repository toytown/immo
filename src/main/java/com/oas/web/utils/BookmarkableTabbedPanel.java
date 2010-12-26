package com.oas.web.utils;

import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class BookmarkableTabbedPanel extends TabbedPanel {

	private PageParameters pageParameters;
	private String tabParameterName = "tab";
	private int defaultTabIndex = 0;

	public BookmarkableTabbedPanel(String id, List<ITab> tabs) {
		super(id, tabs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Using this constructor the following defaults take effect:
	 * <ul>
	 * <litabParameterName = "tab"</li> <lidefaultTabIndex = 0</li
	 * </ul>
	 * 
	 * @param id
	 *            component id
	 * @param tabs
	 *            list of ITab objects used to represent tabs
	 * @param pageParameters
	 *            Container for parameters to a requested page. A parameter for
	 *            the selected tab will be inserted.
	 */
	public BookmarkableTabbedPanel(String id, List<ITab> tabs, PageParameters pageParameters) {
		super(id, tabs);
		this.pageParameters = pageParameters;

		if (pageParameters.containsKey(tabParameterName)) {
			String tab = pageParameters.getString(tabParameterName);
			setSelectedTab(Integer.parseInt(tab));
		} else
			setSelectedTab(defaultTabIndex);
	}

	
	
	/**
	 * @param id
	 *            component id
	 * @param tabs
	 *            list of ITab objects used to represent tabs
	 * @param defaultTabIndex
	 *            Set the tab to by displayed by default. The url for this tab
	 *            will not contain any tab specific information. If you want to
	 *            display the first tab by default, you can use the constructor
	 *            without this parameter.
	 * @param pageParameters
	 *            Container for parameters to a requested page. A parameter for
	 *            the selected tab will be inserted.
	 */
	public BookmarkableTabbedPanel(String id, List<ITab> tabs, int defaultTabIndex, String tabParameterName, PageParameters pageParameters) {
		this(id, tabs, pageParameters);
		this.defaultTabIndex = defaultTabIndex;
		setSelectedTab(defaultTabIndex);
		this.tabParameterName = tabParameterName;
	}

	@Override
	protected WebMarkupContainer newLink(String linkId, int index) {
		if (index == defaultTabIndex)
			pageParameters.remove(tabParameterName);
		else
			pageParameters.put(tabParameterName, "" + index);

		WebMarkupContainer link = new BookmarkablePageLink(linkId, getPage().getClass(), pageParameters);
		if (index == getSelectedTab())
			link.setEnabled(false);
		return link;
	}

}
