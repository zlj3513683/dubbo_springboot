package zlj.study.spi;

import org.apache.dubbo.common.URL;

/**
 * @author zoulinjun
 * @title: English
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/2/28 9:27
 */
public class Chilse implements Study{

    @Override
    public void doSomthing(URL url) {
        System.out.println("study chilse");
    }

    @Override
    public void hello() {
        System.out.println("hello");
    }
}
