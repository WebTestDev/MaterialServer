package com.thingword.alphonso.bean;

import java.sql.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;


public class LoadingInfo {
	private int Id;
    private Date date;
    private String cBatch;
    private String iQuantity;
    private String cInvStd;
    private String cInvName;
    private String cInvCode;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getcBatch() {
		return cBatch;
	}
	public void setcBatch(String cBatch) {
		this.cBatch = cBatch;
	}
	public String getiQuantity() {
		return iQuantity;
	}
	public void setiQuantity(String iQuantity) {
		this.iQuantity = iQuantity;
	}
	public String getcInvStd() {
		return cInvStd;
	}
	public void setcInvStd(String cInvStd) {
		this.cInvStd = cInvStd;
	}
	public String getcInvName() {
		return cInvName;
	}
	public void setcInvName(String cInvName) {
		this.cInvName = cInvName;
	}
	public String getcInvCode() {
		return cInvCode;
	}
	public void setcInvCode(String cInvCode) {
		this.cInvCode = cInvCode;
	}
	public int getId() {
		return Id;
	}
	@JsonIgnore
	public void setId(int id) {
		Id = id;
	}

}
