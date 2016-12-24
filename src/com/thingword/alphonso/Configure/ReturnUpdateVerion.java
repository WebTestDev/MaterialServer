package com.thingword.alphonso.Configure;

import com.thingword.alphonso.bean.UpdateVeriosn;

public class ReturnUpdateVerion {
	private String return_msg;
	private String return_code;
	private UpdateVeriosn version;
	
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public UpdateVeriosn getVersion() {
		return version;
	}
	public void setVersion(UpdateVeriosn version) {
		this.version = version;
	}
}
