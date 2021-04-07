package zlj.study.consumer.future;

import org.junit.jupiter.api.Test;
import org.springframework.util.ObjectUtils;

/**
 * @author zoulinjun
 * @title: ObjectUtilTest
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/3/2 13:06
 */
public class ObjectUtilTest {

    @Test
    public void test(){
        String sq = "ss";
        String sq1 = "ss";
        System.out.println(ObjectUtils.nullSafeEquals(sq,sq1));
    }
}
