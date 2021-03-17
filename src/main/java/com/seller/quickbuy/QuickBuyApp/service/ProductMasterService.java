package com.seller.quickbuy.QuickBuyApp.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.seller.quickbuy.QuickBuyApp.entity.ProductMaster;
import com.seller.quickbuy.QuickBuyApp.entity.ProductMasterStage;
import com.seller.quickbuy.QuickBuyApp.message.response.ResponseMessage;
import com.seller.quickbuy.QuickBuyApp.repository.ProductMasterRepository;
import com.seller.quickbuy.QuickBuyApp.response.ProductMasterResponse;

/**
 * 
 * @author jyoti.bhosale
 *
 */

public interface ProductMasterService {
	
	
	
	ProductMaster findOne(String productName);

	ProductMaster save(ProductMaster productMaster);

	void delete(Long productId);
	
	List<ProductMaster> findAllProduct();

//	ProductMaster findByProductId(Long productId);
	Optional findByProductId(Long productId);
	ProductMaster update(@Valid ProductMaster product);
//	List<ProductMaster> findAllInCategory(Long categoryType)
	
	/**
	 * 
	 * @param categoryId to be Find
	 * @return all products by categoryId
	 */
	List<ProductMaster> findByCategoryId(Long categoryId);

	Page<ProductMaster> findAllInCategory(Long categoryType, Pageable request);
	
    List<ProductMasterStage> save(@Valid MultipartFile file,Integer id);
	
	

	List<ProductMaster> findByProductName(String itemName);

	

}
