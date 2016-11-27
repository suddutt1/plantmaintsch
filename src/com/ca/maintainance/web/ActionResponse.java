package com.ca.maintainance.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ca.maintainance.scheduler.data.DataUtil;

public class ActionResponse {
	public static final int OK_RESPONSE=0;
	public static final int NO_RESULT_RESPONSE=1;
	public static final int INVALID_INPUT_RESPONSE=2;
	public static final int EXCEPTION_RESPONSE=99;
	private static final SimpleDateFormat _DT_FMT = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	private Object result;
	private int status;
	private String responseText;
	private String servertime;
	
	public ActionResponse()
	{
		super();
	}

	/**
	 * @param result
	 * @param status
	 * @param responseText
	 */
	public ActionResponse(Object result, int status, String responseText) {
		super();
		this.result = result;
		this.status = status;
		this.responseText = responseText;
		this.servertime = _DT_FMT.format(new Date());
		
	}

	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the responseText
	 */
	public String getResponseText() {
		return responseText;
	}

	/**
	 * @param responseText the responseText to set
	 */
	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	/**
	 * @return the servertime
	 */
	public String getServertime() {
		return servertime;
	}

	/**
	 * @param servertime the servertime to set
	 */
	public void setServertime(String servertime) {
		this.servertime = servertime;
	}
	
	public String toJson()
	{
		return DataUtil.toJson(this);
	}
	

}
