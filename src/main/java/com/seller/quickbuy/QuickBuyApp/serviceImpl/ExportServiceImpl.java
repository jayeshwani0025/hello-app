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
import com.seller.quickbuy.QuickBuyApp.message.request.OrderExportFilters;
import com.seller.quickbuy.QuickBuyApp.message.request.ProductExportFilters;
import com.seller.quickbuy.QuickBuyApp.message.request.ProductMasterFilters;
import com.seller.quickbuy.QuickBuyApp.service.ExportService;
import com.seller.quickbuy.QuickBuyApp.utils.ExportReport;
import com.seller.quickbuy.QuickBuyApp.utils.OrderQuery;
import com.seller.quickbuy.QuickBuyApp.utils.ProductMasterQuery;
import com.seller.quickbuy.QuickBuyApp.utils.ProductQuery;
import com.seller.quickbuy.QuickBuyApp.utils.QuickBuyUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
public class ExportServiceImpl implements ExportService {

	private static Connection conn;

	private static final Logger logger = LogManager.getLogger(ExportServiceImpl.class);
	private String fileName;
	@Autowired
	CloudConnectionConfig config;
	
	@Override
	public String getProductExport(String format, Integer id, ProductExportFilters productExport)
			throws FileNotFoundException, JRException {
		logger.info("In ExportServiceImpl getProductExport()......START");
		try {
			logger.info("In config.dataSource().getConnection()......START");
			conn = config.dataSource().getConnection();
			logger.info("In config.dataSource().getConnection()......END" + conn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			
			File	file = ResourceUtils.getFile(QuickBuyUtils.getPropertyValue("COM.PRODUCT.EXPORT"));
			JasperDesign jd = JRXmlLoader.load(file);
//			JasperDesign jd = JRXmlLoader.load(new File(QuickBuyUtils.getPropertyValue("COM.PRODUCT.EXPORT")));
			logger.info("In getProductExport file name : ---------> "+jd.getName());
			JRDesignQuery query = new JRDesignQuery();
			query.setText(ProductQuery.getQuery(productExport));
			logger.info("In ProductQuery.getQuery(productExport) "+productExport+" ......START");
			jd.setQuery(query);
			JasperReport jasperReport = JasperCompileManager.compileReport(jd);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,new HashMap<>(),conn);
			fileName = ExportReport.exportReport(jasperPrint,format,QuickBuyUtils.getPropertyValue("file.path.product"),id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("In ExportServiceImpl getProductExport()......Excepton" + e.toString());
//			e.printStackTrace();
		}
		logger.info("In ExportServiceImpl getProductExport()......END");
		return fileName;
}
	@Override
	public String getProductMasterExport(String format, Integer id, ProductMasterFilters productMasterExport)
			throws FileNotFoundException, JRException {
		logger.info("In ExportServiceImpl getProductMasterExport()......START");
		try {
			logger.info("In config.dataSource().getConnection()......START");
			conn = config.dataSource().getConnection();
			logger.info("In config.dataSource().getConnection()......END" + conn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//			conn = config.dataSource().getConnection();
			try {
				File	file = ResourceUtils.getFile(QuickBuyUtils.getPropertyValue("COM.PRODUCT.MASTER.EXPORT"));
				JasperDesign jd = JRXmlLoader.load(file);
//				JasperDesign jd = JRXmlLoader.load(new File(QuickBuyUtils.getPropertyValue("COM.PRODUCT.MASTER.EXPORT")));
				logger.info("In getProductMasterExport file name : ---------> "+jd.getName());
//				JasperDesign jd = JRXmlLoader.load(file);
				JRDesignQuery query = new JRDesignQuery();
				query.setText(ProductMasterQuery.getMasterQuery(productMasterExport));
				logger.info("In ProductQuery.getQuery(productMasterExport) "+productMasterExport+" ......START");
				jd.setQuery(query);
				JasperReport jasperReport = JasperCompileManager.compileReport(jd);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,new HashMap<>(),conn);
				logger.info("In ExportServiceImpl getProductMasterExport()......END" + jasperPrint.toString());
				fileName = ExportReport.exportReport(jasperPrint,format,QuickBuyUtils.getPropertyValue("file.path.productMaster"),id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("In ExportServiceImpl getProductMasterExport()......Excepton" + e.toString());
			}
		
			logger.info("In ExportServiceImpl getProductMasterExport()......END");
		return fileName;
}
	@Override
	public String getOrderExport(String format, Integer id, OrderExportFilters orderExport)
			throws FileNotFoundException, JRException {
		logger.info("In ExportServiceImpl getOrderExport()......START");
		try {
			logger.info("In config.dataSource().getConnection()......START");
			conn = config.dataSource().getConnection();
			logger.info("In config.dataSource().getConnection()......END" + conn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			File	file = ResourceUtils.getFile(QuickBuyUtils.getPropertyValue("COM.ORDER.EXPORT"));
			JasperDesign jd = JRXmlLoader.load(file);
			
//			conn = config.dataSource().getConnection();
//			JasperDesign jd = JRXmlLoader.load(new File(QuickBuyUtils.getPropertyValue("COM.ORDER.EXPORT")));
//			File file = ResourceUtils.getFile(QuickBuyUtils.getPropertyValue("COM.ORDER.EXPORT"));
			logger.info("In getOrderExport ---------> "+jd.getName());
//			System.out.println(file.getName());
//			JasperDesign jd = JRXmlLoader.load(file);
			JRDesignQuery query = new JRDesignQuery();
			query.setText(OrderQuery.getOrderQuery(orderExport));
			logger.info("In OrderQuery.getQuery(productMasterExport) "+orderExport+" ......START");
			jd.setQuery(query);
			JasperReport jasperReport = JasperCompileManager.compileReport(jd);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,new HashMap<>(),conn);
			fileName = ExportReport.exportReport(jasperPrint,format,QuickBuyUtils.getPropertyValue("file.path.order"),id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("In ExportServiceImpl getOrderExport()......Excepton" + e.toString());
		}
		logger.info("In ExportServiceImpl getOrderExport()......END");
		return fileName;
	}

}


