package com.seller.quickbuy.QuickBuyApp.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SELLER_CONTRACTS")
//@JsonRootName("SellerContract")
@ToString(exclude = {"orderList", "cartList"})
public class SellerContract {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "CONTRACT_ID")
	private Long contractId;
	@Column(name = "CONTRACT_DETAILS")
	private String contractDetails;
	
//	@Column(name = "CONTRACT_DOCUMENT", length=100000)
	@Column(name = "CONTRACT_DOCUMENT")
	@Lob
	private byte[] contractDocument;
	
	@Column(name = "DISCOUNT_PERCENTAGE")
	private String discountPercentage;
	@Column(name = "CONTRACT_START_DATE")
	@CreationTimestamp
	private Date contractStartDate;
	@Column(name = "CONTRACT_END_DATE")
	@CreationTimestamp
	private Date contractEndDate;
	@Column(name = "CREATED_BY")
	private String createdBy;
	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;
	@Column(name = "CREATION_DATE")
	@CreationTimestamp
	private Date creationDate;
	@Column(name = "LAST_UPDATE_DATE")
	 @UpdateTimestamp
	private Date lastUpdateDate;
	@Column(name = "LAST_UPDATED_LOGIN")
	private String lastUpdatedLogin;
	@Column(name = "SELLER_ID")
	private Long sellerId;
	@Column(name = "ATTRIBUTE_1")
	private String fileName;
	@Column(name = "ATTRIBUTE_2")
	private String fileType;
	@Column(name = "ACTIVE_CONTRACT")
	private String activeContract="I";
   
	@OneToMany(mappedBy = "orderDiscount")
    @JsonIgnore
    private List<OrderHeader> orderList;

    @OneToMany(mappedBy = "cartDiscount")
    @JsonIgnore
    private List<CartItem> cartList;
}