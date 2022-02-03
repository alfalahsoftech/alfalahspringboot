package com.alfalahsoftech.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.common.file.MultiPartForm;

@Path("fileProcess")
public class FileProcessController extends AFBaseController {
	@Context SecurityContext secCtxt;

	@POST
	@Path("/uploadFiles")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response upploadFile(@Context HttpServletRequest servletReq,@FormDataParam("isNew") String isNew,@FormDataParam("destDir") String dest,  @FormDataParam("file") InputStream inStream, @FormDataParam("file") FormDataContentDisposition fileMetaData) {
		System.out.println("fileMetaData== "+fileMetaData.getFileName());
		MultiPartForm multiPartForm = new MultiPartForm();
		multiPartForm.setFile(inStream);
		multiPartForm.setFileMetaData(fileMetaData);
		multiPartForm.setIsNew(isNew);
		multiPartForm.setDestDir(dest);

		afDebug("Destination: "+multiPartForm.getDestDir());
		afDebug("Is New: "+ multiPartForm.getIsNew());
		afDebug("getFile: "+multiPartForm.getFile());

		String appPath =servletReq.getServletContext().getRealPath("/");

		afDebug(secCtxt.getAuthenticationScheme());

		java.nio.file.Path p =Paths.get(appPath);
		String  msg =uploadFileAt(p, multiPartForm);
		msg = msg.equals("sucess")?"File uploaded scuccessfully":msg;

		return Response.ok("{\"response\":"+msg+"!}").build();
	}


	private String  uploadFileAt(java.nio.file.Path p, MultiPartForm multiPartForm) {
		InputStream inStream = multiPartForm.getFile();
		FormDataContentDisposition fileMetaData  = multiPartForm.getFileMetaData();

		AtomicReference<String> filePath = new AtomicReference<>();
		String fileDirLocation = getFileOrDirLocation(fileMetaData.getFileName(),p, filePath);
		System.out.println("fileDirLocation:   "+ fileDirLocation);
		File serverFile  = null;
		if(Objects.nonNull(fileDirLocation) && multiPartForm.getIsNew().equals("no")) {
			serverFile = new File(fileDirLocation);

		}else {
			fileDirLocation = multiPartForm.getDestDir();
			serverFile  = new File(p.toAbsolutePath().toFile(),fileDirLocation);
			System.out.println("serverFile.isDirectory()== "+serverFile.isDirectory());
			if(!serverFile.exists()) {
				serverFile.mkdirs();
				System.out.println(" Directory created: "+ serverFile.getAbsolutePath());

			}
			serverFile = new File(serverFile,fileMetaData.getFileName());
		}
		System.out.println("File goin to be replaced at: "+serverFile.getAbsolutePath());

		try(BufferedInputStream bfreader = new BufferedInputStream((inStream));
				BufferedOutputStream bfwriter = new BufferedOutputStream(new FileOutputStream(serverFile));) {

			int line = bfreader.read();
			while(line != -1) {

				bfwriter.write(line);
				line = bfreader.read();
			}

			System.out.println("Server File Location=" + serverFile.getAbsolutePath());

		} catch (Exception e) {
			e.printStackTrace();
			return "Error in loading file"+e.getMessage();

		}
		return "success";
	}
	private static String getFileOrDirLocation(String fileName, java.nio.file.Path path, AtomicReference<String> filePath) {
		try {
			Files.list(path).
			forEach(e->{
				System.out.println(e);
				if(e.endsWith(fileName)) {
					filePath.getAndSet(e.toString());
					System.out.println("  File has been at path: " + filePath);
					return ;
				}
				if(Files.isDirectory(e)) {
					System.out.println("Directory");
					getFileOrDirLocation(fileName, e,filePath);
				}
			});	
			System.out.println("filePath 11= "+filePath.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePath.get();
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
