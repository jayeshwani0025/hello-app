package com.seller.quickbuy.QuickBuyApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seller.quickbuy.QuickBuyApp.entity.SellerProductMasterStage;



@Repository
public interface SellerProductMasterStageRepository extends JpaRepository<SellerProductMasterStage, Long> {
	
	

}
