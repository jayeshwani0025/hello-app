package com.seller.quickbuy.QuickBuyApp.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.seller.quickbuy.QuickBuyApp.entity.PoHeader;
import com.seller.quickbuy.QuickBuyApp.entity.SellerContract;

public interface SellerContractService {


	public SellerContract createSellerContract(MultipartFile file,SellerContract sellerContract,Long sellerId);
    public SellerContract updateSellerContract(SellerContract sellerContract);
    public SellerContract getSellerContract(Long contractId);
    Optional<SellerContract> findById(Long contractId);
    public void deleteSellerContract(Long contractId);
    public List<SellerContract> getAllSellerContract();
	public List<SellerContract> getSellerContractbySeller(Long sellerId);
	public SellerContract changeContractStatus(SellerContract sellerContract);
	void update(Optional<SellerContract> isContractExist, SellerContract sellerContract, MultipartFile file) throws IOException;
   
}