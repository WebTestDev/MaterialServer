package com.thingword.alphonso.service;

import com.thingword.alphonso.Configure.ReturnData;
import com.thingword.alphonso.bean.ProductInfoDetail;
import com.thingword.alphonso.bean.ProductionInfo;

public interface ProductionInfoService {
	public ReturnData<ProductionInfo> getProductionInfoByDate(String Date) ;
	
	public ReturnData<ProductionInfo> getProductionInfoByDateAndLine(String Date,String Line) ;
	
	public ReturnData<ProductInfoDetail> getProductInfoDetailByDateAndLine(String Date,String Line) ;
}
