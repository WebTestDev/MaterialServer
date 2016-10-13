package com.thingword.alphonso.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.thingword.alphonso.bean.AuxiliaryInfo;
import com.thingword.alphonso.bean.ProductionInfo;
import com.thingword.alphonso.bean.User;
import com.thingword.alphonso.dao.AuxiliaryInfoDao;
import com.thingword.alphonso.util.HibernateUtil;

public class AuxiliaryInfoDaoImpl implements AuxiliaryInfoDao{

	@Override
	public boolean deleteAuxiliaryInfo() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		boolean flag = false;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "delete from AuxiliaryInfo";
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
	public boolean updateAuxiliaryInfo(List<AuxiliaryInfo> ls) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		boolean flag = false;
		deleteAuxiliaryInfo();
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			for (AuxiliaryInfo auxiliaryInfo : ls)
				s.save(auxiliaryInfo);
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
	public List<AuxiliaryInfo> getAllInfo() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		List<AuxiliaryInfo> uesrs = null;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "select * from auxiliaryinfo";
			Query query = s.createSQLQuery(hql).addEntity(AuxiliaryInfo.class);
			query.setCacheable(true); // …Ë÷√ª∫¥Ê
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
