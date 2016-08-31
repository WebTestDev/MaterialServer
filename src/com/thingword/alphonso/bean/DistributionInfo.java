package com.thingword.alphonso.bean;

import java.sql.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;


public class DistributionInfo {
	private int Id;
    private Date date;
    private String cBatch;
    private String iQuantity;
    private String cInvStd;
    private String cInvName;
    private String cInvCode;
    private String shopnum;
    private String cMoCode;
    private String invcode;
    private String cinvstd_1;
	public int getId() {
		return Id;
	}
	@JsonIgnore
	public void setId(int id) {
		Id = id;
	}
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
	public String getShopnum() {
		return shopnum;
	}
	public void setShopnum(String shopnum) {
		this.shopnum = shopnum;
	}
	public String getcMoCode() {
		return cMoCode;
	}
	public void setcMoCode(String cMoCode) {
		this.cMoCode = cMoCode;
	}
	public String getInvcode() {
		return invcode;
	}
	public void setInvcode(String invcode) {
		this.invcode = invcode;
	}
	public String getCinvstd_1() {
		return cinvstd_1;
	}
	public void setCinvstd_1(String cinvstd_1) {
		this.cinvstd_1 = cinvstd_1;
	}

}
