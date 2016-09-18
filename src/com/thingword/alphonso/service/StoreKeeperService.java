package com.thingword.alphonso.service;

import java.util.List;

import org.hibernate.loader.custom.Return;

import com.thingword.alphonso.Configure.ReqInfo;
import com.thingword.alphonso.Configure.ReturnData;
import com.thingword.alphonso.Configure.ReturnMessage;
import com.thingword.alphonso.bean.DistributionInfo;
import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.StoreKeeper;
import com.thingword.alphonso.bean.User;
import com.thingword.alphonso.dao.LoadingInfoDao;



public interface StoreKeeperService {

    public ReturnData<StoreKeeper> getStoreKeeperList();
   
}