package com.seller.quickbuy.QuickBuyApp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seller.quickbuy.QuickBuyApp.entity.LocationMaster;
import com.seller.quickbuy.QuickBuyApp.entity.ProductCategory;
import com.seller.quickbuy.QuickBuyApp.entity.ProductMaster;
import com.seller.quickbuy.QuickBuyApp.entity.SellerMaster;
import com.seller.quickbuy.QuickBuyApp.entity.SellerProductInventory;
import com.seller.quickbuy.QuickBuyApp.entity.SellerProductMaster;
import com.seller.quickbuy.QuickBuyApp.entity.User;
import com.seller.quickbuy.QuickBuyApp.exception.ResourceNotFoundException;
import com.seller.quickbuy.QuickBuyApp.repository.LocationMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.ProductMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.SellerMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.SellerProductInventoryMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.SellerProductMasterRepository;
import com.seller.quickbuy.QuickBuyApp.response.ProductDetailResponse;
import com.seller.quickbuy.QuickBuyApp.response.ProductMasterResponse;
import com.seller.quickbuy.QuickBuyApp.response.SellerProductResponse;
import com.seller.quickbuy.QuickBuyApp.response.UserCategoryPage;
import com.seller.quickbuy.QuickBuyApp.service.ProductCategoryService;
import com.seller.quickbuy.QuickBuyApp.service.ProductMasterService;
import com.seller.quickbuy.QuickBuyApp.service.UserService;

