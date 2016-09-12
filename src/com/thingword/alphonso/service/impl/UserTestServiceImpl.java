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
import com.thingword.alphonso.bean2.UserTest;
import com.thingword.alphonso.dao.UserTestDao;
import com.thingword.alphonso.dao.impl.UserDaoImpl;
import com.thingword.alphonso.dao.impl.UserTestDaoImpl;
import com.thingword.alphonso.service.UserService;
import com.thingword.alphonso.service.UserTestService;

/**
 * User Service 接口实现
 * 
 * @author waylau.com 2014-7-25
 */
public class UserTestServiceImpl implements UserTestService {

	@Autowired
	private UserTestDaoImpl userTestDaoImpl;

	public UserTestServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<UserTest> getAllUsers() {
		// TODO Auto-generated method stub
		List<UserTest> ls =  userTestDaoImpl.getAllUsers();
		System.out.println("size:"+ls.size()+" "+ls.get(0).getUser()+" "+ls.get(0).getUserID());
		return ls;
	}


	

}