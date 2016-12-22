package com.thingword.alphonso.service;

import com.thingword.alphonso.Configure.ReturnData;
import com.thingword.alphonso.bean.ProductInfoDetail;
import com.thingword.alphonso.bean.ProductionInfo;
import com.thingword.alphonso.bean.StoreProductionInfo;

public interface ProductionInfoService {
	public ReturnData<ProductionInfo> getProductionInfoByDate(String Date) ;
	
	public ReturnData<ProductionInfo> getProductionInfoByDateAndLine(String Date,String Line) ;
	
	public ReturnData<ProductInfoDetail> getProductInfoDetailByDateAndLine(String Date,String Line) ;
	
	public ReturnData<ProductInfoDetail> getProductInfoDetailForTest(String task,String product) ;
	
	public ReturnData<StoreProductionInfo> getStoreProductionInfoByDate(String Date) ;
	
	public ReturnData<ProductInfoDetail> getProductionDetailByTasknumAndProductcode(ProductionInfo productionInfo); 
}
