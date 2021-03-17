package com.seller.quickbuy.QuickBuyApp.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seller.quickbuy.QuickBuyApp.entity.ProductCategory;
import com.seller.quickbuy.QuickBuyApp.entity.SellerMaster;
import com.seller.quickbuy.QuickBuyApp.entity.SellerProductMaster;
import com.seller.quickbuy.QuickBuyApp.message.response.ResponseMessage;
import com.seller.quickbuy.QuickBuyApp.repository.SellerMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.SellerProductMasterRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/seller/product")
public class SellerProductMasterController {

	@Autowired
	private SellerProductMasterRepository sellerProductMasterRepository;
	
	@Autowired
	private SellerMasterRepository sellerMasterRepository;
	
	@PostMapping("/new/{sellerId}")
	public ResponseEntity<?> create(@PathVariable("sellerId") Long sellerId,@RequestBody SellerProductMaster sellerProductMaster)	{
		Optional<SellerMaster> sellerData = sellerMasterRepository.findBySellerId(sellerId);
		System.out.println(sellerData);
//		Optional<SellerProductMaster> sellerProductData = sellerProductMasterRepository.findBySellerId(sellerId);
		
		 if (sellerData.isPresent()) {
			 SellerProductMaster _sellerProduct = new SellerProductMaster();
			 _sellerProduct.setSellerId(sellerId);
			 _sellerProduct.setProductId(sellerProductMaster.getProductId());
		 
				return new ResponseEntity<>(sellerProductMasterRepository.save(_sellerProduct), HttpStatus.OK);
		} else {
		  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
}
