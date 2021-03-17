package com.seller.quickbuy.QuickBuyApp.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seller.quickbuy.QuickBuyApp.entity.Approver;
import com.seller.quickbuy.QuickBuyApp.repository.ApproverRepository;
import com.seller.quickbuy.QuickBuyApp.service.ApproverService;


/**
 * 
 * @author jyoti.bhosale
 *
 */
@Service
public class ApproverServiceImpl implements ApproverService {

	private static final Logger logger = LogManager.getLogger(ApproverServiceImpl.class);

	@Autowired
	ApproverRepository approverRepository;
	
	@Override
	public List<Approver> findAll() {
		
		logger.info("In ApproverServiceImpl findAll()--> START");
		List<Approver> approverList = approverRepository.findAll();
		logger.info("In ApproverServiceImpl findAll()--> END "+ approverList);
		return approverList;
	}

	@Override
	public Optional<Approver> findById(Long approverId) {
		logger.info("In ApproverServiceImpl findById()--> START");
		Optional<Approver> approver = approverRepository.findById(approverId);
		logger.info("In ApproverServiceImpl findById()--> END");
		return approver;
	}

	@Override
	public boolean checkHasNullOrEmptyFeild(@Valid Approver approver) {
		logger.info("In ApproverServiceImpl checkHasNullOrEmptyFeild()--> START");
		boolean hasNullOrEmptyFeild = (Stream.of(approver.getApproverName(),approver.getPassword(),approver.getAddress(),approver.getCity(),
												approver.getCountry(),approver.getUserEmail(),approver.getCreatedBy(),approver.getZip(),
												approver.getLastUpdatedLogin(),approver.getLastUpdatedBy()).anyMatch(feild-> feild==null || feild.isEmpty()));
		logger.info("In ApproverServiceImpl checkHasNullOrEmptyFeild()--> END");
		return hasNullOrEmptyFeild;
	}

	@Override
	public Approver findByEmail(String userEmail) {
		logger.info("In ApproverServiceImpl findByEmail()--> START");
		Approver approver = approverRepository.findByUserEmail(userEmail);
		logger.info("In ApproverServiceImpl findByEmail()--> END");
		return approver;
	}

	@Override
	public void save(Approver approver) {
		
		logger.info("In ApproverServiceImpl save()--> START");
		approverRepository.save(approver);
		logger.info("In ApproverServiceImpl save()--> END");
		
	}

	@Override
	public void delete(Long approverId) {
		
		logger.info("In ApproverServiceImpl delete()--> START");
		approverRepository.deleteById(approverId);
		logger.info("In ApproverServiceImpl delete()--> END");
	}

	@Override
	public void update(Optional<Approver> isApproverExist, Approver approver) {
		
		logger.info("In ApproverServiceImpl update()--> START");
		Approver app = isApproverExist.get();
		logger.info("Approver is : "+app);
		approver.setCreationDate(app.getCreationDate());
		logger.info("Updated Approver is : "+approver);
		approverRepository.save(approver);
		logger.info("In ApproverServiceImpl update()--> END");
	}
}
