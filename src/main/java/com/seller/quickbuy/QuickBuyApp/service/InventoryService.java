package com.seller.quickbuy.QuickBuyApp.service;

import java.util.List;

import com.seller.quickbuy.QuickBuyApp.message.response.InventoryResponse;


public interface InventoryService {


	public String getInventoryStatus(String locationCode, String productId, int quantity);
	
	List<InventoryResponse> inventoryResponse();
	
}
