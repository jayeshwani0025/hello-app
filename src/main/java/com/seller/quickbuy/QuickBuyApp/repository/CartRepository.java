package com.seller.quickbuy.QuickBuyApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seller.quickbuy.QuickBuyApp.entity.UserCart;


public interface CartRepository extends JpaRepository<UserCart, Integer> {

	Long findByCartId(Long userId);
	
}
