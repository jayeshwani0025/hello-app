package com.seller.quickbuy.QuickBuyApp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.seller.quickbuy.QuickBuyApp.entity.SellerProductInventory;

@Repository
public interface SellerProductInventoryMasterRepository  extends JpaRepository<SellerProductInventory, Long>{

	
	Page<SellerProductInventory> findBySellerProductIdOrderByActualPriceAsc(Long sellerPrdId, Pageable pageable);

	List<SellerProductInventory> findBySellerProductIdOrderByActualPriceAsc(Long sellerProductId);

	SellerProductInventory findBySellerInventoryId(Long sellerInventoryId);

	List<SellerProductInventory> findBySellerProductId(Long sellerProductId);
	List<SellerProductInventory> findTop5BySellerProductIdOrderByActualPriceAsc(Long sellerProductId);

//	List<SellerProductInventory> findBySellerProductIdAndLocationId(Long sellerProductId, Long locationId);

	SellerProductInventory findBySellerProductIdAndLocationId(Long sellerProductId, Long locationId);
	List<SellerProductInventory> findBySellerProductIdAndLocationIdOrderByActualPriceAsc(Long sellerProductId, Long locationId);


}
