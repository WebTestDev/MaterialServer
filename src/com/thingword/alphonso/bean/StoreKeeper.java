package com.thingword.alphonso.bean;

import java.sql.Date;

public class StoreKeeper {
	private int Id;
    private String storekeeper;
    private String identifier;
    private String spec;
    private String location;
    private String materialname;
    private String materialnumber;
    private String repository;
	public String getRepository() {
		return repository;
	}
	public void setRepository(String repository) {
		this.repository = repository;
	}
	public String getMaterialnumber() {
		return materialnumber;
	}
	public void setMaterialnumber(String materialnumber) {
		this.materialnumber = materialnumber;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getStorekeeper() {
		return storekeeper;
	}
	public void setStorekeeper(String storekeeper) {
		this.storekeeper = storekeeper;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getMaterialname() {
		return materialname;
	}
	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}
}
