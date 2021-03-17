package com.seller.quickbuy.QuickBuyApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.seller.quickbuy.QuickBuyApp.entity.OrderHeader;

@Repository
public interface OrderRepository extends JpaRepository<OrderHeader, Long> {
	
		OrderHeader findByHeaderId(Long headerId);
//	    Page<OrderHeader> findAllByOrderStatusOrderByCreateTimeDesc(Integer orderStatus, Pageable pageable);
//	    Page<OrderHeader> findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(String buyerEmail, Pageable pageable);
//	    Page<OrderHeader> findAllByOrderByOrderStatusAscCreateTimeDesc(Pageable pageable);
//	    Page<OrderHeader> findAllByBuyerPhoneOrderByOrderStatusAscCreateTimeDesc(String buyerPhone, Pageable pageable);
//	    Page<OrderHeader> findAllByOrderStatus(Integer status, Pageable pageable);
	    int countByOrderStatus(int orderStatus);
	    @Query(value = "select * from order_headers_all where SHIP_TO_CONTACT_ID = :SHIP_TO_CONTACT_ID order by  HEADER_ID Desc fetch next 1 rows only", nativeQuery = true)
	    public OrderHeader findByLatestOrder(Long SHIP_TO_CONTACT_ID);
		OrderHeader findByOrderNumber(Long orderNumber);

//	    findByTitleOrderByTitleAscDescriptionDesc
}

