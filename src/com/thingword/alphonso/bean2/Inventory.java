package com.thingword.alphonso.bean2;

import javax.persistence.Column;

/* *
 * ”√ªß bean
 * @author waylau.com
 * 2014-3-17
 */  
public class Inventory {  
	private String  cInvCode;
	private String  cInvName;
	private String  cInvStd;
	private String  cInvDefine8;
	public String getcInvCode() {
		return cInvCode;
	}
	public void setcInvCode(String cInvCode) {
		this.cInvCode = cInvCode;
	}
	public String getcInvName() {
		return cInvName;
	}
	public void setcInvName(String cInvName) {
		this.cInvName = cInvName;
	}
	public String getcInvStd() {
		return cInvStd;
	}
	public void setcInvStd(String cInvStd) {
		this.cInvStd = cInvStd;
	}
	public String getcInvDefine8() {
		return cInvDefine8;
	}
	public void setcInvDefine8(String cInvDefine8) {
		this.cInvDefine8 = cInvDefine8;
	}
}