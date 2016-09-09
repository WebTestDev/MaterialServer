package com.thingword.alphonso.service;

import java.io.InputStream;

import com.thingword.alphonso.bean.ReturnMessage;

public interface ExcelService {
	public ReturnMessage uploadProductionInfo(String naem,InputStream inputStream);
	public String parseFileName(String name);
}
