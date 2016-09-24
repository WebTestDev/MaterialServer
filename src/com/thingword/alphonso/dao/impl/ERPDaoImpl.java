package com.thingword.alphonso.dao.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.mapping.UnionSubclass;

import com.thingword.alphonso.Configure.WORKSHOP;
import com.thingword.alphonso.bean.ProductInfoDetail;
import com.thingword.alphonso.bean.ProductionInfo;
import com.thingword.alphonso.bean.StoreKeeper;
import com.thingword.alphonso.bean.StoreProductionInfo;
import com.thingword.alphonso.bean.UnLoadingInfo;
import com.thingword.alphonso.bean.User;
import com.thingword.alphonso.bean2.Inventory;
import com.thingword.alphonso.bean2.RdRecord;
import com.thingword.alphonso.bean2.RdRecords;
import com.thingword.alphonso.bean2.UserTest;
import com.thingword.alphonso.bean2.VOrderDetail;
import com.thingword.alphonso.dao.UserDao;
import com.thingword.alphonso.dao.ERPDao;
import com.thingword.alphonso.util.HibernateUtil;
import com.thingword.alphonso.util.HibernateUtil2;

/**
 * 用户DAO实现
 * 
 * @author waylau.com 2014-3-23
 */
public class ERPDaoImpl implements ERPDao {

	private String fillTaskCode(String code) {
		StringBuilder stringBuilder = new StringBuilder();
		if (code.length() < 10) {
			int len = 10 - code.length();
			for (int i = 0; i < len; i++) {
				stringBuilder.append('0');
			}
			stringBuilder.append(code);
		}
		return stringBuilder.toString();
	}

