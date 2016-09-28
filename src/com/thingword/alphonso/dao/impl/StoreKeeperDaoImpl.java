package com.thingword.alphonso.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.thingword.alphonso.bean.StoreKeeper;
import com.thingword.alphonso.bean.UnLoadingInfo;
import com.thingword.alphonso.bean2.RdRecord;
import com.thingword.alphonso.bean2.RdRecords;
import com.thingword.alphonso.dao.StoreKeeperDao;
import com.thingword.alphonso.util.HibernateUtil;
import com.thingword.alphonso.util.HibernateUtil2;

public class StoreKeeperDaoImpl implements StoreKeeperDao{

	@Override
	public List<StoreKeeper> getStoreKeeperList() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();   
        Session s = null;  
        Transaction t = null;  
        List<StoreKeeper> ls = null;  
        try{  
         s = sessionFactory.openSession();  
         t = s.beginTransaction();  
         String hql = "From StoreKeeper ";//where location = '"+"HXK'";  
//         System.out.println(hql);
         Query query = s.createQuery(hql);   
         ls = query.list();    
         t.commit();  
        }catch(Exception err){  
        t.rollback();  
        err.printStackTrace();  
        }finally{  
        s.close();  
        }  
        return ls; 
	}

	@Override
	public List<UnLoadingInfo> getALLUnLoadingInfo(List<UnLoadingInfo> ls) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		List<StoreKeeper> inner = null;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			for (UnLoadingInfo unLoadingInfo : ls) {
				String hql = "select * from storekeeper where materialnumber = '" + unLoadingInfo.getcInvCode() + "'";
				Query query = s.createSQLQuery(hql).addEntity(StoreKeeper.class);
				query.setCacheable(true); // …Ë÷√ª∫¥Ê
				inner = query.list();
				for (StoreKeeper storeKeeper : inner) {
					unLoadingInfo.setExecutor(storeKeeper.getStorekeeper());
				}
			}
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
	public boolean updateUnLoadingInfoList(List<StoreKeeper> ls) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		boolean flag = false;
		deleteUnLoadingInfo();
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			for (StoreKeeper storeKeeper : ls){
				s.save(storeKeeper);
			}
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
	public boolean deleteUnLoadingInfo() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		boolean flag = false;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "delete from StoreKeeper ";
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

}
