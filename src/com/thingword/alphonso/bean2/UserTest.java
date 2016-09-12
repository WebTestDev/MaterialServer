package com.thingword.alphonso.bean2;

import javax.xml.bind.annotation.XmlRootElement;  
/* *
 * ”√ªß bean
 * @author waylau.com
 * 2014-3-17
 */  
@XmlRootElement  
public class UserTest {  
	private int userID;
    private String user;
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}  

}