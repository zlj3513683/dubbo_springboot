package zlj.study.provider.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.api.service.BaseType;
import com.api.service.ProtostuffUtil;
import com.google.gson.JsonArray;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.filter.TokenFilter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zoulinjun
 * @title: TestFilter
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/2/19 14:58
 */
@Activate(order = 1,group = {CommonConstants.PROVIDER},value = {"sys_split"})
@Slf4j
//@Component
public class TestFilter implements Filter {

   static class RedisUtil{
        public static String  getHashString(RedisTemplate<String,Object> redisTemplate,String key,String field){
            Object val = redisTemplate.opsForHash().get(key,field);
            return val == null ? null : val.toString();
        }

       public static Map<String, String> hgetall(RedisTemplate<String,String> redisTemplate,String key) {
           return redisTemplate.execute((RedisCallback<Map<String, String>>) con -> {
               Map<byte[], byte[]> result = con.hGetAll(key.getBytes());
               if (CollectionUtils.isEmpty(result)) {
//                   return new HashMap<>(0);
                   return null;
               }

               Map<String, String> ans = new HashMap<>(result.size());
               for (Map.Entry<byte[], byte[]> entry : result.entrySet()) {
                   ans.put(new String(entry.getKey()), new String(entry.getValue()));
               }
               return ans;
           });
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


       public static <T>  void  putHashObj(RedisTemplate<String,String> redisTemplate,String key,T t){
           //Object转Map
           Map<String,String> map = JSONObject.parseObject(JSONObject.toJSONString(t), Map.class);

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

    private StringRedisTemplate stringRedisTemplate;


    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Data
   class Config implements Serializable {
       private static final long serialVersionUID = -5256061016860221180L;

       private String apiCode;
       private String configKey;
       private String isEnterFb;

       public Config(String apiCode, String configKey, String isEnterFb) {
           this.apiCode = apiCode;
           this.configKey = configKey;
           this.isEnterFb = isEnterFb;
       }
   }

   class Constans{
       public final static String NOT_ENTER_FB = "0";
       public final static String ENTER_FB = "1";
   }

   private boolean isEnterFb(String isEnterFb){
       return Constans.NOT_ENTER_FB.equals(isEnterFb)?false:true;
   }


//    public static void main(String[] args) {
//        getRandomDays();
//    }

   private  int getRandomDays(){
       int max=100;
       int min=30;
       Random random = new Random();
       int s = random.nextInt(max)%(max-min+1) + min;
       return s;
   }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        log.info("enter dubbo filter,request info：" + invoker.getInterface() + " method " +
                invocation.getMethodName() + "() from consumer " + RpcContext.getContext().getRemoteHost()
                + " to provider " + RpcContext.getContext().getLocalHost());
        String methodName = invocation.getMethodName();
        String interfaceName = invoker.getInterface().getName();
        //接口名-方法名-version-group
        //redis存储了数据表行信息
        //先查询redis redis没有再查mysql 如果没查到  不走统一入口  查到了 放入缓存  走统一入口
        //判断走统一入口和是否调用Fb的接口
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("interfaceName=")
                    .append(interfaceName)
                    .append("&methodName=")
                    .append(methodName)
                    .append("&version=")
                    .append(invoker.getUrl().getParameter("version"));
        String configKey = stringBuffer.toString();
        Map<String,String> config = RedisUtil.hgetall(stringRedisTemplate,configKey);
        boolean isUniteEntrance = true;
        boolean isEnterFb = true;
        if(config == null){
            //根据configKey查询数据库  slect * from tb where configKey = "aaaa';
            Config myConfig = new Config("api_001","interfaceName=com.api.service.DemoService&methodName=ins&version=1.0.0","1");
            myConfig = null;
            if(myConfig != null){
                Map<String,String> myMap = new HashMap<>();
                myMap.put("apiCode",myConfig.getApiCode());
                myMap.put("isEnterFb",myConfig.getIsEnterFb());
//                RedisUtil.putHashObj(stringRedisTemplate,configKey,myConfig);
                stringRedisTemplate.opsForHash().putAll(configKey, myMap);
                stringRedisTemplate.expire(configKey, getRandomDays(), TimeUnit.DAYS);
                isEnterFb = isEnterFb(myConfig.getIsEnterFb());
            }else{
                isUniteEntrance = false;
            }
        }else{
            isEnterFb = isEnterFb(config.get("isEnterFb"));
        }

        if(isUniteEntrance){
//        if("ins".equals(methodName)){
            Object[] arguments = invocation.getArguments();
            Class<?>[] clazzs = invocation.getParameterTypes();
            Map<String, String> map = new HashMap<>();
            map.put("methodName","ins1");
            map.put("interfaceName",invoker.getInterface().getName());
            JSONArray agrArr = (JSONArray)JSONArray.toJSON(arguments);
            JSONArray array1 = (JSONArray)JSONArray.toJSON(clazzs);
            map.put("arguments",agrArr.toJSONString());
            map.put("clazzs",array1.toJSONString());


            Object response = sendHttps(map,"http://localhost:8087/del");

            if (!isEnterFb){
                /*AsyncRpcResult asyncRpcResult = new AsyncRpcResult(invocation);
                AppResponse result = new AppResponse();
                try{
                    if(response == null){
                        throw new RuntimeException("异常");
                    }
                    result.setValue(response);
                    asyncRpcResult.complete(result);
                }catch (Exception e){
                    log.error("系统异常",e);
                    result.setException(e.getCause());
                    asyncRpcResult.complete(result);
                }*/
                return null;
            }else{
                //根据response信息封装invocation 实现逻辑 此处具体还要根据情况进行业务实现逻辑
                if(invocation instanceof RpcInvocation){
                    RpcInvocation rpcInvocation = (RpcInvocation) invocation;
                    int len = arguments.length + 1;
                    Object[] newAguments = Arrays.copyOf(arguments,len);
                    Class[] newClazzs =  Arrays.copyOf(clazzs,len);
                    newAguments[len - 1] = response;
                    newClazzs[len - 1] = response.getClass();
                    rpcInvocation.setArguments(newAguments);
                    rpcInvocation.setParameterTypes(newClazzs);
                    rpcInvocation.setMethodName("ins1");
                    return invoker.invoke(rpcInvocation);
                }else{
                    throw new RpcException("Invalid request! Forbid invoke remote service " + invoker.getInterface() + " method " + invocation.getMethodName() + "() from consumer " + RpcContext.getContext().getRemoteHost() + " to provider " + RpcContext.getContext().getLocalHost());
                }
            }

        }

        if(isEnterFb){
            return invoker.invoke(invocation);
        }

        throw new RpcException("Invalid request! Forbid invoke remote service " + invoker.getInterface() + " method " + invocation.getMethodName() + "() from consumer " + RpcContext.getContext().getRemoteHost() + " to provider " + RpcContext.getContext().getLocalHost());

    }

    public  Object sendHttps(Map<String, String> map, String url) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
//            TrustManager tm = new TrustAllStrategy();
//            sslContext.init(null, new TrustManager[] { tm }, null);

//            SSLSocketFactory sslSocketFactory = new SSLSocketFactory(sslContext);
//            sslSocketFactory
//                    .setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//            Scheme protocolScheme = new Scheme("HTTPS", 443, sslSocketFactory);
//            httpClient.getConnectionManager().getSchemeRegistry()
//                    .register(protocolScheme);


            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            // 装填参数
            List<NameValuePair> pars = new ArrayList<NameValuePair>();
            for (String str : map.keySet()) {
                pars.add(new BasicNameValuePair(str, map.get(str)));
            }

            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(pars,
                    "UTF-8");
            httpPost.setEntity(uefEntity);
            // 执行请求操作，并拿到结果（同步阻塞）
            long t1 = System.currentTimeMillis();
            HttpResponse response = httpClient.execute(httpPost);
            long t2 = System.currentTimeMillis();
            // 获取结果实体
            HttpEntity entity = response.getEntity();
            String contentMimeType = EntityUtils.getContentMimeType(entity);

            String body = "";
            if (entity != null) {
                // 按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, "UTF-8");
            }
            EntityUtils.consume(entity);

            if("FAIL".equals(body)){
                return "FAIL";
            }

            JSONObject jsonObject = JSONObject.parseObject(body);
            String clazzStr = jsonObject.getString("clazz");
            Object obj = jsonObject.get("result");

            Class clazz;
            try{
                clazz = Class.forName(clazzStr);
            }catch (Exception e){
                clazz = BaseType.getBaseTypeClass(clazzStr);
                if(clazz == null){
                    log.info("未知类型");
                    throw new RuntimeException("类型不支持");
                }
            }

            Object result;

            if(obj instanceof JSONArray){
                Class componentType = clazz.getComponentType();
                if(componentType.isPrimitive()){
                    result = pack2BaseArr((JSONArray)obj,componentType);
                }else{
                    result = jsonArr2ObjArr((JSONArray)obj,clazz.getComponentType());
                }
            }else if(obj instanceof JSONObject){
                result = json2Obj((JSONObject)obj,clazz);
            }else{
                result = obj;
            }

            return result;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;

    }

