package com.seller.quickbuy.QuickBuyApp.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seller.quickbuy.QuickBuyApp.entity.CartItem;
import com.seller.quickbuy.QuickBuyApp.entity.SellerProductInventory;
import com.seller.quickbuy.QuickBuyApp.entity.User;
import com.seller.quickbuy.QuickBuyApp.entity.UserCart;
import com.seller.quickbuy.QuickBuyApp.exception.RecordNotFoundException;
import com.seller.quickbuy.QuickBuyApp.message.response.ResponseMessage;
import com.seller.quickbuy.QuickBuyApp.repository.CartItemRepository;
import com.seller.quickbuy.QuickBuyApp.repository.CartRepository;
import com.seller.quickbuy.QuickBuyApp.repository.LocationMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.ProductMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.SellerProductInventoryMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.SellerProductMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.UserRepository;
import com.seller.quickbuy.QuickBuyApp.service.CartService;
import com.seller.quickbuy.QuickBuyApp.service.InvoiceService;
import com.seller.quickbuy.QuickBuyApp.service.UserService;
import com.seller.quickbuy.QuickBuyApp.serviceImpl.PriceService;
import com.seller.quickbuy.QuickBuyApp.utils.QuickBuyConstants;

import net.sf.jasperreports.engine.JRException;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/api/cart")
public class CartController {
	private static Logger logger = LogManager.getLogger(CartController.class);
//    @Autowired
//    CartService cartService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	UserService userService;
	@Autowired
	CartItemRepository cartItemRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	SellerProductInventoryMasterRepository sellerProductInventoryRepository;
	@Autowired
	SellerProductMasterRepository sellerProductMasterRepository;
	@Autowired
	LocationMasterRepository locationMasterRepository;
	@Autowired
	ProductMasterRepository productMasterRepository;
	@Autowired
	InvoiceService invoiceService;
	@Autowired
	private PriceService priceService;
	@Autowired
	private CartRepository cartRepository;

