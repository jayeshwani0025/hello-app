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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.opencsv.bean.CsvBindByName;
import com.seller.quickbuy.QuickBuyApp.repository.Importable;

import lombok.Data;

@JsonInclude(Include.NON_EMPTY)
@Entity
@Data
@Table(name = "SELLER_MASTER_STAGE")
public class SellerMasterStage implements Importable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SELLER_ID")
	private Long sellerId;

	@Column(name = "SELLER_NAME")
	@CsvBindByName
	@NotEmpty
	private String sellerName;

	@Column(name = "SELLER_ADDRESS")
	@CsvBindByName
	@NotEmpty
	private String sellerAddress;

	@Column(name = "SELLER_POINT_OF_CONTACT")
	@CsvBindByName
	@NotEmpty
	private String sellerPointOfContact;

	@Column(name = "SELLER_CONTACT_NUMBER")
	@CsvBindByName
	@NotNull
	private Long sellerContactNumber;

	@Column(name = "SELLER_EMAIL_ADDRESS")
	@CsvBindByName
	@NotEmpty
	private String sellerEmailAddress;

	@Column(name = "CITY")
	@CsvBindByName
	@NotEmpty
	private String city;

	@Column(name = "STATE")
	@CsvBindByName
	@NotEmpty
	private String state;

	@Column(name = "COUNTRY")
	@CsvBindByName
	@NotEmpty
	private String country;

	@Column(name = "ZIP_CODE")
	@CsvBindByName
	@NotNull
	private Long zipCode;

	@Column(name = "CREATED_BY")
	@CsvBindByName
	@NotEmpty
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY")
	@CsvBindByName
	@NotEmpty
	private String lastUpdatedBy;

	@Column(name = "LAST_UPDATE_DATE")
	@UpdateTimestamp
	private Date lastUpdatedDate;

	@Column(name = "CREATION_DATE")
	@CreationTimestamp
	private Date creationDate;

	@Column(name = "LAST_UPDATED_LOGIN")
	@CsvBindByName
	@NotEmpty
	private String lastUpdatedLogin;

//     @Column(name = "SELLER_STATUS")
//     @CsvBindByName
//     @NotEmpty
//     private String sellerStatus;

	@Column(name = "SELLER_STATUS")
	private String sellerStatus = "P";

	public SellerMasterStage() {

	}

	public SellerMasterStage(@NotEmpty String sellerName, @NotEmpty String sellerAddress,
			@NotEmpty String sellerPointOfContact, @NotNull Long sellerContactNumber,
			@NotEmpty String sellerEmailAddress, @NotEmpty String city, @NotEmpty String state,
			@NotEmpty String country, @NotNull Long zipCode, @NotEmpty String createdBy, @NotEmpty String lastUpdatedBy,
			@NotEmpty String lastUpdatedLogin) {
		super();
		this.sellerName = sellerName;
		this.sellerAddress = sellerAddress;
		this.sellerPointOfContact = sellerPointOfContact;
		this.sellerContactNumber = sellerContactNumber;
		this.sellerEmailAddress = sellerEmailAddress;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipCode = zipCode;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedLogin = lastUpdatedLogin;

	}
}
