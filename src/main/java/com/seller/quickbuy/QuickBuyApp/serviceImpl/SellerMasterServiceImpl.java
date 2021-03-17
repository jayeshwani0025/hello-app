package com.seller.quickbuy.QuickBuyApp.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.seller.quickbuy.QuickBuyApp.entity.ProductMaster;
import com.seller.quickbuy.QuickBuyApp.entity.SellerContract;
import com.seller.quickbuy.QuickBuyApp.entity.SellerMaster;
import com.seller.quickbuy.QuickBuyApp.entity.SellerMasterStage;
import com.seller.quickbuy.QuickBuyApp.repository.SellerMasterRepository;
import com.seller.quickbuy.QuickBuyApp.repository.SellerMasterStageRepository;
import com.seller.quickbuy.QuickBuyApp.service.SellerMasterService;
import com.seller.quickbuy.QuickBuyApp.utils.CSVHelper;

@Service
public class SellerMasterServiceImpl implements SellerMasterService {



    @Autowired
    private SellerMasterRepository sellerMasterRepository;
    @Autowired
    private SellerMasterStageRepository sellerMasterStageRepository;
    
    
    
   @Override
    public SellerMaster createSeller(SellerMaster seller) {
   if(seller.getSellerContactNumber()== null || seller.getSellerName()== null || seller.getSellerEmailAddress()==null) {
	   return null;
   }
   return sellerMasterRepository.save(seller);
   
    }
 
    @Override
    public SellerMaster updateSeller(SellerMaster seller) {
 
        return sellerMasterRepository.save(seller);
    }
 
    @Override
    public SellerMaster getSeller(Long seller_id) {
        Optional<SellerMaster> optionalSeller = sellerMasterRepository.findById(seller_id);
        if (optionalSeller.isPresent()) {
            return optionalSeller.get();
        }
        return null;
    }
 
    @Override
    public void deleteSeller(Long seller_id) {
    	sellerMasterRepository.deleteById(seller_id);
    }
 
    @Override
    public List<SellerMaster> getAllSeller() {
        return sellerMasterRepository.findAll();
    }

	@Override
	public Optional<SellerMaster> findBySellerId(Long sellerId) {
	return sellerMasterRepository.findById(sellerId);
	}
	
	public List<SellerMasterStage>  save(MultipartFile file,Integer id){
		   List<SellerMasterStage> seller = (List<SellerMasterStage>)(List<?>)CSVHelper.csvImport(file, SellerMasterStage.class,2);
		  sellerMasterStageRepository.saveAll(seller);
		  return seller;
		  }
}