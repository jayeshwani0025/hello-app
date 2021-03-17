
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

@Entity
@Table(name = "Seller_Product_Master")
@Data
public class SellerProductMaster {

	@Id
	@Column(name = "SELLER_PRODUCT_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long sellerProductId;

	@Column(name = "PRODUCT_ID")
	private Long productId;

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
	
	@Column(name = "SELLER_ID")
	private Long sellerId;
	
	@Column(name = "SELLER_COST_PRICE")
	private Long sellerPrice;
	
	@Column(name = "QUANTITY")
	private Long quantity;
				
	@Column(name = "LOCATION_ID")
	private Long locationId;
		
	//	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
//    @JoinColumn(name = "cart_id")
//    @JsonIgnoreProperties("sellerProductMaster") //Prevents infinite recursion
//    @JsonIgnore
//    private CartItem cartItems;

}
