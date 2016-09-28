package com.thingword.alphonso.dao.impl;

import java.util.HashSet;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.ProductionInfo;
import com.thingword.alphonso.bean.UnLoadingInfo;
import com.thingword.alphonso.bean.User;
import com.thingword.alphonso.dao.UnLoadingInfoDao;
import com.thingword.alphonso.util.HibernateUtil;

public class UnLoadingInfoDaoImpl implements UnLoadingInfoDao{

	@Override
	public List<UnLoadingInfo> getUnLoadingInfoByDate(String Date, String person) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();   
        Session s = null;  
        Transaction t = null;  
        List<UnLoadingInfo> ls = null;  
        try{  
         s = sessionFactory.openSession();  
         t = s.beginTransaction();  
         String hql = "From UnLoadingInfo where date = '"+Date+"' and executor = '"+person+"'";    
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
	public List<UnLoadingInfo> getAllUnLoadingInfoByDate(String Date) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();   
        Session s = null;  
        Transaction t = null;  
        List<UnLoadingInfo> ls = null;  
        try{  
         s = sessionFactory.openSession();  
         t = s.beginTransaction();  
         String hql = "From UnLoadingInfo where date = '"+Date+"'";    
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
	public boolean updateUnLoadingInfoList(List<UnLoadingInfo> ls, String date,String batch) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		boolean flag = false;
		deleteUnLoadingInfoByDateAndBatch(date, batch);
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			for (UnLoadingInfo unLoadingInfo : ls){
				unLoadingInfo.setUploadbatch(batch);
				s.save(unLoadingInfo);
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
	public boolean deleteUnLoadingInfoByDateAndBatch(String Date, String batch) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		boolean flag = false;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "delete from UnLoadingInfo where date = '" + Date + "'" + "and uploadbatch ='" + batch
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
	public HashSet<String> getUploadBatchInfoByDate(String Date) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();   
        Session s = null;  
        Transaction t = null;  
        List<UnLoadingInfo> ls = null; 
        HashSet<String> set = new HashSet<>();
        try{  
         s = sessionFactory.openSession();  
         t = s.beginTransaction();  
         String hql = "From UnLoadingInfo where date = '"+Date+"'";    
         Query query = s.createQuery(hql);   
         ls = query.list();    
         t.commit();  
        }catch(Exception err){  
        t.rollback();  
        err.printStackTrace();  
        }finally{  
        s.close();  
        }  
        for(UnLoadingInfo unLoadingInfo:ls){
        	set.add(unLoadingInfo.getUploadbatch());
        }
        return set; 
	}

	@Override
	public List<UnLoadingInfo> getUploadBatchInfoByDateAndUploadBatch(String Date,String uploadbatch) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();   
        Session s = null;  
        Transaction t = null;  
        List<UnLoadingInfo> ls = null; 
        try{  
         s = sessionFactory.openSession();  
         t = s.beginTransaction();  
         String hql = "From UnLoadingInfo where date = '"+Date+"' and uploadbatch = '"+uploadbatch+"'";  
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
	

}
