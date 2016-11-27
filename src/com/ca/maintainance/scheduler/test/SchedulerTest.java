package com.ca.maintainance.scheduler.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ca.maintainance.scheduler.Schedule;
import com.ca.maintainance.scheduler.Scheduler;

public class SchedulerTest {

	@Test
	public void test() {
		Schedule plan = Scheduler.findSuitableSchedule("1000301");
		assertNotNull(plan);
		System.out.println(plan);
	}

}
