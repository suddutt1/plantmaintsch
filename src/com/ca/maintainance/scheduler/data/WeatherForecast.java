package com.ca.maintainance.scheduler.data;

public class WeatherForecast {

	private String date;//yyyy.MM.dd format
	private long time;
	private long precpProb;
	private long visibility;//in meters
	
	public WeatherForecast()
	{
		super();
	}

	/**
	 * @param date
	 * @param time
	 * @param precpProb
	 * @param visibility
	 */
	public WeatherForecast(String date, long time, long precpProb,
			long visibility) {
		super();
		this.date = date;
		this.time = time;
		this.precpProb = precpProb;
		this.visibility = visibility;
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
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the precpProb
	 */
	public long getPrecpProb() {
		return precpProb;
	}

	/**
	 * @param precpProb the precpProb to set
	 */
	public void setPrecpProb(long precpProb) {
		this.precpProb = precpProb;
	}

	/**
	 * @return the visibility
	 */
	public long getVisibility() {
		return visibility;
	}

	/**
	 * @param visibility the visibility to set
	 */
	public void setVisibility(long visibility) {
		this.visibility = visibility;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WeatherForecast "+ DataUtil.toJson(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (int) (time ^ (time >>> 32));
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
		if (!(obj instanceof WeatherForecast)) {
			return false;
		}
		WeatherForecast other = (WeatherForecast) obj;
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (time != other.time) {
			return false;
		}
		return true;
	}
	
	
}
