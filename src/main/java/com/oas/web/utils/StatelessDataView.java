package com.oas.web.utils;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;

public abstract class StatelessDataView<T> extends DataView<T> {
	private static final long serialVersionUID = 1L;
	public PageParameters getPp() {
		return pp;
	}

	public void setPp(PageParameters pp) {
		this.pp = pp;
	}

	private PageParameters pp;

	@Override
	protected boolean getStatelessHint() {
		return true;
	}

	protected int getPageNumber(final String param) {
		String numResult = param;
		if (numResult.contains(".wicket-")) {
			numResult = numResult.substring(0, numResult.indexOf(".wicket-"));
		}
		return Integer.valueOf(numResult);
	}

	public StatelessDataView(final String id, final IDataProvider dataProvider, final PageParameters pp) {
		super(id, dataProvider);
		this.pp = pp;
		if (pp.getString(id) != null) {
			int pageNum = getPageNumber(pp.getString(id));
			if (pageNum != -1 && pageNum >= 0 && pageNum <= getPageCount()) {
				setCurrentPage(getPageNumber(pp.getString(id)));
			}
		}
	}

	public StatelessDataView(final String id, final IDataProvider dataProvider, final int itemsPerPage, final PageParameters pp) {
		super(id, dataProvider, itemsPerPage);
		this.pp = pp;
		if (pp.getString(id) != null) {
			int pageNum = getPageNumber(pp.getString(id));
			if (pageNum != -1 && pageNum >= 0 && pageNum <= getPageCount()) {
				setCurrentPage(getPageNumber(pp.getString(id)));
			}
		}
	}

}