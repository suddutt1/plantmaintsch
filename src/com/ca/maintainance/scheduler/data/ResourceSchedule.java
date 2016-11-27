package com.ca.maintainance.scheduler.data;

public class ResourceSchedule {
	private String resouceId;
	private String date;//"yyyy.mm.dd";
	private String shift;
	private String blockedBy;
	private boolean isAvailable;
	
	public ResourceSchedule()
	{
		super();
	}

	/**
	 * @param resouceId
	 * @param date
	 * @param shift
	 * @param blockedBy
	 */
	public ResourceSchedule(String resouceId, String date, String shift,
			String blockedBy) {
		super();
		this.resouceId = resouceId;
		this.date = date;
		this.shift = shift;
		this.blockedBy = blockedBy;
		this.isAvailable = true;
	}

	/**
	 * @return the resouceId
	 */
	public String getResouceId() {
		return resouceId;
	}

	/**
	 * @param resouceId the resouceId to set
	 */
	public void setResouceId(String resouceId) {
		this.resouceId = resouceId;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
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
	 * @return the blockedBy
	 */
	public String getBlockedBy() {
		return blockedBy;
	}

	/**
	 * @param blockedBy the blockedBy to set
	 */
	public void setBlockedBy(String blockedBy) {
		this.blockedBy = blockedBy;
	}

	/**
	 * @return the isAvailable
	 */
	public boolean isAvailable() {
		return isAvailable;
	}

	/**
	 * @param isAvailable the isAvailable to set
	 */
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "Schedule:"+DataUtil.toJson(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((resouceId == null) ? 0 : resouceId.hashCode());
		result = prime * result + ((shift == null) ? 0 : shift.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ResourceSchedule)) {
			return false;
		}
		ResourceSchedule other = (ResourceSchedule) obj;
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (resouceId == null) {
			if (other.resouceId != null) {
				return false;
			}
		} else if (!resouceId.equals(other.resouceId)) {
			return false;
		}
		if (shift == null) {
			if (other.shift != null) {
				return false;
			}
		} else if (!shift.equals(other.shift)) {
			return false;
		}
		return true;
	}
	
	

}
