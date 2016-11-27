package com.ca.maintainance.scheduler.data;

public class ResourceMaster {

	private ResourceType type;
	private String id;
	private String description;
	
	public ResourceMaster()
	{
		super();
	}

	/**
	 * @param type
	 * @param description
	 */
	public ResourceMaster(ResourceType type, String id , String description) {
		super();
		this.type = type;
		this.id = id;
		this.description = description;
	}

	/**
	 * @return the type
	 */
	public ResourceType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(ResourceType type) {
		this.type = type;
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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "ResouceMaster:"+DataUtil.toJson(this);
	}
	
	
}
