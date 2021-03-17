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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.opencsv.bean.CsvBindByName;
import com.seller.quickbuy.QuickBuyApp.repository.Importable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "SELLER_MASTER")
public class SellerMaster {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "SELLER_ID",unique = true, nullable = false)
	private Long sellerId;
    @Column(name = "SELLER_NAME")
    private String sellerName;
    @Column(name = "SELLER_ADDRESS")
    private String sellerAddress;
    @Column(name = "SELLER_POINT_OF_CONTACT")
    private String sellerPointOfContact;
    @Column(name = "SELLER_CONTACT_NUMBER")
	private Long sellerContactNumber;
    @Column(name = "SELLER_EMAIL_ADDRESS")
    private String sellerEmailAddress;
    @Column(name = "CITY")
    private String city;
    @Column(name = "STATE")
    private String state;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "ZIP_CODE")
    private Long zipCode;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;
    @Column(name = "LAST_UPDATE_DATE")
    @UpdateTimestamp
	private Date lastUpdatedDate;
    @Column(name = "CREATION_DATE")
    @CreationTimestamp
    private Date creationDate;
    @Column(name = "LAST_UPDATED_LOGIN")
	private String lastUpdatedLogin;
    
    }