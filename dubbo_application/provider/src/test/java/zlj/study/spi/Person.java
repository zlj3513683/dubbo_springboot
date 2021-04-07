package zlj.study.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.SPI;

/**
 * @author zoulinjun
 * @title: Person
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/2/28 9:24
 */
@SPI
public interface Person {

    void life(URL url);

}
