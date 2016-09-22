package com.thingword.alphonso.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingword.alphonso.Configure.MESSAGE;
import com.thingword.alphonso.Configure.ReqAddUser;
import com.thingword.alphonso.Configure.ReqDelUser;
import com.thingword.alphonso.Configure.ReqUpdateUser;
import com.thingword.alphonso.Configure.ReturnLoginInfo;
import com.thingword.alphonso.Configure.ReturnMessage;
import com.thingword.alphonso.Configure.ReturnUserList;
import com.thingword.alphonso.bean.User;
import com.thingword.alphonso.bean2.RdRecord;
import com.thingword.alphonso.bean2.UserTest;
import com.thingword.alphonso.dao.ERPDao;
import com.thingword.alphonso.dao.impl.UserDaoImpl;
import com.thingword.alphonso.dao.impl.ERPDaoImpl;
import com.thingword.alphonso.service.UserService;
import com.thingword.alphonso.service.ERPService;

/**
 * User Service 接口实现
 * 
 * @author waylau.com 2014-7-25
 */
public class ERPServiceImpl implements ERPService {

	@Autowired
	private ERPDaoImpl erpDaoImpl;


	@Override
	public List<RdRecord> getRd() {
		//erpDaoImpl.getVOrderDetail();
		return null;
	}


	

}