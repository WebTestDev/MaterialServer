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
import com.thingword.alphonso.Configure.ReturnUpdateVerion;
import com.thingword.alphonso.Configure.ReturnUserList;
import com.thingword.alphonso.bean.UpdateVeriosn;
import com.thingword.alphonso.bean.User;
import com.thingword.alphonso.dao.impl.UserDaoImpl;
import com.thingword.alphonso.service.UserService;

/**
 * User Service �ӿ�ʵ��
 * 
 * @author waylau.com 2014-7-25
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
		return false;
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
		if (user == null) {
			message.setReturn_code(MESSAGE.RETURN_FAIL);
			message.setReturn_msg(MESSAGE.LOGIN_FAIL);
		} else {
			message.setAuthority(user.getAuthority());
			message.setEmploy_name(user.getEmployname());
			message.setEmploy_code(user.getEmploycode());
		}
		return message;
	}

	@Override
	public ReturnUserList getUserList() {
		ReturnUserList returnUserList = new ReturnUserList();
		List<User> data = new ArrayList<>();
		

		List<User> ls = userDaoImpl.getAllUsers();
		
//		System.out.println("size:"+ls.size());

		for (User user : ls) {
			int authority = 0;
			try {
				authority = Integer.parseInt(user.getAuthority());
			} catch (Exception e) {
				// System.out.println("�ַ���ת��Ϊ����ʧ��");
			}
			StringBuilder stringBuilder = new StringBuilder();
			if ((authority & 1) != 0) {
				stringBuilder.append("<span id='rights1'>�ֿ�</span> ");
			}
			if ((authority & 4) != 0) {
				stringBuilder.append("<span id='rights2'>����</span> ");
			}
			if ((authority & 8) != 0) {
				stringBuilder.append("<span id='rights3'>����</span> ");
			}
			user.setAuthority(stringBuilder.toString());
			data.add(user);
		}
		returnUserList.setData(data);

		return returnUserList;
	}

	@Override
	public ReturnUserList createUser(ReqAddUser reqAddUser) {
		ReturnUserList returnUserList = new ReturnUserList();
		List<User> data = new ArrayList<>();
		User user = new User();
		user.setUsername(reqAddUser.getAdd_account());
		user.setPasswd(reqAddUser.getAdd_password());
		user.setEmployname(reqAddUser.getAdd_name());
		user.setEmploycode(reqAddUser.getAdd_num());
		String val = reqAddUser.getAdd_rights();
		int auth = 0;
		Pattern pattern_a = Pattern.compile(".+�ֿ�.+");
		Pattern pattern_b = Pattern.compile(".+����.+");
		Pattern pattern_c = Pattern.compile(".+����.+");
		if(pattern_a.matcher(val).matches()){
			auth |= 3;
		}
		if(pattern_b.matcher(val).matches()){
			auth |= 4;
		}
		if(pattern_c.matcher(val).matches()){
			auth |= 8;
		}
		user.setAuthority(String.valueOf(auth));
		String ID = userDaoImpl.createUser(user);
		user.setAuthority(val);
		data.add(user);
		returnUserList.setData(data);
		return returnUserList;
	}

	@Override
	public boolean deleteUserById(ReqDelUser user) {
		return userDaoImpl.deleteUserById(user.getId());
	}

	@Override
	public ReturnUserList updateUser(ReqUpdateUser requser) {
		ReturnUserList returnUserList = new ReturnUserList();
		List<User> data = new ArrayList<>();
		User user = new User();
		user.setUsername(requser.getEdit_account());
		user.setPasswd(requser.getEdit_password());
		user.setUserID(Integer.valueOf(requser.getEdit_id()));
		user.setEmployname(requser.getEdit_name());
		user.setEmploycode(requser.getEdit_num());
		String val = requser.getEdit_rights();
		int auth = 0;
		Pattern pattern_a = Pattern.compile(".+�ֿ�.+");
		Pattern pattern_b = Pattern.compile(".+����.+");
		Pattern pattern_c = Pattern.compile(".+����.+");
		if(pattern_a.matcher(val).matches()){
			auth |= 3;
		}
		if(pattern_b.matcher(val).matches()){
			auth |= 4;
		}
		if(pattern_c.matcher(val).matches()){
			auth |= 8;
		}
		user.setAuthority(String.valueOf(auth));
		if(userDaoImpl.updateUser(user)){
			user.setAuthority(requser.getEdit_rights());
			data.add(user);
//			List<String> inner = new ArrayList<>();
//			inner.add("<input type='checkbox' value='0'>");
//			inner.add(String.valueOf(user.getUserID()));
//			inner.add(user.getEmployname());
//			inner.add(user.getEmploycode());
//			inner.add(user.getUsername());
//			inner.add(user.getPasswd());
//			inner.add(requser.getEdit_rights());
//			data.add(inner);
		}
		returnUserList.setData(data);
		return returnUserList;
	}

	@Override
	public ReturnUpdateVerion getUpdateVerion() {
		ReturnUpdateVerion returnUpdateVerion = new ReturnUpdateVerion();
		returnUpdateVerion.setReturn_code(MESSAGE.RETURN_FAIL);
		UpdateVeriosn updateVeriosn = userDaoImpl.getUpdateVersion();
		if(updateVeriosn!=null){
			returnUpdateVerion.setVersion(updateVeriosn);
			returnUpdateVerion.setReturn_code(MESSAGE.RETURN_SUCCESS);
		}
		return returnUpdateVerion;
	}

}