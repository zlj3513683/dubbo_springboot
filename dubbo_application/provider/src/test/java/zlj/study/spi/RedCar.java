package zlj.study.spi;

/**
 * @author zoulinjun
 * @title: RedCar
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/2/28 9:12
 */
public class RedCar implements Car{

    @Override
    public void drive() {
        System.out.println(" i am red car");
    }
}
