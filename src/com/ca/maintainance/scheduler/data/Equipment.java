package com.ca.maintainance.scheduler.data;

public class Equipment {
	
	private String equipmentNumber;
	private String resourceId;
	private String description;
	private String catalogProfile;
	private long ratePerHour;
	private String currency;
	private String mine;
	private String block;
	private int maintanceInverval;
	
	public Equipment()
	{
		super();
	}

	/**
	 * @param equipmentNumber
	 * @param mine
	 * @param resourceId
	 * @param description
	 * @param catalogProfile
	 * @param ratePerHour
	 * @param currency
	 * @param block
	 */
	public Equipment(String equipmentNumber, String mine, String resourceId,
			String description, String catalogProfile, long ratePerHour,
			String currency, String block) {
		super();
		this.equipmentNumber = equipmentNumber;
		this.mine = mine;
		this.resourceId = resourceId;
		this.description = description;
		this.catalogProfile = catalogProfile;
		this.ratePerHour = ratePerHour;
		this.currency = currency;
		this.block = block;
		//this.maintanceInverval = maintanceInverval;
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
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the catalogProfile
	 */
	public String getCatalogProfile() {
		return catalogProfile;
	}

	/**
	 * @param catalogProfile the catalogProfile to set
	 */
	public void setCatalogProfile(String catalogProfile) {
		this.catalogProfile = catalogProfile;
	}

	/**
	 * @return the ratePerHour
	 */
	public long getRatePerHour() {
		return ratePerHour;
	}

	/**
	 * @param ratePerHour the ratePerHour to set
	 */
	public void setRatePerHour(long ratePerHour) {
		this.ratePerHour = ratePerHour;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the mine
	 */
	public String getMine() {
		return mine;
	}

	/**
	 * @param mine the mine to set
	 */
	public void setMine(String mine) {
		this.mine = mine;
	}

	/**
	 * @return the block
	 */
	public String getBlock() {
		return block;
	}

	/**
	 * @param block the block to set
	 */
	public void setBlock(String block) {
		this.block = block;
	}

	/**
	 * @return the maintanceInverval
	 */
	public int getMaintanceInverval() {
		return maintanceInverval;
	}

	/**
	 * @param maintanceInverval the maintanceInverval to set
	 */
	public void setMaintanceInverval(int maintanceInverval) {
		this.maintanceInverval = maintanceInverval;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "Equipment:"+DataUtil.toJson(this);
	}
	

}
