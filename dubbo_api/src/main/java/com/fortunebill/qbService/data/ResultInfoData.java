package com.fortunebill.qbService.data;

import java.io.Serializable;

/**
 * 接口处理结果类
 * 
 * @author luhq
 *
 */
public class ResultInfoData implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3126495108699969703L;
	
	private String status = "0000";
	private String msg;
	private String resultId;
	/**
	 * 需要返回的数据
	 */
	private Serializable resultData;
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public boolean isSuccess() {
		return "0000".equals(status);
	}
	public String getResultId() {
		return resultId;
	}
	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public Serializable getResultData() {
		return resultData;
	}
	public void setResultData(Serializable resultData) {
		this.resultData = resultData;
	}
	@Override
	public String toString() {
		return "ResultInfoData{" + "status='" + status + '\'' + ", msg='" + msg + '\'' + ", resultId='" + resultId + '\'' + '}';
	}
}
