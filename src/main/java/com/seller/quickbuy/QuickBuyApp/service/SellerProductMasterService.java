package com.seller.quickbuy.QuickBuyApp.service;


import java.util.Optional;

import com.seller.quickbuy.QuickBuyApp.entity.SellerProductMaster;
import java.util.List;

import javax.validation.Valid;


import org.springframework.web.multipart.MultipartFile;


import com.seller.quickbuy.QuickBuyApp.entity.SellerProductMasterStage;


/**
 * 
 * @author jyoti.bhosale
 *
 */
public interface SellerProductMasterService {

	Optional<SellerProductMaster> findBySellerIdAndProductIdAndLocationId(Long sellerId, Long productId,Long locationId);

	List<SellerProductMasterStage>  save(@Valid MultipartFile file,Integer id);


}
