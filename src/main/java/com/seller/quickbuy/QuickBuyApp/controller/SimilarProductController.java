package com.seller.quickbuy.QuickBuyApp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seller.quickbuy.QuickBuyApp.entity.ProductCategory;
import com.seller.quickbuy.QuickBuyApp.entity.ProductMaster;
import com.seller.quickbuy.QuickBuyApp.entity.User;
import com.seller.quickbuy.QuickBuyApp.repository.ProductMasterRepository;
import com.seller.quickbuy.QuickBuyApp.response.RelatedProducts;
import com.seller.quickbuy.QuickBuyApp.response.SimilarProducts;
import com.seller.quickbuy.QuickBuyApp.service.ProductCategoryService;

@RestController
@RequestMapping("api/home")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class SimilarProductController {

	private static final Logger logger = LogManager.getLogger(SimilarProductController.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	ProductCategoryService productCategoryService;
	
	@Autowired
	ProductMasterRepository productMasterRepository;
	
	@GetMapping("/related/products/{categoryId}")
	public ResponseEntity<?> relatedProducts(@PathVariable("categoryId") Long categoryType,Principal principal) {
		logger.info("In ProductMasterController similarProducts()--> START");
		List<RelatedProducts> service = new ArrayList<RelatedProducts>();
		RelatedProducts response = null;
		User user = null;
		ProductCategory productCategory = null;
		
		if (principal != null) {
			//Optional<User> opt = userService.findOne(principal.getName());
			//user = opt.get();
			productCategory = productCategoryService.findByCategoryId(categoryType);
			String sqlQuery = "select * from (select pm.product_name,pm.product_id, pm.PRODUCT_DESCRIPTION,pm.product_icon,pc.category_id, "
					+ "pc.category_name from product_master pm, product_category pc where pm.category_id = pc.category_id "
					+ "and pc.category_name in (select category_name from product_category where category_name != "
					+ "'"+productCategory.getCategoryName()+"' and category_type in (select CATEGORY_TYPE from product_category where category_name"
					+ " = '"+productCategory.getCategoryName()+"')) ORDER BY DBMS_RANDOM.RANDOM) where ROWNUM <= 10";
			
			logger.info(sqlQuery);
			SqlRowSet rs = jdbcTemplate.queryForRowSet(sqlQuery);
			while (rs.next()) {
				response = new RelatedProducts();
				response.setProductId(rs.getLong("product_id"));
				response.setProductName(rs.getString("product_name"));
				response.setCategoryId(rs.getLong("category_id"));
				response.setProductIcon(rs.getString("product_icon"));
				response.setProductCategory(rs.getString("category_name"));
//				response.setProductDescription(rs.getString("product_description"));
				service.add(response);
			}
			
		} else {
			productCategory = productCategoryService.findByCategoryId(categoryType);
			String sqlQuery = "select * from (select pm.product_name,pm.product_id, pm.PRODUCT_DESCRIPTION,pm.product_icon,pc.category_id, "
					+ "pc.category_name from product_master pm, product_category pc where pm.category_id = pc.category_id and "
					+ "pc.category_name in (select category_name from product_category where category_name != "
					+ "'"+productCategory.getCategoryName()+"' and category_type in (select CATEGORY_TYPE from product_category where"
					+ " category_name = '"+productCategory.getCategoryName()+"')) ORDER BY DBMS_RANDOM.RANDOM) where ROWNUM <= 10";
			
			logger.info(sqlQuery);
			SqlRowSet rs = jdbcTemplate.queryForRowSet(sqlQuery);
			while (rs.next()) {
				response = new RelatedProducts();
				response.setProductId(rs.getLong("product_id"));
				response.setProductName(rs.getString("product_name"));
				response.setCategoryId(rs.getLong("category_id"));
				response.setProductIcon(rs.getString("product_icon"));
				response.setProductCategory(rs.getString("category_name"));
				response.setProductDescription(rs.getString("PRODUCT_DESCRIPTION"));
				service.add(response);
			}
		}
		logger.info(" .... "+service);
//		if(service.isEmpty())
//		{
//			logger.info("In ProductMasterController similarProducts()--> END");
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		else {
//			logger.info("In ProductMasterController similarProducts()--> END");
//			return ResponseEntity.ok().body(service);
////					new ResponseEntity<>(service, HttpStatus.OK);
//			
//		}
		return ResponseEntity.ok().body(service);
	}
	
	@GetMapping("/similar/products/{categoryId}/{productId}")
	public ResponseEntity<?> similarProducts(@PathVariable("categoryId") Long categoryType, @PathVariable("productId") Long productId, Principal principal) {
		logger.info("In ProductMasterController similarProducts()--> START");
		List<SimilarProducts> service = new ArrayList<SimilarProducts>();
		SimilarProducts response = null;
		User user = null;
		ProductCategory productCategory = null;
		ProductMaster mster = null;
		if (principal != null) {
			//Optional<User> opt = userService.findOne(principal.getName());
			//user = opt.get();
			productCategory = productCategoryService.findByCategoryId(categoryType);
//			mster = productMasterRepository.findByCategoryId(categoryType).forEach();
			String sqlQuery = "select pm.product_id, pm.product_name,pm.product_icon, pm.product_description, pc.category_id, pc.category_name from product_master pm, "
					+ "product_category pc where pm.category_id = pc.category_id and pc.category_name in (select category_name from"
					+ " product_category where upper(category_name) like '%"+productCategory.getCategoryName()+"%') and (pm.product_id) not in "
					+ productId +" AND ROWNUM <= 10";	
			
			logger.info(sqlQuery);
			SqlRowSet rs = jdbcTemplate.queryForRowSet(sqlQuery);
			while (rs.next()) {
				response = new SimilarProducts();
				response.setProductId(rs.getLong("product_id"));
				response.setProductName(rs.getString("product_name"));
				response.setCategoryId(rs.getLong("category_id"));
				response.setProductIcon(rs.getString("product_icon"));
				response.setProductCategory(rs.getString("category_name"));
				response.setProductDescription(rs.getString("product_description"));
				service.add(response);
			}
			
		}else {
			productCategory = productCategoryService.findByCategoryId(categoryType);
			
			String sqlQuery = "select pm.product_id, pm.product_name, pc.category_id,pm.product_icon, pm.product_description, pc.category_name from product_master pm, "
					+ "product_category pc where pm.category_id = pc.category_id and pc.category_name in (select category_name from"
					+ " product_category where upper(category_name) like  '%"+productCategory.getCategoryName()+"%') and (pm.product_id) not in "
					+ productId + " AND ROWNUM <= 10";	
					
//					"select pm.product_name,pm.product_id, pm.product_description, pm.product_icon, "
//							+ " pc.category_id, pc.category_name from product_master pm,"
//							+" product_category pc where pm.category_id = pc.category_id " + 
//							" and pc.category_name in (select category_name from product_category"
//							+ " where category_name != '"+productCategory.getCategoryName()+"'" + 
//							" and category_type in (select CATEGORY_TYPE from product_category "
//							+ "where category_name = '"+productCategory.getCategoryName()+"'))AND ROWNUM <= 10";
			
			logger.info(sqlQuery);
			SqlRowSet rs = jdbcTemplate.queryForRowSet(sqlQuery);
			while (rs.next()) {
				response = new SimilarProducts();
				response.setProductId(rs.getLong("product_id"));
				response.setProductName(rs.getString("product_name"));
				response.setCategoryId(rs.getLong("category_id"));
				response.setProductIcon(rs.getString("product_icon"));
				response.setProductCategory(rs.getString("category_name"));
				response.setProductDescription(rs.getString("product_description"));
				service.add(response);
			}
		}
		return ResponseEntity.ok().body(service);
	}
}
