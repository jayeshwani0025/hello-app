package com.seller.quickbuy.QuickBuyApp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.opencsv.bean.CsvBindByName;
import com.seller.quickbuy.QuickBuyApp.repository.Importable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author jyoti.bhosale
 *
 */

/**
 * @author shivangi.bajpai
 *
 */
@Entity
@Table(name="Product_Master")
@Data
public class ProductMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUCT_ID")
	private Long productId;
	
	@Column(name = "CATEGORY_ID")
	private Long categoryId;
	 
	@Column(name = "PRODUCT_DESCRIPTION")
	private String productDescription;
	
	@Column(name = "PRODUCT_ICON")
    private String productIcon;
	
	@Column(name = "PRODUCT_NAME",unique = true)
	private String productName;
	
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
	private String catName;
	@Transient
	private Long productPrice;
	@Transient
	private Long sellerInvId;
	


}
