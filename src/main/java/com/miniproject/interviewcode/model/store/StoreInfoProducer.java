package com.miniproject.interviewcode.model.store;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.miniproject.interviewcode.model.user.User;




@Entity
@Table(name = "producer_istore")
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class StoreInfoProducer {

//	id (int): Listing ID (auto-generated)
//	user_id (int): ID of the user who created the listing (required)
//	price (int): Price of the listing. Should be above zero (required)
//	listing_type (str): Type of the listing. rent or sale (required)
//	created_at (int): Created at timestamp. In microseconds (auto-generated)
//	updated_at (int): Updated at timestamp. In microseconds (auto-generated)

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long store_id;

	@Enumerated(EnumType.STRING)
	@Column(name = "listing_type")
    private TypeList listing_type;
 
	@Column(name = "price")
    private int price;

    @Column(name="created_at")
	private long createdAt;
	
	@Column(name="update_at")
    private long updateAt;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User users;

	
	
	public StoreInfoProducer() {

	}

	  public StoreInfoProducer(User userId, TypeList  listing_type, int price, long createdAt, long updateAt ) {
	    this.users = userId;
	    this.listing_type = listing_type;
	    this.price = price;
	    this.createdAt = createdAt;
	    this.updateAt = updateAt;
	  }

	public Long getStore_id() {
		return store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	

	public User getUserId() {
		return users;
	}

	public void setUserId(User userId) {
		this.users = userId;
	}

	public TypeList getListing_type() {
		return listing_type;
	}

	public void setListing_type(TypeList listing_type) {
		this.listing_type = listing_type;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public long getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(long updateAt) {
		this.updateAt = updateAt;
	}

	  
	  

	  
	
	  
	
	


}
