package com.alfalahsoftech.common.file;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alfalahsoftech.web.AFApplicationObject;
import com.alfalahsoftech.web.AFObject;
import com.alfalahsoftech.web.AFWebContextListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class AFJsonParser extends AFObject{
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(AFJsonParser.class);

	public static void log(String str) {
		log.debug("This is a debug message");
		log.info("This is an info message");
	}

	public static <T>  T parseFile(File file,Class<T> cls){
		//		JsonParser json = new JsonParser();
		try(FileReader reader =new FileReader(file)) {
			/*	e = json.parse(reader);
			System.out.println(e.isJsonObject());
			JsonObject j = ( JsonObject)e;*/

			Gson g = new Gson();
			T dbConfig =	g.fromJson(reader,cls);
			return dbConfig;

		} catch (JsonIOException e1) {
			e1.printStackTrace();
		} catch (JsonSyntaxException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return null;
	}

	static class PayRollAudDetail{
		public String userName;
		public String userID;//##Comment:Mantis:NA:CRNUM:SS2928: Labor Exception Report Enhancements Part 2 (manager name in feed)
		public String userFirstName;
		public String userLastName;
		public String dateTime;
		public String storeDateTimeDST;
		public String description;//
		public String type;//DAILY,WEEKLY,PAYROLL
		public Number fkHeaderId;// TdyMain,TwkMain,PayRollMain
		public Number fkEmpMainId;// Emp Pk
		public Number fkPunchId;
		public String punchType;
		public Integer fkStrMainId;// Temp
		public String busiDateStr;
		public String newValue;
		public String oldValue;
		public String action;
		public boolean isMgrAuth;
		public String mgrAuthName;
		public Number fkBreakPunchId;
		public String mgrAuthPunchType;
		public String mgrTimeCard;
		public String punchBusiDate;
		public String millis = "0";//##Comment:Mantis:29719:CRNUM:NA If user is opening Archived Daily Timekeeping objects then its throwing an exception
		public boolean isModified;
		public Number isAcrossDay=0;//##Comment:Mantis:NA:CRNUM:CR_00290 Split Payroll in eTimekeeping

	}

	public static final  java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<PayRollAudDetail>>(){}.getType(); //##Comment:Mantis:35613:CRNUM:NA Handling on special characters in the User name

	public static List<Object> parse(File file){
		try {
			return stringToJson(jsonToSingleString(new FileReader(file)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String  jsonToSingleString(File file ){
		try {
			return jsonToSingleString(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String  jsonToSingleString(Reader reader ){
		StringBuilder jsonString  = new StringBuilder();
		BufferedReader bfrReader = new BufferedReader(reader);
		try{
			for(String line = bfrReader.readLine(); line != null; line= bfrReader.readLine()){
				jsonString.append(line).append(" ");
			}
		}catch(Exception ex){

		}
		return jsonString.toString();
	}

	public static List<Object> stringToJson(String jsonString){
		List<Object> list=null;
		try {
			list =	jsonArrToList(new JSONArray(jsonString));
			System.out.println("Lis===> "+list);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<Object> jsonArrToList(JSONArray jsonArray) throws JSONException{
		ArrayList<Object>  list = new  ArrayList<>();
		Object obj = null;
		int len = jsonArray.length();
		if(jsonArray != null){
			for(int i  = 0; i <len; i++){
				obj = jsonArray.get(i);
				if(obj instanceof JSONObject){
					JSONObject  jsonObj = (JSONObject)obj;
					list.add(toMap(jsonObj));
				}else{
					list.add((JSONArray)obj);
				}
			}
		}
		return list;
	}

	public static  HashMap<String,Object> toMap(JSONObject jsonMap) throws JSONException{
		HashMap<String,Object> hashMap  = new  HashMap<>();
		Iterator<String> it  = jsonMap.keys();
		while(it.hasNext()){
			String  key = it.next();
			hashMap.put(key, fromJson(jsonMap.get(key)));

		}
		return hashMap;
	}

	public static  List<Object> toList(JSONArray array) throws JSONException{
		ArrayList<Object> list = new ArrayList<>();
		for (int i = 0; i < array.length(); i++) {
			list.add(array.get(i));
		}
		return list;
	}
	public  static  Object fromJson(Object jsonObj) throws JSONException{
		if(jsonObj != null){
			if(jsonObj instanceof JSONObject){
				return toMap((JSONObject)jsonObj);
			}else if(jsonObj instanceof JSONArray){
				return toList((JSONArray)jsonObj);
			}
		}
		return jsonObj;	
	}
	public static ArrayList<String> medindia(){
		///home/malam/workspace/alfalahsoftech/src/main/resources/META-INF/glbDir/medindia.net.txt
		File file = new File("./src/main/resources/META-INF/glbDir/medindia.net.txt");
		//		File file = new File(AFApplicationObject.META_PATH+"glbDir/medindia.net.txt.txt");   //for runtime
		ArrayList<String> mediList = new ArrayList<>();
		HashMap<String,String> medi_type = new HashMap<>();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader bfr = new BufferedReader(fr);
			String line  = bfr.readLine();

			while (line != null) {
				//			System.out.println(line);
				if(line.trim().equalsIgnoreCase("<tr>")) {

					line  = bfr.readLine();  //1-skip
					line  = bfr.readLine();
					line  = bfr.readLine();
					line = line.substring(line.indexOf("&nbsp;")).replace("</td>","").replace("</a>", "").replace("&nbsp;", "");
					String name_type=line.trim();
					mediList.add(line.trim());
				}
				line  = bfr.readLine();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println(medi_type.size());
		System.out.println(medi_type);

		System.out.println(mediList.size());
		System.out.println(mediList);

		return mediList;
	}
	public static ArrayList<String> getAllMedi(){
		File file = new File(AFApplicationObject.META_PATH+"glbDir/medi.txt");
		ArrayList<String> mediList = new ArrayList<>();
		HashMap<String,String> medi_type = new HashMap<>();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader bfr = new BufferedReader(fr);
			String line  = bfr.readLine();

			while (line != null) {
				//			System.out.println(line);
				if(line.trim().equalsIgnoreCase("<tr>")) {

					line  = bfr.readLine();  //1-skip
					line  = bfr.readLine();
					line = line.replace("<td>", "").replace("</td>","");
					String name_type=line.trim();
					mediList.add(line.trim());
					line  = bfr.readLine();  //3-skip 
					line  = bfr.readLine();  //4-skip
					line  = bfr.readLine();  //5-skip
					line  = bfr.readLine();  //6-skip
					line  = bfr.readLine();  //7-skip
					line  = bfr.readLine();  //8-skip
					line  = bfr.readLine();  //9-skip
					line = line.replace("<td>", "").replace("</td>","");

					medi_type.put(name_type, (line!=null?line.trim():""));

				}
				line  = bfr.readLine();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println(medi_type.size());
		System.out.println(medi_type);

		System.out.println(mediList.size());
		System.out.println(mediList);

		return mediList;
	}


	class RestCshMain{
		public String siteID;
		public String partnerID;
		public String eventID;
		public List<ERSRestCshMain> data;
		@Override
		public String toString() {
			return "RestCshMain [siteID=" + siteID + ", partnerID=" + partnerID + ", eventID=" + eventID + ", data="
					+ data + "]";
		}
		
	}
	class ERSRestCshMain {
		private String busiDate;
		private Double confNumb1;
		private Double confNumb2;
		private Double confNumb3;
		private Double confNumb4;
		private Double confNumb5;
		private Double confNumb6;
		private Double confNumb7;
		private Double confNumb8;
		private Double confNumb9;
		private Double confNumb10;
		private Double confNumb11;
		private Double confNumb12;
		private Double confNumb13;
		private Double confNumb14;
		private Double confNumb15;
		private Double confNumb16;
		@Override
		public String toString() {
			return "ERSRestCshMain [busiDate=" + busiDate + ", confNumb1=" + confNumb1 + ", confNumb2=" + confNumb2
					+ ", confNumb3=" + confNumb3 + ", confNumb4=" + confNumb4 + ", confNumb5=" + confNumb5
					+ ", confNumb6=" + confNumb6 + ", confNumb7=" + confNumb7 + ", confNumb8=" + confNumb8
					+ ", confNumb9=" + confNumb9 + ", confNumb10=" + confNumb10 + ", confNumb11=" + confNumb11
					+ ", confNumb12=" + confNumb12 + ", confNumb13=" + confNumb13 + ", confNumb14=" + confNumb14
					+ ", confNumb15=" + confNumb15 + ", confNumb16=" + confNumb16 + "]";
		}
		
	}

	public static void main(String[] args) {
		log("main method");
		
		System.out.println("main Method called ----------------");
	System.out.println("palin date: "+plainDateStr("20200414"));
		medindia();
		//getAllMedi();
		File file = new File(AFWebContextListener.contextPath+"src/main/resources/META-INF/glbDir/menu/menu.txt");
		file = new File("./src/main/resources/META-INF/glbDir/menu/menu.txt");
		//		File file = new File("X:/Other_Workspace/CentralMonitoring/src/main/resources/META-INF/setup/smsAdaptors.json");
		try {
			System.out.println("Go for json");
			//			Object obj=	getGsonObject().fromJson(jsonToSingleString(new File("./src/main/resources/META-INF/glbDir/menu/tdyaudmainjson.json")),type);
			Type typ =new com.google.gson.reflect.TypeToken<Map<String,String>>(){}.getType();
			String jsonStr = jsonToSingleString(new File("./src/main/resources/META-INF/glbDir/menu/restcshmain.json"));
			JSONObject jb = new JSONObject(jsonStr);
			JSONArray jArray = jb.getJSONArray("data");
			jArray.forEach(jsonObj->{
				JSONObject json = (JSONObject)jsonObj;
				Iterator<String> keyItr=json.keys();
				while(keyItr.hasNext()) {
				 String key = keyItr.next();
				 Object value = json.get(key);
				 System.out.println("Key: " + key + " Value: " + value);
				}
			});
			
			RestCshMain obj=	getGsonObject().fromJson(jsonStr,RestCshMain.class);

			obj.data.forEach(e->{
				System.out.println(getGsonObject().toJson(e));
			});

			/*	String jsonString= jsonToSingleString(new FileReader(file));
			HashMap<String,Object> m = (HashMap<String,Object>)stringToJson(jsonString).get(0);
			System.out.println(m);
			System.out.println("Liiiiii-= ");*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static Date plainDateStr(String dateStr) {
		String datePattern = "\\d{4}_\\d{1,2}_\\d{1,2}";
		if(dateStr.matches(datePattern)) {
			System.out.println("matched-------------");
		}
		SimpleDateFormat format = new SimpleDateFormat("%Y%m%d");
		Date d=null;
		try {
			d = format.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
		
	}
	public static Gson getGsonObject(){
		GsonBuilder builder = new GsonBuilder();
		builder.disableHtmlEscaping();
		return builder.create();
	}
}


