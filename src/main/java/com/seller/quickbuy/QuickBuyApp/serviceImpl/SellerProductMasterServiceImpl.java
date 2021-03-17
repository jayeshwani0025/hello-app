package com.seller.quickbuy.QuickBuyApp.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seller.quickbuy.QuickBuyApp.entity.SellerProductMaster;
import com.seller.quickbuy.QuickBuyApp.repository.SellerProductMasterRepository;
import com.seller.quickbuy.QuickBuyApp.service.SellerProductMasterService;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;


import com.seller.quickbuy.QuickBuyApp.entity.SellerProductMasterStage;
import com.seller.quickbuy.QuickBuyApp.repository.SellerProductMasterStageRepository;

import com.seller.quickbuy.QuickBuyApp.utils.CSVHelper;

/**
 * 
 * @author jyoti.bhosale
 *
 */
@Service
public class SellerProductMasterServiceImpl implements SellerProductMasterService {

	@Autowired
	SellerProductMasterRepository sellerProductMasterRepository;
	@Autowired
	private SellerProductMasterStageRepository sellerProductMasterStageRepository;
	
	@Override
	public Optional<SellerProductMaster> findBySellerIdAndProductIdAndLocationId(Long sellerId, Long productId,Long locationId) {
		
		return sellerProductMasterRepository.findBySellerIdAndProductIdAndLocationId(sellerId, productId,locationId);
	}

	
	public List<SellerProductMasterStage>  save(@Valid MultipartFile file,Integer id){
		   List<SellerProductMasterStage> sellerProducts = (List<SellerProductMasterStage>)(List<?>)CSVHelper.csvImport(file, SellerProductMasterStage.class,3);
		   sellerProductMasterStageRepository.saveAll(sellerProducts);
		   return sellerProducts;
		  }

}