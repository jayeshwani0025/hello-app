package com.seller.quickbuy.QuickBuyApp.service;

import java.util.List;

import javax.validation.Valid;

import com.seller.quickbuy.QuickBuyApp.entity.LocationMaster;


/**
 * 
 * @author jyoti.bhosale
 *
 */
public interface LocationMasterService {

	/**
	 * 
	 * @param locationName to be search into Master
	 * @return Location if loactionName is exist
	 */
	LocationMaster findOne(String locationName);

	/**
	 * 
	 * @param locationMaster to be added
	 * @return LocationMaster
	 * @throws Exception 
	 */
	LocationMaster save(@Valid LocationMaster locationMaster) throws Exception;

	/**
	 * 
	 * @return all locations
	 */
	List<LocationMaster> getAll();

	/**
	 * 
	 * @param location to be update
	 * @return
	 */
	LocationMaster update(@Valid LocationMaster location);

	/**
	 * 
	 * @param locationId to be deleted
	 */
	void delete(Long locationId);

	LocationMaster findByLocationId(Long locationId);

}
