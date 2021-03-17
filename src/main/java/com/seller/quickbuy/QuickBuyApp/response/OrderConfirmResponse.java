package com.seller.quickbuy.QuickBuyApp.response;

import java.math.BigDecimal;
import java.util.List;

import com.seller.quickbuy.QuickBuyApp.entity.OrderHeader;
import com.seller.quickbuy.QuickBuyApp.entity.OrderLines;
import com.seller.quickbuy.QuickBuyApp.entity.ProductMaster;

import lombok.Data;

@Data
public class OrderConfirmResponse {
	
	private Long orderNumber;
	private BigDecimal total;
	private OrderHeader orderHeader;
//	private List<OrderLines> orderLines;
	private List<ProductMaster> productMaster;
	
	List<OrderDetailsResponse> orderDetails;
	
	

}
