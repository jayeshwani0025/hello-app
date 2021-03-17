package com.seller.quickbuy.QuickBuyApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seller.quickbuy.QuickBuyApp.entity.SellerProductMaster;

@Repository
public interface SellerProductMasterRepository extends JpaRepository<SellerProductMaster, Long>{
	Optional<SellerProductMaster> findBySellerId(Long sellerId);
	Page<SellerProductMaster> findByProductId(Long prdId, Pageable pageable);
	List<SellerProductMaster> findByProductId(Long productId);
	List<SellerProductMaster> findTop5ByProductId(Long productId);
	SellerProductMaster findBySellerProductId(Long sellerProductId);
	Optional<SellerProductMaster> findBySellerIdAndProductIdAndLocationId(Long sellerId,Long productId,Long locationId);

}
