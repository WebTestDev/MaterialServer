package com.thingword.alphonso.dao;

import java.util.List;

import com.thingword.alphonso.bean.DistributionInfo;
import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.StoreKeeper;
import com.thingword.alphonso.bean.UnLoadingInfo;


public interface StoreKeeperDao {
	public List<StoreKeeper> getStoreKeeperList();  
	
	public List<UnLoadingInfo> getALLUnLoadingInfo(List<UnLoadingInfo> ls);  
	
	public boolean updateUnLoadingInfoList(List<StoreKeeper> ls);
	
	public boolean deleteUnLoadingInfo();
}