	private final CartService cartService;

	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@RequestMapping(value = "/addList", method = RequestMethod.POST)
	public ResponseEntity addToCartLocal(@RequestBody String payload, Principal principal) throws IOException {
		logger.info("In addToCart()" + QuickBuyConstants.LOG_SEPRATOR_WITH_START );
		UserCart cart = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode actualObj = mapper.readTree(payload);
			if (actualObj.isArray()) {
				for (JsonNode jsonNode : actualObj) {
					JsonNode jsonNode1 = jsonNode.get("productId");
					JsonNode jsonNode2 = jsonNode.get("productPrice");
					JsonNode jsonNode3 = jsonNode.get("sellerInventoryId");
					JsonNode jsonNode4 = jsonNode.get("quantity");
					System.out.println(jsonNode1);
					Long requestProductId = jsonNode1.longValue();
					Long sellerInvId = jsonNode3.longValue();
					Long quantity = jsonNode4.longValue();
					System.out.println(sellerInvId);
					Long requestProductAmount = jsonNode2.longValue();
					cart = cartService.addToCart(principal, requestProductId, requestProductAmount, sellerInvId, quantity);
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		logger.info("In addToCart()" + QuickBuyConstants.LOG_SEPRATOR_WITH_END  );
		return new ResponseEntity<UserCart>(cart, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity addToCart(@RequestBody String payload, Principal principal) throws IOException {
		logger.info("In addToCart()" + QuickBuyConstants.LOG_SEPRATOR_WITH_START );
		UserCart cart = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode actualObj = mapper.readTree(payload);
				JsonNode jsonNode1 = actualObj.get("productId");
				JsonNode jsonNode2 = actualObj.get("amount");
				JsonNode jsonNode3 = actualObj.get("sellerInventoryId");
				JsonNode jsonNode4 = actualObj.get("quantity");
				System.out.println(jsonNode1);
				Long requestProductId = jsonNode1.longValue();
				Long sellerInvId = jsonNode3.longValue();
				Long quantity = jsonNode4.longValue();
				System.out.println(sellerInvId);
				Long requestProductAmount = jsonNode2.longValue();
				cart = cartService.addToCart(principal, requestProductId, requestProductAmount, sellerInvId, quantity);
				logger.info("In addToCart() cart saved successfully"  + QuickBuyConstants.LOG_SEPRATOR_WITH_SAVED  );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		logger.info("In addToCart()" + QuickBuyConstants.LOG_SEPRATOR_WITH_END  );
		return new ResponseEntity<UserCart>(cart, HttpStatus.OK);
	}

	@RequestMapping(value = "/sellerPdctInfo/{sellerInventoryId}", method = RequestMethod.GET)
	public ResponseEntity getInventoryDetails(@PathVariable("sellerInventoryId") Long sellerInventoryId) {
		logger.info("In getInventoryDetails()" + QuickBuyConstants.LOG_SEPRATOR_WITH_START );
		SellerProductInventory sellerPrctInv = sellerProductInventoryRepository
				.findBySellerInventoryId(sellerInventoryId);
		logger.info("In getInventoryDetails()" + QuickBuyConstants.LOG_SEPRATOR_WITH_END );
		return new ResponseEntity<SellerProductInventory>(sellerPrctInv, HttpStatus.OK);
	}

	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public ResponseEntity fetchCart(Principal principal) {
		if (principal == null || principal.getName() == null) {
			throw new IllegalArgumentException("Invalid access");
		}
		UserCart cart = cartService.fetchCart(principal);
		return new ResponseEntity<UserCart>(cart, HttpStatus.OK);
	}

	@RequestMapping(value = "/cart/{id}", method = RequestMethod.DELETE)
	public ResponseEntity removeFromCart(@PathVariable("id") Long id, Principal principal) {
		UserCart cart = cartService.removeFromCart(principal, id);
		return new ResponseEntity<UserCart>(cart, HttpStatus.OK);
	}

	@RequestMapping(value = "/cart/confirm", method = RequestMethod.POST)
	public ResponseEntity confirmCart(@Valid @RequestBody UserCart cart, BindingResult bindingResult,
			Principal principal) {
		System.out.println("RequestBody -> " + cart.toString());
		if (bindingResult.hasErrors()) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		Boolean result = cartService.confirmCart(principal, cart);
		return result ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/cart", method = RequestMethod.DELETE)
	public ResponseEntity emptyCart(Principal principal) {
		cartService.emptyCart(principal);
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(value = "/orderConfirm/{userId}", method = RequestMethod.POST)
	public ResponseEntity callOrderProcedure(@PathVariable(value = "userId") Long userId, Principal principal)
			throws JRException, FileNotFoundException {
		Optional<User> opt = userService.findOne(principal.getName());
		User user = opt.get();
		// boolean test = cartService.callOrderPackage(user.getUserId());
		Long orderId = cartService.callOrderPackage(user.getUserId());
		System.out.println(orderId);
//		boolean result = false;
		if (orderId != null) {
			invoiceService.getInvoice(orderId, user);
//			result = true;
		}

		return new ResponseEntity(orderId, HttpStatus.OK);
	}

	@RequestMapping(value = "/cart/{productId}", method = RequestMethod.PUT)
	public ResponseEntity modifyItem(@PathVariable("productId") String productId, @RequestBody String payload,
			Principal principal) {
		ObjectMapper mapper = new ObjectMapper();
		Optional<User> opt = userService.findOne(principal.getName());
		User user = opt.get();
		UserCart cart = user.getUserCart();
		List<CartItem> cartItemList = cart.getCartItemList();
		try {
			JsonNode actualObj = mapper.readTree(payload);
			JsonNode jsonNode1 = actualObj.get("quantity");
			JsonNode jsonNode2 = actualObj.get("sellerInventoryId");
			Long quantity = jsonNode1.longValue();
			Long sellerInvId = jsonNode2.longValue();
			for (CartItem item : cartItemList) {
				if((item.getCartProduct().getId().toString()).equals(productId) && item.getSellerInvId().equals(sellerInvId) ) {
				    item.setItemQuantity(quantity);
					UserCart returnCart = priceService.calculatePrice(cart);
					cartRepository.save(returnCart);
					cartItemRepository.save(item);
					break;
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		try {
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(value = "/remove/{param}", method = RequestMethod.DELETE)
	public ResponseEntity removeCartItem(@PathVariable String  param, Principal principal)
			throws RecordNotFoundException {
		
		Map<String, String> map = new HashMap<String, String>();
		 String[] entries = param.split("&");
		 Long productId = null;
		 Long sellerInvId = null;
		 for (String entry : entries) {
			  String[] keyValue = entry.split("=");
			  String key   = keyValue[0];
			  String value = keyValue[1];
			  if(key.equals("productId")) {
				  productId = Long.parseLong(value);
			       System.out.println("Product Id : " + productId);
			   }else if (key.equals("sellerInvId")) {
				   sellerInvId = Long.parseLong(value);
				   System.out.println("Seller Inventory Id : " + sellerInvId);
			   }
		}  
		Optional<User> opt = userService.findOne(principal.getName());
		User user = opt.get();
		UserCart cart = user.getUserCart();
		List<CartItem> list = cart.getCartItemList();
		CartItem items = null;
		
		String sql = null;
		try {
			for (CartItem item : list) {
				if((item.getCartProduct().getId().toString()).equals(productId.toString()) && item.getCartItemStatus().equals("P") && item.getSellerInvId().equals(sellerInvId)) {
					sql = "DELETE from Cart_Item where Cart_Item_Id = " + item.getId()  + " and item_status = 'P' "  + " and product_Id = " + productId + " and seller_inv_id = " + sellerInvId +"";
					System.out.println(sql);
					int count = jdbcTemplate.update(sql);
					System.out.println(count);
					if (count == 1) {
						sql = "UPDATE USER_CART SET TOTAL_PRICE = (Select sum(PRODUCT_PRICE * ITEM_QUANTITY) from cart_item where cart_id = "+ cart.getCartId() +"and ITEM_STATUS='P') , "
								+ "TOTAL_QUANTITY = ( SELECT SUM (ITEM_QUANTITY) from cart_item where cart_id = "+ cart.getCartId() +" and ITEM_STATUS='P') WHERE cart_id = "+ cart.getCartId();
						int count1 = jdbcTemplate.update(sql);
						if(count1 == 1) {
							UserCart returnCart = priceService.calculatePrice(cart);
							cartRepository.save(returnCart);
						}	
					}
					break;
				}
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity(HttpStatus.OK);
	}
	

	@GetMapping("/getCount")
	public ResponseEntity<Integer> getCartCount(Principal principal)
	{
		Integer count = cartService.getCartCount(principal);
		if(count != null)
		{
			return new ResponseEntity<>(count,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(count,HttpStatus.BAD_REQUEST);
		}
	}
	
////    @Autowired
////    ProductService productService;
//   
////    @Autowired
////    ProductInOrderService productInOrderService;
////    @Autowired
////    ProductInOrderRepository productInOrderRepository;
//
//    @PostMapping("")
//    public ResponseEntity<UserCart> mergeCart(@RequestBody Collection<OrderLines> productInOrders, Principal principal) {
//    	Optional<User> opt=userRepository.findByUsername(principal.getName());
//    	User user =opt.get();
//        try {
//            cartService.mergeLocalCart(productInOrders, user);
//        } catch (Exception e) {
//            ResponseEntity.badRequest().body("Merge Cart Failed");
//        }
//        return ResponseEntity.ok(cartService.getCart(user));
//    }
//  
//    @GetMapping("")
//    public UserCart getCart(Principal principal) {
//    	System.out.println(principal.getName());
////    	User user = userService.findOne(principal.getName());
//    	Optional< User> opt = userService.findOne(principal.getName());
////    	Optional<User> opt=userRepository.findByUsername(principal.getName());
//    	List<ProductMaster>  prdReponse =new ArrayList<ProductMaster>();
//    	User user =opt.get();
//    	user.getUserId();
//    	UserCart cart =cartService.getCart(user);
//    	Set<OrderLines>  lines =cart.getOrderItems();
//    	for(OrderLines line: lines) {
//    		var sellerProductInfo = sellerProductInventoryRepository.findBySellerInventoryId(line.getSellerInventoryId());
//    		SellerProductMaster spm = sellerProductMasterRepository.findBySellerProductId(sellerProductInfo.getSellerProductId());
//    		
//    		ProductMaster pstr= productMasterRepository.findByProductId(spm.getProductId());
//    		pstr.getProductIcon();
//    		pstr.getProductName();
//    		pstr.getCategoryId();
//    		prdReponse.add(pstr);
//    		
//    	}
//    	cart.setProductMaster(prdReponse);
//        return cart;
//    }
//
//
//    @PostMapping("/add")
//    public boolean addToCart(@RequestBody ItemForm form, Principal principal) {
//    	System.out.println(form);
//    	System.out.println(principal.getName());
//    	Optional<User> opt=userRepository.findByUsername(principal.getName());
//    	User user =opt.get();
////    	User user = userService.findOne(principal.getName());
//    	String city = user.getCity();
////    	var productInfo = pro.findOne(form.getProductId());
//    	var sellerProductInfo = sellerProductInventoryRepository.findBySellerInventoryId(form.getSellerInventoryId());
//    	LocationMaster locMaster = locationMasterRepository.findByLocationId(sellerProductInfo.getLocationId());
//    	if(locMaster.getLocationName().equalsIgnoreCase(city)) {
//    		if(form.getQuantity() <=  sellerProductInfo.getQuantity()) {
//    			try {
//    				// save to db
//    				mergeCart(Collections.singleton( new OrderLines(sellerProductInfo, form.getQuantity())), principal);
//    				//mergeCart(Collections.singleton(new ProductInOrder(productInfo, form.getQuantity())), principal);
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//    			
//    			
//    		}else {
//    			return false;
//    		}
//    	}else {
////    		locMaster = locationMasterRepository.findByLocationName(city);
////    		
////    		locMaster.getLocationId();
////    		sellerProductInfo.getSellerProductId();
////    		SellerProductMaster sellerProduct=sellerProductMasterRepository.findByProductId(form.getProductId());
//    		return false;
//    	
//		}
//
//        try {
////            mergeCart(Collections.singleton(new OrderLines(productInfo, form.getQuantity())), principal);
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
//    }
//
////    @PutMapping("/{itemId}")
////    public ProductInOrder modifyItem(@PathVariable("itemId") String itemId, @RequestBody Integer quantity, Principal principal) {
////        User user = userService.findOne(principal.getName());
////         productInOrderService.update(itemId, quantity, user);
////        return productInOrderService.findOne(itemId, user);
////    }
////
////    @DeleteMapping("/{itemId}")
////    public void deleteItem(@PathVariable("itemId") String itemId, Principal principal) {
////        User user = userService.findOne(principal.getName());
////         cartService.delete(itemId, user);
////         // flush memory into DB
////    }
////
////
////    @PostMapping("/checkout")
////    public ResponseEntity checkout(Principal principal) throws SQLException, FileNotFoundException, JRException {
////    	User user = userService.findOne(principal.getName());// Email as username
////    	System.out.println(user);
////        //cartService.checkout(user);
////        Long orderId = cartService.getOrderId(user);
////        System.out.println(orderId+" In Controller");
////        //String path = reportService.getInvoice();
////       reportService.getInvoice(orderId, user);
////        return ResponseEntity.ok(null);
////    }
////
//////    @RequestMapping(value = "/getDetails", method = RequestMethod.GET)
//////	@ResponseBody
//////	public ProductInOrder findStudentsUsingNativeQuery(Principal principal) {
//////    	User user = userService.findOne(principal.getName());
//////    	
//////    	System.out.println(user);
//////    	ProductInOrder orderResponse = cartService.findLatestOrders();
//////    	
//////		return orderResponse;
//////	}
////
//////    @RequestMapping(value = "/getDetails", method = RequestMethod.GET)
//////   	@ResponseBody
//////   	public ResponseEntity<OrderConfirmResponse> getOrderConfirmDetails(Principal principal) {
//////       	User user = userService.findOne(principal.getName());
//////       	OrderConfirmResponse orderResponse= new OrderConfirmResponse();
//////       	
//////       	try {
//////			logger.info("orderShipmentDetails()    --------    Start");
//////			orderResponse = cartService.findLatestOrder();
//////			System.out.println("--------------Order Response --------------" + orderResponse);
//////			logger.info("orderShipmentDetails()    --------    End");
//////		} catch (Exception e) {
//////			logger.error("Exception Occured :: orderShipmentDetails()", e);
//////		}
//////		return new ResponseEntity<OrderConfirmResponse>(orderResponse, HttpStatus.ACCEPTED);
//////
//////   	}
//////    
//////    @RequestMapping(value = "/getDetails1/{orderId}", method = RequestMethod.GET)
////// 	public ResponseEntity<ProductInOrder> getOrderConfirm(@PathVariable(value="orderId") Long orderId) {
//////    	List<ProductInOrder> order = null;
//////    	order= productInOrderRepository.findAllById(orderId);
//////    	System.out.println(order);
//////		return null;
//////    	
//////    }

}
