package com.seller.quickbuy.QuickBuyApp.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.seller.quickbuy.QuickBuyApp.entity.ProductMaster;
import com.seller.quickbuy.QuickBuyApp.entity.SellerMaster;
import com.seller.quickbuy.QuickBuyApp.entity.SellerMasterStage;



public interface SellerMasterService {
	
	
;
	public SellerMaster createSeller(SellerMaster seller);
    public SellerMaster updateSeller(SellerMaster seller);
    public SellerMaster getSeller(Long seller_id);
    public void deleteSeller(Long seller_id);
    public List<SellerMaster> getAllSeller();
    public Optional<SellerMaster> findBySellerId(Long sellerId);
    List<SellerMasterStage>  save(@Valid MultipartFile file,Integer id);
}
