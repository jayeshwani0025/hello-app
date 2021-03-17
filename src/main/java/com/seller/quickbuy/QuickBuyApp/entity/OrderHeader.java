package com.seller.quickbuy.QuickBuyApp.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "ORDER_HEADERS_ALL")
@ToString(exclude = "user")
public class OrderHeader {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "HEADER_ID")
	private Long headerId;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
//	@JsonIgnoreProperties("orderList") //Prevents infinite recursion
    @JsonIgnore
    private User user;
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderLines> orderDetailsList;
	
	@OneToOne
    @JoinColumn(name = "contract_id")
    private SellerContract orderDiscount;

//	@Transient
//	@OneToMany(cascade = CascadeType.ALL,  fetch = FetchType.LAZY, mappedBy = "orderHeader")
//   private Set < OrderLines > orderLines = new HashSet<>();
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ORDER_NUMBER")
	private Long orderNumber;
	
	@Column(name = "ORDER_TYPE_ID")
	private Long orderTypeId;
	
	@CreationTimestamp
	@Column(name = "ORDERED_DATE")
	private Date orderedDate;
	
    @Column(name = "PACKING_INSTRUCTIONS")
	private String packingInstructions;
    
    @Column(name = "PAYMENT_AMOUNT")
	private BigDecimal paymentAmount;
    
    @Column(name = "PAYMENT_TYPE_ID")
	private Long paymentTypeId;
    
    @Column(name = "SHIP_TO_CONTACT_ID")
	private Long shipToContactId;
    
    @Column(name = "ORDER_STATUS")
	private String orderStatus;
    
    @Column(name = "ORDER_DOCUMENT", length=100000)
	@Lob
	private byte[] orderDocument;
    
    @Column(name = "ADDITIONAL_DOCUMENT", length=100000)
	@Lob
	private byte[] additionalDocument;
    
    @Column(name = "ITEM_QUANTITY")
	private Long itemQuantity;
    
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
	
    @Column(name = "SHIP_TO_ADDRESS")
    private String shipToAddress;
    @Column(name = "BILL_TO_ADDRESS")
    private String billToAddress ;
     
     
//    public OrderHeader(User buyer) {
//    	this.shipToAddress = buyer.getAddress() + ", " + buyer.getCity() + ", " + buyer.getCountry() + ", " + buyer.getEmail() + ", "
//    			+ buyer.getPhone() + ", " + buyer.getState() + "" + buyer.getZipCode(); 
//    	this.orderStatus = "New";
////    	 this.paymentAmount = buyer.getUserCart().getOrderItems().stream().map(item -> item.getProductPrice().multiply(new BigDecimal(item.getCount())))
////                 .reduce(BigDecimal::add)
////                 .orElse(new BigDecimal(0));
////         this.orderStatus = "New";
//
//    }
}