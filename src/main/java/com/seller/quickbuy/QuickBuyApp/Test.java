package com.seller.quickbuy.QuickBuyApp;

import java.util.HashMap;
import java.util.Map;

public class Test {
	public static void main(String[] args) {
		 String str  = "productId=117&sellerInvId=7322";
		 Map<String, String> map = new HashMap<String, String>();
		 String[] entries = str.split("&");
		 Long productId = null;
		 Long sellerInvId = null;
		 for (String entry : entries) {
			  String[] keyValue = entry.split("=");
			  String key   = keyValue[0];
			  String value = keyValue[1];
			  if(key.equals("productId")) {
				  productId = Long.parseLong(value);
			       System.out.println("Product Id : " + productId);
			   }else if (key.equals("sellerInvId")) {
				   sellerInvId = Long.parseLong(value);
				   System.out.println("Seller Inventory Id : " + sellerInvId);
			   }
		}  
		
	}	   
}
