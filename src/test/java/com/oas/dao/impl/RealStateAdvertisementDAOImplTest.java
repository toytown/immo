package com.oas.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.oas.model.Advertisement;
import com.oas.model.Images;
import com.oas.model.CustomerContactDetails;
import com.oas.model.Customer;
import com.oas.test.utils.OASTestCase;
import com.oas.web.search.SearchRequest;

public class RealStateAdvertisementDAOImplTest extends OASTestCase {

	@Autowired
	@Qualifier("advertisementDAO")
	private AdvertisementDAOImpl advertisementDAO;

	@Autowired
	@Qualifier("customerDAO")
	private CustomerDAOImpl userDAO;

	@Autowired
	@Qualifier("commonDAO")
	private CommonDAOImpl commonDAO;

	@Test
	@Transactional
	@Rollback(false)
	public void testSave() throws IOException {
		Advertisement appartment = new Advertisement();
		appartment.setTitleDescription("2 room spacious, central, peaceful");
		appartment.setTotalRooms(2.0d);
		appartment.setAreaCode("10337");
		appartment.setBuiltYear(1940);
		appartment.setBedRooms(Short.valueOf("2"));
		appartment.setSize(55.50);
		appartment.setCategoryType(commonDAO.getTypes().get(0));
		appartment.setHeatingType(commonDAO.getHeatingTypes().get(0));
		appartment.setCost(400.50d);
		Customer customer = userDAO.findAll().get(0);
		appartment.setUser(customer);

		appartment.setTitleImage("test_image_1.jpg");

		CustomerContactDetails customerContactDetails = new CustomerContactDetails();
		customerContactDetails.setStreet("Bernauerstr");
		customerContactDetails.setHouseNumber("4");
		customerContactDetails.setEmail("ptuladhar@gmx.net");

		advertisementDAO.save(appartment);
	}

	@Test
	@Transactional
	@Rollback(false)
	public void testSave2() {
		Advertisement appartment = new Advertisement();
		appartment.setTitleDescription("3 room with balcony");
		appartment.setTotalRooms(3.0d);
		appartment.setAreaCode("81669");
		appartment.setBuiltYear(1990);
		appartment.setBedRooms(Short.valueOf("2"));
		appartment.setSize(65.50);
		appartment.setCost(850.0d);
		appartment.setBalconyAvailable(true);
		Customer customer = userDAO.findAll().get(0);
		appartment.setUser(customer);
		appartment.setHeatingType(commonDAO.getHeatingTypes().get(0));
		CustomerContactDetails customerContactDetails = new CustomerContactDetails();
		customerContactDetails.setStreet("Schlesierstr");
		customerContactDetails.setHouseNumber("4");
		customerContactDetails.setEmail("ptuladhar@gmx.net");

		advertisementDAO.save(appartment);
	}

	@Test
	@Transactional
	public void testFindAllAppartmentAdvertisement() {
		List<Advertisement> adverts = advertisementDAO
				.findAllAppartments("81669", "München");
		Assert.assertTrue(adverts.size() > 0);
	}

	@Test
	@Transactional
	public void testFindCities() {
		List<String> postalcodes = advertisementDAO.findCities("Berlin");
		Assert.assertTrue(!postalcodes.isEmpty());
	}
	
	@Test
	@Transactional
	public void testFindImages() {
		

		Customer customer = new Customer();
		customer.setUserName("prasanna223");
		customer.setPassword("pp");
		customer.setFirstName("p");
		customer.setLastName("t");
		customer.setInsertTs(new Date());
		CustomerContactDetails customerContactDetails = new CustomerContactDetails();
		customerContactDetails.setCity("Berlin");
		customerContactDetails.setStreet("Schlesierstr");
		customerContactDetails.setHouseNumber("4");
		customerContactDetails.setEmail("ptuladhar@gmx.net");
		customer.getContactDetails().add(customerContactDetails);
		customerContactDetails.setUser(customer);
		
		userDAO.save(customer);
		
		// creates advertisement for above user
		String imageDir = UUID.randomUUID().toString();
		
		Advertisement appartment = new Advertisement();
		appartment.setTitleDescription("1 room advertisement");
		appartment.setTotalRooms(1.0d);
		appartment.setCity("Kaiserlautern");
		appartment.setAreaCode("24534");
		appartment.setStreet("Unter den Linden");
		appartment.setHouseNo("22");			
		appartment.setBuiltYear(1960);
		appartment.setBedRooms(Short.valueOf("2"));
		appartment.setSize(30d);
		appartment.setFloor(2d);
		appartment.setTotalFloors(2d);			
		appartment.setHeatingType(commonDAO.getHeatingTypes().get(0));
		appartment.setCost(350d);
		appartment.setHeatingCostIncluded(false);
		appartment.setUser(customer);
		appartment.setImageDir(imageDir);
		appartment.setCategoryType(commonDAO.getTypes().get(0));
		appartment.setLastRenovatedYear("1965");
		appartment.setDepositPeriod(3.5d);
		appartment.setProvisionFree(true);
		appartment.setProvisionCondition("3.5%");
		appartment.setGarageAvailable(false);
		appartment.setGarageCost(50d);
		appartment.setBalconyAvailable(true);
		appartment.setCellarAvailable(true);
		appartment.setEnergyPassAvailable(true);
		appartment.setKitchenAvailable(true);
		appartment.setFurnished(true);
		appartment.setLiftAvailable(false);
		appartment.setGardenAvailable(false);
		appartment.setToiletWithBathRoom(true);
		appartment.setInsertDate(new Timestamp(System.currentTimeMillis()));
		appartment.setInsertedBy(customer.getUserName());

		File imageFile = new File("src/test/resources/test_image_1" + ".jpg");
		byte[] b = new byte[(int) imageFile.length()];

		String dirLoc = this.getAppConfig().getImageStore();

		
		String imageLocationDir = dirLoc + File.separatorChar + imageDir;
		File imageFileDir = new File(imageLocationDir);
		String imageOutputLocation = null;

		if (!imageFileDir.exists()) {
			try {
				imageFileDir.mkdirs();
				
				String titleImageFileName = imageFile.getName();
				imageOutputLocation = imageFileDir + File.separator + titleImageFileName;
				FileInputStream input = new FileInputStream(imageFile);
				input.read(b);
				FileOutputStream output = new FileOutputStream(imageOutputLocation);
				output.write(b);
				output.close();
			} catch (IOException e) {
				Assert.fail(e.getMessage());
			}

		}
		appartment.setTitleImage(imageFile.getName());
		
		Images images = new Images();
		images.setImageName(imageFile.getName());
		appartment.getImages().add(images);
		images.setAdvertisement(appartment);
		advertisementDAO.save(appartment);	
		

		SearchRequest req = new SearchRequest();
		req.setCity("Kaiserlautern");
		Advertisement appt = advertisementDAO.findRealStateBySearchCriteria(req).get(0);
		
		
		Images imageSaved = appt.getImages().get(0);
		Assert.assertNotNull(imageSaved);
		appt.getImages().remove(imageSaved);
		advertisementDAO.update(appt);
		advertisementDAO.deleteImage(imageSaved);
		
		Advertisement appt1 = advertisementDAO.findRealStateAdvertById(appt.getId());
		List<Images> imageList = appt1.getImages();
		Assert.assertTrue(imageList.size() == 0);
		
		Images imageDel = advertisementDAO.findImageById(imageSaved.getId());
		Assert.assertNull(imageDel);
	}	
}
