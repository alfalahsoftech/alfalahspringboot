package com.alfalahsoftech.exception;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
@Provider
public class FileHandlingException extends Exception implements  ExceptionMapper<FileHandlingException> {
	private static final long serialVersionUID = 1L;
	public FileHandlingException(){
	 super("File Not Found");	
	}
	public FileHandlingException(String message){
		 super(message);	
		}
	
	@Override
	public Response toResponse(FileHandlingException exception) {
		
		return Response.status(Status.NOT_FOUND).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN).build();
	}

}