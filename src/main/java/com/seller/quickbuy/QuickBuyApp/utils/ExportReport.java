package com.seller.quickbuy.QuickBuyApp.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

public class ExportReport {
	private static final Logger logger = LogManager.getLogger(ExportReport.class);
	public static String exportReport(JasperPrint jasperPrint, String format, String path, Integer id) throws JRException {
		logger.info("In exportReport ......START");
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
		String fileName = null;
		try {
			if(id == 1)	{
				//fileName =QuickBuyUtils.getPropertyValue("file.path.productMaster")+"ProductMasterExport_"+timeStamp+"_";
				fileName ="ProductMasterExport_"+timeStamp+"_";
				
			}else if(id == 2){
				//fileName =QuickBuyUtils.getPropertyValue("file.path.product")+"ProductExport_"+timeStamp+"_";
				fileName ="ProductExport_"+timeStamp+"_";
			}else if(id==3)
			{
				//fileName =QuickBuyUtils.getPropertyValue("file.path.order")+"OrderExport_"+timeStamp+"_";
				fileName ="OrderExport_"+timeStamp+"_";
			}
			fileName = fileName.replace("-", "_");
			fileName = fileName.replace(":", "_");
			fileName = path+fileName;
			JRExporter exporter = null;
			if(format.equalsIgnoreCase("pdf"))
			{
				logger.info("In exportReport pdf......START");
				fileName = fileName+".pdf";
				JasperExportManager.exportReportToPdfFile(jasperPrint,fileName);
				logger.info("In exportReport pdf......END : " + fileName);
			}else if(format.equalsIgnoreCase("html"))
			{
				logger.info("In exportReport html......START");
				fileName = fileName+".html";
				JasperExportManager.exportReportToHtmlFile(jasperPrint,fileName);
				logger.info("In exportReport html......END : "+fileName);
			}else if(format.equalsIgnoreCase("csv"))
			{
				logger.info("In exportReport CSV......START");
				fileName = fileName+".csv";
				exporter = new JRCsvExporter(); 
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint)); 
				exporter.setExporterOutput(new SimpleWriterExporterOutput(new File(fileName)));
				SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
				configuration.setWriteBOM(Boolean.TRUE);
				configuration.setRecordDelimiter("\r\n");
				exporter.setConfiguration(configuration); 
				try {
					exporter.exportReport();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info("In exportReport CSV......END : " + fileName);
				
			}else  if (format.equalsIgnoreCase("xls")) { 
				  logger.info("In exportReport xls......START");
				  fileName = fileName+".xls";
			       exporter = new JRXlsExporter();
			       exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);   
			       exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,  fileName);
			       try {
					exporter.exportReport();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			       logger.info("In exportReport xls......END");
			  }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return fileName;
	}
}


