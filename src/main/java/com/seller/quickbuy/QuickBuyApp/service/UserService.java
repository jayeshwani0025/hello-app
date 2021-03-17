package com.seller.quickbuy.QuickBuyApp.service;

import java.util.Optional;

import com.seller.quickbuy.QuickBuyApp.entity.User;

public interface UserService {

	 Optional<User> findOne(String username);
}
