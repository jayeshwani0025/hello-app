//package com.seller.quickbuy.QuickBuyApp.controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.seller.quickbuy.QuickBuyApp.entity.LocationMaster;
//import com.seller.quickbuy.QuickBuyApp.entity.ProductCategory;
//import com.seller.quickbuy.QuickBuyApp.entity.ProductMaster;
//import com.seller.quickbuy.QuickBuyApp.entity.SellerProductInventory;
//import com.seller.quickbuy.QuickBuyApp.entity.SellerProductMaster;
//import com.seller.quickbuy.QuickBuyApp.repository.LocationMasterRepository;
//import com.seller.quickbuy.QuickBuyApp.repository.ProductCategoryRepository;
//import com.seller.quickbuy.QuickBuyApp.repository.ProductMasterRepository;
//import com.seller.quickbuy.QuickBuyApp.repository.SellerProductInventoryMasterRepository;
//import com.seller.quickbuy.QuickBuyApp.repository.SellerProductMasterRepository;
//import com.seller.quickbuy.QuickBuyApp.response.ProductMasterResponse;
//
////@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/user")
//public class ProductMasterResponseController {
//
//	@Autowired
//	ProductMasterRepository productMasterRepository;
//	@Autowired
//	SellerProductMasterRepository sellerProductMasterRepository;
//	@Autowired
//	SellerProductInventoryMasterRepository sellerProductInventoryMasterRepository;
//	@Autowired
//	private LocationMasterRepository locationMasterRepository;
//	@Autowired
//	ProductCategoryRepository productCategoryRepository;
//	
//	@GetMapping("/product")
//	public List<ProductMasterResponse> findAll()
//	{
//       List<ProductMasterResponse> service = new ArrayList<ProductMasterResponse>();
//		ProductMasterResponse response = null;
//		List<ProductMaster> prdMstr = null;
//		List<SellerProductMaster> prdSellrMstr = null;
//		List<SellerProductInventory> sellerPrdsInvs = null;
//		try {
//			 prdMstr = productMasterRepository.findAll();
//			for (ProductMaster prdmstr : prdMstr) {
//				response = new ProductMasterResponse();
//				Long prdId =	prdmstr.getProductId();
//				prdSellrMstr = sellerProductMasterRepository.findByProductId(prdId);
//				prdmstr.getProductId();
//				System.out.println(prdmstr.getProductId());
//				response.setProductId(prdmstr.getProductId());
//				response.setProductName(prdmstr.getProductName());
//				response.setProductDescription(prdmstr.getProductDescription());
//				response.setProductIcon(prdmstr.getProductIcon());
//				ProductCategory category= productCategoryRepository.findByCategoryId(prdmstr.getCategoryId());
//				response.setProductCategory(category.getCategoryName());
//			
//					for(SellerProductMaster smdtls :  prdSellrMstr) {
//						smdtls.getSellerId();
//						Long sellerPrdId = smdtls.getSellerProductId();
//						sellerPrdsInvs = sellerProductInventoryMasterRepository.findBySellerProductId(sellerPrdId);
//							for( SellerProductInventory inv: sellerPrdsInvs) {
//								response.setProductStatus(	inv.getProductStatus());
//								response.setProductQuantity(inv.getQuantity());
//								response.setProductPrice(inv.getActualPrice());
//								Long locationId = inv.getLocationId();
//								LocationMaster locMstr = locationMasterRepository.findByLocationId(locationId);
//								response.setLocationId(locationId);
//								response.setProductLocation(locMstr.getLocationName());
//							}
//						break;
//					}
////				System.out.println(response);
//				service.add(response);
//			} 
//		} catch (Exception e) {
//			
//		}
//		
//		return service;
//	}
//}
