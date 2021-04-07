package zlj.study.spi;

import org.apache.dubbo.common.extension.SPI;

/**
 * @author zoulinjun
 * @title: Car
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/2/28 9:12
 */
@SPI("red")
public interface Car {

    void drive();

}
