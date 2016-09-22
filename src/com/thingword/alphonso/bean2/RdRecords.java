package com.thingword.alphonso.bean2;

import javax.persistence.Column;

/* *
 * 用户 bean
 * @author waylau.com
 * 2014-3-17
 */  
public class RdRecords {  
	private int AutoID;
	private int ID;
    private String cInvCode; //产品编码\ 
    private float iQuantity; //任务单号
	private String cBatch;//类型
	public int getAutoID() {
		return AutoID;
	}
	public void setAutoID(int autoID) {
		AutoID = autoID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getcInvCode() {
		return cInvCode;
	}
	public void setcInvCode(String cInvCode) {
		this.cInvCode = cInvCode;
	}

	public String getcBatch() {
		return cBatch;
	}
	public void setcBatch(String cBatch) {
		this.cBatch = cBatch;
	}
	public float getiQuantity() {
		return iQuantity;
	}
	public void setiQuantity(float iQuantity) {
		this.iQuantity = iQuantity;
	}
	
}