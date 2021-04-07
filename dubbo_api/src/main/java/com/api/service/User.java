package com.api.service;

import java.io.Serializable;

/**
 * @author zoulinjun
 * @title: User
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/2/19 19:03
 */
public class User implements Serializable {


    private static final long serialVersionUID = -846142326629797883L;

    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public User(int id,String name ) {
        this.name = name;
        this.id = id;
    }

    public User() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
