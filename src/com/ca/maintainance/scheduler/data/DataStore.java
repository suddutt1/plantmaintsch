package com.ca.maintainance.scheduler.data;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * This method will talk to data base 
 * @author SUDDUTT1
 *
 */
public interface DataStore {
	Date getLastMaintenanceDate(String equipment);
	List<Equipment> getEquipmentList();
	Equipment getEquipmentDetails(String equipment);
	List<ResourceMaster> getResourceList();
	List<ResourceSchedule> getResourceSchedule();
	List<MaintainanceHistory> getMaintanceHistory(Equipment eq);
	boolean loadDataStore();
	boolean flushDataStore(Map<String,String> context);
	//Add or modify modify an existing schedule
	ResourceSchedule modifySchedule(ResourceSchedule rsch);
	ResourceSchedule getSchedule(String date,String shift,String resourceId);
	ResourceSchedule getAvailableResourceOn(String resoureType,String date,String shift);
	MonthlySchedule getMonthlySchedule(String resourceId,int month,int year);
	
}
