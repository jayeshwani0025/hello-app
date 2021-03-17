package com.seller.quickbuy.QuickBuyApp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seller.quickbuy.QuickBuyApp.entity.CartItem;
import com.seller.quickbuy.QuickBuyApp.entity.ProductDisplay;
import com.seller.quickbuy.QuickBuyApp.entity.ProductMaster;
import com.seller.quickbuy.QuickBuyApp.entity.UserCart;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {
	List<CartItem> findByCartItemStatusAndCart(String itemStatus, UserCart cartId);
	CartItem findByCartItemStatusAndSellerInvId(String itemStatus, Long sellerInvId);
	
//	CartItem delete(Long cartItemId);
}
