package com.seller.quickbuy.QuickBuyApp.serviceImpl;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.seller.quickbuy.QuickBuyApp.entity.ProductMaster;
import com.seller.quickbuy.QuickBuyApp.repository.ProductMasterRepository;
import com.seller.quickbuy.QuickBuyApp.service.ProductService;

@Service
@CacheConfig(cacheNames = "product")
public class ProductServiceImpl implements ProductService {


    private final ProductMasterRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductMasterRepository productRepository) {
        this.productRepository = productRepository;
    }

//    @Override
//    @Cacheable(key = "#id")
//    public ProductMaster findByproductId(Long id) {
//        Optional optional = productRepository.findById(id);
//        return optional.isPresent() ? (ProductMaster) optional.get() : null;
//    }

	@Override
	@Cacheable(key = "#id")
	public ProductMaster findByProductId(Long productId) {
		Optional optional = productRepository.findByProductId(productId);
		 return optional.isPresent() ? (ProductMaster) optional.get() : null;
//		return null;
	}

	@Override
	public List<ProductMaster> findUpAll() {
		//return productRepository.findAll();
		return productRepository.findAllByOrderByProductIdDesc();
	}

	@Override
	public Page<ProductMaster> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
