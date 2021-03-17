package com.seller.quickbuy.QuickBuyApp.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.seller.quickbuy.QuickBuyApp.entity.ProductCategory;
import com.seller.quickbuy.QuickBuyApp.enums.ResultEnum;
import com.seller.quickbuy.QuickBuyApp.exception.MyException;
import com.seller.quickbuy.QuickBuyApp.repository.ProductCategoryRepository;
import com.seller.quickbuy.QuickBuyApp.service.ProductCategoryService;

/**
 * 
 * @author jyoti.bhosale
 *
 */
@Service
@CacheConfig(cacheNames = "product_category")
public class ProductCategoryServiceImpl implements ProductCategoryService {

	private static final Logger logger = LogManager.getLogger(ProductCategoryServiceImpl.class);
	
	@Autowired
	ProductCategoryRepository productCategoryRepository;

	@Override
	public List<ProductCategory> findAll() {
		logger.info("In ProductCategoryService findAll()--> START");
		logger.info("In ProductCategoryService findAll()--> END");
		return productCategoryRepository.findAll();
	}

	@Override
	public ProductCategory findOne(String categoryType) {
		logger.info("In ProductCategoryService findOne()--> START");
		ProductCategory productCategory = productCategoryRepository.findByCategoryType(categoryType);
		logger.info("In ProductCategoryService findOne()--> END");
		return productCategory;
	}

	@Override
	public void save(ProductCategory productCategory) {
		logger.info("In ProductCategoryService save()--> START");
		productCategoryRepository.save(productCategory);
		logger.info("In ProductCategoryService save()--> END");
	}

	@Override
	public Optional<ProductCategory> findById(Long categoryId) {
		logger.info("In ProductCategoryService findById()--> START");
		Optional<ProductCategory> productCategory = productCategoryRepository.findById(categoryId);
		logger.info("In ProductCategoryService findById()--> END");
		return productCategory;
	}

	@Override
	public void delete(Long categoryId) {
		logger.info("In ProductCategoryService delete()--> START");
		productCategoryRepository.deleteById(categoryId);
		logger.info("In ProductCategoryService delete()--> END");
	}

	@Override
	public ProductCategory findByCategoryId(Long categoryType) {
		ProductCategory res = productCategoryRepository.findByCategoryId(categoryType);
        if(res == null) throw new MyException(ResultEnum.CATEGORY_NOT_FOUND);
        return res;
	}

	@Override
	@Cacheable(key = "#category")
	public ProductCategory findByCategoryName(String category) {
	    return productCategoryRepository.findByCategoryName(category);
	}
}
