package com.thingword.alphonso.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.ProductionInfo;
import com.thingword.alphonso.bean.User;
import com.thingword.alphonso.dao.ProductionInfoDao;
import com.thingword.alphonso.util.HibernateUtil;

public class ProductionInfoDaoImpl implements ProductionInfoDao {

	@Override
	public boolean updateProductionInfoList(List<ProductionInfo> ls, String date) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		boolean flag = false;
		deleteByDate(date);
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			for (ProductionInfo productionInfo : ls)
				s.save(productionInfo);
			t.commit();
			flag = true;
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
			s.close();
		}
		return flag;
	}

	@Override
	public boolean deleteByDate(String Date) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		boolean flag = false;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "delete from ProductionInfo where date = '" + Date + "'";
			s.createQuery(hql).executeUpdate();
			t.commit();
			flag = true;
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
			s.close();
		}
		return flag;
	}

	@Override
	public List<ProductionInfo> getProductionInfoByDate(String Date) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		List<ProductionInfo> ls = null;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "From ProductionInfo where date = '" + Date + "'";
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
