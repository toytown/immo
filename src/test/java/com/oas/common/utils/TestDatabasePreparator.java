package com.oas.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.oas.dao.impl.AdvertisementDAOImpl;
import com.oas.dao.impl.CommonDAOImpl;
import com.oas.dao.impl.CustomerDAOImpl;
import com.oas.model.Advertisement;
import com.oas.model.Customer;
import com.oas.model.CustomerContactDetails;
import com.oas.model.Images;
import com.oas.test.utils.OASTestCase;

public class TestDatabasePreparator extends OASTestCase {

	@Autowired
	@Qualifier("advertisementDAO")
	private AdvertisementDAOImpl appartmentAdvertisementDAO;

	@Autowired
	@Qualifier("jdbcTemplate")
	private SimpleJdbcTemplate jdbcTemplate;

	@Autowired
	private CustomerDAOImpl userDAO;

	@Autowired
	private CommonDAOImpl commonDAO;

	private enum CATEGORY_TYPES_APPARTMENT {
		Dachgeschoss(1), Loft(2), Maisonette(3), Penthouse(4), Terrassenwohnung(5), Souterrain(6), Erdgeschoss(7), Etagenwohnung(8), Hochparterre(9), Sonstige(10);
		
		 int val = -1;
		 private CATEGORY_TYPES_APPARTMENT(int i) {
			 val = i;
		 };
		 
		 public int getAppartmentValue() {
			 return val;
		 }		
	};


	private enum CATEGORY_TYPES_HOUSING {
		Bauernhaus(1), Doppelhaushälfte(2), Einfamilienhaus(3), Reihenhaus(4), Mehrfamilienhaus(5), Villa(6), Bungalow(7), Sonstige(8);
		
		 int val = -1;
		 private CATEGORY_TYPES_HOUSING(int i) {
			 val = i;
		 };
		 
		 public int getHousingTypeValue() {
			 return val;
		 }			
	};

	private enum CATEGORY_TYPES_LAND {
		Erbpacht, Kauf
	};
	
	private enum HEATING_TYPES {
		ZentralHeizung(1), EtageHeizung(2), Sonstige(3);
		
		 int val = -1;
		 private HEATING_TYPES(int i) {
			 val = i;
		 };
		 
		 public int getHeatingValue() {
			 return val;
		 }		
	};

	public enum CATEGORY {
		 Appartment(1), House(2), Garage(3), Land(4), Others(5);
		 int val = -1;
		 private CATEGORY(int i) {
			 val = i;
		 };
		 
		 public int getCategoryValue() {
			 return val;
		 }
	};
	
	public void cleanDatabase() {

		jdbcTemplate.update("delete from oas.OAS_APPARTMENT_IMAGES", new HashMap());
		
		jdbcTemplate.update("delete from oas.OAS_APPARTMENT_ADVERTISEMENT", new HashMap());

		jdbcTemplate.update("delete from oas.OAS_CONTACT_DETAILS", new HashMap());

		jdbcTemplate.update("delete from oas.OAS_USER", new HashMap());

		jdbcTemplate.update("delete from oas.OAS_APPARTMENT_TYPES", new HashMap());

		jdbcTemplate.update("delete from oas.OAS_HEATING_TYPES", new HashMap());

		jdbcTemplate.update("delete from oas.OAS_APPARTMENT_CATEGORY", new HashMap());
		
		setupHeatingTypes();
		setupCategories();
		setupCategoryTypes();
		
	}


