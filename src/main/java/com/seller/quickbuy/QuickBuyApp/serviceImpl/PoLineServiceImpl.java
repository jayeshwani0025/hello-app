package com.seller.quickbuy.QuickBuyApp.serviceImpl;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seller.quickbuy.QuickBuyApp.entity.PoHeader;
import com.seller.quickbuy.QuickBuyApp.entity.PoLine;
import com.seller.quickbuy.QuickBuyApp.entity.User;
import com.seller.quickbuy.QuickBuyApp.repository.PoHeaderRepository;
import com.seller.quickbuy.QuickBuyApp.repository.PoLineRepository;
import com.seller.quickbuy.QuickBuyApp.repository.UserRepository;
import com.seller.quickbuy.QuickBuyApp.service.PoHeaderService;
import com.seller.quickbuy.QuickBuyApp.service.PoLineService;


/**
 * 
 * @author jyoti.bhosale
 *
 */
@Service
public class PoLineServiceImpl implements PoLineService {

	private static final Logger logger = LogManager.getLogger(PoLineServiceImpl.class);
	
	@Autowired
	private PoLineRepository poLineRepository;
	
	@Autowired
	private PoHeaderService poHeaderService;
	
	@Autowired
	private PoHeaderRepository poHeaderRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<PoLine> findAll() {
		
		logger.info("In PoLineServiceImpl findAll()--> START");
		List<PoLine> poLineList = poLineRepository.findAll();
		logger.info("In PoLineServiceImpl findAll()--> END "+poLineList);
		return poLineList;
	}

	@Override
	public Optional<PoLine> findById(Long poLineId) {
		logger.info("In PoLineServiceImpl findById()--> START");
		Optional<PoLine> poLine = poLineRepository.findById(poLineId);
		logger.info("In PoLineServiceImpl findById()--> END"+poLine);
		return poLine;
	}

	@Override
	public void delete(Long poLineId) {
		
		logger.info("In PoLineServiceImpl delete()--> START");
		Optional<PoLine> poLine = poLineRepository.findById(poLineId);
		Optional<PoHeader> poHeader = poHeaderRepository.findById(poLine.get().getPoHeaderId());
		poHeader.get().setTotalCost(poHeader.get().getTotalCost()-(poLine.get().getQuantity()*poLine.get().getPrice()));
		poLineRepository.deleteById(poLineId);
		logger.info("In PoLineServiceImpl delete()--> END");
	}

	@Override
	public boolean checkHasNullOrEmptyFeild(PoLine poLine) {
		logger.info("In PoLineServiceImpl checkHasNullOrEmptyFeild()--> START");
		boolean hasNullOrEmptyFeild = (Stream.of(poLine.getItemName(),poLine.getCreatedBy(),poLine.getLastUpdatedBy(),poLine.getLastUpdatedLogin()).
										anyMatch(feild-> feild==null || feild.isEmpty()));
		logger.info("In PoLineServiceImpl checkHasNullOrEmptyFeild()--> END");
		return hasNullOrEmptyFeild;
	}

	@Override
	public List<PoLine> findByItemName(String itemName) {
		logger.info("In PoLineServiceImpl findByItemName()--> START");
		List<PoLine> poLineList = poLineRepository.findByItemName(itemName);
		logger.info("In PoLineServiceImpl findByItemName()--> END");
		return poLineList;
	}

	@Override
	public void save(PoLine poLine,Principal principal) {
		
		logger.info("In PoLineServiceImpl save()--> START");
		Optional<User> user = userRepository.findByUsername(principal.getName());
		if(user.isPresent())
		{
			User usr = user.get();
			poLine.setCreatedBy(usr.getUsername());
			poLine.setLastUpdatedBy(usr.getUsername());
			poLine.setLastUpdatedLogin(usr.getUsername());
		}
		PoLine newPoLine = poLineRepository.save(poLine);
		if(newPoLine != null)
		{
			Optional<PoHeader> poHeader = poHeaderService.findById(newPoLine.getPoHeaderId());
			if(poHeader.isPresent())
			{
				PoHeader po = poHeader.get();
				po.setTotalCost(po.getTotalCost()+(newPoLine.getQuantity()*newPoLine.getPrice()));	
				poHeaderRepository.save(po);
			}
		}
		logger.info("In PoLineServiceImpl save()--> END");
	}

	@Override
	public void update(Optional<PoLine> isPoLineExist,PoLine poLine,Principal principal) {
		
		logger.info("In PoLineServiceImpl update()--> START");
		PoLine oldPoLine = isPoLineExist.get();
		poLine.setCreationDate(oldPoLine.getCreationDate());
		Optional<User> user = userRepository.findByUsername(principal.getName());
		if(user.isPresent())
		{
			User usr = user.get();
			poLine.setLastUpdatedBy(usr.getUsername());
			poLine.setLastUpdatedLogin(usr.getUsername());
		}
		PoLine updatedPoLine = poLineRepository.save(poLine);
		if(updatedPoLine != null)
		{
			Optional<PoHeader> poHeader = poHeaderService.findById(updatedPoLine.getPoHeaderId());
			if(poHeader.isPresent())
			{
				PoHeader po = poHeader.get();
				po.setTotalCost((long) 0);
				List<PoLine> poLineList =poLineRepository.findBypoHeaderId(po.getPoHeaderId());
				if(!poLineList.isEmpty())
				{
					for (PoLine poLineItem : poLineList) {
						po.setTotalCost(po.getTotalCost()+(poLineItem.getQuantity()*poLineItem.getPrice()));
					}
				}
				poHeaderRepository.save(po);
			}
		}
		logger.info("In PoLineServiceImpl update()--> END");
	}

	@Override
	public PoLine findByPoHeaderIdAndItemName(Long poHeaderId, String itemName) {
		
		logger.info("In PoLineServiceImpl findByPoHeaderIdAndItemName()--> START");
		PoLine poLine = poLineRepository.findByPoHeaderIdAndItemName(poHeaderId, itemName);
		logger.info("In PoLineServiceImpl findByPoHeaderIdAndItemName()--> END");
		return poLine;
	}

	@Override
	public List<PoLine> findBypoHeaderId(Long poHeaderId) {
		logger.info("In PoLineServiceImpl findBypoHeaderId()--> START");
		List<PoLine> poLineList =poLineRepository.findBypoHeaderId(poHeaderId);
		logger.info("In PoLineServiceImpl findBypoHeaderId()--> END");
		return poLineList;
	}
}
