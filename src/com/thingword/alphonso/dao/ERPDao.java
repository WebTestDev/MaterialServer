package com.thingword.alphonso.dao;

import java.util.List;

import com.thingword.alphonso.bean.ProductInfoDetail;
import com.thingword.alphonso.bean.ProductionInfo;
import com.thingword.alphonso.bean.StoreProductionInfo;
import com.thingword.alphonso.bean.UnLoadingInfo;
import com.thingword.alphonso.bean2.RdRecord;
import com.thingword.alphonso.bean2.VOrderDetail;


/**
 * User Dao 接口
 * @author waylau.com
 * 2014-3-18
 */  
public interface ERPDao {  

	//根据cPsPcode cMPoCode 获取ID；
    public List<RdRecord> getRdRecord(List<StoreProductionInfo> ls);  
    
	//根据cPsPcode cMPoCode 获取ID；
    public List<UnLoadingInfo> getRdRecords(List<RdRecord> ls);  
    
	//根据cPsPcode cMPoCode 获取ID；
    public List<UnLoadingInfo> updateUnLoadingInfo(List<UnLoadingInfo> ls);  
    
	//根据cPsPcode cMPoCode 获取ID；
    public List<VOrderDetail> getVOrderDetail(List<ProductionInfo> ls);  
    
	//根据cPsPcode cMPoCode 获取ID；
    public List<ProductInfoDetail> getProductInfoDetail(List<VOrderDetail> ls);  
    
}