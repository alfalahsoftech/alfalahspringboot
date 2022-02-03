package com.alfalahsoftech.inv.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator(name="EOMediSold_SEQ", allocationSize=1,sequenceName="EOMediSold_SEQ")
public class EOMediSold extends AFMainEntity {
	private static final long serialVersionUID = 1L;

//	@Column(insertable=false,updatable=false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="EOMediSold_SEQ")
	private Long primaryKey;
	private Integer stateCode;
	private String gstNo;
	private String clientID;
	private String clientName;
	private String address;
	private String contactNo;
	private String itemID;
	private String mediName;
	private String type;
	private String baseUnit;
	private Double unitCost;
	@Temporal(TemporalType.DATE)
	private Date orderDate = new Date();;
	private Double gstPerc;
	private Double costPrice;
	private Double salingPrice;
	@Temporal(TemporalType.TIMESTAMP)
	public Date soldOn = new Date();
	public String notes;
	private Integer quantity= new Integer(0);
	private String payMode;
	private String pack;
	private Double subTotal;
	@Temporal(TemporalType.DATE)
	private Date expDate;
	private String mfgBy;
	private Double discount;
	private String scheme;
	private String rcptNo;
	
	@ManyToOne
	(fetch=FetchType.LAZY)
	@JoinColumn(name="eoClientPK")
	private EOClient eoClient;
	
	
	public Date getSoldOn() {
		return soldOn;
	}

	public void setSoldOn(Date soldOn) {
		this.soldOn = soldOn;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public Integer getStateCode() {
		return stateCode;
	}

	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}
	
	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public Long primaryKey() {
		return this.primaryKey;
	}
	
	public String getMediName() {
		return mediName;
	}

	public void setMediName(String mediName) {
		this.mediName = mediName;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EOClient getEoClient() {
		return this.eoClient;
	}

	public void setEoClient(EOClient eoClient) {
		this.eoClient = eoClient;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getBaseUnit() {
		return baseUnit;
	}

	public void setBaseUnit(String baseUnit) {
		this.baseUnit = baseUnit;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Double getGstPerc() {
		return gstPerc;
	}

	public void setGstPerc(Double gstPerc) {
		this.gstPerc = gstPerc;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public Double getSalingPrice() {
		return salingPrice;
	}

	public void setSalingPrice(Double salingPrice) {
		this.salingPrice = salingPrice;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(Long primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public String getMfgBy() {
		return mfgBy;
	}

	public void setMfgBy(String mfgBy) {
		this.mfgBy = mfgBy;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getRcptNo() {
		return rcptNo;
	}

	public void setRcptNo(String rcptNo) {
		this.rcptNo = rcptNo;
	}



	
	
	

}
