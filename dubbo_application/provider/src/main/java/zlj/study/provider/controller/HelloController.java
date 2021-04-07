package zlj.study.provider.controller;

import com.api.service.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zoulinjun
 * @title: HelloController
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/2/19 14:28
 */
@RestController
public class HelloController {

//    @Reference(version = "1.0.0")
//    private DemoService demoService;
//    @Autowired
//    private DemoService demoService;

//    @RequestMapping("/ins")
//    public String ins(){
//        return demoService.ins();
//    }


}
