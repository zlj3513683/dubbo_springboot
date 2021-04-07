package com.fortunebill.qbService.iface.wpos;

import com.fortunebill.qbService.data.ResultInfoData;

/**
 * @author zoulinjun
 * @title: IBrhManageService
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/3/4 14:22
 */
public interface IBrhManageService {
    ResultInfoData mchtBrhOprChange(String createNewNo);
}
