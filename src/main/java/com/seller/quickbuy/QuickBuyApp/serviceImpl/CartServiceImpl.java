package com.seller.quickbuy.QuickBuyApp.serviceImpl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seller.quickbuy.QuickBuyApp.entity.CartItem;
import com.seller.quickbuy.QuickBuyApp.entity.LocationMaster;
import com.seller.quickbuy.QuickBuyApp.entity.OrderLines;
import com.seller.quickbuy.QuickBuyApp.entity.ProductDisplay;
import com.seller.quickbuy.QuickBuyApp.entity.SellerContract;
import com.seller.quickbuy.QuickBuyApp.entity.SellerProductInventory;
import com.seller.quickbuy.QuickBuyApp.entity.SellerProductMaster;
import com.seller.quickbuy.QuickBuyApp.entity.User;
import com.seller.quickbuy.QuickBuyApp.entity.UserCart;
import com.seller.quickbuy.QuickBuyApp.repository.CartItemRepository;
import com.seller.quickbuy.QuickBuyApp.repository.CartRepository;
import com.seller.quickbuy.QuickBuyApp.repository.LocationMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.ProductDisplayRepository;
import com.seller.quickbuy.QuickBuyApp.repository.SellerContractRepository;
import com.seller.quickbuy.QuickBuyApp.repository.SellerProductInventoryMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.SellerProductMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.UserRepository;
import com.seller.quickbuy.QuickBuyApp.service.CartService;
import com.seller.quickbuy.QuickBuyApp.utils.QuickBuyConstants;

@Service
public class CartServiceImpl implements CartService {

	private static Logger logger = LogManager.getLogger(CartServiceImpl.class);
	
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final ProductDisplayRepository productDisplayRepository;
	private final PriceService priceService;
	private final UserRepository userRepository;

	@PersistenceContext
	private EntityManager entityManger;
	
	@Autowired
	private SellerProductInventoryMasterRepository sellerInvRepository;
	@Autowired
	private LocationMasterRepository locationRepository;
	@Autowired
	SellerProductMasterRepository sellerProductMasterRepository;
	@Autowired
	SellerContractRepository sellerContractRepository;

	@Autowired
	public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository,
			ProductDisplayRepository productDisplayRepository, PriceService priceService,
			UserRepository userRepository) {
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.productDisplayRepository = productDisplayRepository;
		this.priceService = priceService;
		this.userRepository = userRepository;
	}

//	@Override
//	public UserCart getCart(User user) {
//		 return user.getUserCart();
//	}

//	@Override
//	@Transactional
//	public void mergeLocalCart(Collection<OrderLines> productInOrders, User user) {
//		UserCart finalCart = user.getUserCart();
//		OrderHeader header = new OrderHeader();
//		 productInOrders.forEach(productInOrder -> {
//			 System.out.println(productInOrder);
//	         Set<OrderLines> set = finalCart.getOrderItems();
//	         System.out.println(set);
//            Optional<OrderLines> old = set.stream().filter(e -> e.getSellerInventoryId().equals(productInOrder.getSellerInventoryId())).findFirst();
//            OrderLines prod ;
//            if(old.isPresent()) {
//            	  prod = old.get();
//            	  prod.getSellerInventoryId();
//            	
//            }else {
//                prod = productInOrder;
//                prod.setUserCart(finalCart);
//              
////                header.setOrderStatus("New");
//                finalCart.getOrderItems().add(prod);
//              
//                
//            }
//            
////            header.setOrderLines(set);
////            header.setOrderTypeId((long) 1);
////            orderRepository.save( header);
////           OrderHeader headers =  orderRepository.findByHeaderId(header.getHeaderId());
////           prod.setHeaderId(headers.getHeaderId());
//         orderLineRepository.save(prod);
//            
//	        });
//		 cartRepository.save(finalCart);
//	}

