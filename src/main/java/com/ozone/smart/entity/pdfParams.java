package com.ozone.smart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tblpdfparams")
public class pdfParams implements Serializable{

	private static final long serialVersionUID = 1L;
	private String field;
	private String xaxis;
	private String yaxis;
	
	public pdfParams() {		
	}
	
	@Id
	@Column(name="field")
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	public String getXaxis() {
		return xaxis;
	}
	
	public void setXaxis(String xaxis) {
		this.xaxis = xaxis;
	}
		
	public String getYaxis() {
		return yaxis;
	}
	
	public void setYaxis(String yaxis) {
		this.yaxis = yaxis;
	}
	
}