package com.fortunebill.qbService.iface.mpos;

import com.fortunebill.qbService.data.ResultInfoData;
import com.fortunebill.qbService.data.mpos.CreditCheckInfo;

/**
 * 商户身份检查服务
 *
 * @author luhq
 *
 */
public interface IMchntIdentityCheckService {



	/**
	 * 获取商户是否需要人脸识别
	 *
	 * @param mchtNo 商户号
	 * @return
	 */
	public ResultInfoData mchtIsYesOrNoFaceCredit(String mchtNo);


	/**
	 * 恢复商户开通
	 *
	 * @param mchtNo 商户号
	 * @return 开通结果
	 */
	public ResultInfoData resumeMchnt(String mchtNo);


	/**
	 * 新无卡和网联签约成功的银行卡四要素信息需记录征信历史库，
	 * 签约成功或失败的都需要记录，且需要记录失败返回信息。
	 *
	 * @param creditCheckInfo 签约结果
	 * @return 处理结果
	 */
	public ResultInfoData mchtCreditCheck(CreditCheckInfo creditCheckInfo);


}
