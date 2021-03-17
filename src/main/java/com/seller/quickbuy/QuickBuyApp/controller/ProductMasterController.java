package com.seller.quickbuy.QuickBuyApp.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seller.quickbuy.QuickBuyApp.entity.ProductCategory;
import com.seller.quickbuy.QuickBuyApp.entity.ProductDisplay;
import com.seller.quickbuy.QuickBuyApp.entity.ProductMaster;
import com.seller.quickbuy.QuickBuyApp.entity.SellerProductInventory;
import com.seller.quickbuy.QuickBuyApp.entity.SellerProductMaster;
import com.seller.quickbuy.QuickBuyApp.exception.ResourceNotFoundException;
import com.seller.quickbuy.QuickBuyApp.repository.ProductCategoryRepository;
import com.seller.quickbuy.QuickBuyApp.repository.SellerProductInventoryMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.SellerProductMasterRepository;
import com.seller.quickbuy.QuickBuyApp.service.ProductCategoryService;
import com.seller.quickbuy.QuickBuyApp.service.ProductDisplayService;
import com.seller.quickbuy.QuickBuyApp.service.ProductMasterService;
import com.seller.quickbuy.QuickBuyApp.service.ProductService;

/**
 * 
 * @author jyoti.bhosale
 *
 */

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class ProductMasterController {

	private static final Logger logger = LogManager.getLogger(ProductMasterController.class);
	private static final String SINGLE_QUOTE= "'";
	private static final String DOUBLE_QUOTE= "''";
	private final ProductService productService;
	private final ProductDisplayService productDisplayService;
	private final ProductCategoryService productCategoryService;
	private final ProductMasterService productMasterService;
	@Autowired
	public ProductMasterController(ProductService productService, ProductDisplayService productDisplayService,
			ProductCategoryService productCategoryService, ProductMasterService productMasterService) {
		this.productService = productService;
		this.productDisplayService = productDisplayService;
		this.productCategoryService = productCategoryService;
		this.productMasterService= productMasterService;
	}

	@Autowired
	SellerProductMasterRepository sellerProductMasterRepository;
	@Autowired
	SellerProductInventoryMasterRepository sellerProductInventoryMasterRepository;
	@Autowired
	ProductCategoryRepository productCategoryRepository;

	/**
	 * 
	 * @param productMaster is product to be added into the master
	 * @param bindingResult if product is already exist in master
	 * @return ResponseEntity
	 */
//	@PostMapping("/new")
//	public ResponseEntity<?> create(@Valid @RequestBody ProductMaster productMaster ,BindingResult bindingResult) {
//		logger.info("In ProductMasterController create()--> START");
//		ProductMaster productExist = prodMasterService.findOne(productMaster.getProductName());
//		if(productExist != null)
//		{
//			bindingResult.rejectValue(productExist.getProductName(), "error.Product", "There is already a product with the name provided");
//		}
//		if(bindingResult.hasErrors())
//		{
//			return ResponseEntity.badRequest().body(bindingResult);
//		}
//		
//		logger.info("In ProductMasterController create()--> END");
//		return ResponseEntity.ok(prodMasterService.save(productMaster));
//	}
//
//	/**
//	 * 
//	 * @param productId of product to be deleted from master
//	 * @return ResponseEntity
//	 */
//	@DeleteMapping("/delete/{productId}")
//	public ResponseEntity<?> delete(@PathVariable ("productId") Long productId) {
//		logger.info("In ProductMasterController delete()--> START");
//		prodMasterService.delete(productId);
//		logger.info("In ProductMasterController delete()--> END");
//		return ResponseEntity.ok().build();
//	}
//	
//	
//	/**
//	 * 
//	 * @return all products from master
//	 */
//	@GetMapping("/product")
//	public List<ProductMaster> findAll()
//	{
//		List<ProductMaster> service = null;
//		logger.info("In ProductMasterController findAll()--> START");
//		service= prodMasterService.f();
//		logger.info("In ProductMasterController findAll()--> END");
//		return service;
//	}
//	
//	/**
//	 * 
//	 * @param categoryId to be search
//	 * @return all products of categoryId
//	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@GetMapping("/productByCategory/{categoryId}")
//	public ResponseEntity<List<ProductMaster>> getAllProducts(@PathVariable ("categoryId") Long categoryId)
//	{
//		logger.info("In ProductMasterController findAll()--> START");
//		List<ProductMaster> productList = prodMasterService.findByCategoryId(categoryId);
//		if(productList.isEmpty())
//		{
//			return new ResponseEntity(new ResponseMessage("No Records Found for given Category "+categoryId),HttpStatus.BAD_REQUEST);
//		}
//		logger.info("In ProductMasterController findAll()--> END");
//		return new ResponseEntity<List<ProductMaster>>(productList,HttpStatus.OK);
//	}
//	
//
//	@PutMapping("/edit/{productId}")
//	public ResponseEntity<?> edit(@PathVariable("productId") Long productId, @RequestBody ProductMaster product,
//            				BindingResult bindingResult) {
//		logger.info("In ProductMasterController edit()--> START");
//		if(bindingResult.hasErrors())
//		{
//			return ResponseEntity.badRequest().body(bindingResult);
//		}
//		if(productId != product.getProductId())
//		{
//			return ResponseEntity.badRequest().body("Id not Matched");
//		}
//		logger.info("In ProductMasterController edit()--> END");
//		return ResponseEntity.ok(prodMasterService.update(product));
//	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable(value = "productId") Long productId)
			throws ResourceNotFoundException {
		Optional productMaster = productMasterService.findByProductId(productId);
		productMaster.get();
		if(!productMaster.isPresent()) {
			System.out.println("Product Not present");
		}
//			.orElseThrow(() -> new ResourceNotFoundException(productId));
		
		return ResponseEntity.ok().body(productMaster);
	}
//
//	@RequestMapping(value = "/product", method = RequestMethod.GET)
//	public ResponseEntity getProductCategory(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
//			@RequestParam(value = "category", required = false) String[] category) throws IOException {
//		 
//		  String categoryName = null ;
//		  String categoryType = null ;
//
//		ProductCategory productCategory = productCategoryRepository.findByCategoryNameOrCategoryType(categoryName,categoryType);
//		PageRequest pageRequest;
//		pageRequest = PageRequest.of(page, size);
//
//		List returnList = productDisplayService.findAllByProductCategory(pageRequest, productCategory);
//		return new ResponseEntity<List>(returnList, HttpStatus.OK);
//	}

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestParam(value ="page",  required = false) Integer page, @RequestParam(value= "size",  required = false) Integer size,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "categoryType", required = false) String categoryType) throws IOException {
//		if (page == null || size == null) {
//			throw new IllegalArgumentException("Page and size parameters are required");
//		}
		PageRequest pageRequest;
		if (sort != null && !isBlank(sort)) {
			Sort sortRequest = getSort(sort);
			if (sortRequest == null) {
				throw new IllegalArgumentException("Invalid sort parameter");
			}
			pageRequest = PageRequest.of(page, size, sortRequest);
		} else {
			pageRequest = PageRequest.of(page, size);
		}

		if (category != null && !isBlank(category)) {

		     ProductCategory productCategory = null;
		     if(category != null && categoryType != null) {
//		    	 categoryType= categoryType.replaceAll(SINGLE_QUOTE,DOUBLE_QUOTE );
		    	 productCategory = productCategoryRepository.findByCategoryNameAndCategoryType(category, categoryType);
		    	 
		     }else {
		    	 productCategory = productCategoryService.findByCategoryName(category);
		     }
			System.out.println(productCategory);
			if (productCategory == null) {
				throw new IllegalArgumentException("Invalid category parameter");
			}
			List returnList = productDisplayService.findAllByProductCategory(pageRequest, productCategory);
			return new ResponseEntity<List>(returnList, HttpStatus.OK);
		}

		List returnList = productDisplayService.findAll(pageRequest);
		return new ResponseEntity<>(returnList, HttpStatus.OK);
	}

	@RequestMapping(value = "/product", method = RequestMethod.GET, params = "id")
	public ResponseEntity getFullById(@RequestParam("id") Long id) {
		ProductMaster product = productService.findByProductId(id);

		if (product == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		PageRequest pageRequest = null;
		List<SellerProductMaster> sm = sellerProductMasterRepository.findByProductId(product.getProductId());

		for (SellerProductMaster spm : sm) {
			Long sellerPrdId = spm.getSellerProductId();
			Page<SellerProductInventory> sellerPrdsInvs = sellerProductInventoryMasterRepository
					.findBySellerProductIdOrderByActualPriceAsc(sellerPrdId, pageRequest);
			for (SellerProductInventory sellerInv : sellerPrdsInvs) {
				product.setProductPrice(sellerInv.getActualPrice());
				product.setSellerInvId(sellerInv.getSellerInventoryId());
			}
			break;
		}
		return new ResponseEntity<ProductMaster>(product, HttpStatus.OK);
	}

	@RequestMapping(value = "/product/related", method = RequestMethod.GET, params = "id")
	public ResponseEntity getByRelated(@RequestParam("id") Long id) {
		ProductDisplay productDisplay = productDisplayService.findById(id);
		if (productDisplay == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List>(
				productDisplayService.getRelatedProducts(productDisplay.getProductCategory(), id), HttpStatus.OK);
	}

	@RequestMapping(value = "/product/recent", method = RequestMethod.GET)
	public ResponseEntity getByNewlyAdded() {
		List returnList = productDisplayService.findTop8ByOrderByDateCreatedDesc();
		return new ResponseEntity<List>(returnList, HttpStatus.OK);
	}

	@RequestMapping(value = "/product/mostselling", method = RequestMethod.GET)
	public ResponseEntity getByMostSelling() {
		List returnList = productDisplayService.findTop8ByOrderBySellCountDesc();
		return new ResponseEntity<List>(returnList, HttpStatus.OK);
	}

	// TODO rebuild the logic
	@RequestMapping(value = "/product/interested", method = RequestMethod.GET)
	public ResponseEntity getByInterested() {
		List returnList = productDisplayService.findTop8ByOrderBySellCountDesc();
		return new ResponseEntity<List>(returnList, HttpStatus.OK);
	}

	@RequestMapping(value = "/product/search", method = RequestMethod.GET, params = { "page", "size", "keyword" })
	public ResponseEntity searchProduct(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
			@RequestParam("keyword") String keyword) {
		List returnList = productDisplayService.searchProducts(keyword, page, size);
		return new ResponseEntity<List>(returnList, HttpStatus.OK);
	}

	private boolean isBlank(String param) {
		return param.isEmpty() || param.trim().equals("");
	}

	// A better way to do this is storing sorting options in the database
	// and sending those options to the client. Later then the client
	// sends the parameter based upon that.
	private Sort getSort(String sort) {
		switch (sort) {
		case "lowest":
			return Sort.by(Sort.Direction.ASC, "price");
		case "highest":
			return Sort.by(Sort.Direction.DESC, "price");
		case "name":
			return Sort.by(Sort.Direction.ASC, "name");
		default:
			return null;
		}
	}
}
