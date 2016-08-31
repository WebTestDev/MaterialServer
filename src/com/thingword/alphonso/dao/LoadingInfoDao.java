package com.thingword.alphonso.dao;

import java.util.List;

import com.thingword.alphonso.bean.LoadingInfo;


public interface LoadingInfoDao {
	public List<LoadingInfo> getLoadingInfoByDate(String Date,String person);  

}
