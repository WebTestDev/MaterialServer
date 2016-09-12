package com.thingword.alphonso.service;

import java.util.List;

import org.hibernate.loader.custom.Return;

import com.thingword.alphonso.Configure.ReqAddUser;
import com.thingword.alphonso.Configure.ReqDelUser;
import com.thingword.alphonso.Configure.ReqUpdateUser;
import com.thingword.alphonso.Configure.ReturnLoginInfo;
import com.thingword.alphonso.Configure.ReturnMessage;
import com.thingword.alphonso.Configure.ReturnUserList;
import com.thingword.alphonso.bean.User;
import com.thingword.alphonso.bean2.UserTest;


/**
 * User Service ½Ó¿Ú
 * @author waylau.com
 * 2014-7-25
 */
public interface UserTestService {

    public List<UserTest> getAllUsers();
   
    
}