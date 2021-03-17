package com.seller.quickbuy.QuickBuyApp.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="USER_CART")
@ToString
public class UserCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CART_ID")
	private long cartId;

	@OneToOne
    @JoinColumn(name = "USER_ID")
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
//    @NotNull
    private List<CartItem> cartItemList;
//	@Transient
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "userCart")
//	@JsonIgnore
//	private Set<OrderLines> orderItems = new HashSet<>();

	

	@Column(name = "total_price")
    private Float totalPrice;

    @Column(name = "total_cargo_price")
    private Float totalCargoPrice;
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="CREATION_DATE")
//	@CreationTimestamp
	private Date createdDate;
	@Column(name="LAST_UPDATED_BY")
	private String updatedBy;
	@Column(name="LAST_UPDATE_DATE")
	private Date updatedDate;
	@Column(name="TOTAL_QUANTITY")
	private Long itemQuantity;
//	@Column(name="LAST_UPDATED_LOGIN")
//	private User lastUpdatedLogin;
//	@Transient
//	List<ProductMaster> productMaster;
//	@Column(name="TOTAL_QUANTITY")
	
	@Column(name="CART_ORDER_STATUS")
	private String cartOrderStatus = "P";
	

//	@Override
//	public String toString() {
//		return "UserCart{" + "cartId=" + cartId + ", orderItems=" + orderItems + '}';
//	}

	public UserCart(User user) {
		this.user = user;
	}

}
