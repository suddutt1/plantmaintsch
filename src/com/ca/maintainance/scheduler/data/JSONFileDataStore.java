package com.ca.maintainance.scheduler.data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * This data store loads data from a json file. It is not a permanently
 * persistent data store. Data is stored in memory and initial data is loaded
 * from a json file.
 * 
 * @author SUDDUTT1
 *
 */
public class JSONFileDataStore implements DataStore {

	public static String CTX_EQUIPMENT = "EQUIPMENT";
	public static String CTX_RESOURCE_MASTER = "RESOURCE_MASTER";
	public static String CTX_PLANNED_SCHEDULE = "PLANNED_SCHEDULE";
	public static String CTX_EQUIP_MAINT_SCHEDULE = "EQUIP_MAINT_SCHEDULE";
	public static String CTX_WEATHER = "WEATHER";
	public static String CTX_MAINT_HISTORY = "CTX_MAINT_HISTORY";
	

	private static final Logger _LOGGER = Logger
			.getLogger(JSONFileDataStore.class.getName());
	private List<Equipment> equipmentList;
	private Map<String, Equipment> equipmentMap;
	private Map<String, Date> maintenanceHistoryMap;
	private List<ResourceMaster> resourceMasterList;
	private Map<String, List<ResourceMaster>> resourceMasterMap;
	private List<ResourceSchedule> resourceSchedule;
	private Map<ResourceSchedule, ResourceSchedule> resourceScheduleMap;

	public synchronized void setResourceSchedule(
			List<ResourceSchedule> resourceSchedule) {
		this.resourceSchedule = resourceSchedule;
		if (this.resourceScheduleMap == null) {
			this.resourceScheduleMap = new HashMap<>();

		}
		this.resourceScheduleMap.clear();
		for (ResourceSchedule rsch : this.resourceSchedule) {

			this.resourceScheduleMap.put(rsch, rsch);
		}
	}

	public synchronized void setMaintenceHist(
			List<MaintainanceHistory> histoticalList) {
		if (this.maintenanceHistoryMap == null) {
			this.maintenanceHistoryMap = new HashMap<>();
		}
		this.maintenanceHistoryMap.clear();
		for (MaintainanceHistory history : histoticalList) {
			this.maintenanceHistoryMap.put(history.getEquipmentNumber(),
					history.getLastMaintenanceDate());
		}
	}

	public synchronized void setEquipmentList(List<Equipment> equipmentList) {
		this.equipmentList = equipmentList;
		if (this.equipmentMap == null) {
			this.equipmentMap = new HashMap<>();
		}
		this.equipmentMap.clear();
		for (Equipment equip : this.equipmentList) {
			this.equipmentMap.put(equip.getEquipmentNumber(), equip);
		}

	}

	public synchronized void setResourceMasterList(
			List<ResourceMaster> resourceMasterList) {
		this.resourceMasterList = resourceMasterList;
		if (this.resourceMasterMap == null) {
			this.resourceMasterMap = new HashMap<>();
		}
		this.resourceMasterMap.clear();
		for (ResourceMaster res : this.resourceMasterList) {
			String type = res.getType().toString();
			List<ResourceMaster> list = this.resourceMasterMap.get(type);
			if (list == null) {
				list = new ArrayList<>();
				this.resourceMasterMap.put(type, list);
			}
			list.add(res);
		}
	}

	@Override
	public List<Equipment> getEquipmentList() {
		// TODO Auto-generated method stub
		return this.equipmentList;
	}

	@Override
	public List<ResourceMaster> getResourceList() {
		// TODO Auto-generated method stub
		return this.resourceMasterList;
	}

	@Override
	public List<ResourceSchedule> getResourceSchedule() {
		// TODO Auto-generated method stub
		return this.resourceSchedule;
	}

