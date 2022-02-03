package com.alfalahsoftech.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONException;
import org.json.JSONObject;

import com.alfalahsoftech.common.file.AFJsonParser;

@WebListener
public class AFWebContextListener implements ServletContextListener {
	public static String contextPath=null;
	public static JSONObject appConfig;
	static Logger log = Logger.getLogger(AFWebContextListener.class);

	
	public void init(String log4jProp) throws ServletException {
		System.out.println("Log4JInitServlet is initializing log4j");
//		String log4jLocation = config.getInitParameter("log4j-properties-location");

//		ServletContext sc = config.getServletContext();

//		if (log4jLocation == null) {
//			System.err.println("*** No log4j-properties-location init param, so initializing log4j with BasicConfigurator");
//			BasicConfigurator.configure();
//		} else {
//			String webAppPath = sc.getRealPath("/");
//			String log4jProp = webAppPath + log4jLocation;
			File file = new File(log4jProp);
			if (file.exists()) {
				System.out.println("Initializing log4j with: " + log4jProp);
//				FileAppender fp = new  
				PropertyConfigurator.configure(log4jProp);
			} else {
				System.err.println("*** " + log4jProp + " file not found, so initializing log4j with BasicConfigurator");
				BasicConfigurator.configure();
			}
//		}
		
	}
	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		System.out.println("############  contextInitialization started  ###########");
		contextPath = contextEvent.getServletContext().getRealPath("/");
		String meta =  contextEvent.getServletContext().getRealPath("META-INF");
		try {
			init(contextPath+"WEB-INF/classes/log4j.properties");
		} catch (ServletException e) {
			e.printStackTrace();
		}
		log.info("MMMMMMMMMMMMMMM= META="+meta);
		log.info("AFWebContextListener.contextPath = " + contextPath);
		this.startApplication(contextEvent);
	}

	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		//		FNApplicationObject.factory().closeApplication();
	}

	private void startApplication(ServletContextEvent contextEvent) {
		System.out.println("################ startApplication called ##############");
		AFApplicationObject.executeFactory();
		try {
			appConfig = new JSONObject(AFJsonParser.jsonToSingleString(new FileReader(new File(AFApplicationObject.META_PATH+"glbDir/config/appConfig.json"))));
		} catch (JSONException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AFApplicationObject.factoryObj().servletContext=contextEvent.getServletContext();

		//	FNApplicationObject.factory().loadStartUpConfig();
		Enumeration<String> contextParams = contextEvent.getServletContext().getInitParameterNames();
		while (contextParams.hasMoreElements()) {
			String contextParam = contextParams.nextElement();
			AFApplicationObject.argMap.put(contextParam, contextEvent.getServletContext().getInitParameter(contextParam));
		}
		AFApplicationObject.factoryObj().startApplication();
		//	AFApplicationObject.factoryObj().loadData();
		if(AFWebContextListener.appConfig.getBoolean("isProdEnvorment")) {
			HitOtherApplication.hitApp();
		}

	}

}