//	@Override
//	@Transactional
//	public void mergeLocalCart(List<OrderLines> productInOrders, User user) {
//		UserCart finalCart = user.getUserCart();
//		OrderHeader header = new OrderHeader();
//		for(OrderLines lines: productInOrders) {
//			Set<OrderLines> set = finalCart.getOrderItems();
//			Optional<OrderLines> old = set.stream().filter(e -> e.getSellerInventoryId().equals(lines.getSellerInventoryId())).findFirst();
//			  OrderLines prod ;
//			  if (old.isPresent()) {
//	                prod = old.get();
//	                prod.setOrderedQuantity(lines.getOrderedQuantity() + prod.getOrderedQuantity());
//	            } else {
//	                prod = lines;
//	                prod.setUserCart(finalCart);
//	                finalCart.getOrderItems().add(prod);
//	                header.setOrderStatus("New");
//	            }
//	            orderLineRepository.save(prod);
//		}
//	}
	@Override
	public void delete(String itemId, User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkout(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public Long getOrderId(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderLines findLatestOrders() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public UserCart fetchCart(Principal principal) {
//		System.out.println("FETCH CART");
//        User user = getUserFromPrinciple(principal);
//        return user.getUserCart();
//	}
//	
//	
//	 private User getUserFromPrinciple(Principal principal) {
//	        if (principal == null || principal.getName() == null) {
//	            throw new IllegalArgumentException("Invalid access");
//	        }
//	        User user = userRepository.findByEmail(principal.getName());
//	        if (user == null) {
//	            throw new IllegalArgumentException("User not found");
//	        }
//	        return user;
//	    }

	@SuppressWarnings("unused")
	@Override
	public UserCart addToCart(Principal principal, Long id, Long amount, Long sellerInvId, Long quantity) {
		logger.info("In addToCart()" + QuickBuyConstants.LOG_SEPRATOR_WITH_START  );
		User user = getUserFromPrinciple(principal);
		if (amount <= 0 || id <= 0) {
			logger.info("In addToCart()" + QuickBuyConstants.LOG_SEPRATOR_WITH_INVALID + amount + id  );
			throw new IllegalArgumentException("Invalid parameters");
		}
//	        user.getCity();
//		Long sellInvId = Long.parseLong(sellerInvId);
		SellerProductInventory spi = sellerInvRepository.findBySellerInventoryId(sellerInvId);
		SellerProductMaster spm = sellerProductMasterRepository.findBySellerProductId(spi.getSellerProductId());
		SellerContract sc = sellerContractRepository.findByActiveContractAndSellerId("A", spm.getSellerId());
//		System.out.println(sc.getContractId());
		LocationMaster locMaster = locationRepository.findByLocationId(spi.getLocationId());
		UserCart cart = user.getUserCart();
		List<CartItem> cartItems = cart.getCartItemList();
		if (user.getCity().equalsIgnoreCase(locMaster.getCity())) {
			if (cart == null) {
				cart = new UserCart();
				cart.setUser(user);
			} else if (!cartItems.isEmpty()) {
				for (CartItem cartItem : cartItems) {
					if(cartItem.getCartItemStatus().equalsIgnoreCase("P") && cartItem.getSellerInvId().equals(sellerInvId)) {
						ProductDisplay display = cartItem.getCartProduct();
						if(display != null && display.getId().equals(id) ) {
							cartItem.setAmount(amount);
							cartItem.setLocationId(locMaster.getLocationId());
							cartItem.setItemQuantity(cartItem.getItemQuantity() + quantity);
							CartItem ct = cartItemRepository.save(cartItem);
							System.out.println(cartItem.getAvailableFlag());
		                    UserCart returnCart = priceService.calculatePrice(cart);
		                    cartRepository.save(returnCart);
		                    return returnCart;
						}
//						if (display.getId().equals(id)) {
//							
//						}
					}else if (cartItem.getCartItemStatus().equalsIgnoreCase("P")) {
						ProductDisplay display = cartItem.getCartProduct();
						Optional optional = productDisplayRepository.findById(id);
						if (!optional.isPresent()) {
							throw new IllegalArgumentException("Product not found.");
						}
						if(display != null && display.getId().equals(id) ) {
							cartItem = new CartItem();
							cartItem.setAmount(amount);
							cartItem.setCartProduct(display);
							cartItem.setLocationId(locMaster.getLocationId());
							cartItem.setItemQuantity(quantity);
							cartItem.setSellerInvId(sellerInvId);
							cartItem.setItemQuantity(quantity);
							cartItem.setCart(cart);
							CartItem ct = cartItemRepository.save(cartItem);
//							System.out.println(cartItem.getAvailableFlag());
		                    UserCart returnCart = priceService.calculatePrice(cart);
		                    cartRepository.save(returnCart);
		                    return returnCart;
						}
					}
					
				}
			}
		}
		Optional optional = productDisplayRepository.findById(id);
		if (!optional.isPresent()) {
			throw new IllegalArgumentException("Product not found.");
		}
		ProductDisplay product = (ProductDisplay) optional.get();
		CartItem repo = cartItemRepository.findByCartItemStatusAndSellerInvId("P", sellerInvId);
		CartItem cartItem = null;
		if( repo != null ) {
			cartItem.setAmount(amount);
			cartItem.setCartProduct(product);
			cartItem.setSellerInvId(sellerInvId);
			cartItem.setItemQuantity(quantity);
			cartItem.setLocationId(locMaster.getLocationId());
			cartItem.setCartItemStatus("P");
			cartItem.setCart(cart);
			if (cart.getCartItemList() == null) {
				cart.setCartItemList(new ArrayList<>());
			}
			cart.getCartItemList().add(cartItem);
			cart = priceService.calculatePrice(cart);
			cartItemRepository.save(cartItem);
		} else {
			cartItem = new CartItem();
			cartItem.setAmount(amount);
			cartItem.setCartProduct(product);
			cartItem.setSellerInvId(sellerInvId);
			cartItem.setItemQuantity(quantity);
			cartItem.setLocationId(locMaster.getLocationId());
			cartItem.setCartItemStatus("P");
			cartItem.setCart(cart);
			if (cart.getCartItemList() == null) {
				cart.setCartItemList(new ArrayList<>());
			}
			cart.getCartItemList().add(cartItem);
			cart = priceService.calculatePrice(cart);
			cartItemRepository.save(cartItem);
			
		}
			
		
		return cart;
	}

	@Override
	public UserCart fetchCart(Principal principal) {
		logger.info("In fetchCart()" + QuickBuyConstants.LOG_SEPRATOR_WITH_START);
		System.out.println("FETCH CART");
		
		List<CartItem> listItems = new ArrayList<>();
		UserCart cart = null;
		try {
			User user = getUserFromPrinciple(principal);	
			cart = user.getUserCart();	
			List<CartItem> test= cartItemRepository.findByCartItemStatusAndCart("P", cart);
			for (CartItem item: test) {
				if(item.getAvailableFlag().equalsIgnoreCase("A")) {
					SellerProductInventory inventory = sellerInvRepository.findBySellerInventoryId(item.getSellerInvId());
					item.setProductQuantity(inventory.getQuantity());
				}else {
					item.setItemQuantity(0L);
					item.setAmount(0L);
					cart = priceService.calculatePrice(cart);
					cartItemRepository.save(item);
				}
				
			
			}
			cart.setCartItemList(test);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("In fetchCart()" + QuickBuyConstants.LOG_SEPRATOR_WITH_END);
		return cart;
	}

	@Override
	public UserCart removeFromCart(Principal principal, Long id) {
		System.out.println("Remove CartItem id " + id);
		logger.info("In removeFromCart()" + QuickBuyConstants.LOG_SEPRATOR_WITH_START + id );
		User user = getUserFromPrinciple(principal);
		UserCart cart = user.getUserCart();
		try {
			if (cart == null) {
				throw new IllegalArgumentException("Cart not found");
			}
			List<CartItem> cartItemsList = cart.getCartItemList();
			CartItem cartItemToDelete = null;
			for (CartItem i : cartItemsList) {
				if (i.getId().equals(id)) {
					cartItemToDelete = i;
				}
			}
			if (cartItemToDelete == null) {
				throw new IllegalArgumentException("CartItem not found");
			}

			cartItemsList.remove(cartItemToDelete);

			if (cart.getCartItemList() == null || cart.getCartItemList().size() == 0) {
				// TODO make it so it can be deleted with online cartRepository.delete method
//	            cartRepository.delete(cart);
				user.setUserCart(null);
				// setting it to null will delete it
				// because of the orphanRemove mark on the field
				userRepository.save(user);
				return null;
			}

			cart.setCartItemList(cartItemsList);
			cart = priceService.calculatePrice(cart);
			cartItemRepository.delete(cartItemToDelete);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("In removeFromCart()" + QuickBuyConstants.LOG_SEPRATOR_WITH_END + id );
		return cart;
	}

//	    @Override
//	    public Boolean confirmCart(Principal principal, UserCart cart) {
//	        User user = getUserFromPrinciple(principal);
//	        UserCart dbCart = user.getUserCart();
//	        if (dbCart == null) {
//	            throw new IllegalArgumentException("Cart not found");
//	        }
//	        List<CartItem> dbCartItemsList = dbCart.getCartItemList();
//	        List<CartItem> cartItemsList = cart.getCartItemList();
//	        if (dbCartItemsList.size() != cartItemsList.size()) {
//	            return false;
//	        }
//
//	        for (int i = 0; i < dbCartItemsList.size(); i++) {
//	            if (!dbCartItemsList.get(i).getId().equals(cartItemsList.get(i).getId()) &&
//	                    !dbCartItemsList.get(i).getAmount().equals(cartItemsList.get(i).getAmount()) &&
//	                    !dbCartItemsList.get(i).getCartProduct().getId().equals(cartItemsList.get(i).getCartProduct().getId())) {
//	                return false;
//	            }
//	        }
//	        if (
//	                dbCart.getTotalPrice().equals(cart.getTotalPrice())
//	                        && dbCart.getTotalCargoPrice().equals(cart.getTotalCargoPrice())
//	                        && dbCart.getCartId() == (cart.getCartId())) {
//	            if (dbCart.getCartDiscount() != null && cart.getCartDiscount() != null) {
//	                if (dbCart.getCartDiscount().getDiscountPercent().equals(cart.getCartDiscount().getDiscountPercent())
//	                        && dbCart.getCartDiscount().getCode().equals(cart.getCartDiscount().getCode())) {
//	                    System.out.println("equals");
//	                    return true;
//	                }
//	            } else if (dbCart.getCartDiscount() == null && cart.getCartDiscount() == null) {
//	                System.out.println("equals");
//	                return true;
//	            }
//
//	        }
//	        System.out.println("no u");
//	        System.out.println(dbCart.getCartItemList().equals(cart.getCartItemList()));
//	        return false;
//	    }

	@Override
	public void emptyCart(Principal principal) {
		User user = getUserFromPrinciple(principal);
		user.setUserCart(null);
		userRepository.save(user);
	}

	private User getUserFromPrinciple(Principal principal) {
		if (principal == null || principal.getName() == null) {
			throw new IllegalArgumentException("Invalid access");
		}
		Optional<User> opt = userRepository.findByUsername(principal.getName());
		User user = opt.get();
		if (user == null) {
			throw new IllegalArgumentException("User not found");
		}
		return user;
	}

	@Override
	public UserCart getCart(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mergeLocalCart(Collection<OrderLines> productInOrders, User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean confirmCart(Principal principal, UserCart cart) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public Long callOrderPackage(Long userID) {
		logger.info("In callOrderPackage()" + QuickBuyConstants.LOG_SEPRATOR_WITH_END + userID );
		StoredProcedureQuery q = entityManger.createStoredProcedureQuery("QUICKBUY_PKG.CREATE_ORDER");
		q.registerStoredProcedureParameter("P_USER_ID", Long.class, ParameterMode.IN);
		q.setParameter("P_USER_ID", userID);
		q.registerStoredProcedureParameter("P_ORDER_NUMBER", Long.class, ParameterMode.OUT);
		Object key = q.getOutputParameterValue("P_ORDER_NUMBER");
//		System.out.println("KEY....."+key);
		Long result = null;
		try {
			result = (Long) key;
			System.out.println(result);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		logger.info("In callOrderPackage()" + QuickBuyConstants.LOG_SEPRATOR_WITH_END + userID );
		return  result;
	}

	@Override
	public Integer getCartCount(Principal principal) {
		Optional<User> user = userRepository.findByUsername(principal.getName());
		Integer count = null;
		if(user.isPresent())
		{
			List<CartItem> cartItems = cartItemRepository.findByCartItemStatusAndCart("P",user.get().getUserCart());
			if(!cartItems.isEmpty())
			{
				count = cartItems.size();
			}
		}
		return count;
	}

	/*
	 * @Override
	 * 
	 * @Transactional public boolean callOrderPackage(Long userID) {
	 * this.entityManger.
	 * createNativeQuery("BEGIN QUICKBUY_PKG.CREATE_ORDER(:P_USER_ID); END;")
	 * .setParameter("P_USER_ID", userID) .executeUpdate(); return true; }
	 */

	
}
