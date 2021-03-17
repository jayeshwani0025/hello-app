package com.seller.quickbuy.QuickBuyApp.service;

import java.io.FileNotFoundException;

import com.seller.quickbuy.QuickBuyApp.message.request.OrderExportFilters;
import com.seller.quickbuy.QuickBuyApp.message.request.ProductExportFilters;
import com.seller.quickbuy.QuickBuyApp.message.request.ProductMasterFilters;

import net.sf.jasperreports.engine.JRException;

public interface ExportService {
	
	String getProductExport(String format, Integer id, ProductExportFilters productExport) throws FileNotFoundException, JRException;
	String getProductMasterExport(String format, Integer id, ProductMasterFilters productMasterExport) throws FileNotFoundException, JRException;
	String getOrderExport(String format, Integer id, OrderExportFilters orderExport) throws FileNotFoundException, JRException;

	}

