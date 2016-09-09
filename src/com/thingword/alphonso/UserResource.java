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

import com.thingword.alphonso.bean.DistributionInfo;
import com.thingword.alphonso.bean.LoadingInfo;
import com.thingword.alphonso.bean.ReqInfo;
import com.thingword.alphonso.bean.ReturnData;
import com.thingword.alphonso.bean.ReturnLoginInfo;
import com.thingword.alphonso.bean.ReturnMessage;
import com.thingword.alphonso.bean.UnLoadingInfo;
import com.thingword.alphonso.bean.User;
import com.thingword.alphonso.dao.LoadingInfoDao;
import com.thingword.alphonso.dao.impl.LoadingInfoDaoImpl;
import com.thingword.alphonso.service.LoadingInfoService;
import com.thingword.alphonso.service.impl.DistriInfoServiceImpl;
import com.thingword.alphonso.service.impl.ExcelServiceImpl;
import com.thingword.alphonso.service.impl.LoadingInfoServiceImpl;
import com.thingword.alphonso.service.impl.UnLoadingInfoServiceImpl;
import com.thingword.alphonso.service.impl.UserServiceImpl;

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
	@Path("/reqDistriInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnData<DistributionInfo> reqDistriInfo(ReqInfo reqInfo) {
		return distriInfoServiceImpl.getDistriInfoByDate(reqInfo);
	}

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		ReturnMessage returnMessage = excelServiceImpl.uploadProductionInfo(fileDetail.getFileName(),
				uploadedInputStream);
		return Response.status(200).entity(returnMessage.getReturn_msg()).build();

	}

	// /**
	// * 增加
	// * @param user
	// */
	// @POST
	// @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	// public void createUser(User user) {
	// userServiceImpl.createUser(user);
	// }
	//
	// /**
	// * 删除
	// * @param id
	// */
	// @DELETE
	// @Path("{id}")
	// public void deleteUser(@PathParam("id")String id){
	// userServiceImpl.deleteUserById(id);
	// }
	//
	// /**
	// * 修改
	// * @param user
	// */
	// @PUT
	// @Consumes(MediaType.APPLICATION_XML)
	// public void updateUser(User user){
	// userServiceImpl.updateUser(user);
	// }
	//
	// /**
	// * 根据id查询
	// * @param id
	// * @return
	// */
	// @GET
	// @Path("{id}")
	// @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	// public User getUserById(@PathParam("id") String id){
	// System.out.println("getUserByName:"+id);
	// User u = userServiceImpl.getUserByName(id);
	// return u;
	// }
	//
	// /**
	// * 查询所有
	// * @return
	// */
	// @GET
	// @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	// public ReturnData getAllUsers(){
	// List<User> users = new ArrayList<User>();
	// users = userServiceImpl.getAllUsers();
	// ReturnData returnData = new ReturnData();
	// returnData.setReturn_code("111");
	// returnData.setReturn_msg("22");
	// returnData.setData(users);
	// return returnData;
	// }
}