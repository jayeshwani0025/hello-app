package com.seller.quickbuy.QuickBuyApp.entity;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonRootName;

//import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

/**
 * 
 * @author jyoti.bhosale
 *
 */
@Entity
@Table(name = "PO_HEADERS")
@Data
@JsonRootName("PoHeader")  
public class PoHeader {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PO_HEADER_ID")
	private Long poHeaderId;
	
	@Generated(GenerationTime.INSERT)
	@Column(name = "PO_NUMBER",nullable = false,columnDefinition = "UNIQUEIDENTIFIER")
	private BigInteger poNumber;
	
	@Column(name = "SUPPLIER_DTL")
	private String supplierDtl;
	
	@Column(name = "SHIP_TO_DTL")
	private String shipToDtl;
	
	/*
	 * @Column(name = "EXPECTED_SHIP_DATE") private Date expectedShipDate;
	 */
	
	@Column(name = "EXPECTED_SHIP_DATE")
	private LocalDate expectedShipDate = LocalDate.now().plusDays(15);
	
	@CreationTimestamp
	@Column(name = "ORDERED_DATE")
	private Date orderedDate;
	
	@Column(name = "PO_STATUS" ,columnDefinition = "varchar2(255) default 'N'",insertable = false)
	private String poStatus;
	
	@Column(name = "APPROVER_ID")
	private Long approverId;
	
	@Column(name = "REJECT_REMARKS")
	private String rejectRemarks;
	
	@Column(name = "TOTAL_COST")
	private Long totalCost;
	
	@Column(name = "PO_NOTES")
	//@JsonProperty("poNotes")
	@Lob
	private byte[] poNotes;
	
	@Column(name = "ATTRIBUTE_1")
	private String fileName;
	
	@Column(name = "ATTRIBUTE_2")
	private String fileType;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@UpdateTimestamp
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;
	
	@CreationTimestamp
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	@Column(name = "LAST_UPDATED_LOGIN")
	private String lastUpdatedLogin;
	
	@Column(name = "CREATED_USER_EMAIL")
	private String createdUserEmail;
	
	@Column(name = "IS_NOTIFIED",columnDefinition = "varchar2(60) default 'N'",insertable = false)
	private String isNotified;
	
	@Column(name = "SELLER_ID")
	private Long sellerId;
	
	@Column(name = "PO_NOTE")
	private String poNote;
	
	@Column(name = "LOCATION_ID")
	private Long locationId;
}
