package com.thingword.alphonso.dao;

import java.util.List;

import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.ProductionInfo;
import com.thingword.alphonso.bean.User;

public interface ProductionInfoDao {
	public boolean updateProductionInfoList(List<ProductionInfo> ls, String date);
	
	public boolean deleteByDate(String Date);
	
	public List<ProductionInfo> getProductionInfoByDate(String Date);  
}
