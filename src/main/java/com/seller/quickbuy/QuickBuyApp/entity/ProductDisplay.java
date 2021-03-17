package com.seller.quickbuy.QuickBuyApp.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Product_Master")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductDisplay {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("productList")
    private ProductCategory productCategory;

    @Column(name = "PRODUCT_NAME")
    @NotBlank
    private String productName;
    

    @Column(name = "price")
    private Float price;

    @Column(name = "sell_count")
    @JsonIgnore
    private Integer sellCount;

    @Column(name = "cargo_price")
    private Float cargoPrice;

    @Column(name = "PRODUCT_ICON")
    private String productIcon;

    @Column(name = "date_created", insertable = false)
    @JsonIgnore
    private String dateCreated;
    @Transient
    List<SellerProductMaster> sellerProductMaster; 
    @Transient
    List<SellerProductInventory> sellerPrdInventory;
    @Transient
    private Long productPrice;
    
    @Transient
    private List<String> sellerName;
    
}
