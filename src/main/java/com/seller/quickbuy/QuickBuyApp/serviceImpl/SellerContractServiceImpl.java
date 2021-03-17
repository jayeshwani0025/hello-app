package com.seller.quickbuy.QuickBuyApp.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.seller.quickbuy.QuickBuyApp.entity.PoHeader;
import com.seller.quickbuy.QuickBuyApp.entity.SellerContract;
import com.seller.quickbuy.QuickBuyApp.entity.SellerMaster;
import com.seller.quickbuy.QuickBuyApp.exception.FileStorageException;
import com.seller.quickbuy.QuickBuyApp.repository.SellerContractRepository;
import com.seller.quickbuy.QuickBuyApp.repository.SellerMasterRepository;
import com.seller.quickbuy.QuickBuyApp.service.SellerContractService;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class SellerContractServiceImpl implements SellerContractService{

	@Autowired
	SellerContractRepository sellerContractRepository;
	
	@Autowired
	SellerMasterRepository sellerMasterRepository;
	 
	    
	    @Override
	     public SellerContract createSellerContract( MultipartFile file,SellerContract sellerContract,Long sellerId) {
	    	sellerContract.setSellerId(sellerId);
	      
	         String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	         try {
	             
	             if(fileName.contains("..")) {
	                 throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	             }
	             sellerContract.setFileName(fileName);
	             sellerContract.setFileType(fileName.substring(fileName.indexOf(".")+1));
	             sellerContract.setContractDocument(file.getBytes());
	             
	             return sellerContractRepository.save(sellerContract);
	         } catch (IOException ex) {
	             throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	         }
	     }

	    
	     
	  
	     @Override
	     public SellerContract updateSellerContract(SellerContract sellerContract) {
	         return sellerContractRepository.save(sellerContract);
	     }
	  
	     @Override
	     public SellerContract getSellerContract(Long contractId) {
	         Optional<SellerContract> optionalSellerContract = sellerContractRepository.findById(contractId);
	         if (optionalSellerContract.isPresent()) {
	             return optionalSellerContract.get();
	         }
	         return null;
	     }
	  
	     @Override
	     public void deleteSellerContract(Long contractId) {
	    	 sellerContractRepository.deleteById(contractId);
	     }
	  
	     @Override
	     public List<SellerContract> getAllSellerContract() {
	         return sellerContractRepository.findAll();
	     }




		@Override
		public List<SellerContract> getSellerContractbySeller(Long sellerId) {
			List<SellerContract> contract =sellerContractRepository.findAllBySellerId(sellerId);
			return contract;
		}




		@Override
		public SellerContract changeContractStatus(SellerContract sellerContract) {
			SellerContract contract = sellerContractRepository.findByActiveContractAndSellerId("A",sellerContract.getSellerId());
			SellerContract activatedContract = null;
			if(contract != null)
			{
				contract.setActiveContract("I");
				SellerContract changedContract = sellerContractRepository.save(contract);
				if(changedContract != null)
				{
					sellerContract.setActiveContract("A");
					activatedContract = sellerContractRepository.save(sellerContract);
		
					
				}
				
				return activatedContract;
			}
			else {
				sellerContract.setActiveContract("A");
				activatedContract = sellerContractRepository.save(sellerContract);
				return activatedContract;
			}
			
		}




		@Override
		public Optional<SellerContract> findById(Long contractId) {
			Optional<SellerContract> sellerContract = sellerContractRepository.findById(contractId);
			return sellerContract;
		}




		@Override
		public void update(Optional<SellerContract> isContractExist, SellerContract sellerContract, MultipartFile file)
				throws IOException {
			SellerContract cont = isContractExist.get();
			sellerContract.setContractDetails(cont.getContractDetails());
			sellerContract.setContractStartDate(cont.getContractStartDate());
			sellerContract.setContractEndDate(cont.getContractEndDate());
			sellerContract.setDiscountPercentage(cont.getDiscountPercentage());
			sellerContract.setActiveContract(cont.getActiveContract());
			sellerContract.setContractDocument(file.getBytes());
			sellerContract.setFileName(file.getOriginalFilename());
			sellerContract.setFileType(file.getContentType());
			sellerContractRepository.save(sellerContract);
			
			
		}




	
	 }



