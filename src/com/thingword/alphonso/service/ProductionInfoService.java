package com.thingword.alphonso.service;

import com.thingword.alphonso.Configure.ReturnData;
import com.thingword.alphonso.bean.ProductionInfo;

public interface ProductionInfoService {
	public ReturnData<ProductionInfo> getProductionInfoByDate(String Date) ;
}
