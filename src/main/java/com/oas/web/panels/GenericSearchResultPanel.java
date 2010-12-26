package com.oas.web.panels;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.image.resource.RenderedDynamicImageResource;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.oas.common.utils.ImageUtils;
import com.oas.common.utils.OASAplicationConfiguration;
import com.oas.model.Advertisement;
import com.oas.web.main.OASSession;
import com.oas.web.model.AdvertisementDataProvider;
import com.oas.web.pages.RealStateDetailView;
import com.oas.web.search.FavouriteAdvert;
import com.oas.web.search.SearchRequest;

public class GenericSearchResultPanel extends Panel {

	@SpringBean
	protected OASAplicationConfiguration appConfig;
	
	private static final Logger logger = Logger.getLogger(RealStatePreviewPanel.class);
	
	protected static String IMAGE_STORE_LOCATION ;

	protected WebMarkupContainer favoritesContainer;
	
	public GenericSearchResultPanel(String id, IModel<?> model) {
		super(id, model);
		IMAGE_STORE_LOCATION = appConfig.getImageStore();
	}

	
	protected void searchAndRenderResults(final WebMarkupContainer  searchResultContainer, final SearchRequest searchReq) {
		DataView resultView = getSearchResult(searchReq);
		resultView.setOutputMarkupId(true);
		resultView.setItemsPerPage(5);
		searchResultContainer.addOrReplace(new PagingNavigator("navigator", resultView));
		searchResultContainer.addOrReplace(resultView);
	}

	protected DataView getSearchResult(final SearchRequest searchReq) {
		final DataView<Advertisement> dataView = new DataView<Advertisement>("appartmentAdvertView",
				new AdvertisementDataProvider(searchReq)) {

			@Override
			protected void populateItem(final Item<Advertisement> item) {
				final Advertisement advert = (Advertisement) item.getModelObject();

				Link detailImageLink = new Link("detailImageLink"){
					@Override
					public void onClick() {
						setResponsePage(new RealStateDetailView(advert));
					}
				};
				
				Link detailLink = new Link("detailLink"){
					@Override
					public void onClick() {
						setResponsePage(new RealStateDetailView(advert));
					}
				};
				
				AjaxLink favouritesLink = new AjaxLink("favouritesLink"){

					@Override
					public void onClick(AjaxRequestTarget target) {
						OASSession sess = (OASSession) Session.get();
						FavouriteAdvert favouriteAdvert = new FavouriteAdvert();
						favouriteAdvert.setId(advert.getId());
						favouriteAdvert.setTitle(advert.getTitleDescription());						
						sess.addToFavourite(favouriteAdvert);
						favoritesContainer.addOrReplace(new ListView<FavouriteAdvert>("favouriteList", sess.getFavourites()){

							@Override
							protected void populateItem(ListItem<FavouriteAdvert> item) {
								FavouriteAdvert favAdvert = item.getModelObject();
								Link favouriteLink = new Link("favouritesDetailLink") {

									@Override
									public void onClick() {
										setResponsePage(new RealStateDetailView(advert));
									}
								};
								item.add(favouriteLink);
								favouriteLink.add(new Label("title", favAdvert.getTitle()));
								
							}
							
						});
						favoritesContainer.setOutputMarkupId(true);
						target.addComponent(favoritesContainer);
					}

				};
				
				detailImageLink.add(getTitleImage(advert));
				item.add(favouritesLink);
				item.add(detailImageLink);
				item.add(detailLink);
				item.add(new MultiLineLabel("title", advert.getTitleDescription()));
				item.add(new Label("rooms", String.valueOf(advert.getTotalRooms())));
				item.add(new Label("size", String.valueOf(advert.getSize())));
				item.add(new Label("cost", String.valueOf(advert.getCost())));
				item.add(new Label("location", getLocationString(advert.getCity(), advert.getAreaCode(), advert.getStreet(), advert.getHouseNo())));
			}
		};	
		
		return dataView;
	}
	
	protected NonCachingImage getTitleImage(final Advertisement advert) {
		return new NonCachingImage("title_image", new AbstractReadOnlyModel() {

			@Override
			public Object getObject() {
				return new RenderedDynamicImageResource(50, 50) {

					@Override
					protected boolean render(Graphics2D g2) {

						BufferedImage baseIcon = null;
						BufferedImage baseIconOut = null;
						try {
							String titleImageDir = IMAGE_STORE_LOCATION + File.separator + advert.getImageDir();
							String imageFilePath = titleImageDir + File.separator + advert.getTitleImage();
							File imageFile = new File(imageFilePath);
							if (imageFile.canRead()) {
								long startTime = System.currentTimeMillis();
								baseIcon = ImageIO.read(imageFile);
								long endTime = System.currentTimeMillis();
								logger.debug("Time taken to read image : " + (endTime - startTime) + " milliseconds.");
								baseIconOut = ImageUtils.resize(baseIcon, 50, 50);
								endTime = System.currentTimeMillis();
								logger.debug("Time taken to resize image : " + (endTime - startTime) + " milliseconds.");
							} else {
								throw new RuntimeException("No image file found at " + imageFilePath);
							}

						} catch (IOException e) {
							throw new WicketRuntimeException(e);
						}
						g2.drawImage(baseIconOut, null, null);
						return true;
					}

				};
			}
		});		
	}
	
	protected String getLocationString(String city, String areaCode, String street, String houseNo) {
		StringBuilder builder = new StringBuilder();
		
		if (city != null) {
			builder.append(city);
		} else {
			builder.append("");
		}
		
		if (areaCode != null && areaCode.length() > 1) {
			builder.append(", ").append(areaCode);
		}
		
		if (street != null && street.length() > 1) {
			builder.append(", ").append(street);
		}
		
		if (houseNo != null && houseNo.length() > 0) {
			builder.append(", ").append(houseNo);
		}
		return builder.toString();
	}
	
	public class EmptyDataProvider extends SortableDataProvider<Advertisement> {

		public Iterator<Advertisement> iterator(int first, int count) {
			return new ArrayList().iterator();
		}

		public IModel<Advertisement> model(Advertisement appartmentAdvert) {

			return null;
		}

		public int size() {
			return 0;
		}

	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}	
}
