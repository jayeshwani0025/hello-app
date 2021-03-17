package com.seller.quickbuy.QuickBuyApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seller.quickbuy.QuickBuyApp.entity.ProductMasterStage;
import com.seller.quickbuy.QuickBuyApp.entity.SellerProductInventory;

@Repository
public interface ProductMasterStageRepository extends JpaRepository<ProductMasterStage, Long>{

	

}
