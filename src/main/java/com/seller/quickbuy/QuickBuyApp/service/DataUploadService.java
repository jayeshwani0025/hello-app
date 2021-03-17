package com.seller.quickbuy.QuickBuyApp.service;

import org.springframework.data.jpa.repository.query.Procedure;

public interface DataUploadService {

	//Product Master
	@Procedure(name = "QUICKBUY_PKG.CREATE_PRODUCT",outputParameterName = "P_PRD_COUNT")
	int callUploadProductMaster();

	//Seller Master
	@Procedure(name = "QUICKBUY_PKG.CREATE_SELLER",outputParameterName = "P_PRD_COUNT")
	int callUploadSellerMaster();

	//Seller Product Master
	@Procedure(name = "QUICKBUY_PKG.CREATE_SELLER_PRODUCT",outputParameterName = "P_PRD_COUNT")
	int callUploadSellerProductMaster();

	

}
