package com.thingword.alphonso.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingword.alphonso.Configure.MESSAGE;
import com.thingword.alphonso.Configure.ReturnData;
import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.ProductInfoDetail;
import com.thingword.alphonso.bean.ProductionInfo;
import com.thingword.alphonso.bean.StoreProductionInfo;
import com.thingword.alphonso.dao.ProductionInfoDao;
import com.thingword.alphonso.dao.impl.ProductionInfoDaoImpl;
import com.thingword.alphonso.service.ProductionInfoService;

public class ProductionInfoServiceImpl implements ProductionInfoService{

	@Autowired
	private ProductionInfoDaoImpl productionInfoDaoImpl;
	
	@Override
	public ReturnData<ProductionInfo> getProductionInfoByDate(String Date) {
		ReturnData<ProductionInfo> returnData= new ReturnData<>();
			
		List<ProductionInfo> ls= productionInfoDaoImpl.getProductionInfoByDate(Date);
//		System.out.println("getProductionInfoByDate:"+ls.size());
		returnData.setReturn_code(MESSAGE.RETURN_FAIL);
		returnData.setReturn_msg(MESSAGE.QUERY_NONE);
		if(ls != null){
			if(!ls.isEmpty()){
				returnData.setReturn_code(MESSAGE.RETURN_SUCCESS);
				returnData.setReturn_msg(MESSAGE.QUERY_SUCCESS);
				returnData.setData(ls);
			}
		}
		return returnData;
	}

	@Override
	public ReturnData<ProductionInfo> getProductionInfoByDateAndLine(String Date, String Line) {
		ReturnData<ProductionInfo> returnData= new ReturnData<>();
		
		List<ProductionInfo> ls= productionInfoDaoImpl.getProductionInfoByDateAndLine(Date,Line);
		returnData.setReturn_code(MESSAGE.RETURN_FAIL);
		returnData.setReturn_msg(MESSAGE.QUERY_NONE);
		if(ls != null){
			if(!ls.isEmpty()){
				returnData.setReturn_code(MESSAGE.RETURN_SUCCESS);
				returnData.setReturn_msg(MESSAGE.QUERY_SUCCESS);
				returnData.setData(ls);
			}
		}
		return returnData;
	}

	@Override
	public ReturnData<ProductInfoDetail> getProductInfoDetailByDateAndLine(String Date, String Line) {
		ReturnData<ProductInfoDetail> returnData= new ReturnData<>();	
		List<ProductInfoDetail> ls= productionInfoDaoImpl.getProductionInfoDetailByDateAndLine(Date,Line);
		returnData.setReturn_code(MESSAGE.RETURN_FAIL);
		returnData.setReturn_msg(MESSAGE.QUERY_NONE);
		if(ls != null){
			if(!ls.isEmpty()){
				returnData.setReturn_code(MESSAGE.RETURN_SUCCESS);
				returnData.setReturn_msg(MESSAGE.QUERY_SUCCESS);
				returnData.setData(ls);
			}
		}
		return returnData;
	}

	@Override
	public ReturnData<StoreProductionInfo> getStoreProductionInfoByDate(String Date) {
		ReturnData<StoreProductionInfo> returnData= new ReturnData<>();
		
		List<StoreProductionInfo> ls= productionInfoDaoImpl.getStoreProductionInfoByDate(Date);
		returnData.setReturn_code(MESSAGE.RETURN_FAIL);
		returnData.setReturn_msg(MESSAGE.QUERY_NONE);
		if(ls != null){
			if(!ls.isEmpty()){
				returnData.setReturn_code(MESSAGE.RETURN_SUCCESS);
				returnData.setReturn_msg(MESSAGE.QUERY_SUCCESS);
				returnData.setData(ls);
			}
		}
		return returnData;
	}

}
