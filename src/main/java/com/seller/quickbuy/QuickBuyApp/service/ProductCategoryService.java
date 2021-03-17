package com.seller.quickbuy.QuickBuyApp.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.seller.quickbuy.QuickBuyApp.entity.ProductCategory;



/**
 * 
 * @author jyoti.bhosale
 *
 */
public interface ProductCategoryService {

	/**
	 * 
	 * @return list of category
	 */
	List<ProductCategory> findAll();

	/**
	 * 
	 * @param categoryType to be Search
	 * @return ProductCategory of categoryType
	 */
	ProductCategory findOne(String categoryType);

	/**
	 * 
	 * @param productCategory to be added
	 */
	void save(ProductCategory productCategory);

	/**
	 * 
	 * @param categoryId to be search
	 * @return ProductCategory of categoryId
	 */
	Optional<ProductCategory> findById(Long categoryId);

	/**
	 * 
	 * @param categoryId to be delete
	 */
	void delete(Long categoryId);

	ProductCategory findByCategoryId(Long category);
//	ProductCategory findByCategoryNameAndCategoryType(String category, String type);

	ProductCategory findByCategoryName(String category);

//	ProductCategory findByCategoryName(String category, String type);
}
