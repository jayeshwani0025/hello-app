package com.seller.quickbuy.QuickBuyApp.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seller.quickbuy.QuickBuyApp.entity.ProductCategory;
import com.seller.quickbuy.QuickBuyApp.entity.ProductMaster;
import com.seller.quickbuy.QuickBuyApp.message.response.ResponseMessage;
import com.seller.quickbuy.QuickBuyApp.repository.ProductCategoryRepository;
import com.seller.quickbuy.QuickBuyApp.response.CategoryPage;
import com.seller.quickbuy.QuickBuyApp.service.ProductCategoryService;
import com.seller.quickbuy.QuickBuyApp.service.ProductMasterService;

import lombok.var;




/**
 * 
 * @author jyoti.bhosale
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders="*")
public class ProductCategoryController {

	private static final Logger logger = LogManager.getLogger(ProductCategoryController.class);

	@Autowired
	ProductCategoryService productCategoryService;
	
	@Autowired
	ProductMasterService productMasterService;
	@Autowired
	ProductCategoryRepository prdRepository;
	
	/**
	 * 
	 * @return list of category with ResponseEntity
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/categories")
	public ResponseEntity<List<ProductCategory>> findAll()
	{
		logger.info("In ProductCategoryController findAll()......START");
		List<ProductCategory> categoryList = productCategoryService.findAll();
		if(categoryList.isEmpty())
		{
			return new ResponseEntity(new ResponseMessage("Fail----> No Record Found!"),HttpStatus.BAD_REQUEST);
		}
		logger.info("In ProductCategoryController findAll()......END"); 
		return new ResponseEntity<List<ProductCategory>>(categoryList, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/categories/{id}")
	public ResponseEntity<ProductCategory> findById(@PathVariable ("id") Long categoryId)
	{
		logger.info("In ProductCategoryController findById()......START");
		Optional<ProductCategory> productCategory = productCategoryService.findById(categoryId);
		if(!productCategory.isPresent())
		{
			return new ResponseEntity(new ResponseMessage("Fail -> Category not present!"),HttpStatus.BAD_REQUEST);
		}
		logger.info("In ProductCategoryController findById()......END");
		return new ResponseEntity(productCategory,HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param productCategory to be added
	 * @return ResponseEntity
	 */
	@PostMapping("/category/new")
	public ResponseEntity<?> create(@Valid @RequestBody ProductCategory productCategory)
	{
		logger.info("In ProductCategoryController create()......START");
		ProductCategory categoryExist = productCategoryService.findOne(productCategory.getCategoryType());
		if(categoryExist != null)
		{
			return new ResponseEntity<>(new ResponseMessage("Fail -> Category is already in use!"), 
										HttpStatus.BAD_REQUEST);
		}
		productCategoryService.save(productCategory);
		logger.info("In ProductCategoryController create()......END");
		return new ResponseEntity<>(new ResponseMessage("Category Added Successfully!"),HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param categoryId to be delete
	 * @return ResponseEntity
	 */
	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<?> delete(@PathVariable ("categoryId") Long categoryId)
	{
		logger.info("In ProductCategoryController delete()......START");
		Optional<ProductCategory> productCategory = productCategoryService.findById(categoryId);
		if(!productCategory.isPresent())
		{
			return new ResponseEntity<>(new ResponseMessage("Fail -> Category not present!"),HttpStatus.BAD_REQUEST);
		}
		else {
			 List<ProductMaster> productList = productMasterService.findByCategoryId(categoryId);
			 if(!productList.isEmpty())
			 {
				 return new ResponseEntity<>(new ResponseMessage("Fail -> Category can not be delete because Child record is present!"),HttpStatus.BAD_REQUEST);
			 }
			 else {
				 productCategoryService.delete(categoryId);
				 logger.info("In ProductCategoryController delete()......END");
				 return new ResponseEntity<>(new ResponseMessage("Category Deleted Successfully!"),HttpStatus.OK);
			}
 		}
		
		//return new ResponseEntity<>(new ResponseMessage("Category Deleted Successfully!"),HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param categoryId to be Update
	 * @param productCategory to be update
	 * @return
	 */
	@PutMapping("/quickBuy/category/{id}/edit")
	public ResponseEntity<?> update(@PathVariable ("id") Long categoryId,
									@Valid @RequestBody ProductCategory productCategory)
	{
		logger.info("In ProductCategoryController update()......START");
		if(categoryId != productCategory.getCategoryId())
		{
			return new ResponseEntity<>(new ResponseMessage("Fail -> Id not Matched!"),HttpStatus.BAD_REQUEST);
		}
		productCategoryService.save(productCategory);
		logger.info("In ProductCategoryController update()......END");
		return new ResponseEntity<>(new ResponseMessage("Category Updated Successfully!"),HttpStatus.OK);
	}
	
	
	  @GetMapping("/category/{type}")
	    public CategoryPage showOne(@PathVariable("type") Long categoryType,
	                                @RequestParam(value = "page", defaultValue = "1") Integer page,
	                                @RequestParam(value = "size", defaultValue = "15") Integer size) {

	        ProductCategory cat = productCategoryService.findByCategoryId(categoryType);
	        PageRequest request = PageRequest.of(page - 1, size);
	        Page<ProductMaster> productInCategory = productMasterService.findAllInCategory(categoryType, request);
	        var tmp = new CategoryPage("", productInCategory);
	        tmp.setCategory(cat.getCategoryName());
	        return tmp;
	    }
	  
//	  @GetMapping("/category1/{type}")
//	    public CategoryPage showOne12(@PathVariable("type") Long categoryType,
//	                                @RequestParam(value = "page", defaultValue = "1") Integer page,
//	                                @RequestParam(value = "size", defaultValue = "15") Integer size) {
//
//	        ProductCategory cat = productCategoryService.findByCategoryId(categoryType);
//	        PageRequest request = PageRequest.of(page - 1, size);
//	        Page<ProductMasterResponse> productInCategory = productMasterService.findAllInCategory(categoryType, request);
//	        var tmp = new CategoryPage("", productInCategory);
//	        tmp.setCategory(cat.getCategoryName());
//	        return tmp;
//	    }
}
