package com.thingword.alphonso.service.impl;

import java.util.List;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.thingword.alphonso.Configure.MESSAGE;
import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.ReqInfo;
import com.thingword.alphonso.bean.ReturnData;
import com.thingword.alphonso.dao.LoadingInfoDao;
import com.thingword.alphonso.dao.impl.LoadingInfoDaoImpl;
import com.thingword.alphonso.service.LoadingInfoService;


public class LoadingInfoServiceImpl implements LoadingInfoService {

    @Autowired
    private LoadingInfoDaoImpl loadingInfoDaoImpl;

	@Override
	public ReturnData<LoadingInfo> getLoadingInfoByDate(ReqInfo reqinfo) {
		ReturnData<LoadingInfo> returnData= new ReturnData<>();
		List<LoadingInfo> ls= loadingInfoDaoImpl.getLoadingInfoByDate(reqinfo.getDate(), reqinfo.getPerson());
		returnData.setReturn_code(MESSAGE.RETURN_FAIL);
		returnData.setReturn_msg(MESSAGE.QUERY_FAIL);
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