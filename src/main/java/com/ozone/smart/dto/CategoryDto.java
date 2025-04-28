package com.ozone.smart.dto;

public class CategoryDto {
	
	private int id;
	private String categoryname;
	private String parentcategory;
	private String menulink;
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
