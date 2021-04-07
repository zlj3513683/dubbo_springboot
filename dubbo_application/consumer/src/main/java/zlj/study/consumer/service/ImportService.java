package zlj.study.consumer.service;

import com.api.service.AaService;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author zoulinjun
 * @title: ImportService
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/3/5 14:09
 */
public class ImportService implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(InjectFactoryBean.class);
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(AaService.class.getName());
        registry.registerBeanDefinition("abcd",beanDefinition);
    }
}
