package zlj.study.provider.spring.service;

import com.api.service.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author zoulinjun
 * @title: InjectService
 * @projectName dubbo_springboot
 * @description:
 * 1、从apiService服务中获取缓存数据
 * 2、遍历所有的缓存数据的class、method组装RefreceBean实现FactoryBean
 * 3、将RefreceBean注入到spring容器
 * @date 2021/3/5 13:08
 */
public class InjectService {

    DefaultListableBeanFactory defaultListableBeanFactory;
    DemoService demoService;



}
