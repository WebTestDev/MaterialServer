package com.thingword.alphonso.service;

import java.util.List;

import org.hibernate.loader.custom.Return;

import com.thingword.alphonso.bean.ReturnLoginInfo;
import com.thingword.alphonso.bean.ReturnMessage;
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
    
    
}