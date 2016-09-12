package com.thingword.alphonso.service.impl;

import java.io.IOException;
import java.io.InputStream;
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
import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.ProductionInfo;
import com.thingword.alphonso.bean.UnLoadingInfo;
import com.thingword.alphonso.dao.impl.ProductionInfoDaoImpl;
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

	@Override
	public ReturnData<ProductionInfo> uploadProductionInfo(String name, InputStream inputStream) {
		ReturnData<ProductionInfo> returnData = new ReturnData<>();

		String date = parseFileName(name);
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
		productionInfoDaoImpl.updateProductionInfoList(ls, date);
		
		returnData.setData(ls);

		return returnData;
	}

	@Override
	public String parseFileName(String name) {
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
				if (!val.isEmpty()) {
					ProductionInfo productionInfo = new ProductionInfo();
					productionInfo.setProductline(val);

					val = sheet.getCell(index++, i).getContents();
					productionInfo.setTasknumber(val);
					
					productionInfo.setWorkshop("二车间");

					val = sheet.getCell(index++, i).getContents();
					productionInfo.setProductcode(val);

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

	@Override
	public ReturnData<UnLoadingInfo> uploadProductionInfoStore(String name, InputStream inputStream) {
		ReturnData<UnLoadingInfo> returnData = new ReturnData<>();

		String date = parseFileName(name);
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
		productionInfoDaoImpl.updateProductionInfoList(ls, date);
		
		{
			//生成当天的unloadinfo并保存到数据可以
			//1. 根据生产产品的ID查找所需要meterial ID；
			//2. 根据metrailID 查找storekeeper数据库 绑定 保管员
			//3.保存到unloadinfo数据库；
			returnData.setReturn_msg("文件上传成功");
			returnData.setReturn_code(MESSAGE.RETURN_SUCCESS);
			List<UnLoadingInfo> lsa = unloadingInfoDaoImpl.getAllUnLoadingInfoByDate("2016-08-25");
			returnData.setData(lsa);
		}

		return returnData;
	}

}
