
package com.seller.quickbuy.QuickBuyApp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.opencsv.bean.CsvBindByName;
import com.seller.quickbuy.QuickBuyApp.repository.Importable;

import lombok.Data;

@Entity
@Table(name = "Seller_Product_Inventory")
@Data
public class SellerProductInventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SELLER_INVENTORY_ID",unique = true, nullable = false)
	private Long sellerInventoryId;

	@Column(name = "SELLER_PRODUCT_ID")
	private Long sellerProductId;

	@Column(name = "LOCATION_ID")
	
	private Long locationId;

	@Column(name = "PRODUCT_STATUS")
	
	private String productStatus;

	@Column(name = "SELLER_PRICE")
	
	private Long sellerPrice;

	@Column(name = "ACTUAL_PRICE")

	private Long actualPrice;

	@Column(name = "QUANTITY")
	
	private Long quantity;

	@Column(name = "CREATED_BY")
	
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY")

	private String lastUpdatedBy;

	

	@Column(name = "LAST_UPDATE_DATE")
	@UpdateTimestamp
	private Date lastUpdateDate;

	@Column(name = "CREATION_DATE")
	@CreationTimestamp
	private Date creationDate;

	@Column(name = "LAST_UPDATED_LOGIN")
	private String lastUpdatedLogin;
	
	@Transient
	private String sellerName;
	

}
