package com.seller.quickbuy.QuickBuyApp.serviceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.seller.quickbuy.QuickBuyApp.config.CloudConnectionConfig;
import com.seller.quickbuy.QuickBuyApp.entity.User;
import com.seller.quickbuy.QuickBuyApp.service.EmailService;
import com.seller.quickbuy.QuickBuyApp.service.InvoiceService;
import com.seller.quickbuy.QuickBuyApp.utils.QuickBuyUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	private static Connection conn;
	private static final Logger logger = LogManager.getLogger(InvoiceServiceImpl.class);
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	CloudConnectionConfig config;
//	static
//	{
//		conn = ConnectionUtil.getMyConnection();
//	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void getInvoice(Long orderId, User user) throws JRException, FileNotFoundException {
		logger.info("In InvoiceServiceImpl getInvoice()......START");
	
		try {
			conn = config.dataSource().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			@SuppressWarnings("rawtypes")
			HashMap param = new HashMap();
			param.put("Order_Id", orderId);
			String invoiveName = orderId+"_"+user.getPhone();
			String path = QuickBuyUtils.getPropertyValue("file.path.invoice");
			File file = ResourceUtils.getFile(QuickBuyUtils.getPropertyValue("COM.MAIL.INVOICE"));
			JasperReport jasperReport = null;
			try {
				jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,param,conn);
			JasperExportManager.exportReportToPdfFile(jasperPrint, path+ invoiveName+".pdf");
			
			//Send Email
			String file1 = path+invoiveName+".pdf";
			emailService.sendEmail(user.getFirstName(), user.getEmail(), file1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		logger.info("In InvoiceServiceImpl getInvoice().......END");
	}

}
