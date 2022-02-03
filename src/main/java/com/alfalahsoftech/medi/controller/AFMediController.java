package com.alfalahsoftech.medi.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.alfalahsoftech.alframe.AFArrayList;
import com.alfalahsoftech.alframe.AFHashMap;
import com.alfalahsoftech.common.file.AFJsonParser;
import com.alfalahsoftech.controller.AFBaseController;
import com.alfalahsoftech.inv.entity.EOItemSold;
import com.alfalahsoftech.inv.entity.EOMediSold;
import com.alfalahsoftech.inv.entity.EOStuffDetail;
import com.alfalahsoftech.medi.entity.EOMedicine;
import com.alfalahsoftech.service.UserSrvcImpl;

@Path("medi")
@SuppressWarnings("unchecked")
public class AFMediController extends AFBaseController {

	Response response;
	UserSrvcImpl srvc = new UserSrvcImpl();

	@POST
	@Path("/addMedi")
	@Consumes(value= { MediaType.APPLICATION_JSON})

	public String addMedi(String reqStr) {
		printObj("Adding request:  "+reqStr);
		EOMedicine obj = (EOMedicine)this.reqRespObject().reqEM().createQuery("SELECT e FROM EOMedicine e where primaryKey=(select max(primaryKey) from EOMedicine)").getResultList().get(0);
		String maxID = obj.getItemID().split("_")[1];
		print("maxID=> "+maxID);
		int newID =Integer.valueOf(maxID)+1;
		
		EOMedicine medi =this.getObjFromStr(EOMedicine.class	, reqStr);
		
		medi.setItemID(this.getUniqueIDWithPrefix("MEDI_", newID , "******"));
		this.saveObject(medi);
		this.commit();
		return   this.gson().toJson("{msg:"+medi.getMediName()+" successfully added!}");
	}

	@POST
	@Path("/dispMedi")
	@Consumes(value= { MediaType.APPLICATION_JSON})
	@Produces(value= { MediaType.APPLICATION_JSON})
	public Response dispMedi(String reqStr) {
		printObj("dispMedi request:  "+reqStr);
		List<EOMedicine> list =(List<EOMedicine>)this.genericAllData(EOMedicine.class); // this.getObjects(EOMedicine.class, " isActive=true"); // srvc.allEOMedicineData();
printObj(list.size());
		/*print("Reading medicnie files===============");
		AtomicInteger i = new AtomicInteger(0);
		ArrayList<String> mediList = AFJsonParser.getAllMedi();
		mediList.forEach(mediName->{
			list.get(i.getAndIncrement()).setMediName(mediName);
		});*/
		response = this.createResponse(list);
		//System.out.println(list);
		return response;
	}

	@POST
	@Path("/editMedi/{mediPK}")
	@Consumes(value= { MediaType.APPLICATION_JSON})
	@Produces(value= { MediaType.APPLICATION_JSON})
	public Response editMedi(@PathParam("mediPK") String mediPK) {
		printObj("edit request:  "+mediPK);
		Object obj = this.reqRespObject().reqEM().createQuery("SELECT e FROM EOMedicine e where primaryKey="+mediPK).getResultList().get(0);//this.getUniqueObject("EOMedicine", mediPK);
		response = this.createResponse(obj);
		return response;
	}

	@POST
	@Path("/updateMedi")
	@Consumes(value= { MediaType.APPLICATION_JSON})
	@Produces(value= { MediaType.APPLICATION_JSON})
	public Response updateMedi(String reqStr) {
		printObj("updateMedi request:  "+reqStr);

		JSONObject jsonObj=new JSONObject(reqStr);
		EOMedicine medi =this.getObjFromStr(EOMedicine.class	, reqStr);

		EOMedicine obj = (EOMedicine)this.reqRespObject().reqEM().createQuery("SELECT e FROM EOMedicine e where primaryKey="+jsonObj.getLong("primaryKey")).getResultList().get(0);
		
		//		Object obj =this.updateObject(EOMedicine.class, reqStr);
		/*obj.setItemID(	jsonObj.getString("itemID"));
		obj.setMediName(jsonObj.getString("mediName"));
		obj.setScheme(jsonObj.getString("scheme"));
		obj.setBatchNo(	jsonObj.getString("batchNo"));
		obj.setUOM(	jsonObj.getString("UOM"));
		obj.setDiscount(	jsonObj.getDouble("discount"));
		obj.setNetRate(jsonObj.getDouble("netRate"));
		obj.setMrp(jsonObj.getDouble("mrp"));
		obj.setIsActive(jsonObj.getInt("isActive")==0?false:true);
		obj.setExpDate(this.formatedDate(jsonObj.getString("expDate")));
		obj.setNotes(jsonObj.getString("notes"));*/

		obj.setItemID(medi.getItemID());
		obj.setMediName(medi.getMediName());
		obj.setScheme(medi.getScheme());
		obj.setBatchNo(medi.getBatchNo());
		obj.setUOM(	medi.getUOM());
		obj.setDiscount(medi.getDiscount());
		obj.setNetRate(medi.getNetRate());
		obj.setMrp(medi.getMrp());
		obj.setIsActive(medi.getIsActive());
		obj.setExpDate(medi.getExpDate());
		obj.setNotes(medi.getNotes());
		obj.setMfgBy(medi.getMfgBy());
		obj.setNetRatePerc(medi.getNetRatePerc());
		obj.setPack(medi.getPack());
		obj.setBonus(medi.getBonus());
		obj.setPurchPerc(medi.getPurchPerc());
		//Transaxtion
		this.reqRespObject().startTransaction();
		this.reqRespObject().reqEM().merge(obj);
		this.reqRespObject().endTransaction();
		
		//	}
		response = this.createResponse(obj);
		return response;// this.gson().toJson("{msg:"+medi.getMediName()+" successfully updated!}");
	}

