package com.seller.quickbuy.QuickBuyApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seller.quickbuy.QuickBuyApp.entity.PoHeader;



@Repository
public interface PoHeaderRepository extends JpaRepository<PoHeader, Long>{

	List<PoHeader> findByApproverId(Long approverId);
	List<PoHeader> findAllByOrderByPoHeaderIdDesc();
	
	
}
