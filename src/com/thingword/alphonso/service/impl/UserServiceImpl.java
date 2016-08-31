package com.thingword.alphonso.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingword.alphonso.Configure.MESSAGE;
import com.thingword.alphonso.bean.ReturnLoginInfo;
import com.thingword.alphonso.bean.ReturnMessage;
import com.thingword.alphonso.bean.User;
import com.thingword.alphonso.dao.impl.UserDaoImpl;
import com.thingword.alphonso.service.UserService;

/**
 * User Service 接口实现
 * @author waylau.com
 * 2014-7-25
 */
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDaoImpl userDaoImpl;

    public UserServiceImpl() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public User getUserById(String id) {
        return userDaoImpl.getUserById(id);
    }

    @Override
    public boolean deleteUserById(String id) {
        return userDaoImpl.deleteUserById(id);
    }

    @Override
    public boolean createUser(User user) {
        return userDaoImpl.createUser(user);
    }

    @Override
    public boolean updateUser(User user) {
        return userDaoImpl.updateUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDaoImpl.getAllUsers();
    }

	@Override
	public User getUserByName(String name) {
		return userDaoImpl.getUserByName(name);
	}

	@Override
	public ReturnLoginInfo checkUser(User user) {
		ReturnLoginInfo message = new ReturnLoginInfo();
		message.setReturn_code(MESSAGE.RETURN_SUCCESS);
		message.setReturn_msg(MESSAGE.LOGIN_SUCCESS);
		
		user = userDaoImpl.getUserByNamePasswd(user.getUsername(), user.getPasswd());
		if(user == null){
			message.setReturn_code(MESSAGE.RETURN_FAIL);
			message.setReturn_msg(MESSAGE.LOGIN_FAIL);
		}else{
			message.setAuthority(user.getAuthority());
		}
		return message;
	}

}