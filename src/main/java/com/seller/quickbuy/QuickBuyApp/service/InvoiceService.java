package com.seller.quickbuy.QuickBuyApp.service;

import java.io.FileNotFoundException;

import com.seller.quickbuy.QuickBuyApp.entity.User;

import net.sf.jasperreports.engine.JRException;

public interface InvoiceService {

	void getInvoice(Long orderId, User user) throws JRException, FileNotFoundException;

}
