package com.thingword.alphonso.service;

import java.io.InputStream;

import com.thingword.alphonso.Configure.ReturnData;
import com.thingword.alphonso.Configure.ReturnMessage;
import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.ProductionInfo;
import com.thingword.alphonso.bean.UnLoadingInfo;

public interface ExcelService {
	public ReturnData<ProductionInfo> uploadProductionInfo(String naem,InputStream inputStream);
	public String parseFileName(String name);
	
	public ReturnData<UnLoadingInfo> uploadProductionInfoStore(String name,InputStream inputStream);
	
}
