package com.thingword.alphonso.dao;

import java.util.HashSet;
import java.util.List;

import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.StoreProductionInfo;
import com.thingword.alphonso.bean.UnLoadingInfo;


public interface UnLoadingInfoDao {
	public List<UnLoadingInfo> getUnLoadingInfoByDate(String Date,String person);  
	
	public List<UnLoadingInfo> getAllUnLoadingInfoByDate(String Date);  
	
	public HashSet<String> getUploadBatchInfoByDate(String Date); 
	
	public List<UnLoadingInfo> getUploadBatchInfoByDateAndUploadBatch(String Date,String uploadbatch); 
	
	public boolean updateUnLoadingInfoList(List<UnLoadingInfo> ls, String date,String batch);
	
	public boolean deleteUnLoadingInfoByDateAndBatch(String Date,String workshop);

}
