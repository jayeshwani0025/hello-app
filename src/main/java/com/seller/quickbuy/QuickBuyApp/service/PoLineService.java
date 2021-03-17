package com.seller.quickbuy.QuickBuyApp.service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.seller.quickbuy.QuickBuyApp.entity.PoLine;



/**
 * 
 * @author jyoti.bhosale
 *
 */
public interface PoLineService {

	/**
	 * 
	 * @return all PoLines
	 */
	List<PoLine> findAll();

	/**
	 * 
	 * @param poLineId to be search
	 * @return PoLine of poLineId
	 */
	Optional<PoLine> findById(Long poLineId);

	/**
	 * 
	 * @param poLineId to be delete
	 */
	void delete(Long poLineId);

	/**
	 * 
	 * @param poLine to be check checkHasNullOrEmpty
	 * @return true if poLine fields are null or empty
	 */
	boolean checkHasNullOrEmptyFeild(PoLine poLine);

	/**
	 * 
	 * @param itemName to be search 
	 * @return PoLine of itemName
	 */
	List<PoLine> findByItemName(String itemName);

	/**
	 * 
	 * @param poLine to be save
	 */
	void save(PoLine poLine,Principal principal);

	/**
	 * 
	 * @param isPoLineExist to be update
	 * @param poLine updated
	 */
	void update(Optional<PoLine> isPoLineExist, @Valid PoLine poLine,Principal principal);

	/**
	 * 
	 * @param poHeaderId to be search
	 * @param itemName to be search
	 * @return PoLine of poHeaderId And itemName
	 */
	PoLine findByPoHeaderIdAndItemName(Long poHeaderId, String itemName);

	/**
	 * 
	 * @param poHeaderId to be search
	 * @return all PoLines of poHeaderId
	 */
	List<PoLine> findBypoHeaderId(Long poHeaderId);

}
