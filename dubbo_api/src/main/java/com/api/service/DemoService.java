package com.api.service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 功能：
 *
 * @author zoulinjun
 * @date 2020/11/28
 */
public interface DemoService {

    String ins(User user,int id,String ss,long[] ids,User[] users,Integer iis);

    String ins1(User user, int id, String ss, long[] ids, User[] users, Integer iis);

    String ins1(User user,int id,String ss,long[] ids,User[] users,Integer iis,String res);


    String del(String id);
    String upd(int id);
    String sel(Integer id);


    default void noParams(){

    }

    default CompletableFuture<String> asyncTest(){
        return null;
    }
}
