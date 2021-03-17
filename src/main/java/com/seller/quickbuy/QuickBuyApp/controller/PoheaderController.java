package com.seller.quickbuy.QuickBuyApp.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.seller.quickbuy.QuickBuyApp.entity.PoHeader;
import com.seller.quickbuy.QuickBuyApp.entity.PoLine;
import com.seller.quickbuy.QuickBuyApp.message.response.ResponseMessage;
import com.seller.quickbuy.QuickBuyApp.service.PoHeaderService;
import com.seller.quickbuy.QuickBuyApp.service.PoLineService;

import oracle.net.aso.h;



/**
 * 
 * @author jyoti.bhosale
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders="*")
public class PoheaderController {

	private static final Logger logger = LogManager.getLogger(PoheaderController.class);
	
	@Autowired
	private PoHeaderService poHeaderService;
	
	@Autowired
	private PoLineService poLineService;
	
	/**
	 * 
	 * @param po to be add
	 * @return Ok if po added otherwise BAD_REQUEST will return
	 */
	@PostMapping("/poHeader/new")
	public ResponseEntity<Long> create(@Valid @RequestBody PoHeader po,Principal principal)
	{
		logger.info("In PoheaderController create()--> START");
		/*
		 * boolean hasNullOrEmptyFeild = poHeaderService.checkHasNullOrEmptyFeild(po);
		 * if(hasNullOrEmptyFeild) { return new ResponseEntity<>(new
		 * ResponseMessage("Fail -> Please Check the feilds must not be null or empty!")
		 * ,HttpStatus.BAD_REQUEST); }
		 */
		
		PoHeader poHeader =poHeaderService.save(po,principal);
		Long hederId = poHeader.getPoHeaderId();
		logger.info("In PoheaderController create()--> END");
		return new ResponseEntity<>(hederId, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param file to be upload
	 * @param purchaseOrder to be save
	 * @return Ok if po added otherwise BAD_REQUEST will return
	 * @throws IOException
	 */
	/*
	 * @SuppressWarnings({ "unchecked", "rawtypes" })
	 * 
	 * @PostMapping("/poHeader/new/fileUpload") //@RequestMapping(value =
	 * "/poHeader/new/fileUpload",method = RequestMethod.POST, consumes =
	 * MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<Long>
	 * save(@RequestParam ("file") MultipartFile file,@RequestBody PoHeader
	 * purchaseOrder) throws IOException {
	 * logger.info("In PoheaderController save()--> START"); PoHeader poHeader =
	 * purchaseOrder; System.out.println(purchaseOrder.getShipToDtl());
	 * poHeader.getFileName(); System.out.println(purchaseOrder); PoHeader po
	 * =poHeaderService.save(purchaseOrder,file); if(po == null) { return new
	 * ResponseEntity(new
	 * ResponseMessage("User not added...!"),HttpStatus.BAD_REQUEST); } else { Long
	 * hederId = po.getPoHeaderId();
	 * logger.info("In PoheaderController save()--> END"); return new
	 * ResponseEntity<>(hederId, HttpStatus.OK); } }
	 */
	
    /**
    *
    * @param file to be upload
    * @param purchaseOrder to be save
    * @return Ok if po added otherwise BAD_REQUEST will return
    * @throws IOException
    */
   @JsonSerialize
   @SuppressWarnings({ "unchecked", "rawtypes" })
   @PostMapping("/poHeader/new/fileUpload")
   public ResponseEntity<Long> save(@RequestParam ("file") MultipartFile file,@RequestParam ("purchaseOrder") String purchaseOrder,Principal principal) throws IOException
   {
       logger.info("In PoheaderController save()--> START");
       
       ObjectMapper obj = new ObjectMapper();
       //obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
       PoHeader poHeader = obj.readValue(purchaseOrder, PoHeader.class);
       PoHeader po =poHeaderService.save(poHeader,file,principal);
       if(po == null)
       {
           return new ResponseEntity(new ResponseMessage("Po not generated...!"),HttpStatus.BAD_REQUEST);
       }
       else {
           Long hederId = po.getPoHeaderId();
           logger.info("In PoheaderController save()--> END");
           return new ResponseEntity<>(hederId, HttpStatus.OK);
       }
   }

	
	/**
	 * 
	 * @return all po's
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/po")
	public ResponseEntity<List<PoHeader>> findAll(Principal principal)
	{
		logger.info("In PoheaderController findAll()--> START");
		
		List<PoHeader> poList = poHeaderService.findAll();
		if(poList.isEmpty())
		{
			return new ResponseEntity(new ResponseMessage("Fail -> No Records Found!"),HttpStatus.BAD_REQUEST);
		}
		logger.info("In PoheaderController findAll()--> END"+ poList);
		return new ResponseEntity(poList,HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param poHeaderId to be search
	 * @return po of poHeaderId
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/po/{id}")
	public ResponseEntity<PoHeader> findById(@PathVariable ("id") Long poHeaderId,Principal principal)
	{
		logger.info("In PoheaderController findById()--> START");
		
		Optional<PoHeader> po = poHeaderService.findById(poHeaderId);
		if(!po.isPresent())
		{
			return new ResponseEntity(new ResponseMessage("Fail -> No Record Found!"),HttpStatus.BAD_REQUEST);
		}
		logger.info("In PoheaderController findById()--> END"+ po);
		return new ResponseEntity(po,HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param poHeaderId to be delete
	 * @return ok if poHeaderId deleted otherwise BAD_REQUEST will return
	 */
	@DeleteMapping("/po/{id}/delete")
	public ResponseEntity<?> delete(@PathVariable ("id") Long poHeaderId,Principal principal)
	{
		logger.info("In PoheaderController delete()--> START");
		
		Optional<PoHeader> po = poHeaderService.findById(poHeaderId);
		if(!po.isPresent())
		{
			return new ResponseEntity<>(new ResponseMessage("Fail -> No Record Found to Delete!"),HttpStatus.OK);
		}
		else {
			List<PoLine> poLineList = poLineService.findBypoHeaderId(poHeaderId);
			if(!poLineList.isEmpty())
			{
				return new ResponseEntity<>(new ResponseMessage("Fail -> Po can not be delete because Child record is present!"),HttpStatus.OK);
			}
			else {
				poHeaderService.delete(poHeaderId);
				logger.info("In PoheaderController delete()--> END");
				return new ResponseEntity<>(new ResponseMessage("PO deleted successfully!"), HttpStatus.OK);
			}
		}
		
	}
	
	/**
	 * 
	 * @param poHeaderId to be update
	 * @param po is updated
	 * @return ok if po of poHeaderId is updared otherwise BAD_REQUEST will return
	 */
	@PutMapping("/quickBuy/po/{id}/edit")
	public ResponseEntity<?> update(@PathVariable("id") Long poHeaderId,
									@Valid @RequestBody PoHeader po,Principal principal)
	{
		logger.info("In PoheaderController update()--> START");
		
		/*if(poHeaderId != po.getPoHeaderId())
		{
			return new ResponseEntity<>(new ResponseMessage("PO id not match."), HttpStatus.BAD_REQUEST);
		}*/
		//else {
			Optional<PoHeader> isPoExist = poHeaderService.findById(poHeaderId);
			if(!isPoExist.isPresent())
			{
				return new ResponseEntity<>(new ResponseMessage("Fail -> No Record Found to Update!"),HttpStatus.BAD_REQUEST);
			}
			else {
				/*
				 * boolean hasNullOrEmptyFeild = poHeaderService.checkHasNullOrEmptyFeild(po);
				 * if(hasNullOrEmptyFeild) { return new ResponseEntity<>(new
				 * ResponseMessage("Fail -> Please Check the feilds must not be null or empty!")
				 * ,HttpStatus.BAD_REQUEST); }
				 */
				poHeaderService.update(isPoExist,po,principal);
				logger.info("In PoheaderController update()--> END");
				return new ResponseEntity<>(new ResponseMessage("PO updated successfully!"), HttpStatus.OK);
			}
		} 
	//}
	
	@PutMapping("/quickBuy/po/{id}/edit/fileUpload")
	public ResponseEntity<?> updateFile(@PathVariable("id") Long poHeaderId,
										@RequestParam ("file") MultipartFile file,
										@RequestParam ("purchaseOrder") String purchaseOrder,Principal principal) throws IOException
	{
		logger.info("In PoheaderController updateFile()--> START");
		
		ObjectMapper obj = new ObjectMapper();
		obj.registerModule(new JavaTimeModule());
		obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
	    PoHeader poHeader = obj.readValue(purchaseOrder, PoHeader.class);
	    System.out.println("Po after conversion"+poHeader);
		/*
		 * if(poHeaderId != poHeader.getPoHeaderId()) { return new ResponseEntity<>(new
		 * ResponseMessage("PO id not match."), HttpStatus.BAD_REQUEST); }
		 */
		//else {
			Optional<PoHeader> isPoExist = poHeaderService.findById(poHeaderId);
			if(!isPoExist.isPresent())
			{
				return new ResponseEntity<>(new ResponseMessage("Fail -> No Record Found to Update!"),HttpStatus.BAD_REQUEST);
			}
			else {
					poHeaderService.update(isPoExist,poHeader,file,principal);
					logger.info("In PoheaderController updateFile()--> END");
					return new ResponseEntity<>(new ResponseMessage("PO updated successfully!"), HttpStatus.OK);
			}
		}
//	}
}
