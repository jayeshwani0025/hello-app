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
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author jyoti.bhosale
 *
 */
@Entity
@Table(name = "APPROVERS")
@Data
@Getter
@Setter
public class Approver {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "APPROVER_ID")
	private Long approverId;
	
	//@PrePersist
	@Column(name = "APPROVER_STATUS")//,columnDefinition = "varchar2(20) default 'N'",insertable = false)
	private String approverStatus;
	
	@Column(name = "APPROVER_NAME")
	private String approverName;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "USER_EMAIL",unique = true)
	private String userEmail;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "PHONE")
	private Long phone;
	
	@Column(name = "ZIP")
	private String zip;
	
	@Column(name = "COUNTRY")
	private String country;
	
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

	public Long getApproverId() {
		return approverId;
	}

	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}

	public String getApproverStatus() {
		return approverStatus;
	}

	public void setApproverStatus(String approverStatus) {
		this.approverStatus = approverStatus;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastUpdatedLogin() {
		return lastUpdatedLogin;
	}

	public void setLastUpdatedLogin(String lastUpdatedLogin) {
		this.lastUpdatedLogin = lastUpdatedLogin;
	}
	
	
}
