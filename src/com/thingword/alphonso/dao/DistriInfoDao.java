package com.thingword.alphonso.dao;

import java.util.List;

import com.thingword.alphonso.bean.DistributionInfo;
import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.UnLoadingInfo;


public interface DistriInfoDao {
	public List<DistributionInfo> getDistriInfoByDate(String Date,String person);  

}
