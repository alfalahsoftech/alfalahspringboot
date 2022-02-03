package com.alfalahsoftech.common.file;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

public class MultiPartForm{
	private  String isNew = "no";
	private  String destDir;
	private  FormDataContentDisposition fileMetaData;
	private  InputStream file;
	
	
	public String getIsNew() {
		return isNew;
	}
	@FormDataParam("isNew")
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	public String getDestDir() {
		return destDir;
	}
	@FormDataParam("destDir")
	public void setDestDir(String destDir) {
		this.destDir = destDir;
	}
	public FormDataContentDisposition getFileMetaData() {
		return fileMetaData;
	}
	@FormDataParam("file")
	public void setFileMetaData(FormDataContentDisposition fileMetaData) {
		this.fileMetaData = fileMetaData;
	}
	public InputStream getFile() {
		return file;
	}
	@FormDataParam("file")
	public void setFile(InputStream file) {
		this.file = file;
	}
	
	
}