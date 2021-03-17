package com.seller.quickbuy.QuickBuyApp.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seller.quickbuy.QuickBuyApp.entity.Approver;
import com.seller.quickbuy.QuickBuyApp.entity.PoHeader;
import com.seller.quickbuy.QuickBuyApp.message.response.ResponseMessage;
import com.seller.quickbuy.QuickBuyApp.service.ApproverService;
import com.seller.quickbuy.QuickBuyApp.service.PoHeaderService;



/**
 * 
 * @author jyoti.bhosale
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders="*")
public class ApproverController {

	private static final Logger logger = LogManager.getLogger(ApproverController.class);
	
	@Autowired
	private ApproverService approverService;
	
	@Autowired
	private PoHeaderService poHeaderService;
	
	/**
	 * 
	 * @return approver list in response if approvers are present otherwise BAD_REQUEST with "No Records Found" Message will return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/approvers")
	public ResponseEntity<List<Approver>> findAll()
	{
		logger.info("In ApproverController findAll()--> START");
		List<Approver> approverList = approverService.findAll();
		if(approverList.isEmpty())
		{
			return new ResponseEntity(new ResponseMessage("Fail -> No Records Found!"),HttpStatus.BAD_REQUEST);
		}
		logger.info("In ApproverController findAll()--> END");
		return new ResponseEntity<>(approverList,HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param approverId to be search
	 * @return if approver of approverId is present then approver will return otherwise BAD_REQUEST with "No Record Found" Message will return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/approvers/{id}")
	public ResponseEntity<Approver> findById(@PathVariable ("id") Long approverId)
	{
		logger.info("In ApproverController findById()--> START");
		Optional<Approver> approver = approverService.findById(approverId);
		if(!approver.isPresent())
		{
			return new ResponseEntity(new ResponseMessage("Fail -> No Record Found!"),HttpStatus.BAD_REQUEST);
		}
		logger.info("In ApproverController findById()--> END");
		return new ResponseEntity(approver,HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param approver to be add
	 * @return OK with "Approver added successfully" message if approver added otherwise BAD_REQUEST will return
	 */
	@PostMapping("/quickBuy/approver/new")
	public ResponseEntity<?> create(@Valid @RequestBody Approver approver)
	{
		logger.info("In ApproverController create()--> START");
		boolean hasNullOrEmptyFeild = approverService.checkHasNullOrEmptyFeild(approver);
		if(hasNullOrEmptyFeild)
		{
			return new ResponseEntity<>(new ResponseMessage("Fail -> Please Check the feilds must not be null or empty!"),HttpStatus.BAD_REQUEST);
		}
		else {
			Approver isApproverExist = approverService.findByEmail(approver.getUserEmail());
			if(isApproverExist != null)
			{
				return new ResponseEntity<>(new ResponseMessage("Fail -> User with email address already present!"),
											HttpStatus.BAD_REQUEST);
			}
			approverService.save(approver);
		}
		logger.info("In ApproverController create()--> END");
		return new ResponseEntity<>(new ResponseMessage("Approver added successfully"),HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param approverId to be delete
	 * @return OK with "Approver Deleted Successfully" message if approver deleted of approverId otherwise BAD_REQUEST will return
	 */
	@DeleteMapping("/quickBuy/approver/{id}/delete")
	public ResponseEntity<?> delete(@PathVariable ("id") Long approverId)
	{
		logger.info("In ApproverController delete()--> START");
		Optional<Approver> approver = approverService.findById(approverId);
		if(!approver.isPresent())
		{
			return new ResponseEntity<>(new ResponseMessage("Fail -> No Record Found to delete!"),HttpStatus.BAD_REQUEST);
		}
		else {
			List<PoHeader> poHeaderList = poHeaderService.findByApproverId(approverId);
			if(!poHeaderList.isEmpty())
			{
				return new ResponseEntity<>(new ResponseMessage("Fail -> Approver can not be delete because Child record is present!"),HttpStatus.BAD_REQUEST);
			}
			else {
				approverService.delete(approverId);
				logger.info("In ApproverController delete()--> END");
				return new ResponseEntity<>(new ResponseMessage("Approver Deleted Successfully!"),HttpStatus.OK);
			}
		}
	}
	
	/**
	 * 
	 * @param approverId to be update
	 * @param approver is Updated
	 * @return ok if approver of approverId updated otherwise BAD_REQUEST will return
	 */
	@PutMapping("quickBuy/approver/{id}/edit")
	public ResponseEntity<?> update(@PathVariable ("id") Long approverId,
									@Valid @RequestBody Approver approver)
	{
		logger.info("In ApproverController update()--> Start");
		if(approverId != approver.getApproverId())
		{
			return new ResponseEntity<>(new ResponseMessage("Fail -> Id not Matched!"),HttpStatus.BAD_REQUEST);
		}
		else {
			Optional<Approver> isApproverExist = approverService.findById(approverId);
			if(!isApproverExist.isPresent())
			{
				return new ResponseEntity<>(new ResponseMessage("Fail -> No Record Found to Update!"),HttpStatus.BAD_REQUEST);
			}
			else {
				boolean hasNullOrEmptyFeild = approverService.checkHasNullOrEmptyFeild(approver);
				if(hasNullOrEmptyFeild)
				{
					return new ResponseEntity<>(new ResponseMessage("Fail -> Please Check the feilds must not be null or empty!"),HttpStatus.BAD_REQUEST);
				}
				approverService.update(isApproverExist,approver);
			}
		}
		logger.info("In ApproverController update()--> END");
		return new ResponseEntity<>(new ResponseMessage("Approver Updated Successfully"),HttpStatus.OK);
	}
}


