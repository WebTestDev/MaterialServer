package com.thingword.alphonso.service.impl;

import java.util.List;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.thingword.alphonso.Configure.MESSAGE;
import com.thingword.alphonso.bean.DistributionInfo;
import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.ReqInfo;
import com.thingword.alphonso.bean.ReturnData;
import com.thingword.alphonso.dao.LoadingInfoDao;
import com.thingword.alphonso.dao.impl.DistriInfoDaoImpl;
import com.thingword.alphonso.dao.impl.LoadingInfoDaoImpl;
import com.thingword.alphonso.service.DistriInfoService;
import com.thingword.alphonso.service.LoadingInfoService;


public class DistriInfoServiceImpl implements DistriInfoService {

    @Autowired
    private DistriInfoDaoImpl distriInfoDaoImpl;

	@Override
	public ReturnData<DistributionInfo> getDistriInfoByDate(ReqInfo reqinfo) {
		ReturnData<DistributionInfo> returnData= new ReturnData<>();
		List<DistributionInfo> ls= distriInfoDaoImpl.getDistriInfoByDate(reqinfo.getDate(), reqinfo.getPerson());
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