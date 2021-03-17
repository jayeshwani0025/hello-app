package com.seller.quickbuy.QuickBuyApp.response;

import lombok.Data;

@Data
public class ProductMasterResponse {

	private Long productId;
	private Long locationId;
	private Long categoryId;
	private String productName;
	private String productIcon;
	private String productCategory;
	private String productDescription;
	private String productStatus;
	private Long productPrice;
	private Long productQuantity;
	private String productLocation;
	private Long sellerInvId;
//	List<SellerProductMaster> sellerProductMaster;
//	List<SellerProductInventory> sellerProductInventory;
}
