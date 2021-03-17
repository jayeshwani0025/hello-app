package com.seller.quickbuy.QuickBuyApp.serviceImpl;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.seller.quickbuy.QuickBuyApp.entity.User;
import com.seller.quickbuy.QuickBuyApp.repository.UserRepository;
import com.seller.quickbuy.QuickBuyApp.service.UserService;
import com.seller.quickbuy.QuickBuyApp.utils.QuickBuyConstants;

@Service
@DependsOn("passwordEncoder")
public class UserServiceImpl implements UserService  {
	
	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
    UserRepository userRepository; 
	@Override
    public Optional<User> findOne(String username) {
		logger.info("In findOne() user serviceImpl " + QuickBuyConstants.LOG_SEPRATOR_WITH_START );
		logger.info("In findOne() user serveceImpl" + QuickBuyConstants.LOG_SEPRATOR_WITH_END );
        return userRepository.findByUsername(username);
        
    }
}
