package com.seller.quickbuy.QuickBuyApp.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.seller.quickbuy.QuickBuyApp.message.request.ProductExportFilters;

public class ProductQuery {
	private static final Logger logger = LogManager.getLogger(ProductQuery.class);

	private static final String SINGLE_QUOTE = "'";
	private static final String DOUBLE_QUOTE = "''";

	public static String getQuery(ProductExportFilters productExport) {
		String text = "SELECT SM.SELLER_NAME," + " prod.PRODUCT_NAME," + " cat.category_name," + " seller.QUANTITY,"
				+ " loc.location_name," + " prod.creation_date " + " FROM product_master prod ,"
				+ " product_category cat," + " seller_product_inventory seller," + " LOCATION_MASTER LOC,"
				+ " SELLER_PRODUCT_MASTER SPM," + " SELLER_MASTER SM " + " WHERE PROD.CATEGORY_ID = CAT.CATEGORY_ID"
				+ " AND PROD.PRODUCT_ID = SPM.PRODUCT_ID" + " AND SPM.SELLER_PRODUCT_ID = SELLER.SELLER_PRODUCT_ID"
				+ " AND LOC.LOCATION_ID = SELLER.LOCATION_ID" + " AND SM.SELLER_ID = SPM.SELLER_ID";

		int isFlag = 1;
		String category = productExport.getProductCategoryName();
		String location = productExport.getLocationName();
		String seller = productExport.getSellerName();
		String product = productExport.getProductName();
		Long minValue = productExport.getMinValue();
		Long maxValue = productExport.getMaxValue();

		// Full Export
		if (category.isEmpty() && location.isEmpty() && seller.isEmpty() && product.isEmpty() && minValue == null
				&& maxValue == null && isFlag == 1) {
			text = text;
			logger.info("Query in full export: " + text);

		}

		// category Export
		if (!(category.isEmpty())) {

			if (isFlag == 1) {
				isFlag = 0;
				category = category.replaceAll(SINGLE_QUOTE, DOUBLE_QUOTE);
				text = text
						+ " and prod.CATEGORY_ID in (select cat.CATEGORY_ID from product_category  cat where cat.CATEGORY_NAME ='"
						+ category + "') ";
				logger.info("Query in Category export: " + text);
			}
		}

		// Location export

		if (!(location.isEmpty())) {

			if (isFlag == 1) {
				isFlag = 0;
				location = location.replaceAll(SINGLE_QUOTE, DOUBLE_QUOTE);
				text = text
						+ " and LOC.LOCATION_ID IN (SELECT LOC.LOCATION_ID FROM LOCATION_MASTER LOC WHERE LOC.LOCATION_NAME ='"
						+ location + "') ";
				logger.info("Query in Locatrion export: " + text);

			}
		}

		// seller Export
		if (!(seller.isEmpty())) {

			if (isFlag == 1) {
				isFlag = 0;
				text = text + " and SM.SELLER_ID IN (SELECT SM.SELLER_ID FROM SELLER_MASTER SM WHERE SM.SELLER_NAME ='"
						+ seller + "') ";
				logger.info("Query in seller export: " + text);

			}
		}

		// product Export
		if (!(product.isEmpty())) {

			if (isFlag == 1) {
				isFlag = 0;
				product = product.replaceAll(SINGLE_QUOTE, DOUBLE_QUOTE);
				text = text + " AND PROD.PRODUCT_NAME ='" + product + "'";
				logger.info("Query in product export: " + text);

			}
		}

		// Greater than Export
		if (minValue != null && maxValue == null) {
			if (isFlag == 1) {
				isFlag = 0;
				text = text
						+ " and SELLER.SELLER_PRODUCT_ID in (select SELLER.SELLER_PRODUCT_ID from SELLER_PRODUCT_INVENTORY  SELLER where SELLER.QUANTITY >="
						+ minValue + ")";
				logger.info("Query in Category export: " + text);

			}

		}

		// less than Export
		if (maxValue != null && minValue == null) {
			if (isFlag == 1) {
				isFlag = 0;
				text = text
						+ " and SELLER.SELLER_PRODUCT_ID in (select SELLER.SELLER_PRODUCT_ID from SELLER_PRODUCT_INVENTORY  SELLER where SELLER.QUANTITY <="
						+ maxValue + ")";

			}

		}

		// Between Export
		if (minValue != null && maxValue != null) {
			if (isFlag == 1) {
				isFlag = 0;
				text = text
						+ " and SELLER.SELLER_PRODUCT_ID in (select SELLER.SELLER_PRODUCT_ID from SELLER_PRODUCT_INVENTORY  SELLER where SELLER.QUANTITY  BETWEEN "
						+ minValue + " AND " + maxValue + ")";

			}
		}

		logger.info("isFlag :" + isFlag);
		logger.info("Query : " + text);
		return text;
	}

	private static Long stringToLong(String string) {
		return string != null && !string.isEmpty() ? Long.parseLong(string) : null;
	}
}
