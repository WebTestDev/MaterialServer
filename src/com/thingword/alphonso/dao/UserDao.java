package com.thingword.alphonso.dao;

import java.util.List;

import com.thingword.alphonso.bean.UpdateVeriosn;
import com.thingword.alphonso.bean.User;  


/**
 * User Dao ½Ó¿Ú
 * @author waylau.com
 * 2014-3-18
 */  
public interface UserDao {  

    public User getUserById(String id);  

    public boolean deleteUserById(String id);  
    
    public User getUserByName(String name);

    public String createUser(User user);  

    public boolean updateUser(User user);  

    public List<User> getAllUsers();  
    
    public User getUserByNamePasswd(String name, String passwd);
    
    public UpdateVeriosn getUpdateVersion();
}