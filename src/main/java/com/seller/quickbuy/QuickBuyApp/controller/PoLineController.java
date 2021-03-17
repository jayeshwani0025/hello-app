package com.seller.quickbuy.QuickBuyApp.controller;

import java.security.Principal;
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

import com.seller.quickbuy.QuickBuyApp.entity.PoHeader;
import com.seller.quickbuy.QuickBuyApp.entity.PoLine;
import com.seller.quickbuy.QuickBuyApp.entity.ProductMaster;
import com.seller.quickbuy.QuickBuyApp.entity.SellerProductMaster;
import com.seller.quickbuy.QuickBuyApp.message.response.ResponseMessage;
import com.seller.quickbuy.QuickBuyApp.service.PoHeaderService;
import com.seller.quickbuy.QuickBuyApp.service.PoLineService;
import com.seller.quickbuy.QuickBuyApp.service.ProductMasterService;
import com.seller.quickbuy.QuickBuyApp.service.SellerProductMasterService;



/**
 * 
 * @author jyoti.bhosale
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders="*")
public class PoLineController {

	private static final Logger logger = LogManager.getLogger(PoLineController.class);
	
	@Autowired
	private PoLineService poLineService;
	
	@Autowired
	private PoHeaderService poHeaderService;
	
	@Autowired
	private ProductMasterService productMasterService;
	
	@Autowired
	private SellerProductMasterService sellerProductMasterService;
	
	/**
	 * 
	 * @return All PoLines
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/poLines")
	public ResponseEntity<List<PoLine>> findAll(Principal principal)
	{
		logger.info("In PoLineController findAll()--> START");
		
		List<PoLine> poLineList = poLineService.findAll();
		if(poLineList.isEmpty())
		{
			return new ResponseEntity(new ResponseMessage("Fail -> No Records Found!"),HttpStatus.BAD_REQUEST);
		}
		logger.info("In PoLineController findAll()--> END");
		return new ResponseEntity<>(poLineList,HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param poLineId to be search
	 * @return PoLine of poLineId
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/poLines/{id}")
	public ResponseEntity<PoLine> findById(@PathVariable ("id") Long poLineId,Principal principal)
	{
		logger.info("In PoLineController findById()--> START");
		
		Optional<PoLine> poLine = poLineService.findById(poLineId);
		if(!poLine.isPresent())
		{
			return new ResponseEntity(new ResponseMessage("Fail -> No Record Found!"),HttpStatus.BAD_REQUEST);
		}
		logger.info("In PoLineController findById()--> END");
		return new ResponseEntity(poLine,HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/quickBuy/poLines/{poHeaderId}")
	public ResponseEntity<List<PoLine>> findBypoHeaderId(@PathVariable ("poHeaderId") Long poHeaderId,Principal principal)
	{
		logger.info("In PoLineController findBypoHeaderId()--> START");
		
		List<PoLine> poLineList = poLineService.findBypoHeaderId(poHeaderId);
		if(poLineList.isEmpty())
		{
			return new ResponseEntity(new ResponseMessage("Fail -> No Records Found!"),HttpStatus.BAD_REQUEST);
		}
		logger.info("In PoLineController findBypoHeaderId()--> END");
		return new ResponseEntity<>(poLineList,HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param poLineId to be delete
	 * @return Ok if record deleted otherwise Bad Request will return
	 */
	@DeleteMapping("/quickBuy/poLine/{id}/delete")
	public ResponseEntity<?> delete(@PathVariable ("id") Long poLineId,Principal principal)
	{
		logger.info("In PoLineController delete()......START");
		
		Optional<PoLine> poLine = poLineService.findById(poLineId);
		if(!poLine.isPresent())
		{
			return new ResponseEntity<>(new ResponseMessage("Fail -> No Record Found to be delete!"),HttpStatus.BAD_REQUEST);
		}
		poLineService.delete(poLineId);
		logger.info("In PoLineController delete()......END");
		return new ResponseEntity<>(new ResponseMessage("Record Deleted Successfully!"),HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param itemName to be search
	 * @return PoLine of itemName
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/poLines/byName/{name}")
	public ResponseEntity<List<PoLine>> findByItemName(@PathVariable ("name") String itemName,Principal principal)
	{
		logger.info("In PoLineController findByItemName()......START");
		
		List<PoLine> poLineList = poLineService.findByItemName(itemName);
		if(poLineList.isEmpty())
		{
			return new ResponseEntity(new ResponseMessage("Fail -> No Records Found!"),
										HttpStatus.BAD_REQUEST);
		}
		logger.info("In PoLineController findByItemName()......END");
		return new ResponseEntity(poLineList,HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param poLine to be add
	 * @return Ok if PoLine added otherwise BAD_REQUEST will return
	 */
	@PostMapping("/quickBuy/poLine/new")
	public ResponseEntity<?> create (@ Valid @RequestBody PoLine poLine,Principal principal)
	{
		logger.info("In PoLineController create()......START");
		
		/*boolean hasNullOrEmptyFeild = poLineService.checkHasNullOrEmptyFeild(poLine);
		if(hasNullOrEmptyFeild)
		{
			return new ResponseEntity<>(new ResponseMessage("Fail -> Please Check the feilds must not be null or empty!"),HttpStatus.BAD_REQUEST);
		}*/
		//else {
			//PoLine isPoLineExist = poLineService.findByItemName(poLine.getItemName());
			//List<ProductMaster> productMaster = productMasterService.findByProductName(poLine.getItemName());
			ProductMaster productMaster = null;
			try {
				productMaster = productMasterService.findOne(poLine.getItemName());
			} catch (Exception e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity<>(new ResponseMessage("Product Is Duplicate...."),HttpStatus.BAD_REQUEST);
			}
			Optional<PoHeader> poHeader = poHeaderService.findById(poLine.getPoHeaderId());
			if(poHeader.isPresent() && productMaster!=null)
			{
				
				Optional<SellerProductMaster> sellerProductMaster = 
								sellerProductMasterService.findBySellerIdAndProductIdAndLocationId(poHeader.get().getSellerId(),productMaster.getProductId(),poHeader.get().getLocationId());
				if(sellerProductMaster.isPresent())
				{
					PoLine isPoLineExist = poLineService.findByPoHeaderIdAndItemName(poLine.getPoHeaderId(),poLine.getItemName());
					if(isPoLineExist != null)
					{
						return new ResponseEntity<>(new ResponseMessage("Fail -> Item already present!"),
													HttpStatus.OK);
					}
					else {
						poLineService.save(poLine,principal);
						logger.info("In PoLineController create()......END");
						return new ResponseEntity<>(new ResponseMessage("PoLine Added Successfully!"),HttpStatus.OK);
					}
				}
				else {
					return new ResponseEntity<>(new ResponseMessage("Product for the seller is not exist."),HttpStatus.OK);
				}
			}
			else {
				 return new ResponseEntity<>(new ResponseMessage("Product is not exist."),HttpStatus.OK);
			}
			
		//}
		
	}
	
	/**
	 * 
	 * @param poLineId to be update
	 * @param poLine to be updated 
	 * @return Ok if poLine of poLineId updated successfully otherwise BAD_REQUEST will return
	 */
	@PutMapping("quickBuy/poLine/{id}/edit")
	public ResponseEntity<?> update(@PathVariable ("id") Long poLineId,
									@Valid @RequestBody PoLine poLine,Principal principal)
	{
		logger.info("In PoLineController update()--> Start");
		
		/*if(poLineId != poLine.getPoLineId())
		{
			return new ResponseEntity<>(new ResponseMessage("Fail -> Id not Matched!"),HttpStatus.BAD_REQUEST);
		}*/
		//else {
			Optional<PoLine> isPoLineExist = poLineService.findById(poLineId);
			if(!isPoLineExist.isPresent())
			{
				return new ResponseEntity<>(new ResponseMessage("Fail -> No Record Found to Update!"),HttpStatus.BAD_REQUEST);
			}
			else {
				/*
				 * boolean hasNullOrEmptyFeild = poLineService.checkHasNullOrEmptyFeild(poLine);
				 * if(hasNullOrEmptyFeild) { return new ResponseEntity<>(new
				 * ResponseMessage("Fail -> Please Check the feilds must not be null or empty!")
				 * ,HttpStatus.BAD_REQUEST); }
				 */
				poLineService.update(isPoLineExist,poLine,principal);
			}
		//}
		logger.info("In PoLineController update()--> END");
		return new ResponseEntity<>(new ResponseMessage("PoLine Updated Successfully!"),HttpStatus.OK);
	}
}