	@Test
	@Transactional
	@Rollback(false)
	public void testPrepareDatabase() {

		// cleans database
		cleanDatabase();

		// creates data : 1 zimmer, Berlin, from 30qm, from 350 eur
		for (int i = 1; i <= 5; i++) {
			CustomerContactDetails customerContactDetails = new CustomerContactDetails();
			customerContactDetails.setStreet("Prenzlauerberg");
			customerContactDetails.setHouseNumber("" + i);
			customerContactDetails.setEmail("ptuladhar@gmx.net");
			customerContactDetails.setCity("Munich");

			Customer customer = new Customer();
			customer.setUserName("test-user-" + i);
			customer.setPassword("password-" + i);
			customer.setInsertTs(new Date());

			customer.getContactDetails().add(customerContactDetails);
			customerContactDetails.setUser(customer);
			userDAO.save(customer);

			// creates advertisement for above user
			String imageDir = UUID.randomUUID().toString();
			
			Advertisement appartment = new Advertisement();
			appartment.setTitleDescription("1 room advertisement");
			appartment.setTotalRooms(1.0d);
			appartment.setCity("Berlin");
			appartment.setAreaCode("10337");
			appartment.setStreet("Unter den Linden");
			appartment.setHouseNo("22");			
			appartment.setBuiltYear(1960);
			appartment.setBedRooms(Short.valueOf("2"));
			appartment.setSize(30d + ((i -1) * 2.0d));
			appartment.setFloor(i *1d);
			appartment.setTotalFloors(i *1d + 1);			
			appartment.setHeatingType(commonDAO.getHeatingTypes().get(0));
			appartment.setCost(350d + ((i -1) * 5.0d));
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
			appartment.setBalconyAvailable(i%2 ==0);
			appartment.setCellarAvailable(true);
			appartment.setEnergyPassAvailable(true);
			appartment.setKitchenAvailable(true);
			appartment.setFurnished(true);
			appartment.setLiftAvailable(false);
			appartment.setGardenAvailable(false);
			appartment.setToiletWithBathRoom(true);
			appartment.setBarrierFree(true);
			appartment.setSeniorAppartment(false);
			appartment.setInsertDate(new Timestamp(System.currentTimeMillis()));
			appartment.setInsertedBy(customer.getUserName());
			
			
			appartment.setCategory(commonDAO.getCategories().get(0));
			
			File imageFile = new File("src/test/resources/test_image_" + i + ".jpg");
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
			Images aptImages = new Images();
			aptImages.setImageName(imageFile.getName());
			aptImages.setAdvertisement(appartment);
			appartment.getImages().add(aptImages);
			// advertisement.setTitleImage(b);
			appartmentAdvertisementDAO.save(appartment);

		}

		
		
		// creates data : 2zimmer, Munich, from 60qm, from 700 eur
		for (int i = 1; i <= 5; i++) {
			CustomerContactDetails customerContactDetails = new CustomerContactDetails();
			customerContactDetails.setStreet("Schlesierstr");
			customerContactDetails.setHouseNumber("" + i);
			customerContactDetails.setEmail("ptuladhar@gmx.net");
			customerContactDetails.setCity("Munich");
			
			
			Customer customer = new Customer();
			customer.setUserName("test-user-" + i);
			customer.setPassword("password-" + i);
			customer.setInsertTs(new Date());

			customer.getContactDetails().add(customerContactDetails);
			customerContactDetails.setUser(customer);
			userDAO.save(customer);

			// creates advertisement for above user
			String imageDir = UUID.randomUUID().toString();
			Advertisement appartment = new Advertisement();
			appartment.setTitleDescription("2 room advertisement, peaceful");
			appartment.setTotalRooms(2.0d);
			appartment.setCity("Muenchen");
			appartment.setAreaCode("81669");
			appartment.setStreet("Pertisaustr");
			appartment.setHouseNo("24");
			appartment.setBuiltYear(1970);
			appartment.setBedRooms(Short.valueOf("2"));
			appartment.setSize(60d + ((i -1) * 2.0d));
			appartment.setFloor(i *1d);
			appartment.setTotalFloors(i *1d + 1);
			appartment.setHeatingType(commonDAO.getHeatingTypes().get(1));
			appartment.setCost(700d + ((i -1) * 5.0d));
			appartment.setHeatingCostIncluded(true);
			appartment.setUser(customer);
			appartment.setCategoryType(commonDAO.getTypes().get(1));
			appartment.setImageDir(imageDir);
			appartment.setLastRenovatedYear("1980");
			appartment.setDepositPeriod(3d);
			appartment.setProvisionFree(true);
			appartment.setProvisionCondition("3.5%");
			appartment.setGarageAvailable(true);
			appartment.setGarageCost(50d);
			appartment.setBalconyAvailable(i%2 ==0);
			appartment.setCellarAvailable(true);
			appartment.setEnergyPassAvailable(true);
			appartment.setKitchenAvailable(true);
			appartment.setFurnished(false);
			appartment.setLiftAvailable(true);
			appartment.setGardenAvailable(false);			
			appartment.setToiletWithBathRoom(true);
			appartment.setBarrierFree(true);
			appartment.setSeniorAppartment(true);			
			appartment.setInsertDate(new Timestamp(System.currentTimeMillis()));
			appartment.setInsertedBy(customer.getUserName());
			appartment.setCategory(commonDAO.getCategories().get(1));
			
			File imageFile = new File("src/test/resources/test_image_" + i + ".jpg");
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
			Images aptImages = new Images();
			aptImages.setImageName(imageFile.getName());
			aptImages.setAdvertisement(appartment);			
			// advertisement.setTitleImage(b);
			appartmentAdvertisementDAO.save(appartment);

		}
		
		
		// creates data : 3zimmer, Hamburg, 90qm, 700 eur
		for (int i = 5; i <=10; i++) {
			CustomerContactDetails customerContactDetails = new CustomerContactDetails();
			customerContactDetails.setStreet("Hamburgstr");
			customerContactDetails.setHouseNumber("" + i);
			customerContactDetails.setEmail("ptuladhar@gmx.net");
			customerContactDetails.setCity("Hamburg");

			Customer customer = new Customer();
			customer.setUserName("test-user-" + i);
			customer.setPassword("password-" + i);
			customer.setInsertTs(new Date());

			customer.getContactDetails().add(customerContactDetails);
			customerContactDetails.setUser(customer);
			userDAO.save(customer);

			// creates advertisement for above user
			String imageDir = UUID.randomUUID().toString();
			Advertisement appartment = new Advertisement();
			appartment.setTitleDescription("3 room advertisement, peaceful");
			appartment.setTotalRooms(3.0d);
			appartment.setCity("Hamburg");
			appartment.setAreaCode("20253 ");
			appartment.setStreet("Bahnhofstr");
			appartment.setHouseNo("3");
			appartment.setBuiltYear(1998);
			appartment.setBedRooms(Short.valueOf("3"));
			appartment.setBathRooms(Short.valueOf("2"));
			appartment.setSize(101d + ((i -1) * 2.5d));
			appartment.setFloor(i *1d);
			appartment.setTotalFloors(i *1d + 1);
			appartment.setHeatingType(commonDAO.getHeatingTypes().get(2));
			appartment.setCost(1000d + ((i -1) * 6.0d));
			appartment.setHeatingCostIncluded(true);
			appartment.setUser(customer);
			appartment.setCategoryType(commonDAO.getTypes().get(2));
			appartment.setImageDir(imageDir);
			appartment.setLastRenovatedYear("1995");
			appartment.setDepositPeriod(3d);
			appartment.setProvisionFree(true);
			appartment.setProvisionCondition("3.5%");
			appartment.setGarageAvailable(i%2 ==0);
			appartment.setGarageCost(60d);
			appartment.setBalconyAvailable(i%2 ==0);
			appartment.setCellarAvailable(true);
			appartment.setEnergyPassAvailable(true);
			appartment.setKitchenAvailable(false);
			appartment.setFurnished(true);
			appartment.setLiftAvailable(true);
			appartment.setGardenAvailable(false);			
			appartment.setToiletWithBathRoom(false);
			appartment.setBarrierFree(false);
			appartment.setSeniorAppartment(true);			
			appartment.setInsertDate(new Timestamp(System.currentTimeMillis()));
			appartment.setInsertedBy(customer.getUserName());
			appartment.setCategory(commonDAO.getCategories().get(2));
			
			File imageFile = new File("src/test/resources/test_image_" + i + ".jpg");
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
			Images aptImages = new Images();
			aptImages.setImageName(imageFile.getName());
			aptImages.setAdvertisement(appartment);			
			// advertisement.setTitleImage(b);
			appartmentAdvertisementDAO.save(appartment);

		}		
	}

