package com.thingword.alphonso.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.thingword.alphonso.Configure.BatchData;
import com.thingword.alphonso.Configure.MESSAGE;
import com.thingword.alphonso.Configure.ReqInfo;
import com.thingword.alphonso.Configure.ReturnBatchData;
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
		
		//1.获取查询的unloadinginfo
		//2.若为空，查询是否有上传当天的生产记录
		
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
	
	@Override
	public ReturnData<UnLoadingInfo> getAllUnLoadingInfoByDate(ReqInfo reqInfo) {
		
		//1.获取查询的unloadinginfo
		//2.若为空，查询是否有上传当天的生产记录
		
		ReturnData<UnLoadingInfo> returnData= new ReturnData<>();
		List<UnLoadingInfo> ls= unloadingInfoDaoImpl.getAllUnLoadingInfoByDate(reqInfo.getDate());
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

	@Override
	public ReturnBatchData<UnLoadingInfo> getBatchUnLoadingInfoByDate(ReqInfo reqInfo) {
		ReturnBatchData<UnLoadingInfo> returnData= new ReturnBatchData<>();
		HashSet<String> ls= unloadingInfoDaoImpl.getUploadBatchInfoByDate(reqInfo.getDate());
		returnData.setReturn_code(MESSAGE.RETURN_FAIL);
		returnData.setReturn_msg(MESSAGE.QUERY_NONE);
		List<BatchData<UnLoadingInfo>> lsDatas = new ArrayList<>();
	
		if(ls != null){
			if(ls.size() != 0){
				for(String uploadbatch:ls){
					BatchData<UnLoadingInfo> batchData = new BatchData<>();
					List<UnLoadingInfo> innerdata = unloadingInfoDaoImpl.
							getUploadBatchInfoByDateAndUploadBatch(reqInfo.getDate(), uploadbatch);
					batchData.setBatch(uploadbatch);
					batchData.setData(innerdata);
					lsDatas.add(batchData);
				}
				returnData.setReturn_code(MESSAGE.RETURN_SUCCESS);
				returnData.setReturn_msg(MESSAGE.QUERY_SUCCESS);
				returnData.setData(lsDatas);
			}				
		}
		return returnData;
	} 
}