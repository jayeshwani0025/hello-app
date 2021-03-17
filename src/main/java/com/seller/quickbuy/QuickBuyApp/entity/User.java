package com.seller.quickbuy.QuickBuyApp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ALL_USERS", uniqueConstraints = { @UniqueConstraint(columnNames = { "USER_NAME" }),
		@UniqueConstraint(columnNames = { "email" }) })
public class User implements Serializable {
	private static final long serialVersionUID = 4887904943282174032L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "USER_STATUS")
	private String userStatus;

	@NotBlank
	@Size(min = 3, max = 50)
	@Column(name = "USER_NAME")
	private String username;
	@NotBlank
	@Column(name = "FIRST_NAME")
	private String firstName;
	@Column(name = "MIDDLE_NAME")
	private String middleName;
	@NotBlank
	@Column(name = "LAST_NAME")
	private String lastName;

//    @NaturalId
	@Email
	@Column(name = "email")
	private String email;

	@NotBlank
//    @Size(min=6, max = 100)
	@Column(name = "PASSWORD")
	private String password;

	@NotBlank
	@Column(name = "CITY")
	private String city;
	@NotBlank
	@Column(name = "STATE")
	private String state;

//    @Size(min=6, max = 100)
	@Column(name = "ADDRESS")
	private String address;

	@NotBlank
	@Column(name = "PHONE")
	private String phone;

	@NotBlank
	@Column(name = "ZIP_CODE")
	private String zipCode;

//    @Size(min=6, max = 100)
	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATION_DATE")
	@CreationTimestamp
	private Date createdDate;

	@Column(name = "LAST_UPDATED_BY")
	private Date lastUpdatedBy;

	@Column(name = "LAST_UPDATE_DATE")
	@UpdateTimestamp
	private Date lastUpdatedDate;

	@Column(name = "LAST_UPDATED_LOGIN")
	private Date lastUpdatedLogin;
	@Column(name = "ATTRIBUTE_1")
	private String attrribute1;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    private Cart cart;

	public User() {
	}

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore // fix bi-direction toString() recursion problem
	private UserCart userCart;

	public User(String userName, String email, String firstName, String middleName, String lastName, String password,
			String city, String state, String address, String phone, String zipCode, String country) {
		this.username = userName;
		this.email = email;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.password = password;
		this.city = city;
		this.state = state;
		this.address = address;
		this.phone = phone;
		this.zipCode = zipCode;
		this.country = country;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAttrribute1() {
		return attrribute1;
	}

	public void setAttrribute1(String attrribute1) {
		this.attrribute1 = attrribute1;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Date lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Date getLastUpdatedLogin() {
		return lastUpdatedLogin;
	}

	public void setLastUpdatedLogin(Date lastUpdatedLogin) {
		this.lastUpdatedLogin = lastUpdatedLogin;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public UserCart getUserCart() {
		return userCart;
	}

	public void setUserCart(UserCart userCart) {
		this.userCart = userCart;
	}

}