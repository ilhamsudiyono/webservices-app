package com.miniproject.interviewcode.model.role;

import java.io.Serializable;
import javax.persistence.*;
import java.time.Instant;


@Entity
@Table(name = "m_role")
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(length = 100)
	private RoleName name;
	
	@Column(name = "created_date")
	private Instant createdDate = Instant.now();
	

	@Column(name = "created_by")
	private Long createdBy;
	
	
	@Column(name = "modified_by")
	private Integer modifiedBy;
	
	@Column(name = "modifiedDate")
	private Instant modifiedDate;

	

	public Role() {

	}

	public Role(RoleName name) {
		this.name = name;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RoleName getName() {
		return name;
	}

	public void setName(RoleName name) {
		this.name = name;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long long1) {
		this.createdBy = long1;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Instant getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Instant modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	

}