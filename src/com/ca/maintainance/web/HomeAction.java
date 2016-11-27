package com.ca.maintainance.web;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ca.maintainance.scheduler.Schedule;
import com.ca.maintainance.scheduler.Scheduler;
import com.ca.maintainance.scheduler.data.DataStoreManager;
import com.ibm.app.web.frmwk.WebActionHandler;
import com.ibm.app.web.frmwk.annotations.RequestMapping;
import com.ibm.app.web.frmwk.bean.ModelAndView;
import com.ibm.app.web.frmwk.bean.ViewType;

public class HomeAction implements WebActionHandler {

	private static final Logger _LOGGER = Logger.getLogger(HomeAction.class.getName());
	
	@RequestMapping("home.wss")
	public ModelAndView loadHomePage(HttpServletRequest request,HttpServletResponse response)
	{
		
		ModelAndView mvObject = new ModelAndView(ViewType.JSP_VIEW);
		mvObject.setView("app/home.jsp");
		mvObject.addModel("equipmentList", DataStoreManager.getDataStore().getEquipmentList());
		return mvObject;
	}
	@RequestMapping("getPlan.wss")
	public ModelAndView getPlan(HttpServletRequest request,HttpServletResponse response)
	{
		ActionResponse actResponse = null;
		ModelAndView mvObject = new ModelAndView(ViewType.AJAX_VIEW);
		try{
			String equipmentNo = getSafeString(request.getParameter("equipmentId"));
			Schedule sch = Scheduler.findSuitableSchedule(equipmentNo);
			actResponse= new ActionResponse(sch, ActionResponse.OK_RESPONSE, "Schedule complete");
		}catch(Exception ex)
		{
			_LOGGER.log(Level.SEVERE,"|HOMEACTION| Unable to retrieve plan ",ex);
			actResponse= new ActionResponse(null, ActionResponse.EXCEPTION_RESPONSE, "Error thrown:"+ ex.getMessage());
		}
		finally{
			mvObject.setView(actResponse.toJson());
		}
		return mvObject;
	}
	private String getSafeString(String elem)
	{
		return (elem!=null? elem.trim():"");
	}
	
}
