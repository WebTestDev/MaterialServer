package com.thingword.alphonso.dao.impl;

import java.util.List;  

import org.hibernate.Query;  
import org.hibernate.Session;  
import org.hibernate.SessionFactory;  
import org.hibernate.Transaction;

import com.thingword.alphonso.bean.User;
import com.thingword.alphonso.dao.UserDao;
import com.thingword.alphonso.util.HibernateUtil;  


/**
 * 用户DAO实现
 * @author waylau.com
 * 2014-3-23
 */  
public class UserDaoImpl implements UserDao {  

    @Override  
    public User getUserById(String id) {  
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();   
        Session s = null;  
        Transaction t = null;  
        User user = null;  
        try{  
         s = sessionFactory.openSession();  
         t = s.beginTransaction();  
         String hql = "from User where Id="+id;    
         Query query = s.createQuery(hql);    
         user = (User) query.uniqueResult();   
         t.commit();  
        }catch(Exception err){  
        t.rollback();  
        err.printStackTrace();  
        }finally{  
        s.close();  
        }  
        return user;  
    }  

    @Override  
    public boolean deleteUserById(String id) {  
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();   
        Session s = null;  
        Transaction t = null;  
        boolean flag = false;  
        try{  
         s = sessionFactory.openSession();  
         t = s.beginTransaction();  
         User user = new User();    
         user.setUserID(Integer.valueOf(id));
         t.commit();  
         flag = true;  
        }catch(Exception err){  
        t.rollback();  
        err.printStackTrace();  
        }finally{  
        s.close();  
        }  
        return flag;  
    }  

    @Override  
    public boolean createUser(User user) {  
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();   
        Session s = null;  
        Transaction t = null;  
        boolean flag = false;  
        try{  
//        System.out.println(user.getUserId()+" "+user.getUserName()+" "+user.getAge());
         s = sessionFactory.openSession();  
         t = s.beginTransaction();  
         s.save(user);  
         t.commit();  
         flag = true;  
        }catch(Exception err){  
        t.rollback();  
        err.printStackTrace();  
        }finally{  
        s.close();  
        }  
        return flag;  
    }  

    @Override  
    public boolean updateUser(User user) {  
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();   
        Session s = null;  
        Transaction t = null;  
        boolean flag = false;  
        try{  
         s = sessionFactory.openSession();  
         t = s.beginTransaction();  
         s.update(user);  
         t.commit();  
         flag = true;  
        }catch(Exception err){  
        t.rollback();  
        err.printStackTrace();  
        }finally{  
        s.close();  
        }  
        return flag;  
    }  

    @Override  
    public List<User> getAllUsers() {  
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();   
        Session s = null;  
        Transaction t = null;  
        List<User> uesrs = null;  
        try{  
         s = sessionFactory.openSession();  
         t = s.beginTransaction();  
         String hql = "select * from UserInfo";    
         Query query = s.createSQLQuery(hql).addEntity(User.class);    
         query.setCacheable(true); // 设置缓存
         uesrs = query.list();    
         t.commit();  
        }catch(Exception err){  
        t.rollback();  
        err.printStackTrace();  
        }finally{  
        s.close();  
        }  
        return uesrs;  
    }

	@Override
	public User getUserByName(String name) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();   
        Session s = null;  
        Transaction t = null;  
        User user = null ;
        try{  
         s = sessionFactory.openSession();  
         t = s.beginTransaction();  
         String hql = "from User where username = "+"'"+name+"'";    
         Query query = s.createQuery(hql);    
         user = (User) query.uniqueResult();   
         t.commit();  
        }catch(Exception err){  
        t.rollback();  
        err.printStackTrace();  
        }finally{  
        s.close();  
        }  
        return user;  
	}

	@Override
	public User getUserByNamePasswd(String name, String passwd) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();   
        Session s = null;  
        Transaction t = null;  
        User user = null ;
        try{  
         s = sessionFactory.openSession();  
         t = s.beginTransaction();  
         String hql = "from User where username = '"+name+"'"+ "and passwd = '"+passwd+"'";    
         System.out.println(hql);
         Query query = s.createQuery(hql);    
         user = (User) query.uniqueResult();   
         t.commit();  
        }catch(Exception err){  
        t.rollback();  
        err.printStackTrace();  
        }finally{  
        s.close();  
        }  
        return user;  
	}  

}