    private static Object pack2BaseArr(JSONArray array,Class clazz){
        int length = array.size();
        String baseStr = BaseType.getBaseTypeStr(clazz);
        switch (baseStr){
            case MyBaseType.INT:
                int[] intArray = new int[length];
                for (int i = 0; i < length; i++) {
                    intArray[i] = Integer.parseInt(array.getString(i));
                }
                return intArray;
            case MyBaseType.LONG:
                long[] longArray = new long[length];
                for (int i = 0; i < length; i++) {
                    longArray[i] = Long.parseLong(array.getString(i));
                }
                return longArray;
            case MyBaseType.BYTE:
                byte[] byteArray = new byte[length];
                for (int i = 0; i < length; i++) {
                    byteArray[i] = Byte.parseByte(array.getString(i));
                }
                return byteArray;
            case MyBaseType.DOUBLE:
                double[] doubles = new double[length];
                for (int i = 0; i < length; i++) {
                    doubles[i] = Double.parseDouble(array.getString(i));
                }
                return doubles;
            case MyBaseType.CHAR:
                char[] chars = new char[length];
                for (int i = 0; i < length; i++) {
                    chars[i] = array.getString(i).charAt(0);
                }
                return chars;
            case MyBaseType.SHORT:
                short[] shorts = new short[length];
                for (int i = 0; i < length; i++) {
                    shorts[i] = Short.parseShort(array.getString(i));
                }
                return shorts;
            case MyBaseType.FLOAT:
                float[] floats = new float[length];
                for (int i = 0; i < length; i++) {
                    floats[i] = Float.parseFloat(array.getString(i));
                }
                return floats;
            case MyBaseType.BOOLEAN:
                boolean[] booleans = new boolean[length];
                for (int i = 0; i < length; i++) {
                    booleans[i] = Boolean.parseBoolean(array.getString(i));
                }
                return booleans;
            default:
                return null;
        }
    }