	@Override
	public List<MaintainanceHistory> getMaintanceHistory(Equipment eq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean loadDataStore() {

		boolean isLoadSuccess = false;
		try {
			Gson gson = new GsonBuilder().create();
			Equipment[] equipments = gson.fromJson(readJson("equipment.json"),
					Equipment[].class);
			setEquipmentList(new ArrayList<>(Arrays.asList(equipments)));
			ResourceMaster[] resrc = gson.fromJson(
					readJson("resource_master.json"), ResourceMaster[].class);
			setResourceMasterList(new ArrayList<>(Arrays.asList(resrc)));
			ResourceSchedule[] sch = gson
					.fromJson(readJson("planned_schedule.json"),
							ResourceSchedule[].class);
			setResourceSchedule(new ArrayList<>(Arrays.asList(sch)));
			Type type = new TypeToken<HashMap<String, Date>>(){}.getType();
			this.maintenanceHistoryMap = gson.fromJson(readJson("maint_history.json"), type);
			isLoadSuccess = true;
		} catch (Exception ex) {
			_LOGGER.log(Level.SEVERE,
					"|JSONStore| Error in loading data into Store", ex);
			isLoadSuccess = false;
		}
		return isLoadSuccess;
	}

	@Override
	public boolean flushDataStore(Map<String, String> context) {
		// TODO Auto-generated method stub
		boolean isFlushSuccess = true;
		try {
			// Save equipmentMaster
			String path = context.get(CTX_EQUIPMENT);
			String json = DataUtil.toJson(this.equipmentList);
			isFlushSuccess = writeToFile(path, json) & isFlushSuccess;
			path = context.get(CTX_RESOURCE_MASTER);
			json = DataUtil.toJson(this.resourceMasterList);
			isFlushSuccess = writeToFile(path, json) & isFlushSuccess;
			path = context.get(CTX_PLANNED_SCHEDULE);
			json = DataUtil.toJson(this.resourceSchedule);
			isFlushSuccess = writeToFile(path, json) & isFlushSuccess;
			path = context.get(CTX_MAINT_HISTORY);
			json = DataUtil.toJson(this.maintenanceHistoryMap);
			isFlushSuccess = writeToFile(path, json) & isFlushSuccess;

		} catch (Exception ex) {
			_LOGGER.log(Level.WARNING,
					"|JSONStore| Unable to fulush data in file...|", ex);
			isFlushSuccess = false;
		}
		return isFlushSuccess;
	}

	@Override
	public ResourceSchedule modifySchedule(ResourceSchedule rsch) {
		// TODO Auto-generated method stub
		if (rsch != null && this.resourceScheduleMap.containsKey(rsch)) {
			ResourceSchedule existingSchedule = this.resourceScheduleMap
					.get(rsch);
			existingSchedule.setAvailable(rsch.isAvailable());
			existingSchedule.setBlockedBy(rsch.getBlockedBy());
			return existingSchedule;
		} else {
			this.resourceScheduleMap.put(rsch, rsch);
			this.resourceSchedule.add(rsch);
			return rsch;
		}
	}

	private boolean writeToFile(String path, String json) {
		FileOutputStream fos = null;
		boolean isWriteSuccess = false;
		try {
			fos = new FileOutputStream(path);
			fos.write(json.getBytes());
			fos.flush();
			isWriteSuccess = true;
		} catch (Exception ex) {
			_LOGGER.log(Level.SEVERE, "|JSONStore| Unable to write to :" + path);
			isWriteSuccess = false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					_LOGGER.log(Level.SEVERE,
							"|JSONStore|Unable to close file :" + path);
				}
			}
		}
		return isWriteSuccess;
	}

	private String readJson(String jsonName) throws Exception {
		String content = null;
		InputStream inputScr = JSONFileDataStore.class.getClassLoader()
				.getResourceAsStream(jsonName);
		Scanner scr = new Scanner(inputScr);
		scr.useDelimiter("\\A");
		content = (scr.hasNext() ? scr.next() : null);
		scr.close();
		inputScr.close();
		return content;
	}

	@Override
	public ResourceSchedule getSchedule(String date, String shift,
			String resourceId) {
		// TODO Auto-generated method stub
		ResourceSchedule itemToSearch = new ResourceSchedule(resourceId, date,
				shift.toUpperCase(), "");
		return this.resourceScheduleMap.get(itemToSearch);

	}

	@Override
	public Equipment getEquipmentDetails(String equipment) {

		return this.equipmentMap.get(equipment);
	}

	@Override
	public ResourceSchedule getAvailableResourceOn(String resoureType,
			String date, String shift) {
		List<ResourceMaster> resources = this.resourceMasterMap
				.get(resoureType);
		for (ResourceMaster resource : resources) {
			ResourceSchedule sch = this.getSchedule(date, shift,
					resource.getId());
			if (sch.isAvailable()) {
				return sch;
			}
		}
		return null;
	}

	@Override
	public Date getLastMaintenanceDate(String equipment) {
		// TODO Auto-generated method stub
		return this.maintenanceHistoryMap.get(equipment);
	}
}
