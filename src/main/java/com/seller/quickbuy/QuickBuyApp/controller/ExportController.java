package com.seller.quickbuy.QuickBuyApp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seller.quickbuy.QuickBuyApp.message.request.OrderExportFilters;
import com.seller.quickbuy.QuickBuyApp.message.request.ProductExportFilters;
import com.seller.quickbuy.QuickBuyApp.message.request.ProductMasterFilters;
import com.seller.quickbuy.QuickBuyApp.service.ExportService;
import com.seller.quickbuy.QuickBuyApp.utils.MediaTypeUtils;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class ExportController {

	private static final Logger logger = LogManager.getLogger(ExportController.class);

	@Autowired
	ExportService exportService;

	@Autowired
	private ServletContext servletContext;
	@PostMapping(value = "/export/{format}/{id}")
	public ResponseEntity<InputStreamResource> getExport(@RequestBody HashMap<String, Optional<String>> exportFieldMap,
			@PathVariable(value = "format") String format, @PathVariable(value = "id") Integer id)
			throws FileNotFoundException, JRException {
		logger.info("In ExportController Controller getExport()......START");

		String fileName = null;
		
		try {
			if (id == 1) {
				logger.info("In Product Master getExport() with : "+ format + " format......START");
				logger.info(exportFieldMap);
				ProductMasterFilters productMasterFilters = ProductMasterFilters.getFilter(exportFieldMap);
				fileName = exportService.getProductMasterExport(format, id, productMasterFilters);
				logger.info("In Product Master getExport() with : "+ format + " format......END");
			}else if (id == 2) {
				logger.info("In Product Inventory getExport() with : "+ format + " format ......START");
				ProductExportFilters productExportFilters = ProductExportFilters.getFilter(exportFieldMap);
				fileName = exportService.getProductExport(format, id, productExportFilters);
				logger.info("In Product Inventory getExport() with : "+ format + " format......END");
			}else if (id == 3) {
				logger.info("In Order getExport() : "+ format + "  format ......START");
				OrderExportFilters orderExportFilters = OrderExportFilters.getFilter(exportFieldMap);
				fileName = exportService.getOrderExport(format, id, orderExportFilters);
				logger.info("In Order getExport() with : "+ format + " format......END");
			}
			
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
		MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
		File file = new File(fileName);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		logger.info("In getExport() with "+ format + " format......END");
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
				.contentType(mediaType)
				.contentLength(file.length())
				.body(resource);

	}
}

/*
 * @PostMapping("/export/productMaster/{format}/{id}") public String
 * getProductMasterExport(@RequestBody ProductExportFilters productExport,
 * 
 * @PathVariable(value="format") String format,
 * 
 * @PathVariable(value ="id") Integer id) throws FileNotFoundException,
 * JRException { return exportService.getProductExport(format,id,productExport);
 * }
 * 
 * 
 * @PostMapping("/export/productInventory/{format}/{id}") public String
 * getProductInventoryExport(@RequestBody ProductMasterFilters
 * productMasterExport,
 * 
 * @PathVariable(value="format") String format,
 * 
 * @PathVariable (value ="id") Integer id) throws
 * FileNotFoundException,JRException { return
 * exportService.getProductMasterExport(format,id,productMasterExport); }
 * 
 * @PostMapping("/export/orderMaster/{format}/{id}") public String
 * getOrderExport(@RequestBody OrderExportFilters orderExport,
 * 
 * @PathVariable(value="format") String format,
 * 
 * @PathVariable (value="id") Integer id) throws FileNotFoundException,
 * JRException { return exportService.getOrderExport(format,id,orderExport); }
 */