	@Transactional
	@Rollback(false)
	public void setupHeatingTypes() {
		// garage categoryTypes
		HEATING_TYPES[] types = HEATING_TYPES.values();
		for (int i = 0; i < types.length; i++) {
			jdbcTemplate.update("insert into oas.OAS_HEATING_TYPES(id, description) values (" + types[i].getHeatingValue() + ", '" + types[i] + "')");
		}
	}

	@Transactional
	@Rollback(false)
	public void setupCategories() {
		// garage categoryTypes
		CATEGORY[] types = CATEGORY.values();
		for (int i = 0; i < types.length; i++) {
			jdbcTemplate.update("insert into oas.OAS_APPARTMENT_CATEGORY(id, description) values (" + types[i].getCategoryValue() + ", '" + types[i] + "')");
		}
	}
	
	@Transactional
	@Rollback(false)
	public void setupCategoryTypes() {
		// advertisement categoryTypes
		CATEGORY_TYPES_APPARTMENT[] types = CATEGORY_TYPES_APPARTMENT.values();
		for (int i = 0; i < types.length; i++) {
			jdbcTemplate.update("insert into oas.OAS_APPARTMENT_TYPES(id, description, category_id) values (" + types[i].getAppartmentValue() + ", '" + types[i] + "', " +  CATEGORY.Appartment.getCategoryValue() + ")");
		}
	}

}
