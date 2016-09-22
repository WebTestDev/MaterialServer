package com.thingword.alphonso.service;

import java.util.List;

import com.thingword.alphonso.Configure.ReqInfo;
import com.thingword.alphonso.Configure.ReturnData;
import com.thingword.alphonso.bean.UnLoadingInfo;
import com.thingword.alphonso.dao.UnLoadingInfoDao;

public interface UnLoadingInfoService {

	public ReturnData<UnLoadingInfo> getUnLoadingInfoByDate(ReqInfo reqInfo);
	
	public ReturnData<UnLoadingInfo> getAllUnLoadingInfoByDate(ReqInfo reqInfo);

}
