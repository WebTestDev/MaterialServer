package com.thingword.alphonso.service;

import java.util.List;

import com.thingword.alphonso.bean.ReqInfo;
import com.thingword.alphonso.bean.ReturnData;
import com.thingword.alphonso.bean.UnLoadingInfo;
import com.thingword.alphonso.dao.UnLoadingInfoDao;

public interface UnLoadingInfoService {

	public ReturnData<UnLoadingInfo> getUnLoadingInfoByDate(ReqInfo reqInfo);

}
