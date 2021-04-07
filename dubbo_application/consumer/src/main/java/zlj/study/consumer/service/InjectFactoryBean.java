package zlj.study.consumer.service;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author zoulinjun
 * @title: InjectFactoryBean
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/3/5 13:20
 */
public class InjectFactoryBean<T> implements FactoryBean<T> {

    private Class<T> myClass;

    public InjectFactoryBean(Class<T> myClass) {
        this.myClass = myClass;
    }

    @Override
    public T getObject() throws Exception {
        return myClass.newInstance();
    }

    @Override
    public Class<?> getObjectType() {
        return this.myClass;
    }
}
