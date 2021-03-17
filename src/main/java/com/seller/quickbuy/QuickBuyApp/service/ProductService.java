package com.seller.quickbuy.QuickBuyApp.service;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;

import com.seller.quickbuy.QuickBuyApp.entity.ProductMaster;

public interface ProductService {

	 ProductMaster findByProductId(Long productId);
//
//	    // All selling products
	    List<ProductMaster> findUpAll();
//	    // All products
	    Page<ProductMaster> findAll(Pageable pageable);
//	    Page<ProductMaster> findByLocationId(Pageable pageable, Integer locationId);
//	    // All products in a category
//	    Page<ProductMaster> findAllInCategory(Integer categoryType, Pageable pageable);
//
////	    // increase stock
//	   void increaseStock(String productId, int amount);
//	//
////	    //decrease stock
//	   void decreaseStock(String productId, int amount);
//
//	   ProductMaster offSale(String productId);
//
//	   ProductMaster onSale(String productId);
//
//	   ProductMaster update(ProductMaster productInfo);
//	   ProductMaster save(ProductMaster productInfo);
//
//	    void delete(String productId);
//
//		ProductMaster findOne(Long productId);
}
