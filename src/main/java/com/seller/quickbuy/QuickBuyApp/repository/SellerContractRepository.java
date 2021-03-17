package com.seller.quickbuy.QuickBuyApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seller.quickbuy.QuickBuyApp.entity.PoHeader;
import com.seller.quickbuy.QuickBuyApp.entity.SellerContract;

@Repository
public interface SellerContractRepository extends JpaRepository<SellerContract, Long> {

	SellerContract findBySellerId(Long sellerId);
	

	List<SellerContract> findAllBySellerId(Long sellerId);

	List<SellerContract> findAllByOrderByContractIdDesc();

	SellerContract findByActiveContractAndSellerId(String activeContract,Long sellerId);


}
