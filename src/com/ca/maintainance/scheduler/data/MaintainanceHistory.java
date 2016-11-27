package com.ca.maintainance.scheduler.data;

import java.util.Date;

public class MaintainanceHistory {

	private String equipmentNumber;
	private Date lastMaintenanceDate;
	
	
	public MaintainanceHistory()
	{
		super();
	}


	/**
	 * @param equipmentNumber
	 * @param lastMaintenanceDate
	 */
	public MaintainanceHistory(String equipmentNumber, Date lastMaintenanceDate) {
		super();
		this.equipmentNumber = equipmentNumber;
		this.lastMaintenanceDate = lastMaintenanceDate;
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
	 * @return the lastMaintenanceDate
	 */
	public Date getLastMaintenanceDate() {
		return lastMaintenanceDate;
	}


	/**
	 * @param lastMaintenanceDate the lastMaintenanceDate to set
	 */
	public void setLastMaintenanceDate(Date lastMaintenanceDate) {
		this.lastMaintenanceDate = lastMaintenanceDate;
	}
	
	
}
