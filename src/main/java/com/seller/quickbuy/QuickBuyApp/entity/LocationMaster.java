package com.seller.quickbuy.QuickBuyApp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;

@Entity
@Table(name = "Location_Master")
@Data
public class LocationMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOCATION_ID")
	private Long locationId;
	
	@NotEmpty(message = "Location can't be empty")
	@NotNull(message = "Location can't be null")
	@Column(name = "LOCATION_NAME",unique = true)
	private String locationName;
	
	@NotEmpty(message = "Address can't be empty")
	@Column(name = "LOCATION_ADDERSS")
	private String locationAddress;
	
	@NotEmpty(message = "City can't be empty")
	@Column(name = "CITY")
	private String city;
	
	@NotEmpty(message = "State can't be empty")
	@Column(name = "STATE")
	private String state;
	
	@NotEmpty(message = "Country can't be empty")
	@Column(name = "COUNTRY")
	private String country;
	
	//@NonNull
	@Column(name = "ZIP_CODE")
	private Integer zipCode;
	
//	@NotEmpty(message = "Created By can't be empty")
	@Column(name = "CREATED_BY")
	private String createdBy;
	
//	@NotEmpty(message = "Last Updated By can't be empty")
	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@UpdateTimestamp
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;
	
	@CreationTimestamp
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
//	@NotEmpty(message = "Last Updated Login can't be empty")
	@Column(name = "LAST_UPDATED_LOGIN")
	private String lastUpdatedLogin;
}
