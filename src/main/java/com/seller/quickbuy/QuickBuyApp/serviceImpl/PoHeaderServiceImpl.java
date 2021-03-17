package com.seller.quickbuy.QuickBuyApp.serviceImpl;


import java.io.IOException;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.seller.quickbuy.QuickBuyApp.entity.PoHeader;
import com.seller.quickbuy.QuickBuyApp.entity.User;
import com.seller.quickbuy.QuickBuyApp.repository.PoHeaderRepository;
import com.seller.quickbuy.QuickBuyApp.repository.UserRepository;
import com.seller.quickbuy.QuickBuyApp.service.PoHeaderService;


/**
 * 
 * @author jyoti.bhosale
 *
 */
@Service
public class PoHeaderServiceImpl implements PoHeaderService,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(PoHeaderServiceImpl.class);
	
	@Autowired
	PoHeaderRepository poHeaderRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public PoHeader save(PoHeader po,Principal principal) {
		logger.info("In PoHeaderServiceImpl save()--> START");
		Optional<User> user = userRepository.findByUsername(principal.getName());
		if(user.isPresent())
		{
			User usr = user.get();
			po.setCreatedBy(usr.getUsername());
			po.setCreatedUserEmail(usr.getEmail());
			po.setLastUpdatedBy(usr.getUsername());
			po.setLastUpdatedLogin(usr.getUsername());
		}
		po.setTotalCost((long) 0);
		PoHeader poHeader = poHeaderRepository.save(po);
		logger.info("In PoHeaderServiceImpl save()--> END");
		return poHeader;
	}

	@Override
	public void delete(Long poHeaderId) {
		
		logger.info("In PoHeaderServiceImpl delete()--> START");
		poHeaderRepository.deleteById(poHeaderId);
		logger.info("In PoHeaderServiceImpl delete()--> END");
	}

	@Override
	public void update(Optional<PoHeader> isPoExist,PoHeader po,Principal principal) {
		logger.info("In PoHeaderServiceImpl update()--> START");
		Optional<User> user = userRepository.findByUsername(principal.getName());
		if(user.isPresent())
		{
			User usr = user.get();
			po.setLastUpdatedBy(usr.getUsername());
			po.setLastUpdatedLogin(usr.getUsername());
		}
		PoHeader oldPoHeader = isPoExist.get();
		po.setOrderedDate(oldPoHeader.getOrderedDate());
		po.setCreationDate(oldPoHeader.getCreationDate());
		po.setPoNumber(oldPoHeader.getPoNumber());
		po.setPoStatus(oldPoHeader.getPoStatus());
		po.setIsNotified(oldPoHeader.getIsNotified());
		po.setExpectedShipDate(oldPoHeader.getExpectedShipDate());
		po.setPoStatus("N");
		poHeaderRepository.save(po);
		logger.info("In PoHeaderServiceImpl update()--> END");
	}

	@Override
	public List<PoHeader> findAll() {
		logger.info("In PoHeaderServiceImpl findAll()--> START");
		//List<PoHeader> poList = poHeaderRepository.findAll();
		List<PoHeader> poList = poHeaderRepository.findAllByOrderByPoHeaderIdDesc();
		logger.info("In PoHeaderServiceImpl findAll()--> END"+ poList);
		return poList;
	}

	@Override
	public Optional<PoHeader> findById(Long poHeaderId) {
		logger.info("In PoHeaderServiceImpl findById()--> START");
		Optional<PoHeader> po = poHeaderRepository.findById(poHeaderId);
		logger.info("In PoHeaderServiceImpl findById()--> END"+ po);
		return po;
	}

	@Override
	public boolean checkHasNullOrEmptyFeild(PoHeader po) {
		logger.info("In PoHeaderServiceImpl checkHasNullOrEmptyFeild()--> START");
		boolean hasNullOrEmptyFeild = (Stream.of(po.getSupplierDtl(),po.getShipToDtl(),po.getRejectRemarks(),po.getCreatedBy(),
												po.getLastUpdatedBy(),po.getLastUpdatedLogin()).
												anyMatch(feild-> feild==null || feild.isEmpty()));
		logger.info("In PoHeaderServiceImpl checkHasNullOrEmptyFeild()--> END");
		return hasNullOrEmptyFeild;
	}

	@Override
	public List<PoHeader> findByApproverId(Long approverId) {
		logger.info("In PoHeaderServiceImpl findByApproverId()--> START");
		List<PoHeader> poHeaderList = poHeaderRepository.findByApproverId(approverId);
		logger.info("In PoHeaderServiceImpl findByApproverId()--> END");
		return poHeaderList;
	}
	
	@Override
	public PoHeader save(PoHeader poHeader, MultipartFile file,Principal principal) throws IOException {
		logger.info("In PoHeaderServiceImpl save()--> START");
		Optional<User> user = userRepository.findByUsername(principal.getName());
		if(user.isPresent())
		{
			User usr = user.get();
			poHeader.setCreatedBy(usr.getUsername());
			poHeader.setCreatedUserEmail(usr.getEmail());
			poHeader.setLastUpdatedBy(usr.getUsername());
			poHeader.setLastUpdatedLogin(usr.getUsername());
		}
		poHeader.setPoNotes(file.getBytes());
		poHeader.setFileName(file.getOriginalFilename());
		poHeader.setFileType(file.getContentType());
		poHeader.setTotalCost((long) 0);
		PoHeader po = poHeaderRepository.save(poHeader);
		logger.info("In PoHeaderServiceImpl save()--> END");
		return po;
	}

	@Override
	public void update(Optional<PoHeader> isPoExist, PoHeader po, MultipartFile file,Principal principal) throws IOException {
		logger.info("In PoHeaderServiceImpl update()--> START");
		Optional<User> user = userRepository.findByUsername(principal.getName());
		if(user.isPresent())
		{
			User usr = user.get();
			po.setLastUpdatedBy(usr.getUsername());
			po.setLastUpdatedLogin(usr.getUsername());
		}
		PoHeader oldPoHeader = isPoExist.get();
		po.setOrderedDate(oldPoHeader.getOrderedDate());
		po.setCreationDate(oldPoHeader.getCreationDate());
		po.setPoNumber(oldPoHeader.getPoNumber());
		po.setPoStatus(oldPoHeader.getPoStatus());
		po.setIsNotified(oldPoHeader.getIsNotified());
		po.setExpectedShipDate(oldPoHeader.getExpectedShipDate());
		po.setPoNotes(file.getBytes());
		po.setFileName(file.getOriginalFilename());
		po.setFileType(file.getContentType());
		po.setPoStatus("N");
		poHeaderRepository.save(po);
		logger.info("In PoHeaderServiceImpl update()--> END");
	}
}

