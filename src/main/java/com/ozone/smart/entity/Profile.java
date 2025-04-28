package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="tblprofile")

public class Profile implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String profile;
  	private String category;
  	
  	public Profile() {  		
  	}

  	
  	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	
    public String getProfile() {
       return profile;
    }

    public void setProfile(String profile) {
       this.profile = profile;
    }

    public String getCategory() {
       return category;
    }

    public void setCategory(String category) {
       this.category = category;
    }    
}
