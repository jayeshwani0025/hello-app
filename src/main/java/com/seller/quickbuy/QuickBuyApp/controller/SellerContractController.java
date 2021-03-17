package com.seller.quickbuy.QuickBuyApp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import static com.seller.quickbuy.QuickBuyApp.utils.QuickBuyConstants.*;
import static com.seller.quickbuy.QuickBuyApp.utils.QuickBuyUtils.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.seller.quickbuy.QuickBuyApp.entity.PoHeader;
import com.seller.quickbuy.QuickBuyApp.entity.SellerContract;
import com.seller.quickbuy.QuickBuyApp.entity.SellerMaster;
import com.seller.quickbuy.QuickBuyApp.exception.SellerClientException;
import com.seller.quickbuy.QuickBuyApp.message.response.ResponseMessage;
import com.seller.quickbuy.QuickBuyApp.service.SellerContractService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders="*")
@RequestMapping("/api/contract")
public class SellerContractController {

	private static final Logger logger = LogManager.getLogger(SellerContractController.class);
	

	@Autowired
    private SellerContractService sellerContractService;

     ObjectMapper objectMapper =new ObjectMapper();
     
   
	 @PostMapping(consumes = MediaType.ALL_VALUE,path = "/createSellerContract/{sellerId}")
	    public ResponseEntity<SellerContract> createSellerContract(@RequestParam(required=true,value="file")  MultipartFile file,	@RequestParam(value="sellerContract")  String sellerContract
	    		,@PathVariable(value = "sellerId") Long sellerId) throws IOException 
	 {
		 System.out.println(sellerContract);
		 System.out.println(file.getName());
		 objectMapper.registerModule(new JavaTimeModule());
		 objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        SellerContract sellercontracts=objectMapper.readValue(sellerContract, SellerContract.class);
		
		 SellerContract sellercontract = sellerContractService.createSellerContract(file,sellercontracts,sellerId);
	    	
	      return new ResponseEntity<>(sellercontract,HttpStatus.CREATED);
	
	    }

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@PutMapping(produces = MediaType.ALL_VALUE, consumes = MediaType.ALL_VALUE, path = "/update/{id}")
	    public ResponseEntity<SellerContract> updateSellerContract(@RequestBody SellerContract sellerContract,@PathVariable ("id") Long contractId) {
	       
	        sellerContractService.updateSellerContract(sellerContract);
	        return new ResponseEntity(HttpStatus.OK);
	    }
		
		@PutMapping("/update/{id}/edit/fileUpload")
		public ResponseEntity<?> updateFile(@PathVariable("id") Long contractId,
											@RequestParam ("file") MultipartFile file,
											@RequestParam ("sellerContractDetail") String sellerContractDetail) throws IOException
		{
			
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		    SellerContract sellerContracts = obj.readValue(sellerContractDetail, SellerContract.class);
		   
			
				Optional<SellerContract> isContractExist = sellerContractService.findById(contractId);
				if(!isContractExist.isPresent())
				{
					return new ResponseEntity<>(new ResponseMessage("No Record Found to Update!"),HttpStatus.BAD_REQUEST);
				}
				else {
					sellerContractService.update(isContractExist, sellerContracts, file);
						return new ResponseEntity<>(new ResponseMessage("Contract updated successfully!"), HttpStatus.OK);
				}
			}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@PutMapping(produces = MediaType.ALL_VALUE, consumes = MediaType.ALL_VALUE, path = "/updateStatus")
	    public ResponseEntity<SellerContract> updateContractStatus(@RequestBody SellerContract sellerContract) {
			
			SellerContract sContract = sellerContractService.changeContractStatus(sellerContract);
			if(sContract != null) {
				
				return new ResponseEntity(HttpStatus.OK);
				
			}
			else {
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
	    }
	 
	    @DeleteMapping(produces = MediaType.ALL_VALUE, consumes = MediaType.ALL_VALUE, path = "/{contractId}")
	    public ResponseEntity<String> deleteSellerContract(@PathVariable(value = "contractId") Long contractId) {
	    	sellerContractService.deleteSellerContract(contractId);
	        return new ResponseEntity<>("contract with ContractId : " + contractId + " deleted successfully", HttpStatus.OK);
	    }
	    @GetMapping(produces = MediaType.ALL_VALUE, consumes = MediaType.ALL_VALUE, path = "/getSellerContract/{contractId}")
	    public ResponseEntity<SellerContract> getSellerContract(@PathVariable(value = "contractId") Long contractId) {
	        return new ResponseEntity<>(sellerContractService.getSellerContract(contractId), HttpStatus.OK);
	    }
	 
	    @GetMapping(produces = "application/json",consumes = "application/json", path = "/{contractId}")
	    public ResponseEntity<List<SellerContract>> getAllSellerContract() {
	        return new ResponseEntity<>(sellerContractService.getAllSellerContract(), HttpStatus.OK);
	    }

	    @SuppressWarnings("unchecked")
		@GetMapping(consumes = MediaType.ALL_VALUE,produces = MediaType.ALL_VALUE,path = "/getContract/{sellerId}")
	    public ResponseEntity<?> getSellerContractList(@PathVariable(value= "sellerId") Long sellerId ){
	    	List<SellerContract> contracts = sellerContractService.getSellerContractbySeller(sellerId);
	    	return new ResponseEntity(contracts, HttpStatus.OK);
	    }
		
	}