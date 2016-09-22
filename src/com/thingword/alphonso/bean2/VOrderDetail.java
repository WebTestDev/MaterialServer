package com.thingword.alphonso.bean2;

import java.sql.Date;

public class VOrderDetail {
	private int MoDid;
	private String invName;
	private String invStd;
	private String mocode;
	private String invcode;
	private Date date;
	private String linenum;
	private String workshop;
	public int getMoDid() {
		return MoDid;
	}
	public void setMoDid(int moDid) {
		MoDid = moDid;
	}
	public String getInvName() {
		return invName;
	}
	public void setInvName(String invName) {
		this.invName = invName;
	}
	public String getInvStd() {
		return invStd;
	}
	public void setInvStd(String invStd) {
		this.invStd = invStd;
	}
	public String getMocode() {
		return mocode;
	}
	public void setMocode(String mocode) {
		this.mocode = mocode;
	}
	public String getInvcode() {
		return invcode;
	}
	public void setInvcode(String invcode) {
		this.invcode = invcode;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
