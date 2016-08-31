package com.thingword.alphonso.bean;

import javax.xml.bind.annotation.XmlRootElement;  
/* *
 * ”√ªß bean
 * @author waylau.com
 * 2014-3-17
 */  
@XmlRootElement  
public class User {  
	private int userID;
    private String username;  
    private String passwd;  
    private String authority;
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}  

}