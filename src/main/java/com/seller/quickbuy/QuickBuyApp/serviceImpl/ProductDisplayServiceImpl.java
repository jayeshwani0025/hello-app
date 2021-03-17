package com.seller.quickbuy.QuickBuyApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.seller.quickbuy.QuickBuyApp.controller.SellerMasterController;
import com.seller.quickbuy.QuickBuyApp.entity.LocationMaster;
import com.seller.quickbuy.QuickBuyApp.entity.ProductCategory;
import com.seller.quickbuy.QuickBuyApp.entity.ProductDisplay;
import com.seller.quickbuy.QuickBuyApp.entity.SellerMaster;
import com.seller.quickbuy.QuickBuyApp.entity.SellerProductInventory;
import com.seller.quickbuy.QuickBuyApp.entity.SellerProductMaster;
import com.seller.quickbuy.QuickBuyApp.repository.ProductDisplayRepository;
import com.seller.quickbuy.QuickBuyApp.repository.SellerMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.SellerProductInventoryMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.SellerProductMasterRepository;
import com.seller.quickbuy.QuickBuyApp.service.ProductDisplayService;

@Service
@CacheConfig(cacheNames = "product_display")
public class ProductDisplayServiceImpl implements ProductDisplayService {

	private final SellerProductMasterRepository sellerProductRepository;
	private final ProductDisplayRepository productDisplayRepository;
	private final SellerProductInventoryMasterRepository sellerInvRepository;
	private final SellerMasterRepository sellerMasterRepository;

    @Autowired
    public ProductDisplayServiceImpl(ProductDisplayRepository productDisplayRepository, SellerProductMasterRepository sellerProductRepository,
    		SellerProductInventoryMasterRepository sellerInvRepository, SellerMasterRepository sellerMasterRepository) {
        this.productDisplayRepository = productDisplayRepository;
        this.sellerProductRepository =sellerProductRepository;
        this.sellerInvRepository = sellerInvRepository;
        this.sellerMasterRepository = sellerMasterRepository;
    }

    @Override
    @Cacheable(key = "#id")
    public ProductDisplay findById(Long id) {
        Optional optional = productDisplayRepository.findById(id);
        return optional.isPresent() ? (ProductDisplay) optional.get() : null;
    }

    @Override
    @Cacheable(key = "#pageable")
//    @CacheEvict(key="#pageable", allEntries = true)
    public List<ProductDisplay> findAll(Pageable pageable) {
        return productDisplayRepository.findAll(pageable).getContent();
    }

    @Override
    @Cacheable(key = "{#pageable,#productCategory.name}")
    public List<ProductDisplay> findAllByProductCategory(Pageable pageable, ProductCategory productCategory) {
    	List<ProductDisplay> display = productDisplayRepository.findAllByProductCategory(pageable, productCategory);
    	List<ProductDisplay> list = new ArrayList<ProductDisplay>();
    	
    	
    	for(ProductDisplay product : display) {
    		List<SellerProductMaster> listSellerPrd = new ArrayList<SellerProductMaster>();;
//    		List<SellerProductInventory> listSellerInv = null;
//    		SellerProductMaster spm = sellerProductRepository.findBySellerProductId(product.getId());
    		List<SellerProductMaster> slrPrdMstr = sellerProductRepository.findByProductId(product.getId());
    		for(SellerProductMaster smdtls :  slrPrdMstr) {
    			List<String>  sellerName = new ArrayList<String>();
    			List<SellerProductInventory> listSellerInv = new ArrayList<SellerProductInventory>();;
				product.setSellerProductMaster(listSellerPrd);
				Optional<SellerMaster>  sm =  sellerMasterRepository.findBySellerId(smdtls.getSellerId());
				SellerMaster smd = sm.get();
				sellerName.add(smd.getSellerName()); 
				product.setSellerName(sellerName);
				listSellerPrd.add(smdtls);
				List<SellerProductInventory> sellerProductInventory = sellerInvRepository.findBySellerProductIdOrderByActualPriceAsc(smdtls.getSellerProductId());
				for( SellerProductInventory inv: sellerProductInventory) {
					
					inv.getProductStatus();
					product.setProductPrice(inv.getActualPrice());
					listSellerInv.add(inv);
					product.setSellerPrdInventory(listSellerInv);
				}
				
    		}
    		list.add(product);
    		
    	}
    	System.out.println(list.size());
        return list;
    }

    @Override
    @Cacheable(key = "#root.methodName")
    public List<ProductDisplay> findTop8ByOrderByDateCreatedDesc() {
        return productDisplayRepository.findTop8ByOrderByDateCreatedDesc();
    }

    @Override
    @Cacheable(key = "#root.methodName")
    public List<ProductDisplay> findTop8ByOrderBySellCountDesc() {
        return productDisplayRepository.findTop8ByOrderBySellCountDesc();
    }

    @Override
    @CachePut(key = "'findTop8ByOrderBySellCountDesc'")
    public List<ProductDisplay> findTop8ByOrderBySellCountDescCacheRefresh() {
        return productDisplayRepository.findTop8ByOrderBySellCountDesc();
    }

    @Override
    @Cacheable(key = "{#productCategory.name,#id}")
    public List<ProductDisplay> getRelatedProducts(ProductCategory productCategory, Long id) {
        List<ProductDisplay> returnList = productDisplayRepository.findTop8ByProductCategoryAndIdIsNotOrderBySellCountDesc(productCategory, id);
        if (returnList.size() < 8) {
            returnList.addAll(productDisplayRepository.findAllByProductCategoryIsNotOrderBySellCountDesc(productCategory, PageRequest.of(0, 8 - returnList.size())));
        }
        return returnList;
    }

    @Override
    public List<ProductDisplay> searchProducts(String keyword, Integer page, Integer size) {
        if (page == null || size == null) {
            throw new IllegalArgumentException("Page and size parameters are required");
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        return productDisplayRepository.findAllByProductNameContaining(keyword, pageRequest);
    }


}
