package com.ca.maintainance.scheduler.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.ca.maintainance.scheduler.data.DataStore;
import com.ca.maintainance.scheduler.data.DataStoreManager;
import com.ca.maintainance.scheduler.data.MonthlySchedule;
import com.ca.maintainance.scheduler.data.ResourceSchedule;

public class DataStoreManagerTest {

	@Test
	public void testDataIntegrity() {
		DataStore store1 = DataStoreManager.getDataStore();
		assertNotNull(store1);
		//test the single init
		DataStore store2 = DataStoreManager.getDataStore();
		assertNotNull(store2);
		assertEquals(store1, store2);
		//Check if data is loaded correctly or not
		assertNotNull(store2.getEquipmentList());
		assertEquals(true, store2.getEquipmentList().size()>0);
		assertNotNull(store2.getResourceList());
		assertEquals(true, store2.getResourceList().size()>0);
		ResourceSchedule sch = store2.getSchedule("2017.01.22", "SHIFT 1", "WORKSHOP BAY 1");
		assertNotNull(sch);
		assertEquals(true, sch.isAvailable());
		sch = store2.getSchedule("2017.01.23", "SHIFT 3", "WORKSHOP BAY 3");
		assertNotNull(sch);
		assertEquals(true, sch.isAvailable());
		sch = store2.getSchedule("2017.01.23", "SHIFT 2", "SPARES  FOR EXCA");
		assertNotNull(sch);
		assertEquals(false, sch.isAvailable());
		Date lastMaintDate = store2.getLastMaintenanceDate("1000308");
		assertNotNull(lastMaintDate);
		MonthlySchedule monthlySchedule = store2.getMonthlySchedule("SPARES  FOR EXCA", 1, 2017);
		assertNotNull(monthlySchedule);
		assertNotNull(monthlySchedule.getResource());
		assertNotNull(monthlySchedule.getAvailablity());
	}

}
