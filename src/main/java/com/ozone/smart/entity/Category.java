package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblcategory")
public class Category implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String categoryname;
	private String parentcategory;
	private String menulink;
	
	public Category() {		
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
	
	public String getCategoryname() {
		return categoryname;
	}
	
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	
	public String getParentcategory() {
		return parentcategory;
	}
	
	public void setParentcategory(String parentcategory) {
		this.parentcategory = parentcategory;
	}
	
	public String getMenulink() {
		return menulink;
	}
	
	public void setMenulink(String menulink) {
		this.menulink = menulink;
	}
}