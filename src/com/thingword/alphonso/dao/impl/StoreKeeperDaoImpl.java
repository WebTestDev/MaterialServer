package com.thingword.alphonso.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.thingword.alphonso.bean.StoreKeeper;
import com.thingword.alphonso.bean.UnLoadingInfo;
import com.thingword.alphonso.dao.StoreKeeperDao;
import com.thingword.alphonso.util.HibernateUtil;

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
         System.out.println(hql);
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
