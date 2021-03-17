package com.seller.quickbuy.QuickBuyApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seller.quickbuy.QuickBuyApp.entity.Approver;



/**
 * 
 * @author jyoti.bhosale
 *
 */
@Repository
public interface ApproverRepository extends JpaRepository<Approver, Long>{

	/**
	 * 
	 * @param userEmail to be search
	 * @return approver of userEmail
	 */
	Approver findByUserEmail(String userEmail);
	
}
