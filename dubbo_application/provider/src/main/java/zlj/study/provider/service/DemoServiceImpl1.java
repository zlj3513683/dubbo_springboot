package zlj.study.provider.service;

import com.api.service.DemoService;
import com.api.service.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 功能：
 * group：当一个接口有多种实现时，可以用group区分；
 * version：当一个接口的实现，出现不兼容升级时，可以用版本号过渡，版本号不同的服务相互间不引用；
 *
 * @author zoulinjun
 * @date 2020/11/28
 */
//@Service(version = "1.0.1",interfaceClass = DemoService.class,timeout = 10000,group = "test")
//@Service(version = "1.0.1",interfaceClass = DemoService.class,timeout = 10000)
@Service(group = "test")
@Slf4j
public class DemoServiceImpl1 implements DemoService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String ins(User user,int id,String ss,long[] ids,User[] users,Integer iis) {
        log.info("version 1.0.1");
        log.info("group test");
        log.info(redisTemplate.opsForValue().get("test"));
        System.out.println(user);
        System.out.println(id);
        System.out.println(user);
        return "2222";
    }

    @Override
    public String ins1(User user, int id, String ss, long[] ids, User[] users, Integer iis, String res) {
        log.info("最终方法2");
        return "SUCCESS_2";
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
        return "修改成功";
    }

    @Override
    public String sel(Integer id) {
        return "查询成功";
    }
}