	public void createMultiObject(EOMedicine medi) {

		ArrayList<String> list = AFJsonParser.getAllMedi();
		this.reqRespObject().startTransaction();
		for (int i = 0; i < list.size(); i++) {
			EOMedicine  obj = new EOMedicine();
			String id = this.getUniqueID((i+1), "******");
			obj.setItemID(id);
			obj.setMediName(list.get(i));
			obj.setScheme(medi.getScheme());
			obj.setBatchNo(medi.getBatchNo());
			obj.setUOM(	medi.getUOM());
			obj.setDiscount(medi.getDiscount());
			obj.setNetRate(medi.getNetRate());
			obj.setMrp(medi.getMrp());
			obj.setIsActive(medi.getIsActive());
			obj.setExpDate(medi.getExpDate());
			obj.setNotes(medi.getNotes());
			obj.setMfgBy(medi.getMfgBy());
			obj.setNetRatePerc(medi.getNetRatePerc());
			obj.setPack(medi.getPack());
			//Transaxtion
			
			this.reqRespObject().reqEM().persist(obj);
		}
		this.reqRespObject().endTransaction();


	}







	@Path("/soldMedi")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response  saveSoldItems(String req) {
		print("=======> soldMedi\n"+req);
		JSONArray jsonArr=	new JSONArray(req);
		AtomicInteger aIntgQnt = new AtomicInteger(0);
		String receiptNo= getReceiptNo();
		jsonArr.forEach(itemDetails->{	
			printObj(itemDetails);
			EOMediSold itemSold = this.getObjFromStr(EOMediSold.class, itemDetails.toString());
			itemSold.setRcptNo(receiptNo);
			int v =itemSold.getQuantity();
			v+=aIntgQnt.get();
			aIntgQnt.set(v);
			srvc.insertEntity(itemSold);
		});
		System.out.println("aIntgQnt======>"+aIntgQnt.get());

		return this.createResponse("Sold Items Saved!");
	}
	
	@Path("/getRecptNo")
	@GET
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String geRecpNo() {
		return this.getReceiptNo();//this.createResponse("{rcptNo:"+this.getReceiptNo()+"}");
	}
	

	private String getReceiptNo() {
		EOMediSold obj = (EOMediSold)this.reqRespObject().reqEM().createQuery("SELECT e FROM EOMediSold e where primaryKey=(select max(primaryKey) from EOMediSold)").getResultList().get(0);
		String maxID = obj.getRcptNo();
		print("max ReceiptNo => "+maxID);
		int newID =Integer.valueOf((maxID == null ? "0":maxID))+1;
		print("new Repct no : " +newID);
		return this.getUniqueID(newID , "******");
	}

	@Path("/lastSoldMedi/{clientid}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response lastSoldMedi(@PathParam("clientid") String clientid) {
		print("client id = "+clientid);
		List<EOMediSold> lastSoldMedi =this.getObjects(EOMediSold.class," e.clientID='"+clientid+"'");// this.reqRespObject().reqEM().createQuery("Select e.itemID,e.mediName,e.unitCost,e.quantity from EOMediSold e  where e.clientID='"+clientid+"'").getResultList();
		printObj(lastSoldMedi);
//		List<String> lastSoldMediStr = this.reqRespObject().reqEM().createQuery("Select e.itemID,e.mediName,e.unitCost,e.quantity from EOMediSold e  where e.clientID='"+clientid+"'").getResultList();
//		printObj(lastSoldMediStr);
		class LSold{
			String itemID;
			String mediName;
			Double unitCost;
			Integer quantity;
		}
		AFArrayList<LSold>	lastSoldMediReturn = new AFArrayList();
		lastSoldMedi.forEach(e->{
			LSold l = new LSold();
			l.itemID = e.getItemID();
			l.mediName = e.getMediName();
			l.quantity = e.getQuantity();
			l.unitCost = e.getUnitCost();
			lastSoldMediReturn.add(l);
		});
		return this.createResponse(lastSoldMedi);

	}


	//Sold Items
	@GET
	@Path("/dispSoldMedi")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response dispSoldMedi() {
		System.out.println("dispSoldMedi==");
		List<EOMediSold> soldItemList = this.getObjects(EOMediSold.class);
		this.reqRespObject().startTransaction();
		
		/*AtomicInteger newID = new AtomicInteger(1);
		soldItemList.forEach(medi->{
			medi.setRcptNo(this.getUniqueID(newID.getAndIncrement() , "******"));
			this.reqRespObject().reqEM().merge(medi);
		});
		this.reqRespObject().endTransaction();*/
		
		System.out.println("soldItemList== "+soldItemList.size());
		return this.createResponse(soldItemList);
	}




	@POST
	@Path("/delete")
	@Produces(value= {MediaType.APPLICATION_JSON})
	@Consumes(value= {MediaType.APPLICATION_JSON})
	public Response delete(String req) {
		JSONObject jsonObj = this.jsonObject(req);
		String cls = jsonObj.getString("clsName");
		String pk = jsonObj.getString("pk");
		
		print("Delting object for "+req);
		
		this.reqRespObject().startTransaction();
		int isDeleted = this.reqRespObjec().reqEM().createQuery("Delete from " + cls +" where primaryKey="+pk).executeUpdate();
		this.reqRespObject().endTransaction();
		print("Objecte deleted? " + isDeleted);
		return this.createResponse("Delete Success? "+isDeleted);
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
