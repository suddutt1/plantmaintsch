package com.ca.maintainance.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ca.maintainance.scheduler.data.DataStore;
import com.ca.maintainance.scheduler.data.DataStoreManager;
import com.ca.maintainance.scheduler.data.DataUtil;
import com.ca.maintainance.scheduler.data.Equipment;
import com.ca.maintainance.scheduler.data.ResourceSchedule;
import com.ca.maintainance.scheduler.data.WeatherDataManager;
import com.ca.maintainance.scheduler.data.WeatherDataService;
import com.ca.maintainance.scheduler.data.WeatherForecast;

public class Scheduler {

	private static final Logger _LOGGER = Logger.getLogger(Schedule.class
			.getName());
	private static final SimpleDateFormat _DT_FMT = new SimpleDateFormat(
			"yyyy.MM.dd");

	private Scheduler() {
		super();
	}

	public static Schedule findSuitableSchedule(String equipmentNo) {
		List<String> logicalTrace = null;
		Schedule plan = null;
		try {
			plan = new Schedule();
			logicalTrace = new ArrayList<>(10);
			plan.setPlanningTace(logicalTrace);
			plan.setEquipmentNumber(equipmentNo);
			DataStore dataStore = DataStoreManager.getDataStore();

			Equipment equipment = dataStore.getEquipmentDetails(equipmentNo);
			if (equipment != null) {
				logicalTrace.add("Equipment found in the equipment master");
				// First find the last maintenance date
				Date lastMaintDate = findLastMaintanceDate(dataStore,equipmentNo);
				if (lastMaintDate == null) {
					// Set 12.31.2016 as last maint day
					lastMaintDate = _DT_FMT.parse("2016.12.31");
				}
				plan.setLastMaintenceDate(_DT_FMT.format(lastMaintDate));
				logicalTrace.add("Equipment last maintenance date is "
						+ _DT_FMT.format(lastMaintDate));
				// Next planned maintenance date
				Calendar calendar = GregorianCalendar.getInstance();
				calendar.clear();
				calendar.setTime(lastMaintDate);
				logicalTrace.add("Equipment OEM maintenance interval "
						+ equipment.getMaintanceInverval());
				calendar.add(Calendar.DAY_OF_YEAR,
						equipment.getMaintanceInverval());
				String plannedDate = _DT_FMT.format(calendar.getTime());
				logicalTrace.add("Equipment maintenance planned date "
						+ plannedDate);
				//plan.setPlannedDate(plannedDate);
				List<String> resourceTypes = getResoutceTypeForMaintenance(equipment);
				logicalTrace.add("Resources required for equipment "
						+ resourceTypes.toString());
				findSuitablePlan(resourceTypes, calendar, 30, dataStore, plan,
						equipment.getMine(), equipment.getBlock());
			} else {
				logicalTrace.add("Equipment not found in the equipment master");
			}
		} catch (Exception ex) {
			_LOGGER.log(
					Level.WARNING,
					"|SCHEDULER| Exception while finding schedule for equipmentId",
					ex);
			logicalTrace.add("Exception thrown . Aborting..");
		}
		return plan;
	}

	private static boolean isWeatherSuitable(String date, String shift,
			Schedule plan, String mine, String block) {
		List<WeatherForecast> shiftForecast = null;
		WeatherDataService service = WeatherDataManager.getServiceInstance();
		long startTime = 0;
		long endTime = 7;
		if ("SHIFT 2".equalsIgnoreCase(shift)) {
			startTime = 8;
			endTime = 16;
		} else if ("SHIFT 3".equalsIgnoreCase(shift)) {
			startTime = 17;
			endTime = 24;
		}

		shiftForecast = service.getWeatherForecast(date, startTime, endTime, 0,
				0);
		if (shiftForecast == null) {
			plan.getPlanningTace().add("No weather forecast data ...");
			return true;
		} else {
			boolean isWeatherGood = true;
			for (WeatherForecast forecast : shiftForecast) {
				if (forecast.getPrecpProb() > 0
						|| forecast.getVisibility() < 5000) {
					isWeatherGood = false;
					plan.getPlanningTace().add(
							"Forecast not suitable during " + date + ": "
									+ shift);
					break;
				}
			}
			if (isWeatherGood) {
				plan.getPlanningTace().add(
						"Forecase suitable during " + date + ": " + shift);
			}
			return isWeatherGood;
		}
	}

	private static void findSuitablePlan(List<String> resources,
			Calendar startDate, int maxSearch, DataStore ds, Schedule plan,
			String mine, String block) {
		int dayIncr = 0;
		String[] shifts = { "SHIFT 1", "SHIFT 2", "SHIFT 3" };
		String date = _DT_FMT.format(startDate.getTime());
		List<ResourceSchedule> availableSlots = new ArrayList<>();
		for (dayIncr = 0; dayIncr < maxSearch; dayIncr++) {
			for (String shift : shifts) {
				ResourceSchedule sch = null;
				availableSlots.clear();
				for (String resType : resources) {
					sch = ds.getAvailableResourceOn(resType, date, shift);
					if (sch == null) {
						break;
					}
					availableSlots.add(sch);
				}
				if (sch != null
						&& isWeatherSuitable(date, shift, plan, mine, block)) {
					// We have found it
					plan.setPlannedDate(date);
					plan.setShift(shift);
					// This is for verbose output
					plan.getPlanningTace();
					plan.setSchedule(availableSlots);
					return;
				}
			}
			startDate.add(Calendar.DAY_OF_YEAR, 1);// Check on the next day
			date = _DT_FMT.format(startDate.getTime());
		}
		if (dayIncr == maxSearch) {
			plan.getPlanningTace().add(
					"Planned date not found within a timespan of " + maxSearch
							+ " days");
		}
	}

	private static List<String> getResoutceTypeForMaintenance(Equipment equip) {
		Map<String, List<String>> maintenanceResourceMap = new HashMap<>();
		maintenanceResourceMap.put(
				"EXCA",
				Arrays.asList(new String[] { "WORKSHOP", "SPARE_EXCA",
						"MANPOWER", "SUBCONTRATOR" }));
		maintenanceResourceMap.put(
				"DUMP",
				Arrays.asList(new String[] { "WORKSHOP", "SPARE_DUMP",
						"MANPOWER", "SUBCONTRATOR" }));
		maintenanceResourceMap.put(
				"HAUL",
				Arrays.asList(new String[] { "WORKSHOP", "SPARE_HAULER",
						"MANPOWER", "SUBCONTRATOR" }));
		maintenanceResourceMap.put(
				"SHO",
				Arrays.asList(new String[] { "WORKSHOP", "SPARE_SHO",
						"MANPOWER", "SUBCONTRATOR" }));
		return maintenanceResourceMap.get(equip.getCatalogProfile());

	}

	private static Date findLastMaintanceDate(DataStore dataStore,
			String equipmentId) {
		return dataStore.getLastMaintenanceDate(equipmentId);
	}
}
