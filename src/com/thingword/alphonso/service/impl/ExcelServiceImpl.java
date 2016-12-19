package com.thingword.alphonso.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.attribute.standard.PrinterMessageFromOperator;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingword.alphonso.Configure.MESSAGE;
import com.thingword.alphonso.Configure.ReturnData;
import com.thingword.alphonso.Configure.ReturnMessage;
import com.thingword.alphonso.Configure.WORKSHOP;
import com.thingword.alphonso.bean.AuxiliaryInfo;
import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.ProductInfoDetail;
import com.thingword.alphonso.bean.ProductionInfo;
import com.thingword.alphonso.bean.StoreKeeper;
import com.thingword.alphonso.bean.StoreProductionInfo;
import com.thingword.alphonso.bean.UnLoadingInfo;
import com.thingword.alphonso.bean2.RdRecord;
import com.thingword.alphonso.bean2.VOrderDetail;
import com.thingword.alphonso.dao.AuxiliaryInfoDao;
import com.thingword.alphonso.dao.impl.AuxiliaryInfoDaoImpl;
import com.thingword.alphonso.dao.impl.ERPDaoImpl;
import com.thingword.alphonso.dao.impl.ProductionInfoDaoImpl;
import com.thingword.alphonso.dao.impl.StoreKeeperDaoImpl;
import com.thingword.alphonso.dao.impl.UnLoadingInfoDaoImpl;
import com.thingword.alphonso.service.ExcelService;

