package com.miniproject.interviewcode.model.request;

import javax.xml.bind.annotation.XmlElement;

public class UserManagementRequest {
	
	 @XmlElement(name = "name", required = false)
	 public String name;

	 @XmlElement(name = "pageNo", required = false)
	 public int pageNo;
	    
	 @XmlElement(name = "pageSize", required = false)
	 public int pageSize;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	 
	 
	 


}
