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
/**
 * 
 * @author jyoti.bhosale
 *
 */
@Entity
@Table(name="Product_Category")
@Data
public class ProductCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CATEGORY_ID")
	private Long categoryId;
	
	@NotEmpty(message = "Category Name can't be Empty")
	@NotNull(message = "Category Name can't be null")
	@Column(name = "CATEGORY_NAME")
	private String categoryName;
	
	@NotEmpty(message = "Category Type can't be Empty")
	@NotNull(message = "Category Type can't be null")
	@Column(name = "CATEGORY_TYPE",unique = true)
	private String categoryType;
	
	
	@Column(name = "CATEGORY_SUBTYPE")
	private String categorySubType;
	
//	@NotEmpty(message = "Created By Name can't be Empty")
//	@NotNull(message = "Created By Name can't be null")
	@Column(name = "CREATED_BY")
	private String createdBy;
	
//	@NotEmpty(message = "Last Updated By Name can't be Empty")
//	@NotNull(message = "Last Updated By Name can't be null")
	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@UpdateTimestamp
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;
	
	@CreationTimestamp
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
//	@NotEmpty(message = "Last Updated Login Name can't be Empty")
//	@NotNull(message = "Last Updated Login Name can't be null")
	@Column(name = "LAST_UPDATED_LOGIN")
	private String lastUpdatedLogin;
	
}
