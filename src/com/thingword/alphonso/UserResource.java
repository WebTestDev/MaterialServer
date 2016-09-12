package com.thingword.alphonso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.secure.internal.DisabledJaccServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.thingword.alphonso.Configure.ReqAddUser;
import com.thingword.alphonso.Configure.ReqDelUser;
import com.thingword.alphonso.Configure.ReqInfo;
import com.thingword.alphonso.Configure.ReqUpdateUser;
import com.thingword.alphonso.Configure.ReturnData;
import com.thingword.alphonso.Configure.ReturnLoginInfo;
import com.thingword.alphonso.Configure.ReturnMessage;
import com.thingword.alphonso.Configure.ReturnUserList;
import com.thingword.alphonso.bean.DistributionInfo;
import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.ProductionInfo;
import com.thingword.alphonso.bean.UnLoadingInfo;
import com.thingword.alphonso.bean.User;
import com.thingword.alphonso.dao.LoadingInfoDao;
import com.thingword.alphonso.dao.impl.LoadingInfoDaoImpl;
import com.thingword.alphonso.service.LoadingInfoService;
import com.thingword.alphonso.service.ProductionInfoService;
import com.thingword.alphonso.service.impl.DistriInfoServiceImpl;
import com.thingword.alphonso.service.impl.ExcelServiceImpl;
import com.thingword.alphonso.service.impl.LoadingInfoServiceImpl;
import com.thingword.alphonso.service.impl.ProductionInfoServiceImpl;
import com.thingword.alphonso.service.impl.UnLoadingInfoServiceImpl;
import com.thingword.alphonso.service.impl.UserServiceImpl;
import com.thingword.alphonso.service.impl.UserTestServiceImpl;

import jersey.repackaged.com.google.common.collect.Lists;

/**
 * 用户资源
 * 
 * @author waylau.com 2014-7-26
 */
@Path("/materail")
public class UserResource {
	private static final Logger LOGGER = Logger.getLogger(UserResource.class.getName());

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private LoadingInfoServiceImpl loadingInfoServiceImpl;

	@Autowired
	private UnLoadingInfoServiceImpl unloadingInfoServiceImpl;

	@Autowired
	private DistriInfoServiceImpl distriInfoServiceImpl;

	@Autowired
	private ExcelServiceImpl excelServiceImpl;
	
	@Autowired
	private ProductionInfoServiceImpl productionInfoServiceImpl;
	
	@Autowired
	private UserTestServiceImpl userTestServiceImpl;

	public UserResource() {
		LOGGER.fine("UserResource()");
	}

	@POST
	@Path("/reqUserLoginInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnLoginInfo reqUserLogin(User user) {
		ReturnLoginInfo returnLoginInfo = userServiceImpl.checkUser(user);
		return returnLoginInfo;
	}

	@POST
	@Path("/reqLoadingInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnData<LoadingInfo> reqLoadingInfo(ReqInfo reqInfo) {
		System.out.println("reqLoadingInfo");
		return loadingInfoServiceImpl.getLoadingInfoByDate(reqInfo);
	}

	@POST
	@Path("/reqUnLoadingInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnData<UnLoadingInfo> reqUnLoadingInfo(ReqInfo reqInfo) {
		return unloadingInfoServiceImpl.getUnLoadingInfoByDate(reqInfo);
	}
	
	@POST
	@Path("/reqProductionInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnData<ProductionInfo> reqProductionInfo(ReqInfo reqInfo) {
		return productionInfoServiceImpl.getProductionInfoByDate(reqInfo.getDate());
	}

	@POST
	@Path("/reqDistriInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnData<DistributionInfo> reqDistriInfo(ReqInfo reqInfo) {
		return distriInfoServiceImpl.getDistriInfoByDate(reqInfo);
	}
	
	@POST
	@Path("/reqUserTest")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnData<DistributionInfo> reqUserTest(ReqInfo reqInfo) {
		userTestServiceImpl.getAllUsers();
		return null;
	}

//	@POST
//	@Path("/upload")
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
//			@FormDataParam("file") FormDataContentDisposition fileDetail) {
//
//		ReturnMessage returnMessage = excelServiceImpl.uploadProductionInfo(fileDetail.getFileName(),
//				uploadedInputStream);
//		return Response.status(200).entity(returnMessage.getReturn_msg()).build();
//
//	}
	
	@POST
	@Path("/uploadProductionInfoForStore")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public ReturnData<UnLoadingInfo> uploadProductionInfoForStore(@FormDataParam("filepath") InputStream uploadedInputStream,
			@FormDataParam("filepath") FormDataContentDisposition fileDetail) {

		return excelServiceImpl.uploadProductionInfoStore(fileDetail.getFileName(),
				uploadedInputStream);

	}
	
	@POST
	@Path("/uploadProductionInfo")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public ReturnData<ProductionInfo> uploadProductionInfo(@FormDataParam("filepath") InputStream uploadedInputStream,
			@FormDataParam("filepath") FormDataContentDisposition fileDetail) {

		return excelServiceImpl.uploadProductionInfo(fileDetail.getFileName(),
				uploadedInputStream);

	}
	
	@GET
	@Path("/getLoadinfoForTest")
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnData<UnLoadingInfo> getLoadinfoForTest() {
		ReqInfo reqInfo = new ReqInfo();
		reqInfo.setDate("2016-08-25");
		return unloadingInfoServiceImpl.getUnLoadingInfoByDate(reqInfo);
	}
	

	@GET
	@Path("/reqUserList")
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnUserList reqUserList() {
		return userServiceImpl.getUserList();
	}

	@POST
	@Path("/reqAddUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnUserList reqAddUser(ReqAddUser adduser) {
		System.out.println("adduser" + adduser.getAdd_rights());
		return userServiceImpl.createUser(adduser);
	}

	@POST
	@Path("/reqDelUser")
	@Consumes(MediaType.APPLICATION_JSON)
	public String reqDelUser(ReqDelUser deluser) {
		System.out.println("deluser" + deluser.getId());
		if (userServiceImpl.deleteUserById(deluser))
			return String.valueOf(1);
		else
			return String.valueOf(0);
	}
	
	@POST
	@Path("/reqUpdateUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnUserList reqUpdateUser(ReqUpdateUser user) {
		System.out.println("updateAddUser" + user.getEdit_account());
		return userServiceImpl.updateUser(user);
		//return userServiceImpl.createUser(adduser);
	}

}