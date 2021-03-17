package com.seller.quickbuy.QuickBuyApp.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.seller.quickbuy.QuickBuyApp.message.request.OrderExportFilters;

public class OrderQuery {
	private static final Logger logger = LogManager.getLogger(OrderQuery.class);

	public static String getOrderQuery(OrderExportFilters orderExport) {
		String text = "SELECT USR.USER_NAME," + " HDR.ORDER_NUMBER,HDR.ORDER_STATUS,"
				+ " USR.CITY,USR.STATE,usr.ZIP_CODE,USR.EMAIL,USR.PHONE,"
				+ " HDR.PAYMENT_AMOUNT,HDR.SHIP_TO_ADDRESS,HDR.ITEM_QUANTITY,HDR.ORDERED_DATE"
				+ " FROM ORDER_HEADERS_ALL HDR," + " ALL_USERS usr" + " WHERE HDR.SHIP_TO_CONTACT_ID = usr.USER_ID";

		String orderStatus = orderExport.getOrderStatus();

		// Full Export
		if (orderStatus.isEmpty()) {
			text = text;
			logger.info("Query in full export: " + text);
		}

		// categoryNameFilter Export
		if (!(orderStatus.isEmpty())) {

			text = text + " and HDR.ORDER_STATUS = '" + orderStatus + "'";
			logger.info("Query in Order export: " + text);

		}

		logger.info("Query : " + text);
		return text;
	}
}
