package com.seller.quickbuy.QuickBuyApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seller.quickbuy.QuickBuyApp.entity.PoLine;



/**
 * 
 * @author jyoti.bhosale
 *
 */
@Repository
public interface PoLineRepository extends JpaRepository<PoLine, Long>{

	/**
	 * 
	 * @param itemName to be search
	 * @return all Po of itemName
	 */
	List<PoLine> findByItemName(String itemName);
	
	/**
	 * 
	 * @param poHeaderId to be search
	 * @param itemName to be search
	 * @return Po of poHeaderId and itemName
	 */
	PoLine findByPoHeaderIdAndItemName(Long poHeaderId,String itemName);
	
	/**
	 * 
	 * @param poHeaderId to be search
	 * @return all Po's of poHeaderId
	 */
	List<PoLine> findBypoHeaderId(Long poHeaderId);
	
	/**
	 * 
	 * @param poHeaderId to be search
	 * @return all PoLines of poHeaderId
	 */
//	List<PoLine> findByPoHeaderId(Long poHeaderId);
	
}

