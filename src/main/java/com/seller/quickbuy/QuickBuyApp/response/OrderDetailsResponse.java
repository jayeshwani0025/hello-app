package com.seller.quickbuy.QuickBuyApp.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OrderDetailsResponse {

	private String productIcon;
	private String productName;
	private String productDescription;
	private Long productPrice;
	private Long itemQuantity;
	private LocalDate expectedShipDate; 
}
