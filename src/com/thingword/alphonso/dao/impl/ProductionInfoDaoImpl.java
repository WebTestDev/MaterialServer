package com.thingword.alphonso.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.ProductInfoDetail;
import com.thingword.alphonso.bean.ProductionInfo;
import com.thingword.alphonso.bean.StoreProductionInfo;
import com.thingword.alphonso.bean.User;
import com.thingword.alphonso.dao.ProductionInfoDao;
import com.thingword.alphonso.util.HibernateUtil;

public class ProductionInfoDaoImpl implements ProductionInfoDao {

	@Override
	public boolean updateProductionInfoList(List<ProductionInfo> ls, String date, String workshop) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		boolean flag = false;
		deleteByDateAndWorkShop(date, workshop);
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
	public boolean deleteByDateAndWorkShop(String Date, String workshop) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		boolean flag = false;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "delete from ProductionInfo where date = '" + Date + "'" + "and workshop = '" + workshop + "'";
			System.out.println(hql);
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
	
	@Override
	public List<ProductionInfo> getProductionInfoByDateAndLine(String Date,String linenum) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		List<ProductionInfo> ls = null;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "From ProductionInfo where date = '" 
			+ Date + "' and productline = '"+linenum+"'";
			Query query = s.createQuery(hql);
			ls = query.list();
			t.commit();
			System.out.println(hql);
			System.out.println("size:"+ls.size());
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
			s.close();
		}
		return ls;
	}
	
	@Override
	public List<ProductInfoDetail> getProductionInfoDetailByDateAndLine(String Date, String linenum) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		List<ProductInfoDetail> ls = null;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "From ProductInfoDetail where date = '" 
			+ Date + "' and linenum = '"+linenum+"'";
			Query query = s.createQuery(hql);
			ls = query.list();
			t.commit();
			System.out.println(hql);
			System.out.println("size:"+ls.size());
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
			s.close();
		}
		return ls;
	}


	@Override
	public boolean updateStoreProductionInfoList(List<StoreProductionInfo> ls, String date, String batch) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		boolean flag = false;
		deleteStoreProductionInfoByDateAndBatch(date, batch);
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			for (StoreProductionInfo productionInfo : ls)
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
	public boolean deleteStoreProductionInfoByDateAndBatch(String Date, String batch) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		boolean flag = false;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "delete from StoreProductionInfo where date = '" + Date + "'" + "and uploadbatch ='" + batch
					+ "'";
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
	public boolean updateDetailList(List<ProductInfoDetail> ls, String date,String workshop) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		boolean flag = false;
		deleteDetailByDateAndWokshop(date, workshop);
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			for (ProductInfoDetail detail : ls)
				s.save(detail);
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
	public boolean deleteDetailByDateAndWokshop(String Date,String workshop) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		boolean flag = false;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "delete from ProductInfoDetail where date = '" + Date
					+ "' and workshop = '"+workshop +"'" ;
			System.out.println(hql);
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
	public List<StoreProductionInfo> getStoreProductionInfoByDate(String Date) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		List<StoreProductionInfo> ls = null;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "From StoreProductionInfo where date = '" + Date + "'";
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
