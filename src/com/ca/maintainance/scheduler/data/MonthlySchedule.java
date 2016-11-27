package com.ca.maintainance.scheduler.data;

import java.util.Map;

public class MonthlySchedule {
	
	private ResourceMaster resource;
	private Map<String,boolean[]> availablity;
	/**
	 * @return the resource
	 */
	public ResourceMaster getResource() {
		return resource;
	}
	/**
	 * @param resource the resource to set
	 */
	public void setResource(ResourceMaster resource) {
		this.resource = resource;
	}
	/**
	 * @return the availablity
	 */
	public Map<String, boolean[]> getAvailablity() {
		return availablity;
	}
	/**
	 * @param availablity the availablity to set
	 */
	public void setAvailablity(Map<String, boolean[]> availablity) {
		this.availablity = availablity;
	}
	

}
