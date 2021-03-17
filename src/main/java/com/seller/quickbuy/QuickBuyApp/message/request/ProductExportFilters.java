package com.seller.quickbuy.QuickBuyApp.message.request;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.seller.quickbuy.QuickBuyApp.utils.ProductQuery;

import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProductExportFilters {
	
	private static final Logger logger = LogManager.getLogger(ProductExportFilters.class);
	
	private static final String PRODUCT_NAME_FIELD="productName";
	private static final String PRODUCT_CATEGORY_NAME_FIELD="productCategoryName";
	private static final String SELLER_NAME_FIELD="sellerName";
	private static final String LOCATION_NAME_FIELD="locationName";
	private static final String MIN_VALUE_FIELD="minValue";
	private static final String MAX_VALUE_FIELD="maxValue";
	
	
	    private String productName;
	    private String productCategoryName;
	    private String sellerName;
	    private String locationName;
	    private Long minValue;
	    private Long maxValue;
	    
	    public ProductExportFilters() {
	        this.productName = "";
	        this.productCategoryName = "";
	        this.sellerName = "";
	        this.locationName = "";
	        this.minValue =Long.MIN_VALUE;
	        this.maxValue =Long.MAX_VALUE;
	        
	    }

	    public static ProductExportFilters getFilter(Map<String, Optional<String>> filterMap) {
			ProductExportFilters productExportFilters = new ProductExportFilters();
			for(Entry entry : filterMap.entrySet()) {
				String key = (String)entry.getKey();
				Optional <String> value = (Optional<String>) entry.getValue();
				switch (key) {
				case PRODUCT_NAME_FIELD:
					if(value.isPresent()) {
						productExportFilters.setProductName(value.get().trim());
					}
					else {
						logger.warn("Invalid value type, ignoring the value" , PRODUCT_NAME_FIELD);
					}
					
					break;
				case PRODUCT_CATEGORY_NAME_FIELD:
					if(value.isPresent()) {
						productExportFilters.setProductCategoryName(value.get().trim());
					}
					else {
						logger.warn("Invalid value type, ignoring the value" , PRODUCT_CATEGORY_NAME_FIELD);
					}
					break;
				case SELLER_NAME_FIELD:
					if(value.isPresent()) {
						productExportFilters.setSellerName(value.get().trim());
					}
					else {
						logger.warn("Invalid value type, ignoring the value" , SELLER_NAME_FIELD);
					}
					break;
				case LOCATION_NAME_FIELD:
					if(value.isPresent()) {
						productExportFilters.setLocationName(value.get().trim());
					}
					else {
						logger.warn("Invalid value type for, ignoring the value" , LOCATION_NAME_FIELD);
					}
					break;
				case MIN_VALUE_FIELD:
					
					if(value.isPresent() && value.get().trim().length()>0) {
						try {
						
						     productExportFilters.setMinValue(Long.parseLong(value.get().trim()));
					   }catch(NumberFormatException e) {
						   
						   logger.warn("Invalid value, ignoring the value" , MIN_VALUE_FIELD); 
					   }
					}
					else {
						logger.warn("Invalid value type , ignoring the value" , MIN_VALUE_FIELD);
					}
					
					break;
				case MAX_VALUE_FIELD:
					if(value.isPresent() && value.get().trim().length()>0) {
						try {
							
						     productExportFilters.setMinValue(Long.parseLong(value.get().trim()));
					   }catch(NumberFormatException e) {
						   
						   logger.warn("Invalid value , ignoring the value" , MAX_VALUE_FIELD); 
					   }
					}
					else {
						logger.warn("Invalid value type for, ignoring the value" , MAX_VALUE_FIELD);
					}
					
					break;
				default :
					throw new RuntimeException("Invalid field name for Product filter");
				}
				
			}
			return productExportFilters;
		}
	}
