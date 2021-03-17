package com.seller.quickbuy.QuickBuyApp.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.seller.quickbuy.QuickBuyApp.entity.Approver;



/**
 * 
 * @author jyoti.bhosale
 *
 */
public interface ApproverService {

	/**
	 * 
	 * @return all approvers
	 */
	List<Approver> findAll();

	/**
	 * 
	 * @param approverId to be search
	 * @return Approver of approverId
	 */
	Optional<Approver> findById(Long approverId);

	/**
	 * 
	 * @param approver to be check null or empty values
	 * @return true if null or empty values of feilds are present otherwise false will return
	 */
	boolean checkHasNullOrEmptyFeild(@Valid Approver approver);

	/**
	 * 
	 * @param userEmail to be search
	 * @return approver of userEmail
	 */
	Approver findByEmail(String userEmail);

	/**
	 * 
	 * @param approver to be add
	 */
	void save( Approver approver);

	/**
	 * 
	 * @param approverId to delete
	 */
	void delete(Long approverId);

	/**
	 * 
	 * @param isApproverExist is to update
	 * @param approver is updated
	 */
	void update(Optional<Approver> isApproverExist,Approver approver);

}
