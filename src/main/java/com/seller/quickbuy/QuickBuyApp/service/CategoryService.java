
package com.seller.quickbuy.QuickBuyApp.service;

import java.util.List;

import com.seller.quickbuy.QuickBuyApp.entity.ProductCategory;

public interface CategoryService {

	List<ProductCategory> findAll();

	ProductCategory findByCategoryType(Integer categoryType);

	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

	ProductCategory save(ProductCategory productCategory);
}
