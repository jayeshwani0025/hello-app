package com.seller.quickbuy.QuickBuyApp.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CartItem {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_ITEM_ID")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "cart_id")
    @JsonIgnoreProperties("cartItemList") //Prevents infinite recursion
    @JsonIgnore
    private UserCart cart;

    @ManyToOne //TODO one to many ??? many to many????
    @JoinColumn(name = "product_id")
    private ProductDisplay cartProduct;
    
    @ManyToOne
    @JoinColumn(name = "contract_id")
    private SellerContract cartDiscount;
//    @OneToMany(mappedBy = "cartItems", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
//    private List<SellerProductMaster> sellerProductMaster;

    @Column(name = "PRODUCT_PRICE")
    private Long amount;
    
    @Column(name = "ITEM_QUANTITY")
    private Long itemQuantity;
    
    @Column(name = "SELLER_INV_ID")
    private Long sellerInvId;
    
    @Column(name = "LOCATION_ID")
    private Long locationId;
    
    
    @Column(name = "ITEM_STATUS")
    private String cartItemStatus;
    
    @Column(name = "AVAILABLE_FLAG")
    private String availableFlag;
    
    @Transient
    private Long productQuantity;
    
}
