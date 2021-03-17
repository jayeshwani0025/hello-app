package com.seller.quickbuy.QuickBuyApp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

/**
 * 
 * @author jyoti.bhosale
 *
 */
@Entity
@Table(name = "PO_LINES")
@Data
public class PoLine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PO_LINE_ID")
	private Long poLineId;
	
	
	@Column(name = "PO_HEADER_ID") 
	private Long poHeaderId;
	 
		
	/*
	 * @ManyToOne(optional = false)
	 * 
	 * @JoinColumn(name = "PO_HEADER_ID") private PoHeader poHeader;
	 */
	
	/*
	 * @Column(name = "LINE_NUMBER") private Long lineNumber;
	 * 
	 * @Column(name = "ITEM_ID") private Long itemId;
	 */
	
	/*
	 * @Column(name = "PO_NUMBER") private Long poNumber;
	 */
	
	@Column(name = "ITEM_NAME",unique = true)
	private String itemName;
	
	@Column(name = "QUANTITY")
	private Long quantity;
	
	@Column(name = "PRICE")
	private Long price;
	
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
}
