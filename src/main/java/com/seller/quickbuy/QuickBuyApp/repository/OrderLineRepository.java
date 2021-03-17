package com.seller.quickbuy.QuickBuyApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seller.quickbuy.QuickBuyApp.entity.OrderLines;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLines, Long> {

}
