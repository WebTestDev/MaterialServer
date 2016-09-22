package com.thingword.alphonso.bean2;

import java.sql.Date;

import javax.persistence.Column;

/* *
 * 用户 bean
 * @author waylau.com
 * 2014-3-17
 */  
public class RdRecord {  
	private int ID;
    private String cPsPcode; //产品编码\ 
    private String cMPoCode; //任务单号
	private String cBusType;//类型
	private String cVouchType;
	private String cHandler;
	private String cMaker;
	private String linenum;
	private Date dVeriDate;
	private Date date;
	private String shop;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getcMPoCode() {
		return cMPoCode;
	}
	public void setcMPoCode(String cMPoCode) {
		this.cMPoCode = cMPoCode;
	}
	public String getcPsPcode() {
		return cPsPcode;
	}
	public void setcPsPcode(String cPsPcode) {
		this.cPsPcode = cPsPcode;
	}
	public String getcBusType() {
		return cBusType;
	}
	public void setcBusType(String cBusType) {
		this.cBusType = cBusType;
	}
	public String getcVouchType() {
		return cVouchType;
	}
	public void setcVouchType(String cVouchType) {
		this.cVouchType = cVouchType;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getShop() {
		return shop;
	}
	public void setShop(String shop) {
		this.shop = shop;
	}
	public Date getdVeriDate() {
		return dVeriDate;
	}
	public void setdVeriDate(Date dVeriDate) {
		this.dVeriDate = dVeriDate;
	}
	public String getcMaker() {
		return cMaker;
	}
	public void setcMaker(String cMaker) {
		this.cMaker = cMaker;
	}
	public String getcHandler() {
		return cHandler;
	}
	public void setcHandler(String cHandler) {
		this.cHandler = cHandler;
	}
	public String getLinenum() {
		return linenum;
	}
	public void setLinenum(String linenum) {
		this.linenum = linenum;
	}
}