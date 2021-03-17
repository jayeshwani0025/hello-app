package com.seller.quickbuy.QuickBuyApp.message.request;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class OrderExportFilters{
	
	private static final Logger logger = LogManager.getLogger(OrderExportFilters.class);
	
	
	private static final String ORDER_STATUS_FIELD="orderStatus";
	private String orderStatus;
	
	public OrderExportFilters() {
        this.orderStatus = "";
	}


	public static OrderExportFilters getFilter(Map<String, Optional<String>> filterMap) {
		OrderExportFilters orderExport = new  OrderExportFilters();
		for(Entry entry : filterMap.entrySet()) {
			String key = (String)entry.getKey();
			Optional <String> value = (Optional<String>) entry.getValue();
			switch (key) {
			case ORDER_STATUS_FIELD:
				
				if(value.isPresent()) {
					orderExport.setOrderStatus(value.get().trim());
				}
				else {
					logger.warn("Invalid value type for %s, ignoring the value" , ORDER_STATUS_FIELD);
				}
			
				break;
			default :
				throw new RuntimeException("Invalid field name for order filter");
			}
			
		}
		
		return orderExport;
	}
	
}