import lombok.experimental.var;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class UserCategoryController {

	@Autowired
	ProductCategoryService productCategoryService;
	@Autowired
	ProductMasterRepository productMasterRepository;
	@Autowired
	ProductMasterService productMasterService;
	@Autowired
	SellerProductMasterRepository sellerProductMasterRepository;
	@Autowired
	SellerProductInventoryMasterRepository sellerProductInventoryMasterRepository;
	@Autowired
	private LocationMasterRepository locationMasterRepository;
	@Autowired
	private SellerMasterRepository sellerMasterRepository;
	@Autowired
	private UserService userService;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@GetMapping("/category/{type}")
	public UserCategoryPage showOne(@PathVariable("type") Long categoryType,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "15") Integer size, Principal principal) {
		List<ProductMasterResponse> service = new ArrayList<ProductMasterResponse>();
		ProductMasterResponse response = null;
		ProductCategory cat = null;
		LocationMaster locMstr = null;
		User user = null;
		
		if (principal != null) {
			Optional<User> opt = userService.findOne(principal.getName());
			user = opt.get();
			cat = productCategoryService.findByCategoryId(categoryType);
			String sql = "SELECT PM.PRODUCT_ID, PM.CATEGORY_ID, PM.PRODUCT_NAME, PM.PRODUCT_ICON, PM.PRODUCT_DESCRIPTION,SPI.SELLER_INVENTORY_ID,"
					+ " PC.CATEGORY_NAME, SPI.PRODUCT_STATUS,SPI.ACTUAL_PRICE,SPI.QUANTITY, SPI.SELLER_INVENTORY_ID, LM.LOCATION_ID, LM.LOCATION_NAME "
					+ "FROM SELLER_PRODUCT_INVENTORY SPI, SELLER_PRODUCT_MASTER SPM, PRODUCT_MASTER PM, PRODUCT_CATEGORY PC, LOCATION_MASTER LM "
					+ "WHERE SPI.SELLER_PRODUCT_ID = SPM.SELLER_PRODUCT_ID AND PM.PRODUCT_ID = SPM.PRODUCT_ID AND PC.CATEGORY_ID = PM.CATEGORY_ID "
					+ "AND LM.LOCATION_ID = SPM.LOCATION_ID AND LM.CITY = (SELECT CITY FROM ALL_USERS WHERE USER_ID =  "+user.getUserId()+" )"
					+ " AND PC.CATEGORY_ID = "+categoryType+"";
//						  "AND TRIM(PC.CATEGORY_NAME) LIKE TRIM('"+categoryType +"')";
			System.out.println(sql);
			SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
			while (rs.next()) {
				response = new ProductMasterResponse();
				response.setProductId(rs.getLong("PRODUCT_ID"));
				response.setLocationId(rs.getLong("LOCATION_ID"));
				response.setCategoryId(rs.getLong("CATEGORY_ID"));
				response.setProductName(rs.getString("PRODUCT_NAME"));
				response.setProductIcon(rs.getString("PRODUCT_ICON"));
				response.setProductCategory(rs.getString("CATEGORY_NAME"));
				response.setProductDescription(rs.getString("PRODUCT_DESCRIPTION"));
				response.setProductStatus(rs.getString("PRODUCT_STATUS"));
				response.setProductPrice(rs.getLong("ACTUAL_PRICE"));
				response.setProductQuantity(rs.getLong("QUANTITY"));
				response.setSellerInvId(rs.getLong("SELLER_INVENTORY_ID"));
				response.setProductLocation(rs.getString("LOCATION_NAME"));
				
				service.add(response);
			}
			
			
		} else {
			try {
				cat = productCategoryService.findByCategoryId(categoryType);
				PageRequest request = PageRequest.of(page - 1, size);
				Page<ProductMaster> prdMstr = productMasterService.findAllInCategory(categoryType, request);

				for (ProductMaster prdmstr : prdMstr) {
					response = new ProductMasterResponse();

					Long prdId = prdmstr.getProductId();
					Page<SellerProductMaster> prdSellrMstr = sellerProductMasterRepository.findByProductId(prdId,
							request);

					for (SellerProductMaster smdtls : prdSellrMstr) {
						Long sellerPrdId = smdtls.getSellerProductId();
						Page<SellerProductInventory> sellerPrdsInvs = sellerProductInventoryMasterRepository
								.findBySellerProductIdOrderByActualPriceAsc(sellerPrdId, request);
						for (SellerProductInventory inv : sellerPrdsInvs) {
							locMstr = locationMasterRepository.findByLocationId(inv.getLocationId());
							response.setProductCategory(cat.getCategoryName());
							response.setProductStatus(inv.getProductStatus());
							response.setProductQuantity(inv.getQuantity());
							response.setProductPrice(inv.getActualPrice());
							response.setLocationId(inv.getLocationId());
							response.setSellerInvId(inv.getSellerInventoryId());
							response.setProductLocation(locMstr.getLocationName());
							response.setProductId(prdmstr.getProductId());
							response.setProductName(prdmstr.getProductName());
							response.setProductDescription(prdmstr.getProductDescription());
							response.setProductIcon(prdmstr.getProductIcon());
						}
						break;
					}
//					System.out.println(response);
					service.add(response);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		Page<ProductMasterResponse> productInCategory = new PageImpl<>(service);
		var tmp = new UserCategoryPage("", productInCategory);
		tmp.setCategory(cat.getCategoryName());
		return tmp;
//	       return (UserCategoryPage) service;
	}

	@SuppressWarnings("unlikely-arg-type")
	@GetMapping("/productSearch/{criteria}")
 	public UserCategoryPage getProductSearch(@PathVariable(value = "criteria") String criteria, Principal principal) {
		List<ProductMasterResponse> service = new ArrayList<ProductMasterResponse>();
		ProductMasterResponse response = null;
		String path = criteria.replaceAll("'", "''");
		User user = null;
		if (principal != null) {
			Optional<User> opt = userService.findOne(principal.getName());
			user = opt.get();
			LocationMaster locationMaster = locationMasterRepository.findByLocationName(user.getCity());
			String sql = "select * from (SELECT PM.PRODUCT_ID, PC.CATEGORY_ID, PM.PRODUCT_NAME, PM.PRODUCT_ICON, PM.PRODUCT_DESCRIPTION,PC.CATEGORY_NAME, SPI.PRODUCT_STATUS,"
					+ " SPI.ACTUAL_PRICE, SPI.QUANTITY FROM PRODUCT_MASTER PM, SELLER_PRODUCT_MASTER SPM, SELLER_PRODUCT_INVENTORY SPI,PRODUCT_CATEGORY PC, "
					+ "LOCATION_MASTER LM WHERE PM.PRODUCT_ID = SPM.PRODUCT_ID AND SPI.SELLER_PRODUCT_ID = SPM.SELLER_PRODUCT_ID AND PM.CATEGORY_ID = PC.CATEGORY_ID "
					+ "AND SPI.ACTUAL_PRICE = (SELECT MIN (ACTUAL_PRICE) FROM SELLER_PRODUCT_INVENTORY WHERE SELLER_PRODUCT_ID = (select SELLER_PRODUCT_ID from "
					+ "(select SELLER_PRODUCT_ID from SELLER_PRODUCT_MASTER where product_id = pm.product_id order by SELLER_COST_PRICE ) where rownum=1)) "
					+ "AND (UPPER(PRODUCT_NAME) LIKE '%"+ path.toUpperCase() +"%') UNION SELECT PM.PRODUCT_ID,PC.CATEGORY_ID, PM.PRODUCT_NAME, PM.PRODUCT_ICON, PM.PRODUCT_DESCRIPTION,"
							+ "PC.CATEGORY_NAME, SPI.PRODUCT_STATUS, SPI.ACTUAL_PRICE, SPI.QUANTITY FROM PRODUCT_MASTER PM, SELLER_PRODUCT_MASTER SPM, "
							+ "SELLER_PRODUCT_INVENTORY SPI,PRODUCT_CATEGORY PC, LOCATION_MASTER LM WHERE PM.PRODUCT_ID = SPM.PRODUCT_ID "
							+ "AND SPI.SELLER_PRODUCT_ID = SPM.SELLER_PRODUCT_ID AND PM.CATEGORY_ID = PC.CATEGORY_ID AND SPI.ACTUAL_PRICE = (SELECT MIN (ACTUAL_PRICE) "
							+ "FROM SELLER_PRODUCT_INVENTORY WHERE SELLER_PRODUCT_ID = (select SELLER_PRODUCT_ID from (select SELLER_PRODUCT_ID from "
							+ "SELLER_PRODUCT_MASTER where product_id = pm.product_id order by SELLER_COST_PRICE ) where rownum=1)) "
							+ "AND UPPER(CATEGORY_NAME) LIKE '%"+ path.toUpperCase() +"%')"; 	
			System.out.println(sql);
			SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
			while (rs.next()) {
				response = new ProductMasterResponse();
				response.setProductId(rs.getLong("PRODUCT_ID"));
//				response.setLocationId(rs.getLong("LOCATION_ID"));
				response.setCategoryId(rs.getLong("CATEGORY_ID"));
				response.setProductName(rs.getString("PRODUCT_NAME"));
				response.setProductIcon(rs.getString("PRODUCT_ICON"));
				response.setProductCategory(rs.getString("CATEGORY_NAME"));
				response.setProductDescription(rs.getString("PRODUCT_DESCRIPTION"));
				response.setProductStatus(rs.getString("PRODUCT_STATUS"));
				response.setProductPrice(rs.getLong("ACTUAL_PRICE"));
				response.setProductQuantity(rs.getLong("QUANTITY"));
//				response.setSellerInvId(rs.getLong("SELLER_INVENTORY_ID"));
//				response.setProductLocation(rs.getString("LOCATION_NAME"));	
				service.add(response);
			}
		}else {
		String sql = "select * from (SELECT PM.PRODUCT_ID, PC.CATEGORY_ID, PM.PRODUCT_NAME, PM.PRODUCT_ICON, PM.PRODUCT_DESCRIPTION,PC.CATEGORY_NAME, "
				+ "SPI.PRODUCT_STATUS, SPI.ACTUAL_PRICE, SPI.QUANTITY FROM PRODUCT_MASTER PM, SELLER_PRODUCT_MASTER SPM, SELLER_PRODUCT_INVENTORY SPI,PRODUCT_CATEGORY PC, "
				+ "LOCATION_MASTER LM WHERE PM.PRODUCT_ID = SPM.PRODUCT_ID AND SPI.SELLER_PRODUCT_ID = SPM.SELLER_PRODUCT_ID AND PM.CATEGORY_ID = PC.CATEGORY_ID AND "
				+ "SPI.ACTUAL_PRICE = (SELECT MIN (ACTUAL_PRICE) FROM SELLER_PRODUCT_INVENTORY WHERE SELLER_PRODUCT_ID = (select SELLER_PRODUCT_ID from (select "
				+ "SELLER_PRODUCT_ID from SELLER_PRODUCT_MASTER where product_id = pm.product_id order by SELLER_COST_PRICE ) where rownum=1)) AND (UPPER(PRODUCT_NAME)"
				+ " LIKE '%"+ path.toUpperCase() +"%') UNION SELECT PM.PRODUCT_ID,PC.CATEGORY_ID, PM.PRODUCT_NAME, PM.PRODUCT_ICON, PM.PRODUCT_DESCRIPTION,PC.CATEGORY_NAME, "
				+ "SPI.PRODUCT_STATUS, SPI.ACTUAL_PRICE, SPI.QUANTITY FROM PRODUCT_MASTER PM, SELLER_PRODUCT_MASTER SPM, SELLER_PRODUCT_INVENTORY SPI,PRODUCT_CATEGORY PC, "
				+ "LOCATION_MASTER LM WHERE PM.PRODUCT_ID = SPM.PRODUCT_ID AND SPI.SELLER_PRODUCT_ID = SPM.SELLER_PRODUCT_ID AND PM.CATEGORY_ID = PC.CATEGORY_ID AND "
				+ "SPI.ACTUAL_PRICE = (SELECT MIN (ACTUAL_PRICE) FROM SELLER_PRODUCT_INVENTORY WHERE SELLER_PRODUCT_ID = (select SELLER_PRODUCT_ID from "
				+ "(select SELLER_PRODUCT_ID from SELLER_PRODUCT_MASTER where product_id = pm.product_id order by SELLER_COST_PRICE ) where rownum=1)) "
				+ "AND UPPER(CATEGORY_NAME) LIKE '%"+ path.toUpperCase() +"%')"; 	
		System.out.println(sql);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
		while (rs.next()) {
			response = new ProductMasterResponse();
			response.setProductId(rs.getLong("PRODUCT_ID"));
//			response.setLocationId(rs.getLong("LOCATION_ID"));
			response.setCategoryId(rs.getLong("CATEGORY_ID"));
			response.setProductName(rs.getString("PRODUCT_NAME"));
			response.setProductIcon(rs.getString("PRODUCT_ICON"));
			response.setProductCategory(rs.getString("CATEGORY_NAME"));
			response.setProductDescription(rs.getString("PRODUCT_DESCRIPTION"));
			response.setProductStatus(rs.getString("PRODUCT_STATUS"));
			response.setProductPrice(rs.getLong("ACTUAL_PRICE"));
			response.setProductQuantity(rs.getLong("QUANTITY"));
//			response.setSellerInvId(rs.getLong("SELLER_INVENTORY_ID"));
//			response.setProductLocation(rs.getString("LOCATION_NAME"));
			
			service.add(response);
			}
		}
		Page<ProductMasterResponse> productInCategory = new PageImpl<>(service);
		var tmp = new UserCategoryPage("", productInCategory);
//		tmp.setCategory(cat.getCategoryName());
		return tmp;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@GetMapping("/product/{productId}")
	public ResponseEntity<ProductDetailResponse> getProductById(@PathVariable(value = "productId") Long productId,  Principal principal)
			throws ResourceNotFoundException {
		ProductDetailResponse productDetailResponse = new ProductDetailResponse();
		User user = null                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           ;
		if (principal != null) {
			Optional<User> opt = userService.findOne(principal.getName());
			user = opt.get();
			String sql = "select * from (SELECT PM.PRODUCT_ID, PM.CATEGORY_ID, PM.PRODUCT_NAME, PM.PRODUCT_ICON, PM.PRODUCT_DESCRIPTION,SPI.SELLER_INVENTORY_ID, PC.CATEGORY_NAME,\r\n" + 
					"SPI.PRODUCT_STATUS,SPI.ACTUAL_PRICE,SPI.QUANTITY,SPM.SELLER_ID, SPM.SELLER_PRODUCT_ID, LM.LOCATION_ID, LM.LOCATION_NAME\r\n" + 
					"FROM SELLER_PRODUCT_INVENTORY SPI, SELLER_PRODUCT_MASTER SPM, PRODUCT_MASTER PM, PRODUCT_CATEGORY PC, LOCATION_MASTER LM\r\n" + 
					"WHERE SPI.SELLER_PRODUCT_ID = SPM.SELLER_PRODUCT_ID\r\n" + 
					"AND PM.PRODUCT_ID = SPM.PRODUCT_ID\r\n" + 
					"AND PC.CATEGORY_ID = PM.CATEGORY_ID\r\n" + 
					"AND LM.LOCATION_ID = SPM.LOCATION_ID\r\n" + 
					"AND PM.PRODUCT_ID = "+productId+" AND LM.LOCATION_NAME = '"+ user.getCity()+"' ORDER BY ACTUAL_PRICE ASC) where rownum = 1";
			System.out.println(sql);
			SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
			while (rs.next()) {
				productDetailResponse.setCategoryId(rs.getLong("CATEGORY_ID"));
				productDetailResponse.setProductId(rs.getLong("PRODUCT_ID"));
				productDetailResponse.setProductName(rs.getString("PRODUCT_NAME"));
				productDetailResponse.setProductDescription(rs.getString("PRODUCT_DESCRIPTION"));
				productDetailResponse.setProductIcon(rs.getString("PRODUCT_ICON"));
				productDetailResponse.setProductCategory(rs.getString("CATEGORY_NAME"));
				productDetailResponse.setSellerProductId(rs.getLong("SELLER_PRODUCT_ID"));
				productDetailResponse.setSellerId(rs.getLong("SELLER_ID"));
				productDetailResponse.setProductPrice(rs.getLong("ACTUAL_PRICE"));
				productDetailResponse.setProductStatus(rs.getString("PRODUCT_STATUS"));
				productDetailResponse.setProductQuantity(rs.getLong("QUANTITY"));
				productDetailResponse.setSellerInventoryId(rs.getLong("SELLER_INVENTORY_ID"));
				
			}	
		}else {
			String sql = "select * from (SELECT PM.PRODUCT_ID, PM.CATEGORY_ID, PM.PRODUCT_NAME, PM.PRODUCT_ICON, "
					+ "PM.PRODUCT_DESCRIPTION,SPI.SELLER_INVENTORY_ID, PC.CATEGORY_NAME,SPI.PRODUCT_STATUS,SPI.ACTUAL_PRICE,"
					+ "SPI.QUANTITY, SPM.SELLER_ID, SPM.SELLER_PRODUCT_ID, LM.LOCATION_ID, LM.LOCATION_NAME "
					+ "FROM SELLER_PRODUCT_INVENTORY SPI, SELLER_PRODUCT_MASTER SPM, PRODUCT_MASTER PM, PRODUCT_CATEGORY PC, "
					+ "LOCATION_MASTER LM WHERE SPI.SELLER_PRODUCT_ID = SPM.SELLER_PRODUCT_ID AND PM.PRODUCT_ID = SPM.PRODUCT_ID"
					+ " AND PC.CATEGORY_ID = PM.CATEGORY_ID AND LM.LOCATION_ID = SPM.LOCATION_ID AND PM.PRODUCT_ID =  " +productId + "  ORDER BY ACTUAL_PRICE ASC) where rownum = 1";
			System.out.println(sql);
			SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
			while (rs.next()) {
				productDetailResponse.setCategoryId(rs.getLong("CATEGORY_ID"));
				productDetailResponse.setProductId(rs.getLong("PRODUCT_ID"));
				productDetailResponse.setProductName(rs.getString("PRODUCT_NAME"));
				productDetailResponse.setProductDescription(rs.getString("PRODUCT_DESCRIPTION"));
				productDetailResponse.setProductIcon(rs.getString("PRODUCT_ICON"));
				productDetailResponse.setProductCategory(rs.getString("CATEGORY_NAME"));
				productDetailResponse.setSellerProductId(rs.getLong("SELLER_PRODUCT_ID"));
				productDetailResponse.setSellerId(rs.getLong("SELLER_ID"));
				productDetailResponse.setProductPrice(rs.getLong("ACTUAL_PRICE"));
				productDetailResponse.setProductStatus(rs.getString("PRODUCT_STATUS"));
				productDetailResponse.setProductQuantity(rs.getLong("QUANTITY"));
				productDetailResponse.setSellerInventoryId(rs.getLong("SELLER_INVENTORY_ID"));
				
			}
			
		}
		
		return ResponseEntity.ok().body(productDetailResponse);
	}

	@SuppressWarnings("unlikely-arg-type")
	@GetMapping("/update/product/{productId}")
	public ResponseEntity<ProductDetailResponse> updateProduct(@PathVariable(value = "productId") Long productId,  Principal principal)
			throws ResourceNotFoundException {
		ProductDetailResponse productDetailResponse = new ProductDetailResponse();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ;
			Optional<ProductMaster> pm = productMasterService.findByProductId(productId);
			ProductMaster productMaster = pm.get();
			productDetailResponse.setCategoryId(productMaster.getCategoryId());
			productDetailResponse.setProductId(productMaster.getProductId());
			productDetailResponse.setProductDescription(productMaster.getProductDescription());
			productDetailResponse.setProductIcon(productMaster.getProductIcon());
			productDetailResponse.setProductName(productMaster.getProductName());
			ProductCategory cat = productCategoryService.findByCategoryId(productMaster.getCategoryId());
			productDetailResponse.setProductCategory(cat.getCategoryName());
			List<SellerProductMaster> sellerProductMaster = sellerProductMasterRepository.findByProductId(productMaster.getProductId());
			productDetailResponse.setSellerProductMaster(sellerProductMaster);
			Iterator<SellerProductMaster> itr = sellerProductMaster.listIterator();
			while (itr.hasNext()) {
				SellerProductMaster sellerProdcttMster = itr.next();
				sellerProdcttMster.getSellerId();
				productDetailResponse.setSellerProductId(sellerProdcttMster.getSellerProductId());
				List<SellerProductInventory> sellerProductInventory = sellerProductInventoryMasterRepository.findBySellerProductIdOrderByActualPriceAsc(sellerProdcttMster.getSellerProductId());
				productDetailResponse.setSellerProductInventory(sellerProductInventory);
				for (SellerProductInventory prdInv : sellerProductInventory) {
					productDetailResponse.setSellerInventoryId(prdInv.getSellerInventoryId());
					SellerProductMaster mstr = sellerProductMasterRepository.findBySellerProductId(prdInv.getSellerProductId());
					productDetailResponse.setSellerId(mstr.getSellerId());
					productDetailResponse.setProductPrice(prdInv.getActualPrice());
					productDetailResponse.setProductStatus(prdInv.getProductStatus());
					productDetailResponse.setProductQuantity(prdInv.getQuantity());
					break;
				}
				break;
			}
		
		return ResponseEntity.ok().body(productDetailResponse);
	}

	
	@GetMapping("/productList/{productId}")
	public ResponseEntity<List<SellerProductResponse>> getAllProductsBySellers(
			@PathVariable(value = "productId") Long productId, Principal principal) throws ResourceNotFoundException {
		List<SellerProductResponse> sellerProductResponse = new ArrayList<SellerProductResponse>();
		SellerProductResponse resp = null;
		if (principal != null) {
			Optional<User> opt = userService.findOne(principal.getName());
			User user = opt.get();
			Optional<ProductMaster> pm = productMasterService.findByProductId(productId);
			ProductMaster productMaster = pm.get();
			List<SellerProductMaster> sellerProductMaster = sellerProductMasterRepository.findByProductId(productMaster.getProductId());
			Iterator<SellerProductMaster> itr = sellerProductMaster.listIterator();
			while (itr.hasNext()) {
				SellerProductMaster sellerProdcttMster = itr.next();
				Optional<SellerMaster> sellerMaster = sellerMasterRepository.findBySellerId(sellerProdcttMster.getSellerId());
				SellerMaster mstr = sellerMaster.get();
				LocationMaster locMastr = locationMasterRepository.findByLocationName(user.getCity());
				if (user.getCity().equals(locMastr.getCity())) {
					SellerProductInventory sellerProductInventory = sellerProductInventoryMasterRepository.findBySellerProductIdAndLocationId(sellerProdcttMster.getSellerProductId(),
									locMastr.getLocationId());
					if (sellerProductInventory != null) {
						resp = new SellerProductResponse();
						LocationMaster sellerLocation = locationMasterRepository.findByLocationId(sellerProductInventory.getLocationId());
						resp.setLocationId(sellerLocation.getLocationId());
						resp.setLocationName(sellerLocation.getLocationName());
						resp.setSellerInvId(sellerProductInventory.getSellerInventoryId());
						resp.setProductPrice(sellerProductInventory.getActualPrice());
						resp.setProductQuantity(sellerProductInventory.getQuantity());
						resp.setSellerName(mstr.getSellerName());
						sellerProductResponse.add(resp);
					}

				}

			}
		} else {
			Optional<ProductMaster> pm = productMasterService.findByProductId(productId);
			ProductMaster productMaster = pm.get();
			List<SellerProductMaster> sellerProductMaster = sellerProductMasterRepository.findTop5ByProductId(productMaster.getProductId());
			Iterator<SellerProductMaster> itr = sellerProductMaster.listIterator();
			Long l = 1L;
			while (itr.hasNext()) {
				SellerProductMaster sellerProdcttMster = itr.next();
				Optional<SellerMaster> sellerMaster = sellerMasterRepository.findBySellerId(sellerProdcttMster.getSellerId());
				SellerMaster mstr = sellerMaster.get();
				List<SellerProductInventory> sellerProductInventory = sellerProductInventoryMasterRepository
						.findBySellerProductIdAndLocationIdOrderByActualPriceAsc(sellerProdcttMster.getSellerProductId(), l);
				for (SellerProductInventory sellerInv : sellerProductInventory) {
					LocationMaster sellerLocation = locationMasterRepository
							.findByLocationId(sellerInv.getLocationId());
					if (sellerInv != null) {
						resp = new SellerProductResponse();
						resp.setLocationId(sellerLocation.getLocationId());
						resp.setLocationName(sellerLocation.getLocationName());
						resp.setSellerInvId(sellerInv.getSellerInventoryId());
						resp.setProductPrice(sellerInv.getActualPrice());
						resp.setProductQuantity(sellerInv.getQuantity());
						resp.setSellerName(mstr.getSellerName());
						sellerProductResponse.add(resp);
					}
				}

			}
		}
		return ResponseEntity.ok().body(sellerProductResponse);
	}

}
