package com.seller.quickbuy.QuickBuyApp.response;

import lombok.Data;

@Data
public class SellerProductResponse {

	private String sellerName;
	private Long productPrice;
	private Long productQuantity;
	private Long sellerInvId;
	private String locationName;
	private Long locationId;
}
