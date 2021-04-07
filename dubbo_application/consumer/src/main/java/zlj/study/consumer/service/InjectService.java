package zlj.study.consumer.service;

/**
 * @author zoulinjun
 * @title: InjectService
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/3/5 13:13
 */

import com.api.service.AaService;
import com.api.service.BbService;
import com.api.service.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.apache.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zoulinjun
 * @title: InjectService
 * @projectName dubbo_springboot
 * @description:
 * 1、从apiService服务中获取缓存数据
 * 2、遍历所有的缓存数据的class、method组装RefreceBean实现FactoryBean
 * 3、将RefreceBean注入到spring容器
 * @date 2021/3/5 13:08
 *
 */
@Component
@Import({ImportService.class})
public class InjectService implements ApplicationContextAware {

//    @DubboReference(version = "1.0.1",timeout = 5000,group = "dev")
    private HelloService helloService;
    @Autowired
    DefaultListableBeanFactory defaultListableBeanFactory;

    private ReferenceBean buildReferenceBeanIfAbsent(String referenceBeanName, AnnotationAttributes attributes,
                                                     Class<?> referencedType)
            throws Exception {

//        ReferenceBean referenceBean;
//
//            // 生成了一个ReferenceBean对象，attributes是@Reference注解的参数值
//        ReferenceBeanBuilder beanBuilder = ReferenceBeanBuilder
//                .create(attributes, applicationContext)
//                .interfaceClass(referencedType);
//        referenceBean = beanBuilder.build();
//
//        referenceBeanCache.put(referenceBeanName, referenceBean);
//        return referenceBean;
        return null;
    }

    private void inject(){
       List<Class> classes = helloService.listClass();

       defaultListableBeanFactory.registerSingleton("fafa",new MyFactoryBean());

//        InjectFactoryBean injectFactoryBean = new InjectFactoryBean(c);

//            defaultListableBeanFactory.registerSingleton("aaavvvv",injectFactoryBean);
        /*defaultListableBeanFactory.registerSingleton("aaavvvv",new BbService());*/
        for (Class c:
             classes) {
//
            //判断容器是否包含bean
            /*Object o = defaultListableBeanFactory.getBean(c);
            if(o == null){
//                ReferenceBeanBuilder beanBuilder = ReferenceBeanBuilder
//                    .create(attributes, applicationContext)
//                    .interfaceClass(referencedType);
//            referenceBean = beanBuilder.build();



            }*/



//                ReferenceBeanBuilder beanBuilder = ReferenceBeanBuilder
//                    .create(attributes, applicationContext)
//                    .interfaceClass(referencedType);
//            referenceBean = beanBuilder.build();

//            ReferenceBean referenceBean = new ReferenceBean();
//            referenceBean.setInterface(c);
//            defaultListableBeanFactory.registerSingleton("aaavvvv" + c.getName(),referenceBean);

            InjectFactoryBean injectFactoryBean = new InjectFactoryBean(c);
            defaultListableBeanFactory.registerSingleton("aaavvvv" + c.getName(),injectFactoryBean);

//            ReferenceBean referenceBean = buildReferenceBeanIfAbsent(referenceBeanName, attributes, injectedType);

            // 把referenceBean添加到Spring容器中去
//            registerReferenceBean(referencedBeanName, referenceBean, attributes, injectedType);


//            defaultListableBeanFactory.registerSingleton("aaavvvv",new BbService());
            /*GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(InjectFactoryBean.class);
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(AaService.class.getName());*/
//            definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName());
//            definition.setBeanClass(this.mapperFactoryBean.getClass());
//            defaultListableBeanFactory.registerBeanDefinition("fa_"+c.getName(),beanDefinition);
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        inject();
//        System.out.println(applicationContext.getBean(AaService.class));
//        System.out.println(applicationContext.getBean(BbService.class));
//        System.out.println(applicationContext.getBean("aaavvvv"));
    }
}
