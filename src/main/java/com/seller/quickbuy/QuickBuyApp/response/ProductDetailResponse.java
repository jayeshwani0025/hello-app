package com.seller.quickbuy.QuickBuyApp.response;

import java.util.List;

import com.seller.quickbuy.QuickBuyApp.entity.ProductMaster;
import com.seller.quickbuy.QuickBuyApp.entity.SellerProductInventory;
import com.seller.quickbuy.QuickBuyApp.entity.SellerProductMaster;

import lombok.Data;

@Data
public class ProductDetailResponse {
	private Long productId;
	private Long locationId;
	private Long sellerId;
	private Long sellerProductId;
	private Long sellerInventoryId;
	private Long categoryId;
	private String productName;
	private String productIcon;
	private String productCategory;
	private String productDescription;
	private Long productPrice;
	private String productStatus;
	private Long productQuantity;
	private List<SellerProductMaster> sellerProductMaster;
	private List<SellerProductInventory> sellerProductInventory;
	private Long count;
	public ProductDetailResponse() {
		
	}
	public ProductDetailResponse(ProductMaster productInfo, SellerProductInventory sellerProductInventory , Long quantity) {
		this.productId = productInfo.getProductId();
		this.productName = productInfo.getProductName();
		this.productIcon = productInfo.getProductIcon();
		this.productDescription = productInfo.getProductDescription();
		this.productQuantity = sellerProductInventory.getQuantity();
		this.sellerInventoryId = sellerProductInventory.getSellerInventoryId();
		this.locationId = sellerProductInventory.getLocationId();
		this.categoryId = productInfo.getCategoryId();
		this.count = quantity;
	}
}

//this.productId = productInfo.getProductId();
//this.productName = productInfo.getProductName();
//this.productDescription = productInfo.getProductDescription();
//this.productIcon = productInfo.getProductIcon();
//this.categoryType = productInfo.getCategoryType();
//this.productPrice = productInfo.getProductPrice();
//this.productStock = productInfo.getProductStock();
//this.count = quantity;
