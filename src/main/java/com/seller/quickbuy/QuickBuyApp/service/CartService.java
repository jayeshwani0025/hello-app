package com.seller.quickbuy.QuickBuyApp.service;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.query.Procedure;

import com.seller.quickbuy.QuickBuyApp.entity.OrderLines;
import com.seller.quickbuy.QuickBuyApp.entity.User;
import com.seller.quickbuy.QuickBuyApp.entity.UserCart;



public interface CartService {
    UserCart getCart(User user);
//    UserCart fetchCart(Principal principal);
    void mergeLocalCart(Collection<OrderLines> productInOrders, User user);
//    void mergeLocalCart(List<OrderLines> productInOrders, User user);
    void delete(String itemId, User user);

    void checkout(User user);
    
//    void orderDetails(User user);
    
    Long getOrderId(User user);
    
	
    OrderLines findLatestOrders();
	
//    UserCart addToCart(Principal principal, Long id, Integer amount);

    UserCart fetchCart(Principal principal);

    UserCart removeFromCart(Principal principal, Long id);

    Boolean confirmCart(Principal principal, UserCart cart);

    void emptyCart(Principal principal);
	UserCart addToCart(Principal principal, Long id, Long amount, Long sellerInvId, Long quantity);
	
	/*
	 * @Procedure(name = "QUICKBUY_PKG.CREATE_ORDER") public boolean
	 * callOrderPackage(Long userID);
	 */
	
	@Procedure(name = "QUICKBUY_PKG.CREATE_ORDER",outputParameterName = "P_ORDER_NUMBER")
	Long callOrderPackage(Long userID);
	
	/**
	 * 
	 * @param principal
	 * @return count of all items in cart
	 */
	Integer getCartCount(Principal principal);
	
}
