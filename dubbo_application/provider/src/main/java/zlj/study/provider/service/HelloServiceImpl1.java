package zlj.study.provider.service;

import com.api.service.HelloService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @author zoulinjun
 * @title: HelloServiceImpl1
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/3/1 11:41
 */
@DubboService(group = "test",timeout = 5000)
public class HelloServiceImpl1 implements HelloService {

    @Autowired
    private HelloService helloServiceImpl;

    @Autowired
    private HelloService helloServiceImpl1;

    @Resource(name = "helloServiceImpl")
    private HelloService helloService;

    @Resource(name = "helloServiceImpl1")
    private HelloService helloService1;

    @Override
    public String timeTest() {
        try {
            System.out.println("hello1");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello1";
    }
}
