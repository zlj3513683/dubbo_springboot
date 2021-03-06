package zlj.study.provider.service;

import com.api.service.DemoService;
import com.api.service.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * 功能：
 *
 * @author zoulinjun
 * @date 2020/11/28
 */
//@Service(version = "1.0.0",interfaceClass = DemoService.class,timeout = 10000,group = "dev")
//@Service(version = "1.0.0",interfaceClass = DemoService.class,timeout = 10000)
@Service(group = "dev")
@Slf4j
public class DemoServiceImpl implements DemoService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private DemoService demoService;

    @Override
    public String ins(User user,int id,String ss,long[] ids,User[] users,Integer iis) {
        log.info("version 1.0.0");
        log.info("group dev");
        log.info(redisTemplate.opsForValue().get("test"));
        System.out.println(id);
        System.out.println(user);
        return "11111";
    }

    @Override
    public String ins1(User user, int id, String ss, long[] ids, User[] users, Integer iis, String res) {
        log.info("进来了最终的方法");
        log.info("上一个方法："+res);
        return "SUCESS";
    }

    @Override
    public String ins1(User user, int id, String ss, long[] ids, User[] users, Integer iis) {
        System.out.println("222222222222222222222222");
        System.out.println(user);
        System.out.println(id);
        System.out.println(user);
        return "新增成功2222222";
    }

    @Override
    public String del(String id) {
        return "删除成功";
    }

    @Override
    public String upd(int id) {

//        String hh = null;
//        hh.getClass();
        int i = 9;
        int j = i/0;
        int k = 0/i;

        throw new NullPointerException();
//        return "修改成功";
    }

    @Override
    public String sel(Integer id) {
        return "查询成功";
    }


    private String testAsync()  {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "async_success";
    }

    @Override
    public CompletableFuture<String> asyncTest() {
        return CompletableFuture.supplyAsync(()->{
            return testAsync();
        });
    }

    @Override
    public void noParams() {
        log.info("this is a no parmas method");
    }
}
