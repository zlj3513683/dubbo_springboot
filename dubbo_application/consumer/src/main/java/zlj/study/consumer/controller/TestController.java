package zlj.study.consumer.controller;

import com.api.service.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能：消费方调用
 *
 * @author zoulinjun
 * @date 2020/11/28
 */
@RestController
public class TestController {

    @Reference(version = "1.0.0")
    private DemoService demoService;

    @RequestMapping("/ins")
    public String ins(){
        return demoService.ins();
    }

    @RequestMapping("/del")
    public String del(){
        return demoService.del();
    }

    @RequestMapping("/upd")
    public String upd(){
        return demoService.upd();
    }

    @RequestMapping("/sel")
    public String sel(){
        return demoService.sel();
    }
}
