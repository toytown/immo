package com.oas.web.panels;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.image.resource.RenderedDynamicImageResource;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.oas.common.utils.ImageUtils;
import com.oas.common.utils.OASAplicationConfiguration;
import com.oas.model.Advertisement;
import com.oas.model.Images;
import com.oas.services.IAdvertisementSearchService;
import com.oas.web.pages.ImageNavigationContentPage;

public class ImageNavigationPanel extends Panel {

	private int currentIndex = 0;

	@SpringBean
	private OASAplicationConfiguration appConfig;

	@SpringBean
	private IAdvertisementSearchService advertisementService;

//	private static final Logger logger = Logger.getLogger(ImageNavigationPanel.class);

	private boolean isLargerView = false;
	private int size = 150;
	
	public ImageNavigationPanel(String id, final long appartmentId, final String imageDir, boolean largeView) {
		super(id);
		this.isLargerView = largeView;
		if (isLargerView) {
			size = 250;
		}
		
		final WebMarkupContainer imageContainer = new WebMarkupContainer("imageContainer");
		final WebMarkupContainer imageContainerAll = new WebMarkupContainer("imageContainerAll");
		imageContainerAll.setVisible(false);
		
		imageContainer.setOutputMarkupId(true);
		imageContainerAll.setOutputMarkupId(true);
		
		Advertisement appartment = advertisementService.findById(appartmentId);
		final List<Images> images = appartment.getImages();
		
		NonCachingImage titleImage = getImage(appartment.getTitleImage(), appartment.getImageDir(), size);
		//image navigation modal window
		final ModalWindow modalWindow;
		add(modalWindow = new ModalWindow("modalImageNavigation"));
		modalWindow.setPageMapName("modalImageNavigation");
		modalWindow.setCookieName("modalImageNavigation");
		
		if (isLargerView) {
			imageContainerAll.setVisible(true);
			ListView<Images> smallImagesList = new ListView<Images>("imagesList", images){

				@Override
				protected void populateItem(ListItem<Images> item) {
					final Images apptImage = item.getModelObject();
					final NonCachingImage titleImage = getImage(apptImage.getImageName(), imageDir, 50);
					final IndicatingAjaxLink showImageLink;
					item.add(showImageLink = new IndicatingAjaxLink("showImageLink") {

						@Override
						public void onClick(AjaxRequestTarget target) {
							imageContainer.addOrReplace(getImage(apptImage.getImageName(), imageDir, 250));
							target.addComponent(imageContainer);
						}
						
					});
					showImageLink.add(titleImage);
				}
				
			};
			imageContainerAll.add(smallImagesList);
		}
		
		modalWindow.setPageCreator(new ModalWindow.PageCreator() {
			public Page createPage() {
				return new ImageNavigationContentPage(new ImageNavigationPanel("imageNavPanel", appartmentId, imageDir, true));
			}
		});

		modalWindow.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {
			public boolean onCloseButtonClicked(AjaxRequestTarget target) {
				return true;
			}
		});

		
		AjaxLink showModalNavigationLink = new AjaxLink("showModalNavigationImagePanelLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				modalWindow.show(target);
			}
			
			@Override
			public boolean isVisible() {
				return !isLargerView;
			}
		};
		
		
		Images apptTitleImage = null;
		for (Images image : images) {
			if (image.getImageName().equals(appartment.getTitleImage())) {
				apptTitleImage = image;
				break;
			}
		}
		
		imageContainer.add(titleImage);
		add(showModalNavigationLink);
		
		final int size = (images != null ? images.size() : 0);

		currentIndex = images.indexOf(apptTitleImage);
		currentIndex++;

		
		add(new AjaxLink("next") {

			@Override
			public void onClick(AjaxRequestTarget target) {
				if (currentIndex >= size) {
					currentIndex = 0;
				}

				if (currentIndex < 0) {
					currentIndex = 0;
				}
				
				String imageName = images.get(currentIndex).getImageName();
				NonCachingImage titleImage = getImage(imageName, imageDir, size);
				imageContainer.addOrReplace(titleImage);
				target.addComponent(imageContainer);
				currentIndex++;
			}

			@Override
			public boolean isVisible() {
				return size > 0 && !isLargerView;
			}

		});

		add(new AjaxLink("previous") {

			@Override
			public void onClick(AjaxRequestTarget target) {
				if (currentIndex >= size) {
					currentIndex = size -2;
				}

				if (currentIndex < 0) {
					currentIndex = 0;
				}
				String imageName = images.get(currentIndex).getImageName();
				NonCachingImage titleImage = getImage(imageName, imageDir, size);
				imageContainer.addOrReplace(titleImage);
				target.addComponent(imageContainer);
				currentIndex--;
			}

			@Override
			public boolean isVisible() {
				return size > 0 && !isLargerView;
			}

		});
		this.add(imageContainer);
		this.add(imageContainerAll);
		this.setOutputMarkupId(true);
		
		
	}


	private NonCachingImage getImage(final String imageFileName, final String imageDir, final int size) {

		return new NonCachingImage("title_image", new AbstractReadOnlyModel() {

			@Override
			public Object getObject() {
				return new RenderedDynamicImageResource(size, size) {

					@Override
					protected boolean render(Graphics2D g2) {

						BufferedImage baseIcon = null;
						BufferedImage baseIconOut = null;
						try {
							String titleImageDir = appConfig.getImageStore() + File.separator + imageDir;
							String imageFilePath = titleImageDir + File.separator + imageFileName;
							File imageFile = new File(imageFilePath);
							if (imageFile.canRead()) {
								baseIcon = ImageIO.read(imageFile);
								baseIconOut = ImageUtils.resize(baseIcon, size, size);
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
}
