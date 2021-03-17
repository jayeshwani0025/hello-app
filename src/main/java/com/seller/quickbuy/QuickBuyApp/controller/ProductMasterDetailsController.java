package com.seller.quickbuy.QuickBuyApp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seller.quickbuy.QuickBuyApp.entity.ProductCategory;
import com.seller.quickbuy.QuickBuyApp.entity.ProductMaster;
import com.seller.quickbuy.QuickBuyApp.entity.User;
import com.seller.quickbuy.QuickBuyApp.message.response.ResponseMessage;
import com.seller.quickbuy.QuickBuyApp.response.ProductMasterResponse;
import com.seller.quickbuy.QuickBuyApp.service.ProductCategoryService;
import com.seller.quickbuy.QuickBuyApp.service.ProductMasterService;
import com.seller.quickbuy.QuickBuyApp.service.UserService;

@RestController
@RequestMapping("/api/productDtls")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class ProductMasterDetailsController {
	
	@Autowired
	private UserService userService;

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	ProductCategoryService productCategoryService;
	
	private static final Logger logger = LogManager.getLogger(ProductMasterDetailsController.class);
	@Autowired
	ProductMasterService productMasterService;
	@PostMapping("/new")
	public ResponseEntity<?> create(@Valid @RequestBody ProductMaster productMaster ,BindingResult bindingResult) {
		logger.info("In ProductMasterController create()--> START");
		ProductMaster productExist = productMasterService.findOne(productMaster.getProductName());
		if(productExist != null)
		{
			bindingResult.rejectValue(productExist.getProductName(), "error.Product", "There is already a product with the name provided");
		}
		if(bindingResult.hasErrors())
		{
			return ResponseEntity.badRequest().body(bindingResult);
		}
		
		logger.info("In ProductMasterController create()--> END");
		return ResponseEntity.ok(productMasterService.save(productMaster));
	}

	/**
	 * 
	 * @param productId of product to be deleted from master
	 * @return ResponseEntity
	 */
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<?> delete(@PathVariable ("productId") Long productId) {
		logger.info("In ProductMasterController delete()--> START");
		productMasterService.delete(productId);
		logger.info("In ProductMasterController delete()--> END");
		return ResponseEntity.ok().build();
	}
	
	
	/**
	 * 
	 * @return all products from master
	 */
	@GetMapping("/product")
	public List<ProductMaster> findAll()
	{
		List<ProductMaster> service = null;
		logger.info("In ProductMasterController findAll()--> START");
		service= productMasterService.findAllProduct();
		logger.info("In ProductMasterController findAll()--> END");
		return service;
	}
	
	/**
	 * 
	 * @param categoryId to be search
	 * @return all products of categoryId
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/productByCategory/{categoryId}")
	public ResponseEntity<List<ProductMaster>> getAllProducts(@PathVariable ("categoryId") Long categoryId)
	{
		logger.info("In ProductMasterController findAll()--> START");
		List<ProductMaster> productList = productMasterService.findByCategoryId(categoryId);
		if(productList.isEmpty())
		{
			return new ResponseEntity(new ResponseMessage("No Records Found for given Category "+categoryId),HttpStatus.BAD_REQUEST);
		}
		logger.info("In ProductMasterController findAll()--> END");
		return new ResponseEntity<List<ProductMaster>>(productList,HttpStatus.OK);
	}
	

	@PutMapping("/edit/{productId}")
	public ResponseEntity<?> edit(@PathVariable("productId") Long productId, @RequestBody ProductMaster product,
            				BindingResult bindingResult) {
		logger.info("In ProductMasterController edit()--> START");
		if(bindingResult.hasErrors())
		{
			return ResponseEntity.badRequest().body(bindingResult);
		}
		if(!productId.equals(product.getProductId()))
		{
			return ResponseEntity.badRequest().body("Id not Matched");
		}
		logger.info("In ProductMasterController edit()--> END");
		return ResponseEntity.ok(productMasterService.update(product));
	}

//	@GetMapping("/product/{productId}")
//	public ResponseEntity<ProductMaster> getProductById(@PathVariable(value = "productId") Long productId)
//			throws ResourceNotFoundException {
//		ProductMaster productMaster = productMasterService.findByProductId(productId);
////				.orElseThrow(() -> new ResourceNotFoundException(productId));
//		return ResponseEntity.ok().body(productMaster);
//	}

	/**
	 * 
	 * @param principal
	 * @return
	 */
	
	
	/**
	 * 
	 * @param principal
	 * @return
	 */
	@GetMapping("/products")
	public ResponseEntity<?> productList(Principal principal) {
		logger.info("In ProductMasterController similarProducts()--> START");
		List<ProductMasterResponse> service = new ArrayList<ProductMasterResponse>();
		ProductMasterResponse response = null;
		User user = null;
				
		if (principal != null) {
			Optional<User> opt = userService.findOne(principal.getName());
			user = opt.get();
			String sqlQuery = "select pm.product_name from product_master pm, seller_product_master spm, seller_product_inventory spi\r\n" + 
							"where pm.product_id = spm.product_id\r\n" + 
							"and spi.seller_product_id = spm.seller_product_id\r\n" + 
							"and spm.seller_id = 24 and spm.location_id = 2 and pm.product_name not in ('Solid Men Polo Neck Green T-Shirt')";
			logger.info(sqlQuery);
			SqlRowSet rs = jdbcTemplate.queryForRowSet(sqlQuery);
			while (rs.next()) {
				response = new ProductMasterResponse();
				response.setProductName(rs.getString("product_name"));
				service.add(response);
			}
			
		}
		logger.info(" .... "+service);
		if(service.isEmpty())
		{
			logger.info("In ProductMasterController similarProducts()--> END");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			logger.info("In ProductMasterController similarProducts()--> END");
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
}
