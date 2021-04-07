package zlj.study.consumer.service;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author zoulinjun
 * @title: MyFactoryBean
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/3/12 11:09
 */
//@Component
public class MyFactoryBean implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        return new MyService();
    }

    @Override
    public Class<?> getObjectType() {
        return MyService.class;
    }
}
