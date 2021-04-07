package com.allinfinance.po.comm;

import java.io.Serializable;

/**
 * @author zoulinjun
 * @title: FileData
 * @description: TODO
 * @date 2021/3/16 11:20
 */
public class FileData implements Serializable {
    private static final long serialVersionUID = 7429021107289615261L;

    private String fbFilePath;//FB文件目录
    private String filterFiled;//过滤字符串

    public String getFbFilePath() {
        return fbFilePath;
    }

    public void setFbFilePath(String fbFilePath) {
        this.fbFilePath = fbFilePath;
    }

    public String getFilterFiled() {
        return filterFiled;
    }

    public void setFilterFiled(String filterFiled) {
        this.filterFiled = filterFiled;
    }
}
