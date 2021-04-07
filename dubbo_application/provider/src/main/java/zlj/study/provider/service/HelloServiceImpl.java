package zlj.study.provider.service;

import com.api.service.AaService;
import com.api.service.BbService;
import com.api.service.HelloService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zoulinjun
 * @title: HelloServiceImpl
 * @projectName dubbo_springboot
 * @description: 为什么服务端超时不生效 明明超时了 但是不报异常 设置1  sleep了4秒 不理解
 * @date 2021/3/1 11:39
 */
@DubboService(group = "dev",timeout = 1)
public class HelloServiceImpl implements HelloService {

    @Override
    public String timeTest() {
        try {
            System.out.println("hello");
            TimeUnit.SECONDS.sleep(4);
            return "wancheng1";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("hello1111111");
        return "hello";
    }

    @Override
    public List<Class> listClass() {
        List<Class> list = new ArrayList<>();
        list.add(AaService.class);
        list.add(BbService.class);
        return list;
    }
}
