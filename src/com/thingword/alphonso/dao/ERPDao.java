package com.thingword.alphonso.dao;

import java.util.List;

import com.thingword.alphonso.bean.ProductInfoDetail;
import com.thingword.alphonso.bean.ProductionInfo;
import com.thingword.alphonso.bean.StoreProductionInfo;
import com.thingword.alphonso.bean.UnLoadingInfo;
import com.thingword.alphonso.bean2.RdRecord;
import com.thingword.alphonso.bean2.VOrderDetail;


/**
 * User Dao �ӿ�
 * @author waylau.com
 * 2014-3-18
 */  
public interface ERPDao {  

	//����cPsPcode cMPoCode ��ȡID��
    public List<RdRecord> getRdRecord(List<StoreProductionInfo> ls);  
    
	//����cPsPcode cMPoCode ��ȡID��
    public List<UnLoadingInfo> getRdRecords(List<RdRecord> ls);  
    
	//����cPsPcode cMPoCode ��ȡID��
    public List<UnLoadingInfo> updateUnLoadingInfo(List<UnLoadingInfo> ls);  
    
	//����cPsPcode cMPoCode ��ȡID��
    public List<VOrderDetail> getVOrderDetail(List<ProductionInfo> ls);  
    
	//����cPsPcode cMPoCode ��ȡID��
    public List<ProductInfoDetail> getProductInfoDetail(List<VOrderDetail> ls);  
    
}