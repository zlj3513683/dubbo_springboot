package zlj.study.spi;

/**
 * @author zoulinjun
 * @title: CarWrapper
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/2/28 9:17
 */
public class CupWrapper implements Cup{

    private Cup cup;

    public CupWrapper(Cup cup) {
        this.cup = cup;
    }

    @Override
    public void drink() {
        System.out.println("before");
        this.cup.drink();
        System.out.println("after");
    }
}
