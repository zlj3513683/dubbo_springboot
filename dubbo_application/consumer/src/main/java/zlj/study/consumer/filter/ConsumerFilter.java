package zlj.study.consumer.filter;

import com.api.service.User;
import org.apache.dubbo.rpc.*;
import org.springframework.http.HttpRequest;

/**
 * @author zoulinjun
 * @title: ConsumerFilter
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/2/19 15:23
 */
public class ConsumerFilter  {

    /*@Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("进入服务消费方过滤器");
        Object[] objs = invocation.getArguments();
        System.out.println(objs[0]);
        try {
            return invoker.invoke(invocation);
        } finally {
            System.out.println("完成过滤器");
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println(String.class.getName());
        System.out.println(User.class.getName());
        System.out.println(Class.forName("java.lang.String"));
        System.out.println(Class.forName("com.api.service.User"));
    }
*/
}
