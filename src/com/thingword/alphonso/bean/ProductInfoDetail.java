package com.thingword.alphonso.bean;

import java.sql.Date;

public class ProductInfoDetail {
	private int Id;
    private String tasknumber;
    private String productcode;
    private Date date;
    private String invcode;
    private String invname;
    private String invstd;
    private String qty;
    private String bomqty;
    private String define28;
    private String linenum;
    private String workshop;
	public String getTasknumber() {
		return tasknumber;
	}
	public void setTasknumber(String tasknumber) {
		this.tasknumber = tasknumber;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getInvcode() {
		return invcode;
	}
	public void setInvcode(String invcode) {
		this.invcode = invcode;
	}
	public String getInvname() {
		return invname;
	}
	public void setInvname(String invname) {
		this.invname = invname;
	}
	public String getInvstd() {
		return invstd;
	}
	public void setInvstd(String invstd) {
		this.invstd = invstd;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getBomqty() {
		return bomqty;
	}
	public void setBomqty(String bomqty) {
		this.bomqty = bomqty;
	}
	public String getDefine28() {
		return define28;
	}
	public void setDefine28(String define28) {
		this.define28 = define28;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getLinenum() {
		return linenum;
	}
	public void setLinenum(String linenum) {
		this.linenum = linenum;
	}
	public String getWorkshop() {
		return workshop;
	}
	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}
}
