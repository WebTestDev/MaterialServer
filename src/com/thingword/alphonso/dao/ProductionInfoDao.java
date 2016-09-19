package com.thingword.alphonso.dao;

import java.util.List;

import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.ProductionInfo;
import com.thingword.alphonso.bean.StoreProductionInfo;
import com.thingword.alphonso.bean.User;

public interface ProductionInfoDao {
	public boolean updateProductionInfoList(List<ProductionInfo> ls, String date,String workshop);
	
	public boolean updateStoreProductionInfoList(List<StoreProductionInfo> ls, String date,String workshop);
	
	public boolean deleteByDate(String Date);
	
	public boolean deleteByDateAndWorkShop(String Date,String workshop);
	
	public boolean deleteStoreProductionInfoByDateAndBatch(String Date,String workshop);
	
	public List<ProductionInfo> getProductionInfoByDate(String Date);  
}
