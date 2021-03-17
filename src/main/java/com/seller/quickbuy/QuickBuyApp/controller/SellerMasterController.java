package com.seller.quickbuy.QuickBuyApp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seller.quickbuy.QuickBuyApp.entity.SellerMaster;
import com.seller.quickbuy.QuickBuyApp.exception.SellerClientException;
import com.seller.quickbuy.QuickBuyApp.service.SellerMasterService;

@RestController
@RequestMapping("/api/seller")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders="*")
public class SellerMasterController {
	@Autowired
    private SellerMasterService sellerMasterService;

    @PostMapping(consumes = "application/json", produces = "application/json", path = "/create")
    public ResponseEntity<SellerMaster> createSeller(@RequestBody SellerMaster seller) {
    	SellerMaster sellermaster = sellerMasterService.createSeller(seller);
    	if(sellermaster  !=null) {
    		return new ResponseEntity<>(sellermaster, HttpStatus.CREATED);
    	}
    	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        
    }
 
    @PutMapping(path = "/update/{sellerId}")
    public ResponseEntity<SellerMaster> updateSeller(@PathVariable("sellerId") Long sellerId,@RequestBody SellerMaster seller) {
    	 try {
    		 Optional<SellerMaster> existProduct = sellerMasterService.findBySellerId(sellerId);
    		 System.out.println(existProduct);
    	        sellerMasterService.updateSeller(seller);
    	        return new ResponseEntity<>(HttpStatus.OK);
    	    } catch (SellerClientException e) {
    	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	    }   
//        return new ResponseEntity<>(sellerMasterService.updateSeller(seller), HttpStatus.CREATED);
    }
 
    @DeleteMapping(path = "/delete/{sellerId}")
    public ResponseEntity<String> deleteSeller(@PathVariable(value = "sellerId") Long sellerId) {
    	sellerMasterService.deleteSeller(sellerId);
        return new ResponseEntity<>("Seller with SellerId : " + sellerId + " deleted successfully", HttpStatus.OK);
    }
 
    @GetMapping(path = "/{seller_id}", produces = "application/json")
    public ResponseEntity<SellerMaster> getSeller(@PathVariable(value = "seller_id") Long seller_id) {
        return new ResponseEntity<>(sellerMasterService.getSeller(seller_id), HttpStatus.OK);
    }
 
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<SellerMaster>> getAllSeller() {
        return new ResponseEntity<>(sellerMasterService.getAllSeller(), HttpStatus.OK);
    }
 
}