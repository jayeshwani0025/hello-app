package com.seller.quickbuy.QuickBuyApp.service;


import java.util.List;

import org.springframework.data.domain.Pageable;

import com.seller.quickbuy.QuickBuyApp.entity.ProductCategory;
import com.seller.quickbuy.QuickBuyApp.entity.ProductDisplay;

public interface ProductDisplayService {
    ProductDisplay findById(Long id);

    List<ProductDisplay> findAll(Pageable pageable);

    List<ProductDisplay> findAllByProductCategory(Pageable pageable, ProductCategory productCategory);

    List<ProductDisplay> findTop8ByOrderByDateCreatedDesc();

    List<ProductDisplay> findTop8ByOrderBySellCountDesc();

    List<ProductDisplay> findTop8ByOrderBySellCountDescCacheRefresh();

    List<ProductDisplay> getRelatedProducts(ProductCategory productCategory, Long id);

    List<ProductDisplay> searchProducts(String keyword, Integer page, Integer size);
}
