package zlj.study.consumer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.allinfinance.po.comm.FileData;
import com.api.service.DemoService;
import com.api.service.HelloService;
import com.api.service.User;
import com.clazz.MyClass;
import com.clazz.MyClass1;
import com.fortunebill.qbService.data.ResultInfoData;
import com.fortunebill.qbService.data.mpos.CreditCheckInfo;
import com.fortunebill.qbService.iface.mchnt.IMchntQxbService;
import com.fortunebill.qbService.iface.mchnt.IMchtAiService;
import com.fortunebill.qbService.iface.mpos.IMchntIdentityCheckService;
import com.fortunebill.qbService.iface.wpos.IBrhManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import zlj.study.consumer.enums.BaseType;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 功能：消费方调用
 * 2.7.6的@Service注解存在一个接口不能注入多个实现类的bug
 *
 * @author zoulinjun
 * @date 2020/11/28
 */
@RestController
@Slf4j
public class TestController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

//    @Reference(version = "1.0.1",timeout = 20000,group = "dev")
    private DemoService demoService;
//    @Reference(version = "1.0.1",timeout = 20000,group = "test")
    private DemoService demoService2;

//    @DubboReference(version = "1.0.1",timeout = 5000,group = "dev")
    private HelloService helloService1;
//    @DubboReference(version = "1.0.1",timeout = 2000,group = "test")
    private HelloService helloService2;

    @DubboReference(timeout = 20000)
    private IMchtAiService mchtAiService;
//    @DubboReference(timeout = 20000)
    private IMchntQxbService mchntQxbService;
//    @DubboReference(timeout = 20000)
    private IBrhManageService brhManageService;

    /*@DubboReference(version = "1.0.1",timeout = 5000,group = "dev",id = "hehe")
    private HelloService helloService111111111;
    @DubboReference(version = "1.0.1",timeout = 5000,group = "dev")
    private HelloService helloService1111221;
    @DubboReference(version = "1.0.1",timeout = 5000,group = "dev")
    private HelloService helloService1122111;
    @DubboReference(version = "1.0.1",timeout = 2000,group = "dev")
    private HelloService helloService1122222;
    @DubboReference(version = "1.0.1",timeout = 1000,group = "dev")
    private HelloService helloService1122333;
    @DubboReference(version = "1.0.1",timeout = 5000,group = "dev",check = false)
    private HelloService helloService1122444;*/

