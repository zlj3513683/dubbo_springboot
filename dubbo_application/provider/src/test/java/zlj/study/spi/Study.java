package zlj.study.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

/**
 * @author zoulinjun
 * @title: Study
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/2/28 9:26
 */
@SPI
public interface Study {

    @Adaptive
    void doSomthing(URL url);

    void hello();

}
