package com.seller.quickbuy.QuickBuyApp.response;

import lombok.Data;

@Data
public class RelatedProducts {

	private Long productId;
	private Long categoryId;
	private String productName;
	private String productIcon;
	private String productCategory;
	private String productDescription;
}
