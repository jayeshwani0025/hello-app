package com.seller.quickbuy.QuickBuyApp.service;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.seller.quickbuy.QuickBuyApp.entity.PoHeader;



/**
 * 
 * @author jyoti.bhosale
 *
 */
public interface PoHeaderService {

	/**
	 * 
	 * @param po to be generate
	 * @param principal 
	 */
	PoHeader save(PoHeader po, Principal principal);

	/**
	 * 
	 * @param poHeaderId to be delete
	 */
	void delete(Long poHeaderId);

	/**
	 * 
	 * @param isPoExist to update
	 * @param po is updated
	 */
	void update(Optional<PoHeader> isPoExist,PoHeader po,Principal principal);

	/**
	 * 
	 * @return all Po's
	 */
	List<PoHeader> findAll();

	/**
	 * 
	 * @param poHeaderId to be search
	 * @return Optional PoHeader
	 */
	Optional<PoHeader> findById(Long poHeaderId);

	/**
	 * 
	 * @param po to be check has null value or it is empty
	 * @return true if fields are empty or null
	 */
	boolean checkHasNullOrEmptyFeild(PoHeader po);

	/**
	 * 
	 * @param approverId to be search
	 * @return all Po of approverId
	 */
	List<PoHeader> findByApproverId(Long approverId);

	

	/**
	 * 
	 * @param poHeader to be save
	 * @param file to be upload
	 * @return
	 */
	PoHeader save(PoHeader poHeader, MultipartFile file,Principal principal)throws IOException;

	/**
	 * 
	 * @param isPoExist po to be update
	 * @param poHeader is updated po
	 * @param file to be save
	 * @throws IOException 
	 */
	void update(Optional<PoHeader> isPoExist, PoHeader poHeader, MultipartFile file,Principal principal) throws IOException;

}
