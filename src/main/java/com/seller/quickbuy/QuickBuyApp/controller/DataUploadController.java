package com.seller.quickbuy.QuickBuyApp.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.seller.quickbuy.QuickBuyApp.message.response.ResponseMessage;
import com.seller.quickbuy.QuickBuyApp.service.DataUploadService;
import com.seller.quickbuy.QuickBuyApp.service.ProductMasterService;
import com.seller.quickbuy.QuickBuyApp.service.SellerMasterService;
import com.seller.quickbuy.QuickBuyApp.service.SellerProductMasterService;
import com.seller.quickbuy.QuickBuyApp.utils.CSVHelper;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class DataUploadController {

	@Autowired
	private ProductMasterService productMasterService;

	@Autowired
	private SellerMasterService sellerMasterService;

	@Autowired
	private DataUploadService dataUploadService;

	@Autowired
	private SellerProductMasterService sellerProductMasterService;

	private static final Logger logger = LogManager.getLogger(DataUploadController.class);

	@PostMapping("/upload/{id}")
	public ResponseEntity<?> uploadFile(@Valid @RequestParam("file") MultipartFile file,
			@PathVariable(value = "id") Integer id) {
		String message = "";

		logger.info("In DataUploadController Controller......START");

		if (CSVHelper.hasCSVFormat(file) && id == 1) {
			try {
				productMasterService.save(file, id);

				message = "Uploaded the file successfully.";

				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Could not upload the file.";
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			}
		}

		if (CSVHelper.hasCSVFormat(file) && id == 2) {
			try {
				sellerMasterService.save(file, id);

				message = "Uploaded the file successfully.";

				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Could not upload the file.";
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			}
		}

		if (CSVHelper.hasCSVFormat(file) && id == 3) {
			try {
				sellerProductMasterService.save(file, id);

				message = "Uploaded the file successfully.";

				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Could not upload the file.";
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			}
		}

		message = "Please upload a csv file!";
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	}

	@GetMapping(value = "/uploadFile/{id}")
	public ResponseEntity<Integer> callUploadProcedure(@PathVariable(value = "id") Integer id) {
		// int result = productMasterService.callUploadProcedure(id);
		int result = 0;
		if (id == 1) {
			result = dataUploadService.callUploadProductMaster();
		}

		if (id == 2) {
			result = dataUploadService.callUploadSellerMaster();
		}

		if (id == 3) {
			result = dataUploadService.callUploadSellerProductMaster();
		}

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}
