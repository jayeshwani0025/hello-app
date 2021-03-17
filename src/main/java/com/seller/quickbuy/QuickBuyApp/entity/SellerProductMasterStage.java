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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.opencsv.bean.CsvBindByName;
import com.seller.quickbuy.QuickBuyApp.repository.Importable;

import lombok.Data;

@Entity
@JsonInclude(Include.NON_EMPTY)
@Table(name = "SELLER_PRD_MASTER_STAGE")
@Data
public class SellerProductMasterStage implements Importable{
	

	@Id
	@Column(name = "SELLER_PRODUCT_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long sellerProductId;

	@Column(name = "PRODUCT_ID")
	private Long productId;

	@Column(name = "CREATED_BY")
	@CsvBindByName
    @NotEmpty(message = "Created By Name can't be Empty")
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY")
	@CsvBindByName
    @NotEmpty(message = "Created By Name can't be Empty")
	private String lastUpdatedBy;

	@UpdateTimestamp
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@CreationTimestamp
	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@Column(name = "LAST_UPDATED_LOGIN")
	@CsvBindByName
    @NotEmpty(message = "Created By Name can't be Empty")
	private String lastUpdatedLogin;
	
	@Column(name = "SELLER_ID")
	private Long sellerId;
	
	@Column(name = "SELLER_COST_PRICE")
	@CsvBindByName
	@NotNull(message = "Created By Name can't be null")
	private Long sellerPrice;
	
	@Column(name = "QUANTITY")
	@CsvBindByName
	@NotNull(message = "Created By Name can't be null")
	private Long quantity;
				
	@Column(name = "LOCATION_ID")
	private Long locationId;
	
	@Column(name = "SELLER_PRD_STATUS")
	private String sellerPrdStatus ="P";
	
	@Column(name = "PRODUCT_NAME")
	@CsvBindByName
	@NotNull(message = "Product Name can't be null")
	private String productName;
	
	@Column(name = "LOCATION")
	@CsvBindByName
	@NotNull(message = "Location can't be null")
	private String location;
	
	@Column(name = "SELLER_NAME")
	@CsvBindByName
	@NotNull(message = "Seller Name By Name can't be null")
	private String sellerName;

	
	
    public SellerProductMasterStage() {
		
	}



	public SellerProductMasterStage(@NotEmpty(message = "Created By Name can't be Empty") String createdBy,
			@NotEmpty(message = "Created By Name can't be Empty") String lastUpdatedBy,
			@NotEmpty(message = "Created By Name can't be Empty") String lastUpdatedLogin,
			@NotNull(message = "Created By Name can't be null") Long sellerPrice,
			@NotNull(message = "Created By Name can't be null") Long quantity,
			@NotNull(message = "Product Name can't be null") String productName,
			@NotNull(message = "Location can't be null") String location,
			@NotNull(message = "Seller Name By Name can't be null") String sellerName) {
		super();
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedLogin = lastUpdatedLogin;
		this.sellerPrice = sellerPrice;
		this.quantity = quantity;
		this.productName = productName;
		this.location = location;
		this.sellerName = sellerName;
	}



	

}