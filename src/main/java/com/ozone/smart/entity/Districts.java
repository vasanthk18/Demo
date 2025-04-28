package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tbldistricts")
public class Districts implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	
	public Districts() {		
	}
	
	@Id
	@Column(name="id")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
