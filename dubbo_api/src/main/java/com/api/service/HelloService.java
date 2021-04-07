package com.api.service;

import java.util.List;
import java.util.Map;

/**
 * @author zoulinjun
 * @title: HelloService
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/3/1 11:39
 */
public interface HelloService {

    String timeTest();

    default List<Class> listClass(){
        return null;
    }
}
