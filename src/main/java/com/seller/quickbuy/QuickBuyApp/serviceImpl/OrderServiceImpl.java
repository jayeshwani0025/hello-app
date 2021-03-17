package com.seller.quickbuy.QuickBuyApp.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seller.quickbuy.QuickBuyApp.entity.OrderHeader;
import com.seller.quickbuy.QuickBuyApp.entity.User;
import com.seller.quickbuy.QuickBuyApp.repository.OrderRepository;
import com.seller.quickbuy.QuickBuyApp.repository.ProductMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.UserRepository;
import com.seller.quickbuy.QuickBuyApp.service.OrderService;



@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	OrderRepository orderRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProductMasterRepository productMasterRepository;
	@Override
	public OrderHeader findByLatestOrder(User user) {
		Long userId = user.getUserId();
		OrderHeader header = orderRepository.findByLatestOrder(userId);
		return header;
	}
	
//	@Override
//	public Page<OrderHeader> findAll(Pageable pageable) {
//		return orderRepository.findAllByOrderByOrderStatusAscCreateTimeDesc(pageable);
//	}
//	@Override
//	public Page<OrderHeader> findByStatus(Integer status, Pageable pageable) {
//		return orderRepository.findAllByOrderStatusOrderByCreateTimeDesc(status, pageable);
//	}
//	@Override
//	public Page<OrderHeader> findByBuyerEmail(String email, Pageable pageable) {
//		return orderRepository.findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(email, pageable);
//	}
//	@Override
//	public Page<OrderHeader> findByBuyerPhone(String phone, Pageable pageable) {
//		return orderRepository.findAllByBuyerPhoneOrderByOrderStatusAscCreateTimeDesc(phone, pageable);
//	}
//	@Override
//	public OrderHeader findOne(Long orderId) {
//		OrderHeader header = 	orderRepository.findByHeaderId(orderId);
//		if (header == null) {
//			throw new MyException(ResultEnum.ORDER_NOT_FOUND);
//		}
//		return header;
//	}
//	@Override
//	public OrderHeader finish(Long orderId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public OrderHeader cancel(Long orderId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public Page<OrderHeader> findAllByOrderStatus(Integer status, Pageable pageable) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public int countByOrderStatus(int orderStatus) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//	@Override
//	public OrderHeader pendingOrder(Long orderId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
//	
//

}

