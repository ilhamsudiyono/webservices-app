package com.miniproject.interviewcode.model.store;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;





@Entity
@Table(name = "istore")
public class StoreInformation {

//	id (int): Listing ID (auto-generated)
//	user_id (int): ID of the user who created the listing (required)
//	price (int): Price of the listing. Should be above zero (required)
//	listing_type (str): Type of the listing. rent or sale (required)
//	created_at (int): Created at timestamp. In microseconds (auto-generated)
//	updated_at (int): Updated at timestamp. In microseconds (auto-generated)

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "user_id")
    private int userId;
	
    
	@Enumerated(EnumType.STRING)
	@Column(name = "listing_type")
    private TypeList listing_type;
 
	@Column(name = "price")
    private int price;


    @Column(name="created_at")
	private long createdAt;
	
	@Column(name="update_at")
    private long updateAt;
	
	  public StoreInformation() {

	  }

	  public StoreInformation(int userId, TypeList  listing_type, int price, long createdAt, long updateAt ) {
	    this.userId = userId;
	    this.listing_type = listing_type;
	    this.price = price;
	    this.createdAt = createdAt;
	    this.updateAt = updateAt;
	  }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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
