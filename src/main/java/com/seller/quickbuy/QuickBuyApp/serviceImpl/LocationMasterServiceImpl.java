package com.seller.quickbuy.QuickBuyApp.serviceImpl;

import java.util.List;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seller.quickbuy.QuickBuyApp.entity.LocationMaster;
import com.seller.quickbuy.QuickBuyApp.exception.LocationException;
import com.seller.quickbuy.QuickBuyApp.repository.LocationMasterRepository;
import com.seller.quickbuy.QuickBuyApp.service.LocationMasterService;


/**
 * 
 * @author jyoti.bhosale
 *
 */
@Service
public class LocationMasterServiceImpl implements LocationMasterService {

	private static final Logger logger = LogManager.getLogger(LocationMasterServiceImpl.class);
	
	@Autowired
	private LocationMasterRepository locationMasterRepository;

	@Override
	public LocationMaster findOne(String locationName) {
		
		logger.info("In LocationMasterService findOne()--> START");
		logger.info("In LocationMasterService findOne()--> END");
		return locationMasterRepository.findByLocationName(locationName);
	}

	@Override
	public LocationMaster save(@Valid LocationMaster locationMaster) throws Exception {
		logger.info("In LocationMasterService save()--> START");
		logger.info("Location Obj In Service "+locationMaster);
		/*
		 * if(locationMaster != null) { throw new
		 * LocationException("Feilds can't be null"); }
		 */
		
		boolean hasNullOrEmptyFeild = (Stream.of(locationMaster.getLocationName(),locationMaster.getLocationAddress(),locationMaster.getCity())
										.anyMatch(feild-> feild==null || feild.isEmpty()));
		if(hasNullOrEmptyFeild) {
			throw new LocationException("Location Name , Address And City can't be null or empty. Must have a proper value. ");
		}
		else {
				LocationMaster locationExist = locationMasterRepository.findByLocationName(locationMaster.getLocationName());
				if(locationExist != null)
			{
					throw new LocationException("Location Name is already in use!");
			}
		}
		logger.info("In LocationMasterService save()--> END");
		return locationMasterRepository.save(locationMaster);
	}

	@Override
	public List<LocationMaster> getAll() {
		logger.info("In LocationMasterService getAll()--> START");
		List<LocationMaster> list = locationMasterRepository.findAll();
		logger.info("In LocationMasterService getAll()--> END");
		return list;
	}

	@Override
	public LocationMaster update(@Valid LocationMaster location) {
		logger.info("In LocationMasterService update()--> START");
		LocationMaster master = locationMasterRepository.save(location);
		logger.info("In LocationMasterService update()--> END" + master.toString());
		return master;
	}

	@Override
	public void delete(Long locationId) {
		logger.info("In LocationMasterService delete()--> START");
		locationMasterRepository.deleteById(locationId);
		logger.info("In LocationMasterService delete()--> END");
	}

	@Override
	public LocationMaster findByLocationId(Long locationId) {
		 return locationMasterRepository.findByLocationId(locationId);
	}
}
