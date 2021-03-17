package com.seller.quickbuy.QuickBuyApp.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seller.quickbuy.QuickBuyApp.entity.Role;
import com.seller.quickbuy.QuickBuyApp.entity.RoleName;
import com.seller.quickbuy.QuickBuyApp.entity.User;
import com.seller.quickbuy.QuickBuyApp.response.OperationResponse.ResponseStatusEnum;
import com.seller.quickbuy.QuickBuyApp.response.SingleDataSeriseResponse;
import com.seller.quickbuy.QuickBuyApp.response.SingleSellerSerise;
import com.seller.quickbuy.QuickBuyApp.response.SingleSerise;
import com.seller.quickbuy.QuickBuyApp.service.UserService;

@RestController
@RequestMapping(value = "/api/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class DashboardController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	UserService userService;

	@RequestMapping(value = "/seller-product", method = RequestMethod.GET)
	public SingleDataSeriseResponse getSellerAndProductData(Principal principal) {
		if (principal == null || principal.getName() == null) {
			throw new IllegalArgumentException("Invalid access");
		}
		Optional<User> opt = userService.findOne(principal.getName());
		User user = opt.get();
		Set<Role> role = user.getRoles();
		SingleDataSeriseResponse resp = null;
		for(Role roles: role) {
			System.out.println(roles.getName());
			RoleName roleName = roles.getName();
			if(roleName.name().equalsIgnoreCase("ROLE_ADMIN")) {

				   String sql ="select count(spm.product_id) Product_Count, sm.Seller_Name from seller_master sm, product_master pm, seller_product_master spm\r\n" + 
				   		"where spm.product_id = pm.product_id\r\n" + 
				   		"and spm.seller_id = sm.seller_id\r\n" + 
				   		"Group By Sm.Seller_Name";
				   System.out.println(sql);
				   
				   String countType = new String();
			       long count;
			       
			       SingleSerise singleSerise;
			       resp = new SingleDataSeriseResponse();
			       ArrayList<SingleSerise> dataItemList = new ArrayList<SingleSerise>();


			       List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			       
			       for (Map<String, Object> row : list) {
			    	  String str =  (String) row.get("PRODUCT_COUNT").toString();
			    	  String str1 =  (String) row.get("SELLER_NAME");
			    	   System.out.println(str1 +" : "+str);
//			    	   singleSerise = null;

			         singleSerise = new SingleSerise((String)row.get("SELLER_NAME"), new BigDecimal((String) row.get("PRODUCT_COUNT").toString()));
//			           singleSerise = new SingleSerise((String)row.get("name"), new BigDecimal((long)row.get("value")) );{PRODUCT_COUNT=463, SELLER_NAME=Sabir}
			           dataItemList.add(singleSerise);
			       }
			       resp.setItems(dataItemList);
			       resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
			       resp.setOperationMessage("All Categories available in our Database ");

			}		
		}
		
          //resp.setItems(singleSerise);
       return resp;
	}
	@RequestMapping(value = "/all-category", method = RequestMethod.GET)
	public SingleDataSeriseResponse getCategoryData(Principal principal) {
		if (principal == null || principal.getName() == null) {
			throw new IllegalArgumentException("Invalid access");
		}
		Optional<User> opt = userService.findOne(principal.getName());
		User user = opt.get();
		Set<Role> role = user.getRoles();
		SingleDataSeriseResponse resp = null;
		for(Role roles: role) {
			System.out.println(roles.getName());
			RoleName roleName = roles.getName();
			if(roleName.name().equalsIgnoreCase("ROLE_ADMIN")) {
		String fieldName = "";
		String sql ="select count(1) as Quantity , (select category_name from product_category where category_id = pm.category_id) as\r\n" + 
				"Category from product_master pm group by pm.category_id";
		System.out.println(sql);
		   
		   String countType = new String();
	       long count;
	       
	       SingleSerise singleSerise;
	      resp = new SingleDataSeriseResponse();
	       ArrayList<SingleSerise> dataItemList = new ArrayList<SingleSerise>();


	       List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
	       
	       for (Map<String, Object> row : list) {
	    	  String str =  (String) row.get("QUANTITY").toString();
	    	  String str1 =  (String) row.get("CATEGORY");
	    	   System.out.println(str1 +" : "+str);
//	    	   singleSerise = null;
	         singleSerise = new SingleSerise((String)row.get("CATEGORY"), new BigDecimal((String) row.get("QUANTITY").toString()));
//	           singleSerise = new SingleSerise((String)row.get("name"), new BigDecimal((long)row.get("value")) );{PRODUCT_COUNT=463, SELLER_NAME=Sabir}
	           dataItemList.add(singleSerise);
	       }
	       resp.setItems(dataItemList);
	       resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
	       resp.setOperationMessage("All Categories available in our Database");
	       //resp.setItems(singleSerise);
		}
			
		}
	       return resp;
		
	}
	@RequestMapping(value = "/product-location", method = RequestMethod.GET)
	public SingleDataSeriseResponse getLocationData(Principal principal) {
		if (principal == null || principal.getName() == null) {
			throw new IllegalArgumentException("Invalid access");
		}
		Optional<User> opt = userService.findOne(principal.getName());
		User user = opt.get();
		Set<Role> role = user.getRoles();
		SingleDataSeriseResponse resp = null;
		for(Role roles: role) {
			System.out.println(roles.getName());
			RoleName roleName = roles.getName();
			if(roleName.name().equalsIgnoreCase("ROLE_ADMIN")) {
		String sql ="Select Count(Spi.Seller_Product_Id) as seller_product_quantity, Lm.Location_Name From Seller_Product_Inventory Spi, Location_Master Lm\r\n" + 
				"where spi.location_id = lm.location_id group by lm.location_name";
		System.out.println(sql);
		   
		   String countType = new String();
	       long count;
	       
	       SingleSerise singleSerise;
	       resp = new SingleDataSeriseResponse();
	       ArrayList<SingleSerise> dataItemList = new ArrayList<SingleSerise>();


	       List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
	   
	       for (Map<String, Object> row : list) {
//	    	  String str =  (String) row.get("SELLER_PRODUCT").toString();
//	    	  String str1 =  (String) row.get("LOCATION_NAME");
//	    	   System.out.println(str1 +" : "+str);
//	    	   singleSerise = null;
	         singleSerise = new SingleSerise((String)row.get("LOCATION_NAME"), new BigDecimal((String) row.get("SELLER_PRODUCT_QUANTITY").toString()));
//	           singleSerise = new SingleSerise((String)row.get("name"), new BigDecimal((long)row.get("value")) );{PRODUCT_COUNT=463, SELLER_NAME=Sabir}
	           dataItemList.add(singleSerise);
	       }
	       resp.setItems(dataItemList);
	       resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
	       resp.setOperationMessage("All Categories available in our Database");
	       //resp.setItems(singleSerise);
			}
		}
	       return resp;
	}
	
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public SingleDataSeriseResponse getOrderData(Principal principal) {
		if (principal == null || principal.getName() == null) {
			throw new IllegalArgumentException("Invalid access");
		}
		Optional<User> opt = userService.findOne(principal.getName());
		User user = opt.get();
		Set<Role> role = user.getRoles();
		SingleDataSeriseResponse resp = null;
		for(Role roles: role) {
			System.out.println(roles.getName());
			RoleName roleName = roles.getName();
			if(roleName.name().equalsIgnoreCase("ROLE_ADMIN")) {
		String sql = "SELECT SUM(OAA.ORDERED_QUANTITY)  QUANTITY , (SELECT CATEGORY_NAME FROM PRODUCT_CATEGORY WHERE CATEGORY_ID = PM.CATEGORY_ID) CATEGORY_NAME\r\n" + 
				"FROM PRODUCT_MASTER PM, SELLER_PRODUCT_MASTER SPM, SELLER_PRODUCT_INVENTORY SPI, ORDER_LINES_ALL OAA\r\n" + 
				"WHERE PM.PRODUCT_ID = SPM.PRODUCT_ID\r\n" + 
				"AND SPM.SELLER_PRODUCT_ID = SPI.SELLER_PRODUCT_ID\r\n" + 
				"AND OAA.SELLER_INVENTORY_ID = SPI.SELLER_INVENTORY_ID GROUP BY CATEGORY_ID";
		System.out.println(sql);
		   
		   String countType = new String();
	       long count;
	       
	       SingleSerise singleSerise;
	       resp = new SingleDataSeriseResponse();
	       ArrayList<SingleSerise> dataItemList = new ArrayList<SingleSerise>();


	       List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
	   
	       for (Map<String, Object> row : list) {
         singleSerise = new SingleSerise((String)row.get("CATEGORY_NAME"), new BigDecimal((String) row.get("QUANTITY").toString()));

	           dataItemList.add(singleSerise);
	       }
	       resp.setItems(dataItemList);
	       resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
	       resp.setOperationMessage("All Categories available in our Database");
		  }
		}
	       return resp;
	}
	
	@RequestMapping(value = "/sellerProductSales", method = RequestMethod.GET)
	public SingleDataSeriseResponse getSellerProductSalesData(Principal principal) {
		if (principal == null || principal.getName() == null) {
			throw new IllegalArgumentException("Invalid access");
		}
		Optional<User> opt = userService.findOne(principal.getName());
		User user = opt.get();
		Set<Role> role = user.getRoles();
		SingleDataSeriseResponse resp = null;
		for(Role roles: role) {
			System.out.println(roles.getName());
			RoleName roleName = roles.getName();
			if(roleName.name().equalsIgnoreCase("ROLE_ADMIN")) {
		String sql = "SELECT COUNT(DISTINCT OAA.SOURCE_DOCUMENT_ID)  ORDER_QUANTITY , SPM.SELLER_ID ,(select seller_name from seller_master where SELLER_ID = spm.SELLER_ID) as seller_name\r\n" + 
					"FROM PRODUCT_MASTER PM, SELLER_PRODUCT_MASTER SPM, SELLER_PRODUCT_INVENTORY SPI, ORDER_LINES_ALL OAA\r\n" + 
					"WHERE PM.PRODUCT_ID = SPM.PRODUCT_ID\r\n" +
					"AND SPM.SELLER_PRODUCT_ID = SPI.SELLER_PRODUCT_ID\r\n" +
					"AND OAA.SELLER_INVENTORY_ID = SPI.SELLER_INVENTORY_ID GROUP BY SELLER_ID"; 
		System.out.println(sql);
		   
		   String countType = new String();
	       long count;
	       
	       SingleSerise singleSerise;
	       resp = new SingleDataSeriseResponse();
	       ArrayList<SingleSerise> dataItemList = new ArrayList<SingleSerise>();


	       List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
	   
	       for (Map<String, Object> row : list) {
      singleSerise = new SingleSerise((String)row.get("SELLER_NAME"),new BigDecimal((String) row.get("ORDER_QUANTITY").toString()), new BigDecimal((String) row.get("SELLER_ID").toString()));

	           dataItemList.add(singleSerise);
	       }
	       resp.setItems(dataItemList);
	       resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
	       resp.setOperationMessage("All Categories available in our Database");
			}
		}
	       return resp;
	}
	
	@RequestMapping(value = "/sellerOrderCount", method = RequestMethod.GET)
	public SingleDataSeriseResponse getSellerOrderCountData(Principal principal) {
		if (principal == null || principal.getName() == null) {
			throw new IllegalArgumentException("Invalid access");
		}
		Optional<User> opt = userService.findOne(principal.getName());
		User user = opt.get();
		Set<Role> role = user.getRoles();
		SingleDataSeriseResponse resp = null;
		for(Role roles: role) {
			System.out.println(roles.getName());
			RoleName roleName = roles.getName();
			if(roleName.name().equalsIgnoreCase("ROLE_ADMIN")) {
		String sql = "SELECT COUNT(DISTINCT OAA.SOURCE_DOCUMENT_ID)  ORDER_ID , SPM.SELLER_ID, (SELECT SELLER_NAME FROM SELLER_MASTER WHERE SELLER_ID = SPM.SELLER_ID) AS SELLER_NAME\r\n" +
				"FROM PRODUCT_MASTER PM, SELLER_PRODUCT_MASTER SPM, SELLER_PRODUCT_INVENTORY SPI, ORDER_LINES_ALL OAA\r\n" +
				"WHERE PM.PRODUCT_ID = SPM.PRODUCT_ID\r\n" +
				"AND SPM.SELLER_PRODUCT_ID = SPI.SELLER_PRODUCT_ID\r\n" +
				"AND OAA.SELLER_INVENTORY_ID = SPI.SELLER_INVENTORY_ID GROUP BY SELLER_ID";

		System.out.println(sql);
		   
		   String countType = new String();
	       long count;
	       
	       SingleSerise singleSerise;
	       resp = new SingleDataSeriseResponse();
	       ArrayList<SingleSerise> dataItemList = new ArrayList<SingleSerise>();


	       List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
	   
	       for (Map<String, Object> row : list) {
      singleSerise = new SingleSerise((String)row.get("SELLER_NAME"),new BigDecimal((String) row.get("ORDER_ID").toString()), new BigDecimal((String) row.get("SELLER_ID").toString()));

	           dataItemList.add(singleSerise);
	       }
	       resp.setItems(dataItemList);
	       resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
	       resp.setOperationMessage("All Categories available in our Database");
			}
		}
	       return resp;
	}
	
	@RequestMapping(value = "/locationOrderCount", method = RequestMethod.GET)
	public SingleDataSeriseResponse getLocationOrderCountData(Principal principal) {
		if (principal == null || principal.getName() == null) {
			throw new IllegalArgumentException("Invalid access");
		}
		Optional<User> opt = userService.findOne(principal.getName());
		User user = opt.get();
		Set<Role> role = user.getRoles();
		SingleDataSeriseResponse resp = null;
		for(Role roles: role) {
			System.out.println(roles.getName());
			RoleName roleName = roles.getName();
			if(roleName.name().equalsIgnoreCase("ROLE_ADMIN")) {
		String sql = "SELECT count(distinct oaa.SOURCE_DOCUMENT_ID)  ORDER_ID , SPI.location_id,\r\n" +
				"(SELECT LOCATION_NAME FROM LOCATION_MASTER WHERE LOCATION_ID = SPI.LOCATION_ID) AS LOCATION\r\n" +
				"FROM PRODUCT_MASTER PM, SELLER_PRODUCT_MASTER SPM, SELLER_PRODUCT_INVENTORY SPI, ORDER_LINES_ALL OAA\r\n" +
				"WHERE PM.PRODUCT_ID = SPM.PRODUCT_ID\r\n" +
				"AND SPM.SELLER_PRODUCT_ID = SPI.SELLER_PRODUCT_ID\r\n" +
				"AND OAA.SELLER_INVENTORY_ID = SPI.SELLER_INVENTORY_ID GROUP BY SPI.location_id";
		System.out.println(sql);
		   
		   String countType = new String();
	       long count;
	       
	       SingleSerise singleSerise;
	       resp = new SingleDataSeriseResponse();
	       ArrayList<SingleSerise> dataItemList = new ArrayList<SingleSerise>();


	       List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
	   
	       for (Map<String, Object> row : list) {
      singleSerise = new SingleSerise((String)row.get("LOCATION"),new BigDecimal((String) row.get("ORDER_ID").toString()), new BigDecimal((String) row.get("LOCATION_ID").toString()));

	           dataItemList.add(singleSerise);
	       }
	       resp.setItems(dataItemList);
	       resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
	       resp.setOperationMessage("All Categories available in our Database");
			}
		}
	       return resp;
	}
	
	
	@RequestMapping(value = "/locationOrder", method = RequestMethod.GET)
	public SingleDataSeriseResponse getLocationOrderData(Principal principal) {
		if (principal == null || principal.getName() == null) {
			throw new IllegalArgumentException("Invalid access");
		}
		Optional<User> opt = userService.findOne(principal.getName());
		User user = opt.get();
		Set<Role> role = user.getRoles();
		SingleDataSeriseResponse resp = null;
		for(Role roles: role) {
			System.out.println(roles.getName());
			RoleName roleName = roles.getName();
			if(roleName.name().equalsIgnoreCase("ROLE_ADMIN")) {
		String sql = "SELECT SUM(OAA.ORDERED_QUANTITY)   item_QUANTITY , SPI.location_id,\r\n" +
				"(SELECT LOCATION_NAME FROM LOCATION_MASTER WHERE LOCATION_ID = SPI.LOCATION_ID) AS LOCATION\r\n" +
				"FROM PRODUCT_MASTER PM, SELLER_PRODUCT_MASTER SPM, SELLER_PRODUCT_INVENTORY SPI, ORDER_LINES_ALL OAA\r\n" +
				"WHERE PM.PRODUCT_ID = SPM.PRODUCT_ID\r\n" +
				"AND SPM.SELLER_PRODUCT_ID = SPI.SELLER_PRODUCT_ID\r\n" +
				"AND OAA.SELLER_INVENTORY_ID = SPI.SELLER_INVENTORY_ID GROUP BY SPI.LOCATION_ID";

		System.out.println(sql);
		   
		   String countType = new String();
	       long count;
	       
	       SingleSerise singleSerise;
	       resp = new SingleDataSeriseResponse();
	       ArrayList<SingleSerise> dataItemList = new ArrayList<SingleSerise>();


	       List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
	   
	       for (Map<String, Object> row : list) {
      singleSerise = new SingleSerise((String)row.get("LOCATION"),new BigDecimal((String) row.get("ITEM_QUANTITY").toString()) ,new BigDecimal((String) row.get("LOCATION_ID").toString()));

	           dataItemList.add(singleSerise);
	       }
	       resp.setItems(dataItemList);
	       resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
	       resp.setOperationMessage("All Categories available in our Database");
			}
		}
	       return resp;
	}
	
	
}
