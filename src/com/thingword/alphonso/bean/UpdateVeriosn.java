package com.thingword.alphonso.bean;

import org.codehaus.jackson.annotate.JsonIgnore;

public class UpdateVeriosn {
	private int Id;
    private String version;
    private String description;
    private String path;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public int getId() {
		return Id;
	}
	@JsonIgnore
	public void setId(int id) {
		Id = id;
	}

	public String getPath() {
		return path;
	}
	@JsonIgnore
	public void setPath(String path) {
		this.path = path;
	}
}
