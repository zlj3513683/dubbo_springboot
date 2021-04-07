package zlj.study.spi;

/**
 * @author zoulinjun
 * @title: RedCar
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/2/28 9:12
 */
public class GreenCup implements Cup{

    @Override
    public void drink() {
        System.out.println(" i am grenn Cup");
    }
}
