package com.thingword.alphonso.service;

import java.util.List;

import org.hibernate.loader.custom.Return;

import com.thingword.alphonso.Configure.ReqAddUser;
import com.thingword.alphonso.Configure.ReqDelUser;
import com.thingword.alphonso.Configure.ReqUpdateUser;
import com.thingword.alphonso.Configure.ReturnLoginInfo;
import com.thingword.alphonso.Configure.ReturnMessage;
import com.thingword.alphonso.Configure.ReturnUpdateVerion;
import com.thingword.alphonso.Configure.ReturnUserList;
import com.thingword.alphonso.bean.UpdateVeriosn;
import com.thingword.alphonso.bean.User;


/**
 * User Service ½Ó¿Ú
 * @author waylau.com
 * 2014-7-25
 */
public interface UserService {

    public User getUserById(String id);

    public boolean deleteUserById(String id);

    public boolean createUser(User user);

    public boolean updateUser(User user);

    public List<User> getAllUsers();
    
    public User getUserByName(String name);
    
    public ReturnLoginInfo checkUser(User user);
    
    public ReturnUserList getUserList();
    
    public ReturnUserList createUser(ReqAddUser user);
    
    public boolean deleteUserById(ReqDelUser user);
    
    public ReturnUserList updateUser(ReqUpdateUser user);
    
    public ReturnUpdateVerion getUpdateVerion();

    
    
}