package com.ca.maintainance.scheduler;

import java.util.List;

import com.ca.maintainance.scheduler.data.DataUtil;
import com.ca.maintainance.scheduler.data.ResourceSchedule;
import com.ca.maintainance.scheduler.data.WeatherForecast;

public class Schedule {
	
	private String equipmentNumber;
	private String plannedDate;
	private String shift;
	private List<String> planningTace;
	private String lastMaintenceDate;
	private List<ResourceSchedule> schedule;
	private String oemMaintenanceDate;
	private WeatherForecast forecast;
	
	public Schedule()
	{
		super();
	}

	/**
	 * @param equipmentNumber
	 * @param plannedDate
	 * @param planningTace
	 */
	public Schedule(String equipmentNumber, String plannedDate,String shift,
			List<String> planningTace) {
		super();
		this.equipmentNumber = equipmentNumber;
		this.plannedDate = plannedDate;
		this.shift = shift;
		this.planningTace = planningTace;
	}

	/**
	 * @return the equipmentNumber
	 */
	public String getEquipmentNumber() {
		return equipmentNumber;
	}

	/**
	 * @param equipmentNumber the equipmentNumber to set
	 */
	public void setEquipmentNumber(String equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}

	/**
	 * @return the plannedDate
	 */
	public String getPlannedDate() {
		return plannedDate;
	}

	/**
	 * @param plannedDate the plannedDate to set
	 */
	public void setPlannedDate(String plannedDate) {
		this.plannedDate = plannedDate;
	}

	/**
	 * @return the planningTace
	 */
	public List<String> getPlanningTace() {
		return planningTace;
	}

	/**
	 * @param planningTace the planningTace to set
	 */
	public void setPlanningTace(List<String> planningTace) {
		this.planningTace = planningTace;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "Schedule "+DataUtil.toJson(this);
	}

	/**
	 * @return the shift
	 */
	public String getShift() {
		return shift;
	}

	/**
	 * @param shift the shift to set
	 */
	public void setShift(String shift) {
		this.shift = shift;
	}

	/**
	 * @return the lastMaintenceDate
	 */
	public String getLastMaintenceDate() {
		return lastMaintenceDate;
	}

	/**
	 * @param lastMaintenceDate the lastMaintenceDate to set
	 */
	public void setLastMaintenceDate(String lastMaintenceDate) {
		this.lastMaintenceDate = lastMaintenceDate;
	}

	/**
	 * @return the schedule
	 */
	public List<ResourceSchedule> getSchedule() {
		return schedule;
	}

	/**
	 * @param schedule the schedule to set
	 */
	public void setSchedule(List<ResourceSchedule> schedule) {
		this.schedule = schedule;
	}

	/**
	 * @return the oemMaintenanceDate
	 */
	public String getOemMaintenanceDate() {
		return oemMaintenanceDate;
	}

	/**
	 * @param oemMaintenanceDate the oemMaintenanceDate to set
	 */
	public void setOemMaintenanceDate(String oemMaintenanceDate) {
		this.oemMaintenanceDate = oemMaintenanceDate;
	}

	/**
	 * @return the forecast
	 */
	public WeatherForecast getForecast() {
		return forecast;
	}

	/**
	 * @param forecast the forecast to set
	 */
	public void setForecast(WeatherForecast forecast) {
		this.forecast = forecast;
	}
	

}
