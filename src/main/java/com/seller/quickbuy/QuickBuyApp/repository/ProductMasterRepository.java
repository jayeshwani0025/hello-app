package com.seller.quickbuy.QuickBuyApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seller.quickbuy.QuickBuyApp.entity.ProductMaster;

@Repository
public interface ProductMasterRepository extends JpaRepository<ProductMaster, Long>{

	ProductMaster findByProductName(String productName);
	Optional<?> findByProductId(Long productId);

	/**
	 * 
	 * @param categoryId to be Search
	 * @return all products by categoryId
	 */
	List<ProductMaster> findByCategoryId(Long categoryId);
//	List<ProductMaster> findAllByCategoryTypeOrderByProductIdAsc(Long categoryType);
	Page<ProductMaster> findAllByCategoryIdOrderByProductIdAsc(Long categoryType, Pageable request);
	List<ProductMaster> findAllByProductId(Long productId);
	//List<ProductMaster> findByProductNameList(String productName);

	List<ProductMaster> findAllByOrderByProductIdDesc();
}

