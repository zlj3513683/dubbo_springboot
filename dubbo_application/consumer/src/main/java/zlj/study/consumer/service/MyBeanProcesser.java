package zlj.study.consumer.service;

import com.api.service.HelloService;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author zoulinjun
 * @title: MyBeanProcesser
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/3/12 10:32
 */
@Component
public class MyBeanProcesser implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof HelloService){
            System.out.println(beanName + "-----" + bean);
        }
        if(bean instanceof MyFactoryBean){
            MyFactoryBean referenceBean = (MyFactoryBean) bean;
            System.out.println(beanName + "-----" + referenceBean);
        }
        if(bean instanceof MyService){
            System.out.println(beanName + "----111111111111111111111111111111111111111111111-" + bean);
        }

        return bean;
    }
}
