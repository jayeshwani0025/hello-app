package com.seller.quickbuy.QuickBuyApp.serviceImpl;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Service;

import com.seller.quickbuy.QuickBuyApp.service.DataUploadService;

@Service
public class DataUploadServiceImpl implements DataUploadService {

	@PersistenceContext
	private EntityManager entityManger;
	
	@Override
	public int callUploadProductMaster() {
		StoredProcedureQuery q = entityManger.createStoredProcedureQuery("QUICKBUY_PKG.CREATE_PRODUCT");
		q.registerStoredProcedureParameter("P_PRD_COUNT", Integer.class, ParameterMode.OUT);
		Object key = q.getOutputParameterValue("P_PRD_COUNT");
		int result = 0;
		try {
			result = (int) key;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  result;
	}

	@Override
	public int callUploadSellerMaster() {
		StoredProcedureQuery q = entityManger.createStoredProcedureQuery("QUICKBUY_PKG.CREATE_SELLER");
		q.registerStoredProcedureParameter("P_PRD_COUNT", Integer.class, ParameterMode.OUT);
		Object key = q.getOutputParameterValue("P_PRD_COUNT");
		int result = 0;
		try {
			result = (int) key;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  result;
	}

	@Override
	public int callUploadSellerProductMaster() {
		StoredProcedureQuery q = entityManger.createStoredProcedureQuery("QUICKBUY_PKG.CREATE_SELLER_PRODUCT");
		q.registerStoredProcedureParameter("P_PRD_COUNT", Integer.class, ParameterMode.OUT);
		Object key = q.getOutputParameterValue("P_PRD_COUNT");
		int result = 0;
		try {
			result = (int) key;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  result;
	}

	
}
