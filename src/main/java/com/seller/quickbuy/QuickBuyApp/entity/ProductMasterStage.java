
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
@Table(name="PRODUCT_MASTER_STAGE")
@Data
public class ProductMasterStage implements Importable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUCT_ID")
	private Long productId;
	
	@Column(name = "CATEGORY_ID")
	 @CsvBindByName
	 @NotNull
	private Long categoryId;
	 
	@Column(name = "PRODUCT_DESCRIPTION")
	 @CsvBindByName
	 @NotEmpty
	private String productDescription;
	
	@Column(name = "PRODUCT_ICON")
	 @CsvBindByName
	 @NotEmpty
	private String productIcon;
	
	@Column(name = "PRODUCT_NAME",unique = true)
	 @CsvBindByName
	 @NotEmpty
	private String productName;
	
	@Column(name = "CREATED_BY")
	 @CsvBindByName
	 @NotEmpty
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY")
	 @CsvBindByName
	 @NotEmpty
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATE_DATE")
	@UpdateTimestamp
	private Date lastUpdateDate;
	
	@Column(name = "CREATION_DATE")
	@CreationTimestamp
	private Date creationDate;
	
	@Column(name = "LAST_UPDATED_LOGIN")
	 @CsvBindByName
	 @NotEmpty
	private String lastUpdatedLogin;
	
	@Column(name = "PRODUCT_UPDATE_STATUS")
	private String productUpdateStatus = "P";

	
	

	public ProductMasterStage() {
		
	}

    public ProductMasterStage(@NotNull Long categoryId, @NotEmpty String productDescription,
			@NotEmpty String productIcon, @NotEmpty String productName, @NotEmpty String createdBy,
			@NotEmpty String lastUpdatedBy, @NotEmpty String lastUpdatedLogin) {
		super();
		this.categoryId = categoryId;
		this.productDescription = productDescription;
		this.productIcon = productIcon;
		this.productName = productName;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedLogin = lastUpdatedLogin;
		
	}
	}