    class MyBaseType{
        public static final String INT = "int";
        public static final String LONG = "long";
        public static final String BYTE = "byte";
        public static final String DOUBLE = "double";
        public static final String CHAR = "char";
        public static final String SHORT = "short";
        public static final String FLOAT = "float";
        public static final String BOOLEAN = "boolean";
    }

    private <T> T json2Obj(JSONObject jsonObject,Class<T> tClass){
        return JSON.toJavaObject(jsonObject,tClass);
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    private <T> T[] jsonArr2ObjArr(JSONArray jsonArray,Class<T> tClass){
        T[] objects = (T[]) Array.newInstance(tClass, jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            Object obj = jsonArray.get(i);
            if(obj instanceof JSONArray){
                Class sonClass = tClass.getComponentType();
                objects[i] = (T)jsonArr2ObjArr((JSONArray)obj,sonClass);
            }else if(obj instanceof JSONObject){
                objects[i] = (T)json2Obj((JSONObject)obj,tClass);
            }else{
                objects[i] = (T)obj;
            }
        }
        return objects;
    }

    private void post(String url,String path,boolean isEpcc){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        try{
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
//            TrustManager tm = new TrustAllStrategy();
//            sslContext.init(null, new TrustManager[] { tm }, null);

            SSLSocketFactory sslSocketFactory = new SSLSocketFactory(sslContext);
            sslSocketFactory
                    .setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Scheme protocolScheme = new Scheme("HTTPS", 443, sslSocketFactory);
            httpClient.getConnectionManager().getSchemeRegistry()
                    .register(protocolScheme);
//			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("e://test//test21301.xml")));
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String line;
            StringBuilder data = new StringBuilder("");
            while ((line = reader.readLine()) != null){
                data.append(line).append("\n");
            }
            StringEntity entity = new StringEntity(data.toString(), "UTF-8");
            entity.setContentEncoding("UTF-8");
            post.setEntity(entity);
            post.setHeader("ReservedField", 1367 + "");
            if(isEpcc){
                post.setHeader("MsgTp", "epcc.261.001.01");//
            }
            HttpResponse response = httpClient.execute(post);
            String content = EntityUtils.toString(response.getEntity());
        }catch (Exception e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
