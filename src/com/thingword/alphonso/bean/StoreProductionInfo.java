package com.thingword.alphonso.bean;

import java.sql.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;


public class StoreProductionInfo {
	private int Id;
    private String workshop;
    private String productline;
    private String tasknumber;
    private String productcode;
    private String spec;
    private String schedulednum;
    private String dailynum;
    private Date date;
    private String remark;
    private String processflow;
    private String boardcode;
    private String uploadbatch;
	public int getId() {
		return Id;
	}
	@JsonIgnore
	public void setId(int id) {
		Id = id;
	}
	public String getProductline() {
		return productline;
	}
	public void setProductline(String productline) {
		this.productline = productline;
	}

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
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getSchedulednum() {
		return schedulednum;
	}
	public void setSchedulednum(String schedulednum) {
		this.schedulednum = schedulednum;
	}
	public String getDailynum() {
		return dailynum;
	}
	public void setDailynum(String dailynum) {
		this.dailynum = dailynum;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getProcessflow() {
		return processflow;
	}
	public void setProcessflow(String processflow) {
		this.processflow = processflow;
	}
	public String getBoardcode() {
		return boardcode;
	}
	public void setBoardcode(String boardcode) {
		this.boardcode = boardcode;
	}
	public String getWorkshop() {
		return workshop;
	}
	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getUploadbatch() {
		return uploadbatch;
	}
	public void setUploadbatch(String uploadbatch) {
		this.uploadbatch = uploadbatch;
	}

}
