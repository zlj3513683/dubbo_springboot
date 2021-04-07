package zlj.study.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zoulinjun
 * @title: RedisTest
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/2/22 10:24
 */
@SpringBootTest
@Slf4j
public class RedisTest {


    private RedisTemplate redisTemplate;

    @Autowired
    private RedisTemplate<String,String> redisTemplate2;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /*@Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }*/
    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer redisSerializer = new Jackson2JsonRedisSerializer<>(User.class);
        redisTemplate.setHashValueSerializer(redisSerializer);
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        this.redisTemplate = redisTemplate;
    }

    @Test
    public void setTimeoit(){
        Map<String, String> map = new HashMap<>();
        map.put("key1", "123");
        map.put("key2", "111");
        stringRedisTemplate.opsForHash().putAll("test_timeout", map);
        stringRedisTemplate.expire("test_timeout", 10000, TimeUnit.MILLISECONDS);

    }


    @Test
    public void tt(){
        int max=20;
        int min=10;
        Random random = new Random();

        int s = random.nextInt(max)%(max-min+1) + min;
        System.out.println(s);
    }


    @Test
    public void setmap2() throws FileNotFoundException, IOException, ClassNotFoundException{

        //这里使用序列化器Jackson2JsonRedisSerializer让redisTemplate支持序列化
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(User.class));

        //更新redisTemplate
        this.redisTemplate=redisTemplate;

        //为hash设置存储类型(map标志键名，散列key，散列value)
        HashOperations<String, String, Object> miaoshamap=redisTemplate.opsForHash();

        //从数据库获取student列表
        List<User> stulist= new ArrayList<>();
        stulist.add(new User(1,"王大锤子",18));
        stulist.add(new User(2,"王二锤子",20));
        for (User student : stulist) {

            //序列化对象操作
            ObjectOutputStream obi=null;
            ByteArrayOutputStream bai=null;
            bai=new ByteArrayOutputStream();
            obi=new ObjectOutputStream(bai);
            obi.writeObject(student);
            byte[] byt=bai.toByteArray();

            //保存序列化后的对象到redis缓存中
            redisTemplate.boundHashOps("test").put(Integer.toString(student.getId()),byt);
        }
        //设置过期时间2000秒
        redisTemplate.expire("test", 2000, TimeUnit.SECONDS);
    }


    @Test
    public void getmap(){
        //这里使用序列化器Jackson2JsonRedisSerializer让redisTemplate支持序列化
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(User.class));
        this.redisTemplate=redisTemplate;
        //根据刚才保存的map键名为test，得到其保存的值
        List<User> list=redisTemplate.boundHashOps("test").values();

        for (User student : list) {
            log.info(student.getName());
        }
        System.out.println(list);
    }



    @Test
    public void setRedisMap() {
        {
            try {
                //创建一个pojo对象
                User2 u = new User2("aaa");
                Map<String, String> map = new HashMap<String, String>();
                //将pojo对象存入map中，这里需要将pojo对象序列化一下
                map.put("key1", "123");
                map.put("key2", "111");


                User user = new User(1,"er",12);
//                JSON.toJavaObject(jsonObject,tClass);
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("aaa","111");
//                jsonObject.put("bbb",123);
                //这里将Map写入redis数据库
//                redisTemplate.opsForHash().putAll("map4", jsonObject);
                stringRedisTemplate.opsForHash().putAll("map1", map);
                redisTemplate2.opsForHash().putAll("map2", map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    @Test
    public void set(){
//        redisTemplate.opsForValue().set("myKey","myValue");
//        System.out.println(redisTemplate.opsForValue().get("myKey"));
        Map map = new HashMap();
        map.put("aaa","111");
        map.put("bbb",234);
        redisTemplate.opsForHash().put("tt1","a",111);
//        redisTemplate.opsForHash().putAll("hash_test2",map);
//        RedisUtil.setmap(redisTemplate);
        Map<String, Object> map1 = RedisUtil.hgetall(redisTemplate,"tt1");
        log.info(redisTemplate.opsForValue().get("test").toString());
    }


    static class User2 implements Serializable{

        private static final long serialVersionUID = -2091559023852080896L;

        private String name;

        public User2(String name) {
            this.name = name;
        }
    }

    static class RedisUtil{
        public static String  getHashString(RedisTemplate<String,String> redisTemplate,String key,String field){
            Object val = redisTemplate.opsForHash().get(key,field);
            return val == null ? null : val.toString();
        }



        public static void setmap(RedisTemplate redisTemplate){

            //这里使用序列化器Jackson2JsonRedisSerializer让redisTemplate支持序列化
            redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(User.class));

            //为hash设置存储类型(map标志键名，散列key，散列value)
//            HashOperations<String, String, Object> miaoshamap=redisTemplate.opsForHash();

            //从数据库获取student列表
//            redisTemplate.boundHashOps("test").put("a1",123);
//            redisTemplate.boundHashOps("test").put("a2","而过舅子");
            redisTemplate.boundHashOps("test").put("a3",new User(1,"wanger",12));
            //设置过期时间2000秒
            redisTemplate.expire("test", 2000, TimeUnit.SECONDS);
        }


        public static Map<String, Object> hgetall(RedisTemplate<String,Object> redisTemplate, String key) {
            return redisTemplate.execute((RedisCallback<Map<String, Object>>) con -> {
                Map<byte[], byte[]> result = con.hGetAll(key.getBytes());
                if (CollectionUtils.isEmpty(result)) {
//                   return new HashMap<>(0);
                    return null;
                }

                Map<String, Object> ans = new HashMap<>(result.size());
                for (Map.Entry<byte[], byte[]> entry : result.entrySet()) {
                    try {
                        ans.put(new String(entry.getKey()), bytesToObject(entry.getValue()));
                    } catch (IOException e) {
                        log.error("",e);
                    } catch (ClassNotFoundException e) {
                        log.error("",e);
                    }
                }
                return ans;
            });
        }

        public static Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream sIn = new ObjectInputStream(in);
            return sIn.readObject();
        }

        public Map<String, String> hmget(RedisTemplate<String,Object> redisTemplate,String key, List<String> fields) {
            List<String> result = redisTemplate.<String, String>opsForHash().multiGet(key, fields);
            Map<String, String> ans = new HashMap<>(fields.size());
            int index = 0;
            for (String field : fields) {
                if (result.get(index) == null) {
                    continue;
                }
                ans.put(field, result.get(index));
            }
            return ans;
        }


        public static void  putHashString(RedisTemplate<String,Object> redisTemplate,String key,String field,String value){
            redisTemplate.opsForHash().put(key, field, value);
        }


        public static <T>  void  putHashObj(RedisTemplate<String,Object> redisTemplate,String key,T t){
            //Object转Map
            Map<String,Object> map = JSONObject.parseObject(JSONObject.toJSONString(t), Map.class);

            //这里将Map写入redis数据库
            redisTemplate.opsForHash().putAll(key, map);
        }

        /**
         * 功能描述: 使用pipelined批量存储
         *
         */
        public void executePipelined(RedisTemplate<String,Object> redisTemplate,Map<String, String> map, long seconds) {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            redisTemplate.executePipelined(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    map.forEach((key, value) -> {
                        connection.set(serializer.serialize(key), serializer.serialize(value), Expiration.seconds(seconds), RedisStringCommands.SetOption.UPSERT);
                    });
                    return null;
                }
            },serializer);
        }

    }

}
