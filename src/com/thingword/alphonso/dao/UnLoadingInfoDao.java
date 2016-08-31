package com.thingword.alphonso.dao;

import java.util.List;

import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.UnLoadingInfo;


public interface UnLoadingInfoDao {
	public List<UnLoadingInfo> getUnLoadingInfoByDate(String Date,String person);  

}