	private String fillProductCode(String code, String worksop) {
		if (worksop.equals(WORKSHOP.WORKSHOP2)) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append('3');
			stringBuilder.append(code);
			return stringBuilder.toString();
		} else if (worksop.equals(WORKSHOP.WORKSHOP1)) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append('2');
			stringBuilder.append(code);
			return stringBuilder.toString();
		}
		return null;

	}

	@Override
	public List<RdRecord> getRdRecord(List<StoreProductionInfo> ls) {
		SessionFactory sessionFactory = HibernateUtil2.getSessionFactory();
		Session s = null;
		Transaction t = null;
		List<RdRecord> recordls = new ArrayList<>();

		List<RdRecord> inner = null;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			for (StoreProductionInfo storeProductionInfo : ls) {
				String pcode = fillProductCode(storeProductionInfo.getProductcode(), storeProductionInfo.getWorkshop());
				String tcode = fillTaskCode(storeProductionInfo.getTasknumber());
				String hql = "select * from RdRecord where cMPoCode = '" + tcode + "' and cPsPcode = '" + pcode
						+"' and dVeriDate = '"+ storeProductionInfo.getDate()
						+"' and cHandler = '"+ "马美丽"
						+"' and cBusType = '配比出库'";
				Query query = s.createSQLQuery(hql).addEntity(RdRecord.class);
				query.setCacheable(true); // 设置缓存
				inner = query.list();
				for (RdRecord rdRecord : inner) {
					rdRecord.setDate(storeProductionInfo.getDate());
					rdRecord.setShop(storeProductionInfo.getWorkshop());
					rdRecord.setLinenum(storeProductionInfo.getProductline());
					 System.out.println("rdRecord maker:"+rdRecord.getcMaker()+" handler:"+rdRecord.getcHandler()
					 +" date:"+rdRecord.getdVeriDate());
					recordls.add(rdRecord);
				}
				System.out.println("******************************************");
			}

			t.commit();
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
			s.close();
		}
		return recordls;
	}

	@Override
	public List<UnLoadingInfo> getRdRecords(List<RdRecord> ls) {
		SessionFactory sessionFactory = HibernateUtil2.getSessionFactory();
		Session s = null;
		Transaction t = null;
		List<RdRecords> inner = null;
		List<UnLoadingInfo> unLoadingInfols = new ArrayList<>();
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			for (RdRecord rdRecord : ls) {
				String hql = "select * from RdRecords where ID = '" + rdRecord.getID() + "'";
				// System.out.println("haha:" + hql);
				Query query = s.createSQLQuery(hql).addEntity(RdRecords.class);
				query.setCacheable(true); // 设置缓存
				inner = query.list();
				for (RdRecords rdRecords : inner) {
					UnLoadingInfo unLoadingInfo = new UnLoadingInfo();

					unLoadingInfo.setDate(rdRecord.getDate());
					unLoadingInfo.setShopnum(rdRecord.getShop());
					unLoadingInfo.setInvcode(rdRecord.getcPsPcode());
					unLoadingInfo.setcMoCode(rdRecord.getcMPoCode());
					unLoadingInfo.setLinenum(rdRecord.getLinenum());

					unLoadingInfo.setcBatch(rdRecords.getcBatch());
					unLoadingInfo.setiQuantity(String.valueOf(rdRecords.getiQuantity()));
					unLoadingInfo.setcInvCode(rdRecords.getcInvCode());
					unLoadingInfols.add(unLoadingInfo);
					 System.out.println(rdRecord.getID()+" "+rdRecords.getcInvCode());
				}
				System.out.println("******************************************");
			}
			t.commit();
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
			s.close();
		}
		return unLoadingInfols;
	}

	@Override
	public List<UnLoadingInfo> updateUnLoadingInfo(List<UnLoadingInfo> ls) {
		SessionFactory sessionFactory = HibernateUtil2.getSessionFactory();
		Session s = null;
		Transaction t = null;
		List<Inventory> inner = null;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			for (UnLoadingInfo unLoadingInfo : ls) {
				String hql = "select * from inventory where cInvCode = '" + unLoadingInfo.getcInvCode() + "'";
				Query query = s.createSQLQuery(hql).addEntity(Inventory.class);
				query.setCacheable(true); // 设置缓存
				inner = query.list();
				for (Inventory inventory : inner) {
					unLoadingInfo.setcInvDefine8(inventory.getcInvDefine8());
					unLoadingInfo.setcInvStd(inventory.getcInvStd());
					unLoadingInfo.setcInvName(inventory.getcInvName());
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
	public List<VOrderDetail> getVOrderDetail(List<ProductionInfo> ls) {
		SessionFactory sessionFactory = HibernateUtil2.getSessionFactory();
		Session s = null;
		Transaction t = null;
		List<Integer> inner = null;
		List<VOrderDetail> vOrderDetaills = new ArrayList<>();
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			for (ProductionInfo productionInfo : ls) {
				String pcode = productionInfo.getProductcode();//fillProductCode(productionInfo.getProductcode(), productionInfo.getWorkshop());
				String tcode = fillTaskCode(productionInfo.getTasknumber());
				String hql = "select modid from v_mom_orderdetail where mocode = '" + tcode + "' and invcode = '"
						+ pcode +"'";
				Query query = s.createSQLQuery(hql);
				query.setCacheable(true); // 设置缓存
				inner = query.list();
				for (Integer res : inner) {
					VOrderDetail vOrderDetail = new VOrderDetail();
					vOrderDetail.setDate(productionInfo.getDate());
					vOrderDetail.setMocode(tcode);
					vOrderDetail.setInvcode(pcode);
					vOrderDetail.setMoDid(res);
					vOrderDetail.setWorkshop(productionInfo.getWorkshop());
					vOrderDetail.setLinenum(productionInfo.getProductline());
					System.out.println(vOrderDetail.getMoDid()+" "+vOrderDetail.getDate());
					vOrderDetaills.add(vOrderDetail);
					
				}
			}
			t.commit();
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
			s.close();
		}
		return vOrderDetaills;
	}

	@Override
	public List<ProductInfoDetail> getProductInfoDetail(List<VOrderDetail> ls) {
		SessionFactory sessionFactory = HibernateUtil2.getSessionFactory();
		Session s = null;
		Transaction t = null;
		List<Object[]> inner = null;
		List<ProductInfoDetail> productInfoDetaills = new ArrayList<>();
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			for (VOrderDetail vOrderDetail : ls) {
				String hql = "select invcode,invname,invstd,define28 from v_mom_moallocate where modid = '" + vOrderDetail.getMoDid() + "'";
				Query query = s.createSQLQuery(hql);
				query.setCacheable(true); // 设置缓存
				inner = query.list();
				for (Object[] res : inner) {
					ProductInfoDetail productInfoDetail = new ProductInfoDetail();
					productInfoDetail.setInvcode((String)res[0]);
					productInfoDetail.setInvname((String)res[1]);
					productInfoDetail.setInvstd((String)res[2]);
					productInfoDetail.setDefine28((String)res[3]);
					productInfoDetail.setProductcode(vOrderDetail.getInvcode());
					productInfoDetail.setTasknumber(vOrderDetail.getMocode());
					productInfoDetail.setDate(vOrderDetail.getDate());
					productInfoDetail.setLinenum(vOrderDetail.getLinenum());
					productInfoDetail.setWorkshop(vOrderDetail.getWorkshop());
//					System.out.println(productInfoDetail.getInvcode()+" "+productInfoDetail.getInvname());
					productInfoDetaills.add(productInfoDetail);
				}
				System.out.println("*****************************");
			}
			t.commit();
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
			s.close();
		}
		return productInfoDetaills;
	}
}