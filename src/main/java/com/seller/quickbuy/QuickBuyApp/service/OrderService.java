package com.seller.quickbuy.QuickBuyApp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.seller.quickbuy.QuickBuyApp.entity.OrderHeader;
import com.seller.quickbuy.QuickBuyApp.entity.User;

public interface OrderService {
	
//	public OrderHeader createOrder(OrderHeader order);
//    public OrderHeader updateOrder(OrderHeader order);
//    public OrderHeader getOrder(Long headerId);
//    public void deleteOrder(Long headerId);
//    public List<OrderHeader> getAllOrder();
//    OrderHeader findByHeaderId(Long headerId);
    
//    Page<OrderHeader> findAll(Pageable pageable);
//    Page<OrderHeader> findByStatus(Integer status, Pageable pageable);
//    Page<OrderHeader> findByBuyerEmail(String email, Pageable pageable);
//    Page<OrderHeader> findByBuyerPhone(String phone, Pageable pageable);
//    OrderHeader findOne(Long orderId);
//    OrderHeader finish(Long orderId);
//    OrderHeader cancel(Long orderId);
//    Page<OrderHeader> findAllByOrderStatus(Integer status, Pageable pageable);
//    int countByOrderStatus(int orderStatus);
//    OrderHeader pendingOrder(Long orderId);
	OrderHeader findByLatestOrder(User user);

}
