package com.miniproject.interviewcode.model.request;

import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlElement;

public class SInformationRequest {

	 @XmlElement(name = "userId", required = false)
	 public String userId;

	 @XmlElement(name = "pageNo", required = false)
	 public int pageNo;
	    
	 @XmlElement(name = "pageSize", required = false)
	 public int pageSize;

	 @XmlElement(name = "listing_type", required = false)
	 public String listingType;

	 @XmlElement(name = "price", required = false)
	 public int price;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getListingType() {
		return listingType;
	}

	public void setListingType(String listingType) {
		this.listingType = listingType;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	 
	 
	 

}
