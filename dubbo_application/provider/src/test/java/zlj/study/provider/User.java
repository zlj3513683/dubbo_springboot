package zlj.study.provider;

import lombok.Data;

import java.io.Serializable;

/**
    * @title: User
    * @projectName dubbo_springboot
    * @description: TODO
    * @author zoulinjun
    * @date 2021/2/22 16:54
    */
@Data
class User implements Serializable {

    private static final long serialVersionUID = -2091559023852080896L;
    private int id;
    private String name;
    private int age;

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}