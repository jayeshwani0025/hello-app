package com.seller.quickbuy.QuickBuyApp.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.seller.quickbuy.QuickBuyApp.message.request.ProductMasterFilters;

public class ProductMasterQuery {
	private static final Logger logger = LogManager.getLogger(ProductQuery.class);

	private static final String SINGLE_QUOTE = "'";
	private static final String DOUBLE_QUOTE = "''";

	public static String getMasterQuery(ProductMasterFilters productMasterExport) {

		String text = "SELECT PROD.PRODUCT_NAME," + "PROD.PRODUCT_DESCRIPTION," + "CAT.CATEGORY_NAME,"
				+ " CAT.CATEGORY_TYPE," + " PROD.CREATION_DATE" + " FROM PRODUCT_MASTER PROD ,"
				+ " PRODUCT_CATEGORY CAT " + " WHERE PROD.CATEGORY_ID = CAT.CATEGORY_ID";

		String categoryName = productMasterExport.getProductCategoryName();
		String categoryType = productMasterExport.getProductCategoryType();
		String productName = productMasterExport.getProductName();
		// Full Export
		if (categoryName.isEmpty() && categoryType.isEmpty() && productName.isEmpty()) {
			text = text;
			logger.info("Query in full export: " + text);
		}
		// categoryNameFilter Export
		if (!(categoryName.isEmpty())) {
			categoryName = categoryName.replaceAll(SINGLE_QUOTE, DOUBLE_QUOTE);
			text = text + " and trim(upper(CAT.CATEGORY_NAME)) = upper('%" + categoryName + "%')";
			logger.info("Query in Category export: " + text);

		}

		// categoryTypeFilter export
		if (!(categoryType.isEmpty())) {

			categoryType = categoryType.replaceAll(SINGLE_QUOTE, DOUBLE_QUOTE);
			text = text + " and CAT.CATEGORY_TYPE = '" + categoryType + "'";
			logger.info("Query in CategoryType export: " + text);

		}

		// seller Export
		if (!(productName.isEmpty())) {

			productName = productName.replaceAll(SINGLE_QUOTE, DOUBLE_QUOTE);
			text = text + " and PROD.PRODUCT_NAME = '" + productName + "'";
			logger.info("Query in seller export: " + text);

		}

		logger.info("Query : " + text);
		return text;

	}

}
