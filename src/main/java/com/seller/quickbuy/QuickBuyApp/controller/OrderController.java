package com.seller.quickbuy.QuickBuyApp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seller.quickbuy.QuickBuyApp.entity.OrderHeader;
import com.seller.quickbuy.QuickBuyApp.entity.OrderLines;
import com.seller.quickbuy.QuickBuyApp.entity.ProductMaster;
import com.seller.quickbuy.QuickBuyApp.entity.SellerProductInventory;
import com.seller.quickbuy.QuickBuyApp.entity.SellerProductMaster;
import com.seller.quickbuy.QuickBuyApp.entity.User;
import com.seller.quickbuy.QuickBuyApp.repository.OrderHeaderRepository;
import com.seller.quickbuy.QuickBuyApp.repository.OrderRepository;
import com.seller.quickbuy.QuickBuyApp.repository.ProductMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.SellerProductInventoryMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.SellerProductMasterRepository;
import com.seller.quickbuy.QuickBuyApp.response.OrderConfirmResponse;
import com.seller.quickbuy.QuickBuyApp.response.OrderDetailsResponse;
import com.seller.quickbuy.QuickBuyApp.service.OrderService;
import com.seller.quickbuy.QuickBuyApp.service.UserService;
import com.seller.quickbuy.QuickBuyApp.utils.QuickBuyConstants;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class OrderController {
	private static final Logger logger = LogManager.getLogger(OrderController.class);
	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;
	@Autowired
	private SellerProductInventoryMasterRepository sellerInvRepository;
	@Autowired
	private SellerProductMasterRepository sellerProductMasterRepo;
	@Autowired
	private ProductMasterRepository productMasterRepository;
	@Autowired
	private OrderRepository orderRepository;

//	 @PostMapping(consumes = "application/json", produces = "application/json", path = "/createOrder")
//	    public ResponseEntity<OrderHeader> createOrder(@RequestBody OrderHeader order) {
//		 OrderHeader orderZHeader = orderService.createOrder(order);
//	    	if(orderHeader  !=null) {
//	    		return new ResponseEntity<>(orderHeader, HttpStatus.CREATED);
//	    	}
//	    	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//	        
//	    }
//
//	    @SuppressWarnings({ "unchecked", "rawtypes" })
//		@PutMapping(consumes = "application/json", produces = "application/json", path = "/update/{headerId}")
//	    public ResponseEntity<OrderHeader> updateOrder(@RequestBody OrderHeader order,@PathVariable ("headerId") Long headerId) {
//	       
//	        if(headerId != order.getHeaderId())
//	        {
//	            return new ResponseEntity(new ResponseMessage("Fail -> Id not Matched!"),HttpStatus.BAD_REQUEST);
//	        }
//	        orderService.updateOrder(order);
//	        return new ResponseEntity(new ResponseMessage("Done"),HttpStatus.OK);
//	    }
//	 
//	    @DeleteMapping(produces = "application/json", consumes = "application/json", path = "/{headerId}")
//	    public ResponseEntity<String> deleteOrder(@PathVariable(value = "headerId") Long headerId) {
//	    	orderService.deleteOrder(headerId);
//	        return new ResponseEntity<>("Order with HeaderId : " + headerId + " deleted successfully", HttpStatus.OK);
//	    }
//	 
//	    public ResponseEntity<OrderHeader> getSellerContract(@PathVariable(value = "headerId") Long headerId) {
//	        return new ResponseEntity<>(orderService.getOrder(headerId), HttpStatus.OK);
//	    }
//	 
//	    @GetMapping(produces = "application/json")
//	    public ResponseEntity<List<OrderHeader>> getAllSeller() {
//	        return new ResponseEntity<>(orderService.getAllOrder(), HttpStatus.OK);
//	    }

//	@GetMapping("/Order/{orderStatus}")
//	public ResponseEntity<OrderHeader> getOrderById(@PathVariable(value = "orderStatus") String orderStatus, Principal principal) {
//		Optional<User> opt = userService.findOne(principal.getName());
//		User user = opt.get();
//		OrderHeader orderHeader = orderService.findByOrderStatus(orderStatus, user);
//		return ResponseEntity.ok().body(orderHeader);
//	}
//	

	@GetMapping("/order/orderSummary/{orderNumber}")
	public ResponseEntity<OrderConfirmResponse> getOrderByStatus(@PathVariable("orderNumber") Long orderNumber,
			Principal principal) {
		logger.info("In getOrderByStatus()" + QuickBuyConstants.LOG_SEPRATOR_WITH_START);
		Optional<User> opt = userService.findOne(principal.getName());
		User user = opt.get();
		OrderConfirmResponse response = new OrderConfirmResponse();
		List<ProductMaster> prdList = new ArrayList<>();
		List<OrderDetailsResponse> orderDetailResp = new ArrayList<>();
		try {
			OrderHeader orderHeader = orderRepository.findByOrderNumber(orderNumber);
            //OrderHeader orderHeader = orderService.findByLatestOrder(user);
			response.setOrderHeader(orderHeader);
			List<OrderLines> orderLines = orderHeader.getOrderDetailsList();
			for (OrderLines lines : orderLines) {
				SellerProductInventory sellerPrctInv = sellerInvRepository
						.findBySellerInventoryId(lines.getSellerInventoryId());
				SellerProductMaster sellerMaster = sellerProductMasterRepo
						.findBySellerProductId(sellerPrctInv.getSellerProductId());
				Optional<?> prctMaster = productMasterRepository.findByProductId(sellerMaster.getProductId());
				//prctMaster.isPresent() ? (ProductMaster) prctMaster.get() : null;
				ProductMaster mstr = (ProductMaster) prctMaster.get();
				OrderDetailsResponse resp = new OrderDetailsResponse();
				resp.setProductName(mstr.getProductName());
				resp.setProductIcon(mstr.getProductIcon());
				resp.setProductDescription(mstr.getProductDescription());
				resp.setProductPrice(sellerPrctInv.getActualPrice());
				resp.setItemQuantity(lines.getOrderedQuantity());
				resp.setExpectedShipDate(lines.getScheduleShipDate());
				orderDetailResp.add(resp);
				mstr.setSellerInvId(lines.getSellerInventoryId());
				prdList.add(mstr);

				System.out.println(prctMaster);
				lines.getOrderedQuantity();

			}
			response.setTotal(orderHeader.getPaymentAmount());
			response.setOrderNumber(orderHeader.getOrderNumber());
			response.setProductMaster(prdList);
			response.setOrderDetails(orderDetailResp);
			//return ResponseEntity.ok().body(orderHeader);
		} catch (Exception e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("In getOrderByStatus()" + QuickBuyConstants.LOG_SEPRATOR_WITH_END);
		return ResponseEntity.ok().body(response);
	}
}