import javassist.expr.NewArray;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelServiceImpl implements ExcelService {

	@Autowired
	private ProductionInfoDaoImpl productionInfoDaoImpl;
	@Autowired
	private UnLoadingInfoDaoImpl unloadingInfoDaoImpl;
	@Autowired
	private ERPDaoImpl erpDaoImpl;
	@Autowired
	private StoreKeeperDaoImpl storeKeeperDaoImpl;
	@Autowired
	private AuxiliaryInfoDaoImpl auxiliaryInfoDaoImpl;

	private String parseFileNameWorkShop2(String name) {
		String val = null;

		String regex = "\\d{4}-\\d{1,2}-\\d{1,2}-\\D+2.xls";
		String regex_date = "\\d{4}-\\d{1,2}-\\d{1,2}";

		Pattern p1 = Pattern.compile(regex);
		Pattern p2 = Pattern.compile(regex_date);
		if (p1.matcher(name).matches()) {
			Matcher matcher = p2.matcher(name);
			if (matcher.find()) {
				String date = matcher.group();
				if (isValidDate(date))
					val = date;
			}
		}

		return val;
	}

	private String[] parseFileNameStoreWorkshop1(String name) {
		String[] val = null;

		String regex = "1\\D+-\\d{4}-\\d{1,2}-\\d{1,2}-\\(\\d{4}-\\d{1,2}-\\d{1,2}\\)-\\(\\d+\\).xls";
		String regex_date = "\\d{4}-\\d{1,2}\\-\\d{1,2}";
		String regex_batch = "\\(\\d+\\)";

		Pattern p1 = Pattern.compile(regex);
		Pattern p2 = Pattern.compile(regex_date);
		Pattern p3 = Pattern.compile(regex_batch);
		if (p1.matcher(name).matches()) {
			Matcher matcher = p2.matcher(name);
			matcher.find();
			matcher.find();
			String date = matcher.group();
			if (isValidDate(date)) {
				val = new String[2];
				val[0] = date;

				Matcher matcher_batch = p3.matcher(name);
				if (matcher_batch.find()) {
					String batch = matcher_batch.group();
					val[1] = batch.substring(1, batch.length() - 1);
				}
			}
		}

		return val;
	}

	private String[] parseFileNameStoreWorkshop2(String name) {
		String[] val = null;

		String regex = "2\\D+-\\d{4}-\\d{1,2}-\\d{1,2}-\\(\\d{4}-\\d{1,2}-\\d{1,2}\\)-\\(\\d+\\).xls";
		String regex_date = "\\d{4}-\\d{1,2}\\-\\d{1,2}";
		String regex_batch = "\\(\\d+\\)";

		Pattern p1 = Pattern.compile(regex);
		Pattern p2 = Pattern.compile(regex_date);
		Pattern p3 = Pattern.compile(regex_batch);
		if (p1.matcher(name).matches()) {
			Matcher matcher = p2.matcher(name);
			matcher.find();
			matcher.find();
			String date = matcher.group();
			if (isValidDate(date)) {
				val = new String[2];
				val[0] = date;

				Matcher matcher_batch = p3.matcher(name);
				if (matcher_batch.find()) {
					String batch = matcher_batch.group();
					val[1] = batch.substring(1, batch.length() - 1);
				}
			}

		}

		return val;
	}

	private String parseFileNameWorkShop1(String name) {
		String val = null;

		String regex = "\\d{4}-\\d{1,2}-\\d{1,2}-\\D+1.xls";
		String regex_date = "\\d{4}-\\d{1,2}-\\d{1,2}";

		Pattern p1 = Pattern.compile(regex);
		Pattern p2 = Pattern.compile(regex_date);
		if (p1.matcher(name).matches()) {
			Matcher matcher = p2.matcher(name);
			if (matcher.find()) {
				String date = matcher.group();
				if (isValidDate(date))
					val = date;
			}
		}

		return val;
	}

	private boolean isValidDate(String str) {
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}

	private List<ProductionInfo> parseProductionLine2(String date, InputStream inputStream) {
		List<ProductionInfo> ls = new ArrayList<>();
		Workbook workbook;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
		try {
			workbook = Workbook.getWorkbook(inputStream);
			Sheet sheet = workbook.getSheet(0);
			for (int i = 2; i < sheet.getRows(); i++) {
				int index = 1;
				Cell cell = sheet.getCell(index++, i);
				String val = cell.getContents();
				cell = sheet.getCell(index++, i);
				String code = cell.getContents();
				if (!val.isEmpty() && !code.isEmpty()) {
					ProductionInfo productionInfo = new ProductionInfo();
					productionInfo.setProductline(val);
					productionInfo.setTasknumber(code);

					productionInfo.setWorkshop(WORKSHOP.WORKSHOP2);

					val = sheet.getCell(index++, i).getContents();
					productionInfo.setProductcode(fillProductCode(val, WORKSHOP.WORKSHOP2));

					val = sheet.getCell(index++, i).getContents();
					productionInfo.setSpec(val);

					val = sheet.getCell(index++, i).getContents();
					productionInfo.setSchedulednum(val);

					val = sheet.getCell(index++, i).getContents();
					productionInfo.setDailynum(val);

					val = sheet.getCell(index++, i).getContents();
					productionInfo.setRemark(val);

					productionInfo.setDate(Date.valueOf(date));
					ls.add(productionInfo);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ls;
	}

	private List<StoreProductionInfo> parseProductionLineStore(String date, String batch, InputStream inputStream,
			String worksop) {
		List<StoreProductionInfo> ls = new ArrayList<>();
		Workbook workbook;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			workbook = Workbook.getWorkbook(inputStream);
			Sheet sheet = workbook.getSheet(0);
			for (int i = 1; i < sheet.getRows(); i++) {
				int index = 1;
				Cell cell = sheet.getCell(index++, i);
				String val = cell.getContents();
				if (!val.isEmpty()) {
					StoreProductionInfo productionInfo = new StoreProductionInfo();
					productionInfo.setProductline(val);
					productionInfo.setUploadbatch(batch);

					val = sheet.getCell(index++, i).getContents();
					productionInfo.setTasknumber(val);

					productionInfo.setWorkshop(worksop);

					val = sheet.getCell(index++, i).getContents();
					productionInfo.setProductcode(val);

					cell = sheet.getCell(index++, i);

					val = cell.getContents();
					productionInfo.setSpec(val);

					// System.out.println("color:"+cell.getCellFormat().getBackgroundColour()+""+val);

					val = sheet.getCell(index++, i).getContents();
					productionInfo.setSchedulednum(val);

					val = sheet.getCell(index++, i).getContents();
					productionInfo.setDailynum(val);

					val = sheet.getCell(index++, i).getContents();
					productionInfo.setRemark(val);

					productionInfo.setDate(Date.valueOf(date));
					ls.add(productionInfo);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ls;
	}

	private List<StoreKeeper> parseStoreKeeper(InputStream inputStream) {
		List<StoreKeeper> ls = new ArrayList<>();
		Workbook workbook;
		try {
			workbook = Workbook.getWorkbook(inputStream);
			Sheet sheet = workbook.getSheet(0);
			for (int i = 1; i < sheet.getRows(); i++) {
				int index = 1;
				Cell cell = sheet.getCell(index++, i);
				String val = cell.getContents();
				if (!val.isEmpty()) {
					StoreKeeper storeKeeper = new StoreKeeper();
					storeKeeper.setRepository(val);

					val = sheet.getCell(index++, i).getContents();
					storeKeeper.setMaterialnumber(val);

					val = sheet.getCell(index++, i).getContents();
					storeKeeper.setMaterialname(val);

					val = sheet.getCell(index++, i).getContents();
					storeKeeper.setLocation(val);

					val = sheet.getCell(index++, i).getContents();
					storeKeeper.setSpec(val);

					val = sheet.getCell(index++, i).getContents();
					storeKeeper.setStorekeeper(val);

					val = sheet.getCell(index++, i).getContents();
					storeKeeper.setIdentifier(val);
					ls.add(storeKeeper);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ls;
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

	private List<ProductionInfo> parseProductionLine1(String date, InputStream inputStream) {
		List<ProductionInfo> ls = new ArrayList<>();
		Workbook workbook;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
		try {
			workbook = Workbook.getWorkbook(inputStream);
			Sheet sheet = workbook.getSheet(0);
			for (int i = 2; i < sheet.getRows(); i++) {
				int index = 0;
				Cell cell = sheet.getCell(index++, i);
				String val = cell.getContents();
				if (!val.isEmpty()) {
					ProductionInfo productionInfo = new ProductionInfo();
					productionInfo.setWorkshop(WORKSHOP.WORKSHOP1);

					productionInfo.setTasknumber(val);
					productionInfo.setDate(Date.valueOf(date));

					val = sheet.getCell(index++, i).getContents();
					productionInfo.setProductcode(fillProductCode(val, WORKSHOP.WORKSHOP1));

					index++;

					val = sheet.getCell(index++, i).getContents();
					productionInfo.setSpec(val);

					val = sheet.getCell(index++, i).getContents();
					productionInfo.setSchedulednum(val);

					val = sheet.getCell(index++, i).getContents();
					productionInfo.setDailynum(val);

					val = sheet.getCell(index++, i).getContents();
					productionInfo.setProductline(val);
					index += 9;
					val = sheet.getCell(index++, i).getContents();
					productionInfo.setRemark(val);
					//
					val = sheet.getCell(index++, i).getContents();
					productionInfo.setProcessflow(val);

					val = sheet.getCell(index++, i).getContents();
					productionInfo.setBoardcode(val);
					ls.add(productionInfo);

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ls;

	}

	@Override
	public ReturnData<ProductionInfo> uploadProductionInfoOfWorkShop2(String name, InputStream inputStream) {
		ReturnData<ProductionInfo> returnData = new ReturnData<>();

		if (name == null) {
			returnData.setReturn_msg("文件名错误");
			returnData.setReturn_code(MESSAGE.RETURN_FAIL);
			return returnData;
		}

		String date = parseFileNameWorkShop2(name);
		if (date == null) {
			returnData.setReturn_msg("文件名错误");
			returnData.setReturn_code(MESSAGE.RETURN_FAIL);
			return returnData;
		}

		// 解析excel数据并保存到数据库
		List<ProductionInfo> ls = parseProductionLine2(date, inputStream);
		if (ls.isEmpty()) {
			returnData.setReturn_msg("文件内容解析出错");
			returnData.setReturn_code(MESSAGE.RETURN_FAIL);
			return returnData;
		}
		returnData.setReturn_msg("文件上传成功");
		returnData.setReturn_code(MESSAGE.RETURN_SUCCESS);
		productionInfoDaoImpl.updateProductionInfoList(ls, date, WORKSHOP.WORKSHOP2);

		// returnData.setData(ls);

		return returnData;
	}

	@Override
	public ReturnData<ProductionInfo> uploadProductionInfoOfWorkshop1(String name, InputStream inputStream) {
		ReturnData<ProductionInfo> returnData = new ReturnData<>();

		if (name == null) {
			returnData.setReturn_msg("文件名错误");
			returnData.setReturn_code(MESSAGE.RETURN_FAIL);
			return returnData;
		}

		String date = parseFileNameWorkShop1(name);
		if (date == null) {
			returnData.setReturn_msg("文件名错误");
			returnData.setReturn_code(MESSAGE.RETURN_FAIL);
			return returnData;
		}

		// 解析excel数据并保存到数据库
		List<ProductionInfo> ls = parseProductionLine1(date, inputStream);
		if (ls.isEmpty()) {
			returnData.setReturn_msg("文件内容解析出错");
			returnData.setReturn_code(MESSAGE.RETURN_FAIL);
			return returnData;
		}
		returnData.setReturn_msg("文件上传成功");
		returnData.setReturn_code(MESSAGE.RETURN_SUCCESS);
		productionInfoDaoImpl.updateProductionInfoList(ls, date, WORKSHOP.WORKSHOP1);

		// returnData.setData(ls);

		return returnData;
	}

	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是GB2312
				String s = encode;
				return s; // 是的话，返回“GB2312“，以下代码同理
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是ISO-8859-1
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是UTF-8
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是GBK
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return ""; // 如果都不是，说明输入的内容不属于常见的编码格式。
	}

	private boolean parseFileNameAuxiliaryInfo(String name) {
		String regex = new String("关键辅料条码扫描清单.xls");
		byte[] temp;
		try {
			temp = name.getBytes("ISO-8859-1");
			String newStr = new String(temp, "GB2312");
			Pattern p1 = Pattern.compile(regex);
			System.out.println(newStr + " " + regex);
			if (p1.matcher(newStr).matches()) {
				return true;
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("catch exceptipn");
		}
		return false;
	}
	
	private List<AuxiliaryInfo> parseAuxiliaryInfo(InputStream inputStream) {
		List<AuxiliaryInfo> ls = new ArrayList<>();
		
		Workbook workbook;
		try {
			workbook = Workbook.getWorkbook(inputStream);
			Sheet sheet = workbook.getSheet(0);
			for (int i = 5; i < sheet.getRows(); i++) {
				int index = 0;
				Cell cell = sheet.getCell(index++, i);
				String val = cell.getContents();
				if (!val.isEmpty() ) {
					AuxiliaryInfo auxiliaryInfo = new AuxiliaryInfo();

					val = sheet.getCell(index++, i).getContents();
					auxiliaryInfo.setInvcode(val);
					
					val = sheet.getCell(index++, i).getContents();
					auxiliaryInfo.setInvname(val);
					
					val = sheet.getCell(index++, i).getContents();
					auxiliaryInfo.setInvstd(val);
					
					val = sheet.getCell(index++, i).getContents();
					auxiliaryInfo.setValidityperiod(val);
					
					val = sheet.getCell(index++, i).getContents();
					auxiliaryInfo.setBrand(val);
					
					val = sheet.getCell(index++, i).getContents();
					auxiliaryInfo.setOrigin(val);
					
					index += 5;
					val = sheet.getCell(index++, i).getContents();
					auxiliaryInfo.setModels(val);
					
					ls.add(auxiliaryInfo);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ls;
	}


	@Override
	public ReturnData<AuxiliaryInfo> uploadAuxiliaryInfo(String name, InputStream inputStream) {
		ReturnData<AuxiliaryInfo> returnData = new ReturnData<>();
		if (name == null) {
			returnData.setReturn_msg("文件错误");
			returnData.setReturn_code(MESSAGE.RETURN_FAIL);
			return returnData;
		}
//		if (!parseFileNameAuxiliaryInfo(name)) {
//			returnData.setReturn_msg("文件名错误");
//			returnData.setReturn_code(MESSAGE.RETURN_FAIL);
//			return returnData;
//		}
		List<AuxiliaryInfo> ls = parseAuxiliaryInfo(inputStream);
		if (ls.isEmpty()) {
			returnData.setReturn_msg("文件内容解析出错");
			returnData.setReturn_code(MESSAGE.RETURN_FAIL);
			return returnData;
		}
		auxiliaryInfoDaoImpl.updateAuxiliaryInfo(ls);
		returnData.setReturn_msg("文件上传成功");
		returnData.setReturn_code(MESSAGE.RETURN_SUCCESS);
		return returnData;
	}

	@Override
	public ReturnData<ProductionInfo> uploadProductionInfo(String name, InputStream inputStream) {
		ReturnData<ProductionInfo> returnData = new ReturnData<>();
		int workshop = 0;
		if (name == null) {
			returnData.setReturn_msg("文件错误");
			returnData.setReturn_code(MESSAGE.RETURN_FAIL);
			return returnData;
		}

		String date = parseFileNameWorkShop1(name);
		if (date == null) {
			date = parseFileNameWorkShop2(name);
			if (date == null) {
				returnData.setReturn_msg("文件名错误");
				returnData.setReturn_code(MESSAGE.RETURN_FAIL);
				return returnData;
			} else {
				workshop = 2;
			}
		} else {
			workshop = 1;
		}

		// 解析excel数据并保存到数据库
		List<ProductionInfo> ls;
		if (workshop == 1) {
			ls = parseProductionLine1(date, inputStream);
		} else {
			ls = parseProductionLine2(date, inputStream);
		}

		if (ls.isEmpty()) {
			returnData.setReturn_msg("文件内容解析出错");
			returnData.setReturn_code(MESSAGE.RETURN_FAIL);
			return returnData;
		}
		returnData.setReturn_msg("文件上传成功");
		returnData.setReturn_code(MESSAGE.RETURN_SUCCESS);
		// System.out.println("workshop "+workshop);
		if (workshop == 1) {
			productionInfoDaoImpl.updateProductionInfoList(ls, date, WORKSHOP.WORKSHOP1);
		} else {
			productionInfoDaoImpl.updateProductionInfoList(ls, date, WORKSHOP.WORKSHOP2);
		}

		List<VOrderDetail> vOrderDetails = erpDaoImpl.getVOrderDetail(ls);
		List<ProductInfoDetail> productInfoDetails = erpDaoImpl.getProductInfoDetail(vOrderDetails);

		if (workshop == 1) {
			productionInfoDaoImpl.updateDetailList(productInfoDetails, date, WORKSHOP.WORKSHOP1);
		} else {
			productionInfoDaoImpl.updateDetailList(productInfoDetails, date, WORKSHOP.WORKSHOP2);
		}

		// returnData.setData(ls);

		return returnData;
	}

	@Override
	public ReturnData<UnLoadingInfo> uploadProductionInfoStore(String name, InputStream inputStream) {
		ReturnData<UnLoadingInfo> returnData = new ReturnData<>();
		int workshop = 0;
		if (name == null) {
			returnData.setReturn_msg("文件名错误");
			returnData.setReturn_code(MESSAGE.RETURN_FAIL);
			return returnData;
		}

		String[] val = parseFileNameStoreWorkshop1(name);
		if (val == null) {
			val = parseFileNameStoreWorkshop2(name);
			if (val == null) {
				// System.out.println("文件错误");
				returnData.setReturn_msg("文件名错误");
				returnData.setReturn_code(MESSAGE.RETURN_FAIL);
				return returnData;
			} else {
				workshop = 2;
			}
		} else {
			workshop = 1;
		}
		// System.out.println("parseFileNameStore "+workshop+" "+val[0]+"
		// "+val[1]);

		// 解析excel数据并保存到数据库
		List<StoreProductionInfo> ls = null;
		if (workshop == 1) {
			ls = parseProductionLineStore(val[0], val[1], inputStream, WORKSHOP.WORKSHOP1);
		} else {
			ls = parseProductionLineStore(val[0], val[1], inputStream, WORKSHOP.WORKSHOP2);
		}
		if (ls.isEmpty()) {
			returnData.setReturn_msg("文件内容解析出错");
			returnData.setReturn_code(MESSAGE.RETURN_FAIL);
			return returnData;
		}
		productionInfoDaoImpl.updateStoreProductionInfoList(ls, val[0], val[1]);
		//
		List<RdRecord> rdRecordls = erpDaoImpl.getRdRecord(ls);
		List<UnLoadingInfo> unLoadingInfols = erpDaoImpl.getRdRecords(rdRecordls);
		unLoadingInfols = erpDaoImpl.updateUnLoadingInfo(unLoadingInfols);
		unLoadingInfols = storeKeeperDaoImpl.getALLUnLoadingInfo(unLoadingInfols);
		unloadingInfoDaoImpl.updateUnLoadingInfoList(unLoadingInfols, val[0], val[1]);
		//
		System.out.println("xls parse size:" + ls.size());
		System.out.println("rds parse size:" + rdRecordls.size());
		System.out.println("unLoadingInfols parse size:" + unLoadingInfols.size());
		//
		//
		// {
		// // 生成当天的unloadinfo并保存到数据可以
		// // 1. 根据生产产品的ID查找所需要meterial ID；
		// // 2. 根据metrailID 查找storekeeper数据库 绑定 保管员
		// // 3.保存到unloadinfo数据库；
		returnData.setReturn_msg("文件上传成功");
		returnData.setReturn_code(MESSAGE.RETURN_SUCCESS);
		// List<UnLoadingInfo> lsa =
		// unloadingInfoDaoImpl.getAllUnLoadingInfoByDate("2016-08-25");
		// returnData.setData(lsa);
		//
		// }
		return returnData;
	}

	@Override
	public ReturnMessage uploadStoreKeeperInfo(String name, InputStream inputStream) {
		ReturnMessage returnData = new ReturnMessage();
		returnData.setReturn_msg("文件上传成功");
		returnData.setReturn_code(MESSAGE.RETURN_SUCCESS);
		List<StoreKeeper> ls = parseStoreKeeper(inputStream);
//		System.out.println("before uploadStoreKeeperInfo size:" + ls.size());
		storeKeeperDaoImpl.updateUnLoadingInfoList(ls);
//		System.out.println("after uploadStoreKeeperInfo size:" + ls.size());
		return returnData;
	}

	@Override
	public ReturnData<AuxiliaryInfo> getAllAuxiliaryInfo() {
		ReturnData<AuxiliaryInfo> returnData= new ReturnData<>();
		
		List<AuxiliaryInfo> ls= auxiliaryInfoDaoImpl.getAllInfo();
		returnData.setReturn_code(MESSAGE.RETURN_FAIL);
		returnData.setReturn_msg(MESSAGE.QUERY_NONE);
		if(ls != null){
			if(!ls.isEmpty()){
				returnData.setReturn_code(MESSAGE.RETURN_SUCCESS);
				returnData.setReturn_msg(MESSAGE.QUERY_SUCCESS);
				returnData.setData(ls);
			}
		}
		return returnData;
	}

}
