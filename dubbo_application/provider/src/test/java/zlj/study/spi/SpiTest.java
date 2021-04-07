package zlj.study.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.config.configcenter.DynamicConfiguration;
import org.apache.dubbo.common.config.configcenter.DynamicConfigurationFactory;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zoulinjun
 * @title: SpiTest
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/2/28 9:14
 */
public class SpiTest {

    @Test
    public void sampleTest(){
        ExtensionLoader<Car> carExtensionLoader = ExtensionLoader.getExtensionLoader(Car.class);
        Car car = carExtensionLoader.getExtension("red");
        car.drive();
    }

    @Test
    public void autoTest(){
        ExtensionLoader<Car> carExtensionLoader = ExtensionLoader.getExtensionLoader(Car.class);
        Car car = carExtensionLoader.getExtension("true");
        car.drive();
    }

    @Test
    public void warpperTest(){
        ExtensionLoader<Cup> cupExtensionLoader = ExtensionLoader.getExtensionLoader(Cup.class);
        Cup cup = cupExtensionLoader.getExtension("green");
        cup.drink();
    }

    @Test
    public void autowiredTest(){
        ExtensionLoader<Person> personExtensionLoader = ExtensionLoader.getExtensionLoader(Person.class);
        URL url = new URL("black", "localhost", 8080);
        url = url.addParameter("study", "english");
//        url = url.addParameter("study", "chils");
        Person person = personExtensionLoader.getExtension(url.getProtocol());
        person.life(url);
    }

    @Test
    public void unmodifiableList(){
        List<String> list = new ArrayList<>();
        list.add("1");
        List<String> list1 = Collections.unmodifiableList(list);//转换成不可变长度的list
        System.out.println(list1);
        list1.add("222");//java.lang.UnsupportedOperationException
        System.out.println(list1);
    }

    /*@Test
    public void duubboAutowired(){
        DynamicConfigurationFactory factories = ExtensionLoader
                .getExtensionLoader(DynamicConfigurationFactory.class)
                .getExtension(url.getProtocol());
        DynamicConfiguration configuration = factories.getDynamicConfiguration(url);

    }*/

}
