package com.thingword.alphonso.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.User;
import com.thingword.alphonso.dao.LoadingInfoDao;
import com.thingword.alphonso.util.HibernateUtil;

public class LoadingInfoDaoImpl implements LoadingInfoDao {

	@Override
	public List<LoadingInfo> getLoadingInfoByDate(String Date, String person) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		List<LoadingInfo> ls = null;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "From LoadingInfo where date = '" + Date + "'";
			Query query = s.createQuery(hql);
			ls = query.list();
			t.commit();
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
			s.close();
		}
		return ls;
	}
}
