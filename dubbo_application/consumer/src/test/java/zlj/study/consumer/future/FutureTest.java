package zlj.study.consumer.future;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * @author zoulinjun
 * @title: FutureTest
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/2/27 10:15
 */
@Slf4j
public class FutureTest {

    @Test
    public void call() throws ExecutionException, InterruptedException {
        log.info("1:" + Thread.currentThread().getName());
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            return test();
        });
        future.get();
        future.whenComplete((s, throwable) -> {
            if(throwable != null){
                log.error("error",throwable);
            }else{
                log.info("2:" +Thread.currentThread().getName());
                log.info("response:" + s);
            }
        });

        Thread.sleep(5000);
        log.info("end");
    }

    public String test()  {
        log.info("3："+Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }

}
