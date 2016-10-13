package com.thingword.alphonso.dao;

import java.util.List;

import com.thingword.alphonso.bean.AuxiliaryInfo;
import com.thingword.alphonso.bean.User;


public interface AuxiliaryInfoDao {
	public boolean deleteAuxiliaryInfo();
	
	public boolean updateAuxiliaryInfo(List<AuxiliaryInfo> ls);
	
	 public List<AuxiliaryInfo> getAllInfo();  
}
