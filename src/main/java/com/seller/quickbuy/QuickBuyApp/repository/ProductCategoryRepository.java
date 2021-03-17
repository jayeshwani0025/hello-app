package com.seller.quickbuy.QuickBuyApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seller.quickbuy.QuickBuyApp.entity.ProductCategory;


/**
 * 
 * @author jyoti.bhosale
 *
 */
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>{

	/**
	 * 
	 * @param categoryType to be search
	 * @return product of categoryType
	 */
	ProductCategory findByCategoryName(String category);
	List<ProductCategory> findAllByOrderByCategoryName();
	ProductCategory findByCategoryType(String categoryType);
	ProductCategory findByCategoryId(Long categoryId);
	ProductCategory findByCategoryNameAndCategoryType(String categoryName, String categoryType);
	// Some category
    List<ProductCategory> findByCategoryTypeInOrderByCategoryTypeAsc(List<Integer> categoryTypes);
    // All category
    List<ProductCategory> findAllByOrderByCategoryType();
    // One category
    ProductCategory findByCategoryType(Integer categoryType);
}
