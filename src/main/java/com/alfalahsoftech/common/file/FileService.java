package com.alfalahsoftech.common.file;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.controller.AFBaseController;
import com.alfalahsoftech.exception.FileHandlingException;
import com.alfalahsoftech.web.AFWebContextListener;

@Path("files")
public class FileService extends AFBaseController {
	private static final long serialVersionUID = 1L;
	@Context
	UriInfo uriInfo;
	private static final String FILE_PATH = AFWebContextListener.contextPath+"/src/com/alflah/common/file/FileService.java";
	//public FileService() {
	//		System.out.println("  FileService  uriInfo= " +uriInfo.getAbsolutePath());// you cannot access uriInfo because it is initialized after constructor invocation
	//}

	//java.lang.NoSuchMethodException: Could not find a suitable constructor in com.alflah.common.file.FileService class.
	//	public FileService(String value) {
	//		System.out.println("  FileService  uriInfo= " +uriInfo.getAbsolutePath());
	//	}
	@Context SecurityContext secCtxt;

	
	@GET
	@Path("/downloadFile/{fileName}")
	public Response downloadFile(@PathParam("fileName") String fileName) throws FileHandlingException {
		System.out.println("sssssssssssssssssssssssssssssssssssssssss");
		if(!fileName.equals("query.sql")) {
			throw new FileHandlingException();
		}

		return Response.status(Status.FOUND).build();
	}


	@GET
	@Path("/test")
	public void testService(String value) {
		System.out.println("  FileService  uriInfo= " +uriInfo.getAbsolutePath());
	}
	@GET
	@Path("/download")
	@Produces("text/plain")
	public Response getFile() {

		File file = new File(FILE_PATH);

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
				"attachment; filename=\"FileService.java\"");
		return response.build();

	}
	@POST
	@Path("/pdf")
	@Produces("application/pdf")
	public javax.ws.rs.core.Response getPdf() throws Exception
	{
		System.out.println("downloading file ");
		File file = new File(AFWebContextListener.contextPath+"HelloWorld.pdf");
		//	    FileInputStream fileInputStream = new FileInputStream(file);
		//	    javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok((Object) fileInputStream);
		//	    responseBuilder.type("application/pdf");
		//	    responseBuilder.header("Content-Disposition", "filename=test.pdf");
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",  "attachment; filename=bill.pdf");

		return response.build();
	}
	@Override
	public Object callMethod(AFHashMap<String, Object> objectHash) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void addResponse() {
		// TODO Auto-generated method stub

	}
}