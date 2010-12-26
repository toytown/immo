package com.oas.services.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.util.file.Folder;
import org.springframework.transaction.annotation.Transactional;

import com.oas.common.utils.OASAplicationConfiguration;
import com.oas.dao.IAdvertisementDAO;
import com.oas.model.Advertisement;
import com.oas.model.Images;
import com.oas.services.IAdvertisementSearchService;
import com.oas.web.search.SearchRequest;

public class AdvertisementSearchServiceImpl implements IAdvertisementSearchService {

	private IAdvertisementDAO advertisementDAO;
	private OASAplicationConfiguration appConfiguration;
	private Logger logger = Logger.getLogger(AdvertisementSearchServiceImpl.class);
	
	public OASAplicationConfiguration getAppConfiguration() {
		return appConfiguration;
	}

	public void setAppConfiguration(OASAplicationConfiguration appConfiguration) {
		this.appConfiguration = appConfiguration;
	}

	public IAdvertisementDAO getAdvertisementDAO() {
		return advertisementDAO;
	}

	public void setAdvertisementDAO(IAdvertisementDAO appartmentAdvertDAO) {
		this.advertisementDAO = appartmentAdvertDAO;
	}

	public List<Advertisement> findAdvertisement(SearchRequest searchRequest) {
		return advertisementDAO.findRealStateBySearchCriteria(searchRequest);
	}

	public List<Advertisement> findRealStatetByZip(String zip) {
		return advertisementDAO.findAllAppartments(zip, zip);
	}

	public Advertisement findById(Long appartmentId) {
		return advertisementDAO.findRealStateAdvertById(appartmentId);
	}

	@Transactional
	public void save(Advertisement appartment) {
		advertisementDAO.save(appartment);
	}

	@Transactional
	public Advertisement update(Advertisement appartment) {
		return advertisementDAO.update(appartment);
	}

	@Transactional
	public void deleteImage(Images appartmentImage) {
		advertisementDAO.deleteImage(appartmentImage);
	}

	public Images findRealStateImageById(long id) {
		return advertisementDAO.findImageById(1);
	}

	@Transactional
	public void delete(Advertisement advert) {
		advertisementDAO.delete(advert);
		advert.getImageDir();
		removeImageDir(advert);
		
	}

	private void removeImageDir(Advertisement advert) {

		if (advert.getImageDir() != null) {
			String imageLocation = appConfiguration.getImageStore();
			Folder uploadFolder =  new Folder(imageLocation + File.separatorChar + advert.getImageDir());
			
			if (uploadFolder.exists()) {
				List<File> fileList = Arrays.asList(uploadFolder.listFiles());
				for (File fileToDelete : fileList) {
					boolean deletionOpt = fileToDelete.delete();
					logger.info("deleting of file : " + fileToDelete.getAbsolutePath() + deletionOpt);
				}
				boolean deleted = uploadFolder.delete();
				if (deleted) {
					logger.info("deleting folder : " + uploadFolder.getAbsolutePath());
				} else {
					logger.info("could not delete folder : " + uploadFolder.getAbsolutePath());
				}
				
			} else {
				logger.warn(" folder : " + uploadFolder.getName() + " does not exists.");
			}			
		} else {
			throw new IllegalArgumentException("Image Dir folder is null for advert id =" + advert.getId());
		}
	}

	public List<String> findCities(String searchStr) {
		return advertisementDAO.findCities(searchStr);
	}
}
