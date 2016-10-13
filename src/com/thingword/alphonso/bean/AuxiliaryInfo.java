package com.thingword.alphonso.bean;

import java.sql.Date;

public class AuxiliaryInfo {
	private int Id;
    private String invcode;
    private String invname;
    private String invstd;
    private String validityperiod;
    private String brand;
    private String origin;
    private String models;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
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
	public String getValidityperiod() {
		return validityperiod;
	}
	public void setValidityperiod(String validityperiod) {
		this.validityperiod = validityperiod;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getModels() {
		return models;
	}
	public void setModels(String models) {
		this.models = models;
	}
}
