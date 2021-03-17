package com.seller.quickbuy.QuickBuyApp.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.seller.quickbuy.QuickBuyApp.entity.ProductCategory;
import com.seller.quickbuy.QuickBuyApp.entity.ProductDisplay;

@Repository
public interface ProductDisplayRepository extends PagingAndSortingRepository<ProductDisplay, Long> {
    List<ProductDisplay> findAllByProductCategory(Pageable pageable, ProductCategory productCategory);

    List<ProductDisplay> findTop8ByOrderByDateCreatedDesc();

    List<ProductDisplay> findTop8ByOrderBySellCountDesc();

    List<ProductDisplay> findTop8ByProductCategoryAndIdIsNotOrderBySellCountDesc(ProductCategory productCategory, Long id);

    List<ProductDisplay> findAllByProductCategoryIsNotOrderBySellCountDesc(ProductCategory productCategory, Pageable pageable);

    List<ProductDisplay> findAllByProductNameContaining(String name, Pageable pageable);
   
}
