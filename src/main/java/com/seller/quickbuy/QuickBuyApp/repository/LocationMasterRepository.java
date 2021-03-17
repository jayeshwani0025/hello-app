package com.seller.quickbuy.QuickBuyApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seller.quickbuy.QuickBuyApp.entity.LocationMaster;

/**
 * 
 * @author jyoti.bhosale
 *
 */
@Repository
public interface LocationMasterRepository extends JpaRepository<LocationMaster, Long>{

	/**
	 * 
	 * @param locationName to be search into the Master
	 * @return LocationMaster if found the locationName
	 */
	LocationMaster findByLocationName(String locationName);

	LocationMaster findByLocationId(Long locationId);

	
}
