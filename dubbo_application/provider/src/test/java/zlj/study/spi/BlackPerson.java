package zlj.study.spi;

import org.apache.dubbo.common.URL;

/**
 * @author zoulinjun
 * @title: BlackPerson
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/2/28 9:24
 */
public class BlackPerson implements Person{

    private Study study;
    //1、先判断是否是spi的接口如果是就从spi容器里面找没找到就生成2、判断是不是spi的接口，如果不是就从spring容器中找先byName然后byType
    //先通过spi生成代理对象会判断是否为接口 并且@SPI注解 然后生成这个接口的代理对象（方法能代理的原则是加@Adaptive和参数带有Url参数）
    //如果没有@SPI注解的就去spring容器里面找先通过名称找然后通过type找bean
    //然后反射赋值
    //代理类如下
    /*package zlj.study.spi;
    import org.apache.dubbo.common.extension.ExtensionLoader;
    public class Study$Adaptive implements zlj.study.spi.Study {
        public void doSomthing(org.apache.dubbo.common.URL arg0)  {
            if (arg0 == null) throw new IllegalArgumentException("url == null");
            org.apache.dubbo.common.URL url = arg0;
            String extName = url.getParameter("study");
            if(extName == null) throw new IllegalStateException("Failed to get extension (zlj.study.spi.Study) name from url (" + url.toString() + ") use keys([study])");
            zlj.study.spi.Study extension = (zlj.study.spi.Study)ExtensionLoader.getExtensionLoader(zlj.study.spi.Study.class).getExtension(extName);
            extension.doSomthing(arg0);
        }
        public void hello()  {
            throw new UnsupportedOperationException("The method public abstract void zlj.study.spi.Study.hello() of interface zlj.study.spi.Study is not adaptive method!");
        }
    }*/
    public void setStudy(Study study) {
        this.study = study;
    }

    @Override
    public void life(URL url) {
        System.out.println("my life start");
        study.doSomthing(url);
//        study.hello();//未加@Adaptive和参数Url的方法 直接会抛出异常
        System.out.println("mylift end");
    }
}
