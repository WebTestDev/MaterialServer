package com.thingword.alphonso.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.thingword.alphonso.bean.User;
import com.thingword.alphonso.bean2.UserTest;
import com.thingword.alphonso.dao.UserDao;
import com.thingword.alphonso.dao.UserTestDao;
import com.thingword.alphonso.util.HibernateUtil;
import com.thingword.alphonso.util.HibernateUtil2;

/**
 * 用户DAO实现
 * 
 * @author waylau.com 2014-3-23
 */
public class UserTestDaoImpl implements UserTestDao {


	@Override
	public List<UserTest> getAllUsers() {
		SessionFactory sessionFactory = HibernateUtil2.getSessionFactory();
		Session s = null;
		Transaction t = null;
		List<UserTest> uesrs = null;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "select * from usertest";
			Query query = s.createSQLQuery(hql).addEntity(UserTest.class);
			query.setCacheable(true); // 设置缓存
			uesrs = query.list();
			t.commit();
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
			s.close();
		}
		return uesrs;
	}


}