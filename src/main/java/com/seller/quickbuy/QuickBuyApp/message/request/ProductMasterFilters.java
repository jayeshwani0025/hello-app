package com.seller.quickbuy.QuickBuyApp.message.request;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProductMasterFilters {
	
	private static final Logger logger = LogManager.getLogger(ProductMasterFilters.class);

	
	private static final String PRODUCT_NAME_FIELD="productName";
	private static final String PRODUCT_CATEGORY_NAME_FIELD="productCategoryName";
	private static final String PRODUCT_CATEGORY_TYPE_FIELD="productCategoryType";
	
	 private String productName;
	    private String productCategoryName;
	    private String productCategoryType;
	    
	    public ProductMasterFilters() {
	        this.productName = "";
	        this.productCategoryName = "";
	        this.productCategoryType = "";
	    }

	
public static ProductMasterFilters getFilter(Map<String, Optional<String>> filterMap) {
			
			ProductMasterFilters productMasterFilters = new ProductMasterFilters();
			for(Entry entry : filterMap.entrySet()) {
				
				String key = (String)entry.getKey();
				Optional <String> value = (Optional<String>) entry.getValue();
			
				switch (key) {
				
				case PRODUCT_NAME_FIELD:
					if(value.isPresent()) {
						
						productMasterFilters.setProductName(value.get().trim());
						
					}
					else {
						logger.warn("Invalid value type for %s, ignoring the value" , PRODUCT_NAME_FIELD);
					}
					break;
				case PRODUCT_CATEGORY_NAME_FIELD:
					if(value.isPresent()) {
						productMasterFilters.setProductCategoryName(value.get().trim());
					}
					else {
						logger.warn("Invalid value type for %s, ignoring the value" , PRODUCT_CATEGORY_NAME_FIELD);
					}
					break;
				case PRODUCT_CATEGORY_TYPE_FIELD:
					if(value.isPresent()) {
						productMasterFilters.setProductCategoryType(value.get().trim());
					}
					else {
						logger.warn("Invalid value type for %s, ignoring the value" , PRODUCT_CATEGORY_TYPE_FIELD);
					}
					break;
				default :
					throw new RuntimeException("Invalid field name for Product filter");
				}
				
			}
			return productMasterFilters;
		
		}
	}
