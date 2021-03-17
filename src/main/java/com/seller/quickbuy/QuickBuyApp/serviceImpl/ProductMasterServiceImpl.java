package com.seller.quickbuy.QuickBuyApp.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;


import com.seller.quickbuy.QuickBuyApp.entity.ProductCategory;
import com.seller.quickbuy.QuickBuyApp.entity.ProductMaster;
import com.seller.quickbuy.QuickBuyApp.entity.ProductMasterStage;
import com.seller.quickbuy.QuickBuyApp.repository.ProductCategoryRepository;
import com.seller.quickbuy.QuickBuyApp.repository.ProductMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.ProductMasterStageRepository;
import com.seller.quickbuy.QuickBuyApp.response.ProductMasterResponse;
import com.seller.quickbuy.QuickBuyApp.service.ProductMasterService;
import com.seller.quickbuy.QuickBuyApp.utils.CSVHelper;

/**
 * 
 * @author jayesh.wani
 *
 */

@Service
@Transactional
public class ProductMasterServiceImpl implements ProductMasterService {

	private static final Logger logger = LogManager.getLogger(ProductMasterServiceImpl.class);
	
	@Autowired
	ProductMasterRepository productMasterRepository;
	@Autowired
	ProductCategoryRepository productCategoryRepository;
	@Autowired
	ProductMasterStageRepository productMasterStageRepository;
	
	
	@Override
	public ProductMaster findOne(String productName) {
		logger.info("In ProductMasterServiceImpl -> findOne()........START");
		logger.info("In ProductMasterServiceImpl -> findOne()........END");
		return productMasterRepository.findByProductName(productName);
	}

	@Override
	public ProductMaster save(ProductMaster productMaster) {
		logger.info("In ProductMasterServiceImpl -> save()........START");
		ProductMaster productMasterdtls = productMasterRepository.save(productMaster);
		logger.info("In ProductMasterServiceImpl -> save()........END");
		return productMasterdtls;
	}

	@Override
	public void delete(Long productId) {
		logger.info("In ProductMasterServiceImpl -> delete()........START");
		productMasterRepository.deleteById(productId);
		logger.info("In ProductMasterServiceImpl -> delete()........END");
	}

//	@Override
//	public List<ProductMasterResponse> findAll() {
//		logger.info("In ProductMasterServiceImpl -> findAll()........START");
//		List<ProductMasterResponse> resp= null;
//		List<ProductMaster> list  = productMasterRepository.findAll();
//		Long catId = null;
//		for(ProductMaster temp : list) {
//		catId =	temp.getCategoryId();
//		ProductCategory pm = productCategoryRepository.findByCategoryId(catId);
//		String catname = pm.getCategoryName();
//		
//		}
//		
//		logger.info("In ProductMasterServiceImpl -> findAll()........END");
//		return resp;
//	}

	
	@Override
	public List<ProductMaster> findAllProduct() {
		logger.info("In ProductMasterServiceImpl -> findAll()........Start");
		List<ProductMaster> list  = productMasterRepository.findAll();
		Iterator<ProductMaster> itr = list.listIterator();
		
		while(itr.hasNext()) {
			ProductMaster pm = itr.next();
			Long catId =	pm.getCategoryId();
			ProductCategory pc = productCategoryRepository.findByCategoryId(catId);
			String catname = pc.getCategoryName();
			pm.setCatName(catname);
		}
		logger.info("In ProductMasterServiceImpl -> findAll()........END");
		return list;
	}
	@Override
	public ProductMaster update(@Valid ProductMaster product) {
		logger.info("In ProductMasterServiceImpl -> update()........START");
		logger.info("In ProductMasterServiceImpl -> update()........END");
		return productMasterRepository.save(product);
	}

	@Override
	public Optional findByProductId(Long productId) {
		
		return productMasterRepository.findByProductId(productId);
	}

	@Override
	public List<ProductMaster> findByCategoryId(Long categoryId) {
		logger.info("In ProductMasterServiceImpl -> findByCategoryId()........START");
		List<ProductMaster> productList = productMasterRepository.findByCategoryId(categoryId);
		logger.info("In ProductMasterServiceImpl -> findByCategoryId()........END");
		return productList;
	}

	@Override
	public Page<ProductMaster> findAllInCategory(Long categoryType, Pageable request) {
		   return productMasterRepository.findAllByCategoryIdOrderByProductIdAsc(categoryType, request);
	}
	


	@Override
	public List<ProductMaster> findByProductName(String itemName) {
		
		//return productMasterRepository.findByProductNameList(itemName);
		return null;
	}


	public List<ProductMasterStage> save(MultipartFile file, Integer id) {
		 List<ProductMasterStage> products = (List<ProductMasterStage>)(List<?>)CSVHelper.csvImport(file, ProductMasterStage.class,1);
		 productMasterStageRepository.saveAll(products);
           return products;
	}


}
	

	
	
//	@Override
//	public List<ProductMaster> findAllInCategory(Long categoryType) {
//		// TODO Auto-generated method stub
//		return  productMasterRepository.findAllByCategoryTypeOrderByProductIdAsc(categoryType);
//	}


