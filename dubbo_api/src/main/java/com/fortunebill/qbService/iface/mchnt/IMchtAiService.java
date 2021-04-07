package com.fortunebill.qbService.iface.mchnt;

import com.allinfinance.po.comm.FileData;

public interface IMchtAiService {

    public String aiUpdateCheck(String mchtNo);

    void test(String hh,int i);

    String aiUpdateCheck(String mchtNo, FileData fileData);
}
