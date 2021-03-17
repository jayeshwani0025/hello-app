package com.seller.quickbuy.QuickBuyApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seller.quickbuy.QuickBuyApp.entity.SellerContract;
import com.seller.quickbuy.QuickBuyApp.entity.SellerMaster;
import com.seller.quickbuy.QuickBuyApp.entity.SellerMasterStage;
import com.seller.quickbuy.QuickBuyApp.entity.SellerProductMaster;

@Repository
public interface SellerMasterRepository  extends JpaRepository<SellerMaster, Long> {

	Optional<SellerMaster> findBySellerId(Long sellerId);

	SellerProductMaster save(SellerProductMaster sellerProductMaster);
	List<SellerMaster> findAllByOrderBySellerIdDesc();

}
