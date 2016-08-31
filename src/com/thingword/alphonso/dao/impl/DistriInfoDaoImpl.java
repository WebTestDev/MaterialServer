package com.thingword.alphonso.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.thingword.alphonso.bean.DistributionInfo;
import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.UnLoadingInfo;
import com.thingword.alphonso.dao.DistriInfoDao;
import com.thingword.alphonso.util.HibernateUtil;


public class DistriInfoDaoImpl implements DistriInfoDao {
	public List<DistributionInfo> getDistriInfoByDate(String Date,String person){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();   
        Session s = null;  
        Transaction t = null;  
        List<DistributionInfo> ls = null;  
        try{  
         s = sessionFactory.openSession();  
         t = s.beginTransaction();  
         String hql = "From DistributionInfo where date = '"+Date+"'"; 
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
