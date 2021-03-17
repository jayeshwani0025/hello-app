package com.seller.quickbuy.QuickBuyApp.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seller.quickbuy.QuickBuyApp.entity.LocationMaster;
import com.seller.quickbuy.QuickBuyApp.exception.ResourceNotFoundException;
import com.seller.quickbuy.QuickBuyApp.message.response.ResponseMessage;
import com.seller.quickbuy.QuickBuyApp.repository.LocationMasterRepository;
import com.seller.quickbuy.QuickBuyApp.service.LocationMasterService;

/**
 *
 * @author jyoti.bhosale
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class LocationMasterController {

	private static final Logger logger = LogManager.getLogger(LocationMasterController.class);

	@Autowired
	private LocationMasterService locationMasterService;

	@Autowired
	private LocationMasterRepository locationMasterRepository;

	/**
	 *
	 * @param locationMaster is new location to be saved
	 * @param bindingResult  if location is already exist
	 * @return ResponseEntity
	 * @throws Exception
	 */

	@PostMapping("/location/new")
	public ResponseEntity<?> create(@Valid @RequestBody LocationMaster locationMaster) throws Exception {
		logger.info("In LocationMasterController create()--> START");
		locationMasterService.save(locationMaster);
		logger.info("In LocationMasterController create()--> END");
		return new ResponseEntity<>(new ResponseMessage("Location Added successfully!"), HttpStatus.OK);
	}

	/**
	 *
	 * @return all Locations
	 */
	@GetMapping("/location")
	public List<LocationMaster> findAll() {
		logger.info("In LocationMasterController findAll()--> SYART");
		logger.info("In LocationMasterController findAll()--> END");
		return locationMasterService.getAll();
	}

	/**
	 *
	 * @param locationId
	 * @param location      to be update
	 * @param bindingResult
	 * @return
	 */
	@PutMapping("quickBuy/location/{id}/edit")
	public ResponseEntity edit(@Valid @RequestBody LocationMaster location, @PathVariable("id") Long locationId,
			BindingResult bindingResult) {
		logger.info("In locationMasterController edit()--> START");
		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(bindingResult);
		}
		if (locationId != location.getLocationId()) {
			return ResponseEntity.badRequest().body("Id not Matched");
		}
		logger.info("In LocationtMasterController edit()--> END");
		return ResponseEntity.ok(locationMasterService.update(location));
	}

	@GetMapping("/location/{locationId}")
	public ResponseEntity<LocationMaster> getLocationById(@PathVariable(value = "locationId") Long locationId)
			throws ResourceNotFoundException {
		LocationMaster locationMaster = locationMasterService.findByLocationId(locationId);

		return ResponseEntity.ok().body(locationMaster);
	}

	/**
	 *
	 * @param locationId to be deleted
	 * @return ResponseEntity
	 */
	@DeleteMapping("/location/{id}/delete")
	public ResponseEntity delete(@PathVariable("id") Long locationId) {
		logger.info("In LocationMasterController delete()--> START");
		locationMasterService.delete(locationId);
		logger.info("In LocationMasterController delete()--> END");
		return ResponseEntity.ok().build();
	}
}