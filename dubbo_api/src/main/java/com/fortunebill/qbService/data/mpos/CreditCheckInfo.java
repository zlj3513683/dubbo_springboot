package com.fortunebill.qbService.data.mpos;

import com.fortunebill.qbService.data.AbstractValidateData;


/**
 * 新无卡和网联签约结果
 * 
 * @author luhq
 * 
 * @version 1.0
 */
public class CreditCheckInfo extends AbstractValidateData{
	private String mchtNo = null;
	
	/**
	 * 签约类型  1：新无卡，2：网联
	 */
	
	private String type = null;
	
	/**
	 * 签约结果  0：成功，1：失败
	 */
	private String resultCode = null;
	
	/**
	 * 签约结果说明  
	 */
	private String resultMsg = null;

	
	private String name = null;
	
	private String idCard = null;
	
	private String bankNo = null;
	
	private String telPhone = null;
	
	private String oprId = null;
	


	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	
	public String getOprId() {
		return oprId;
	}

	public void setOprId(String oprId) {
		this.oprId = oprId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

}