//    @DubboReference(timeout = 20000)
    private IMchntIdentityCheckService iMchntIdentityCheckService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @RequestMapping("/testFile")
    public String testFile(HttpServletRequest request){
        FileData fileData = new FileData();
        fileData.setFilterFiled("111");
        fileData.setFbFilePath("C:/zlj/doc/dev/dubbo/");
        String result = mchtAiService.aiUpdateCheck("111",fileData);
        System.out.println(result);
        return null;
    }


    @RequestMapping("/creditCheckInfo")
    public String creditCheckInfo(HttpServletRequest request){
        CreditCheckInfo creditCheckInfo = new CreditCheckInfo();
        creditCheckInfo.setBankNo("6225768379917170");
        creditCheckInfo.setIdCard("320681199107250015");
        creditCheckInfo.setMchtNo("848290959636027");
        creditCheckInfo.setTelPhone("creditCheckInfo");
        creditCheckInfo.setName("二狗子");
        creditCheckInfo.setOprId("app");
        creditCheckInfo.setResultCode("0");
        creditCheckInfo.setResultMsg("成功");
        creditCheckInfo.setType("1");

        ResultInfoData result = iMchntIdentityCheckService.resumeMchnt("111");
        System.out.println("msg:" + result.getMsg());
        return null;
    }




    @RequestMapping("/testwpos")
    public String testwpos(HttpServletRequest request){
        System.out.println(applicationContext.getBean("fafa"));
        System.out.println(applicationContext.getBean("@Reference(group=dev,timeout=2000,version=1.0.1) com.api.service.HelloService"));
        ResultInfoData resultInfoData = brhManageService.mchtBrhOprChange("aaa");
        return resultInfoData.getStatus();
    }
    @RequestMapping("/testmcht")
    public String testmcht(HttpServletRequest request){
        ResultInfoData resultInfoData = mchntQxbService.stopMchnt("aaa");
        return resultInfoData.getResultId();
    }

    //测试服务端超时
    @RequestMapping("/testtimeOut1")
    public String testtimeOut1(HttpServletRequest request){
        try{
            return helloService1.timeTest();
        }catch (Exception e){
            log.error("异常了",e);
        }
        return "aaasss";
    }
    //测试客户端超时
    @RequestMapping("/testtimeOut2")
    public String testtimeOut2(HttpServletRequest request){
        try{
            return helloService2.timeTest();
        }catch (Exception e){
            log.error("异常了",e);
        }
        return "aaasss";
    }

    @RequestMapping("/testApi")
    public String testApi(HttpServletRequest request){
         mchtAiService.test("aaaaaa111",12345);
        return mchtAiService.aiUpdateCheck("abcd1111");
    }

    @RequestMapping("/testApi1")
    public String testApi1(HttpServletRequest request){
         mchtAiService.test("aaaaaa111",12345);
         return "test";
    }

    @RequestMapping("/testApi2")
    public String testApi2(HttpServletRequest request){
        return mchtAiService.aiUpdateCheck("abcd1111");
    }

    @RequestMapping("/ins")
    public String ins(HttpServletRequest request){
        User user = new User();
        user.setId(1);
        user.setName("二狗子");
//        ins(User user,int id,String ss,long[] ids,User[] users,Integer iis);
        long[] lons = {1,2};
        User[] users = new User[2];
        users[0] = user;
        users[1] =new User(1,"eee");
        return demoService.ins(user,1,"123",lons,users,new Integer(123));
//        return user1.getName();
    }

    @RequestMapping("/del")
    @ResponseBody
    public JSONObject del(HttpServletRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String  methodName = request.getParameter("methodName");
        String  interfaceName = request.getParameter("interfaceName");
        String  arguments = request.getParameter("arguments");
        String  clazzs = request.getParameter("clazzs");

        JSONArray argumentArr = JSONArray.parseArray(arguments);
        JSONArray clazzJsArr = JSONArray.parseArray(clazzs);

        int size = clazzJsArr.size();
        Class[] clazzArr = new Class[size];
        for (int i=0;i<size;i++) {
            Object cla = clazzJsArr.get(i);
            try{
                clazzArr[i] = Class.forName(cla.toString());
            }catch (Exception e){
                Class clazz = BaseType.getBaseTypeClass(cla.toString());
                if(clazz == null){
                    System.out.println("未知类型");
                    throw new RuntimeException("类型不支持");
                }
                clazzArr[i] = clazz;
            }
        }

        Object[] objects = new Object[size];
        for (int i=0;i<size;i++) {
            Object obj = argumentArr.get(i);
            if(obj instanceof JSONArray){
                Class componentType = clazzArr[i].getComponentType();
                if(componentType.isPrimitive()){
                    objects[i] = pack2BaseArr((JSONArray)obj,componentType);
                }else{
                    objects[i] = jsonArr2ObjArr((JSONArray)obj,clazzArr[i].getComponentType());
                }
            }else if(obj instanceof JSONObject){
                objects[i] = json2Obj((JSONObject)obj,clazzArr[i]);
            }else{
                objects[i] = obj;
            }
        }

        Class clazz = Class.forName(interfaceName);
        Object obj = applicationContext.getBean(clazz);
        try{

            Method m = clazz.getMethod(methodName, clazzArr);
            String result = (String) m.invoke(obj,objects);
            System.out.println("响应结果----------" + result);
            JSONObject jsonObject = new JSONObject();

//            User user = new User(123,"网二码子");
//            jsonObject.put("result",user);
//            jsonObject.put("clazz",user.getClass());
            jsonObject.put("result",result);
            jsonObject.put("clazz",result.getClass());
            return jsonObject;
        }catch (Exception e){
            System.out.println("------------------" + methodName);
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("interfaceName=com.api.service.DemoService&methodName=ins&version=1.0.0".length());
        JSONArray array = new JSONArray();
        array.add(1);
        array.add(1);
        Object o = pack2BaseArr(array,Integer.class);
        System.out.println(o);
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

    @RequestMapping("/upd")
    public String upd(){
        return demoService.upd(1);
    }

    @RequestMapping("/sel")
    public String sel(){
        return demoService.sel(new Integer(1));
    }

    @RequestMapping("/async")
    public String async() throws ExecutionException, InterruptedException {
        String result = null;
        CompletableFuture<String> future = demoService.asyncTest();
        future.whenComplete((result1,excetion) -> {
            if(excetion == null){
                log.info("result:" + result1);
            }else{
                log.error("",excetion);
            }
        });
        log.info("执行其他的内容");
        return future.get();
    }

    @RequestMapping("/noparm")
    public String noparams()  {
        demoService.noParams();
        return "sucess";
    }

}
