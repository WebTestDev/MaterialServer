package com.thingword.alphonso.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingword.alphonso.Configure.MESSAGE;
import com.thingword.alphonso.Configure.ReturnData;
import com.thingword.alphonso.bean.ProductionInfo;
import com.thingword.alphonso.bean.StoreKeeper;
import com.thingword.alphonso.dao.impl.StoreKeeperDaoImpl;
import com.thingword.alphonso.service.StoreKeeperService;

public class StoreKeeperServiceImpl implements StoreKeeperService{

    @Autowired
    private StoreKeeperDaoImpl storeKeeperDaoImpl;

	@Override
	public ReturnData<StoreKeeper> getStoreKeeperList() {
		ReturnData<StoreKeeper> returnData= new ReturnData<>();
		
		List<StoreKeeper> ls= storeKeeperDaoImpl.getStoreKeeperList();
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
