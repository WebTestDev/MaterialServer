package com.thingword.alphonso.service.impl;

import java.util.List;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.thingword.alphonso.Configure.MESSAGE;
import com.thingword.alphonso.Configure.ReqInfo;
import com.thingword.alphonso.Configure.ReturnData;
import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.UnLoadingInfo;
import com.thingword.alphonso.dao.LoadingInfoDao;
import com.thingword.alphonso.dao.impl.LoadingInfoDaoImpl;
import com.thingword.alphonso.dao.impl.UnLoadingInfoDaoImpl;
import com.thingword.alphonso.service.LoadingInfoService;
import com.thingword.alphonso.service.UnLoadingInfoService;


public class UnLoadingInfoServiceImpl implements UnLoadingInfoService {

    @Autowired
    private UnLoadingInfoDaoImpl unloadingInfoDaoImpl;

	@Override
	public ReturnData<UnLoadingInfo> getUnLoadingInfoByDate(ReqInfo reqInfo) {
		ReturnData<UnLoadingInfo> returnData= new ReturnData<>();
		List<UnLoadingInfo> ls= unloadingInfoDaoImpl.getUnLoadingInfoByDate(reqInfo.getDate(), reqInfo.getPerson());
		returnData.setReturn_code(MESSAGE.RETURN_FAIL);
		returnData.setReturn_msg(MESSAGE.QUERY_NONE);
		if(ls != null){
			if(ls.size() != 0){
				returnData.setReturn_code(MESSAGE.RETURN_SUCCESS);
				returnData.setReturn_msg(MESSAGE.QUERY_SUCCESS);
				returnData.setData(ls);
			}
		}
		return returnData;
	} 
}