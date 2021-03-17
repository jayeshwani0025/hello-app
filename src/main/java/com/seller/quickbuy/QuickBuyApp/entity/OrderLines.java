package com.seller.quickbuy.QuickBuyApp.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ORDER_LINES_ALL")
public class OrderLines {
	
	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 @Column(name = "LINE_ID",unique = true, nullable = false)
	 private Long lineId;

	 
	 @Column(name = "SOURCE_DOCUMENT_ID")
	 private Long sourceDocumentId;
	 
	 @Generated(GenerationTime.INSERT)
	 @Column(name = "LINE_NUMBER", nullable = false,columnDefinition = "UNIQUEIDENTIFIER")
	 private Long lineNumber;
	 
	 @Column(name = "SCHEDULE_SHIP_DATE")
	 private LocalDate scheduleShipDate = LocalDate.now().plusDays(15);;
	 
	 @Column(name = "CANCELLED_QUANTITY")
	 private Long cancelledQuantity;
	 
	 @Column(name = "SHIPPED_QUANTITY")
	 private Long shippedQuantity;
	 
	 @Column(name = "ORDERED_QUANTITY")
	 private Long orderedQuantity;
	 
	 @Column(name = "INVOICED_QUANITITY")
	 private Long invoicedQuanitity;
	 
	 @Column(name = "SHIP_TO_ORG_ID")
	 private String shipToOrgId;
	 
	 @Column(name = "SELLER_INVENTORY_ID")
	 private Long sellerInventoryId;
	 
	 @Column(name = "CANCELLED_FLAG")
	 private String cancelledFlag;
	 
	 @Column(name = "BOOKED_FLAG")
	 private String bookedFlag;
	 
	 @Column(name = "FLOW_STATUS_CODE")
	 private String flowStatusCode;
	 
	 @Column(name = "TRANSACTION_QUANTITY")
	 private Long transactionQuantity;
	 
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

//	 @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
////   @JoinColumn(name = "USER_ID")
//    @JsonIgnore
//    private UserCart userCart;
	 
	@ManyToOne
    @JoinColumn(name = "header_id")
    @JsonIgnoreProperties("orderDetailsList")
    @JsonIgnore
    private OrderHeader order;
	    
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "HEADER_ID")
//    @JsonIgnore
//    private OrderHeader orderHeader;
////	 @Column(name = "HEADER_ID")
////	 @JoinColumn(name = "HEADER_ID")
////	  private Long headerId;
//	  
//	  public OrderLines(SellerProductInventory sellerProductInfo, Long quantity) {
//		  this.sellerInventoryId= sellerProductInfo.getSellerInventoryId();
//		  this.orderedQuantity = quantity;
//		  
//	  }
}

//this.productId = productInfo.getProductId();
//this.productName = productInfo.getProductName();
//this.productDescription = productInfo.getProductDescription();
//this.productIcon = productInfo.getProductIcon();
//this.categoryType = productInfo.getCategoryType();
//this.productPrice = productInfo.getProductPrice();
//this.productStock = productInfo.getProductStock();
//this.count = quantity;