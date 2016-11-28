package com.ca.maintainance.web;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ca.maintainance.scheduler.Schedule;
import com.ca.maintainance.scheduler.Scheduler;
import com.ca.maintainance.scheduler.data.DataStoreManager;
import com.ca.maintainance.scheduler.data.MonthlyForecast;
import com.ca.maintainance.scheduler.data.MonthlySchedule;
import com.ca.maintainance.scheduler.data.WeatherDataManager;
import com.ibm.app.web.frmwk.WebActionHandler;
import com.ibm.app.web.frmwk.annotations.RequestMapping;
import com.ibm.app.web.frmwk.bean.ModelAndView;
import com.ibm.app.web.frmwk.bean.ViewType;

public class HomeAction implements WebActionHandler {

	private static final Logger _LOGGER = Logger.getLogger(HomeAction.class
			.getName());

	@RequestMapping("home.wss")
	public ModelAndView loadHomePage(HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mvObject = new ModelAndView(ViewType.JSP_VIEW);
		mvObject.setView("app/home.jsp");
		mvObject.addModel("equipmentList", DataStoreManager.getDataStore()
				.getEquipmentList());
		return mvObject;
	}

	@RequestMapping("getPlan.wss")
	public ModelAndView getPlan(HttpServletRequest request,
			HttpServletResponse response) {
		ActionResponse actResponse = null;
		ModelAndView mvObject = new ModelAndView(ViewType.AJAX_VIEW);
		try {
			String equipmentNo = getSafeString(request
					.getParameter("equipmentId"));
			Schedule sch = Scheduler.findSuitableSchedule(equipmentNo);
			actResponse = new ActionResponse(sch, ActionResponse.OK_RESPONSE,
					"Schedule complete");
		} catch (Exception ex) {
			_LOGGER.log(Level.SEVERE, "|HOMEACTION| Unable to retrieve plan ",
					ex);
			actResponse = new ActionResponse(null,
					ActionResponse.EXCEPTION_RESPONSE, "Error thrown:"
							+ ex.getMessage());
		} finally {
			mvObject.setView(actResponse.toJson());
		}
		return mvObject;
	}

	@RequestMapping("plantschedulehome.wss")
	public ModelAndView loadPlantScheduleHome(HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mvObject = new ModelAndView(ViewType.JSP_VIEW);
		mvObject.setView("app/scheduleHome.jsp");
		mvObject.addModel("resources", DataStoreManager.getDataStore()
				.getResourceList());
		return mvObject;
	}
	@RequestMapping("weatherhome.wss")
	public ModelAndView loadWeatherHome(HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mvObject = new ModelAndView(ViewType.JSP_VIEW);
		mvObject.setView("app/weatherHome.jsp");
		/*mvObject.addModel("resources", DataStoreManager.getDataStore()
				.getResourceList());*/
		return mvObject;
	}

	@RequestMapping("getMonthlySchedule.wss")
	public ModelAndView getResouceSchedule(HttpServletRequest request,
			HttpServletResponse response) {

		ActionResponse actResponse = null;
		ModelAndView mvObject = new ModelAndView(ViewType.AJAX_VIEW);
		try {
			String resourceId = getSafeString(request
					.getParameter("resourceId"));
			int month = getSafeInt(request.getParameter("month"));
			int year = getSafeInt(request.getParameter("year"));
			if (month == Integer.MIN_VALUE || year == Integer.MIN_VALUE) {
				actResponse = new ActionResponse(null,
						ActionResponse.INVALID_INPUT_RESPONSE,
						"Invalid input given");
			} else {
				MonthlySchedule ms = DataStoreManager.getDataStore()
						.getMonthlySchedule(resourceId, month, year);
				actResponse = new ActionResponse(ms,
						ActionResponse.OK_RESPONSE, "Monthly schedule found");
			}

		} catch (Exception ex) {
			_LOGGER.log(Level.SEVERE,
					"|HOMEACTION| Unable to monthly retrieve schedule ", ex);
			actResponse = new ActionResponse(null,
					ActionResponse.EXCEPTION_RESPONSE, "Error thrown:"
							+ ex.getMessage());
		} finally {
			mvObject.setView(actResponse.toJson());
		}
		return mvObject;
	}
	@RequestMapping("getMonthlyWeather.wss")
	public ModelAndView getMonthlyWeather(HttpServletRequest request,
			HttpServletResponse response) {

		ActionResponse actResponse = null;
		ModelAndView mvObject = new ModelAndView(ViewType.AJAX_VIEW);
		try {
			String location = getSafeString(request
					.getParameter("location"));
			int month = getSafeInt(request.getParameter("month"));
			int year = getSafeInt(request.getParameter("year"));
			String shift = getSafeString(request.getParameter("shift"));
			if (month == Integer.MIN_VALUE || year == Integer.MIN_VALUE || shift.length()==0) {
				actResponse = new ActionResponse(null,
						ActionResponse.INVALID_INPUT_RESPONSE,
						"Invalid input given");
			} else {
				long startTime = 0;
				long endTime = 7;
				if ("SHIFT 2".equalsIgnoreCase(shift)) {
					startTime = 8;
					endTime = 15;
				} else if ("SHIFT 3".equalsIgnoreCase(shift)) {
					startTime = 16;
					endTime = 23;
				}
				
				MonthlyForecast forecast = WeatherDataManager.getServiceInstance()
						.getMonthlyWeather(month, year, startTime, endTime, 0.0, 0.0);
				actResponse = new ActionResponse(forecast,
						ActionResponse.OK_RESPONSE, "Monthly forecast found");
			}

		} catch (Exception ex) {
			_LOGGER.log(Level.SEVERE,
					"|HOMEACTION| Unable to monthly retrieve monthly weather ", ex);
			actResponse = new ActionResponse(null,
					ActionResponse.EXCEPTION_RESPONSE, "Error thrown:"
							+ ex.getMessage());
		} finally {
			mvObject.setView(actResponse.toJson());
		}
		return mvObject;
	}

	private int getSafeInt(String elem) {
		int value = 0;
		try {
			value = Integer.parseInt(getSafeString(elem));
		} catch (Exception ex) {
			value = Integer.MIN_VALUE;
		}
		return value;
	}

	private String getSafeString(String elem) {
		return (elem != null ? elem.trim() : "");
	}

}
