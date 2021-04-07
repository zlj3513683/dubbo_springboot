package com.fortunebill.qbService.iface.mchnt;

import com.fortunebill.qbService.data.ResultInfoData;

/**
 * 钱小宝商户服务接口
 * 
 * @author luhq
 *
 */
public interface IMchntQxbService {
	

	/**
	 * 商户停用接口
	 * 
	 * @param mchtNo 商户号
	 * @return 停用处理结果
	 */
 public ResultInfoData stopMchnt(String mchtNo);


}
