package com.api.service;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import java.lang.reflect.Array;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * protostuff 序列化工具类，基于protobuf封装
 */
public class ProtostuffUtil {

    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();

    private static <T> Schema<T> getSchema(Class<T> clazz) {
        @SuppressWarnings("unchecked")
        Schema<T> schema = (Schema<T>) cachedSchema.get(clazz);
        if (schema == null) {
            schema = RuntimeSchema.getSchema(clazz);
            if (schema != null) {
                cachedSchema.put(clazz, schema);
            }
        }
        return schema;
    }

    /**
     * 序列化
     *
     * @param obj
     * @return
     */
    public static <T> byte[] serializer(T obj) {
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(clazz);
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    public static <T> Object serializerAll(T obj) {
        if(isWrapClass(obj.getClass())){
            return obj;
        }else{
            return Base64.getEncoder().encodeToString(serializer(obj));
        }
    }

    /**
     * 反序列化
     *
     * @param data
     * @param clazz
     * @return
     */
    public static <T> T deserializer(byte[] data, Class<T> clazz) {
        try {
            T obj = clazz.newInstance();
            Schema<T> schema = getSchema(clazz);
            ProtostuffIOUtil.mergeFrom(data, obj, schema);
            return obj;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static <T> T deserializerAll(Object objk, Class<T> clazz) {
        try {
            if(isWrapClass(clazz)){
                return (T)objk;
            }else{
                return deserializer(Base64.getDecoder().decode(objk.toString()),clazz);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private static boolean isWrapClass(Class clz) {
        try {
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    private  static <T> T[] tetst(Class<T> clazz,int len){
        T[] objects = null;
        if(clazz.isPrimitive()){
//            objects = (T[]) Array.newInstance(clazz, len);;
        }else{
            objects = (T[]) Array.newInstance(clazz, len);;
        }
        return objects;
    }

    private static Object tt(){
        int[] ins = new int[2];
        ins[0] = 1;
        ins[1] = 12;
        return ins;
    }

    public static void main(String[] args) {
        /*Boolean boo = new Boolean(true);
        Object serObj = ProtostuffUtil.serializerAll(boo);

        User user = new User(1, "zhuge");
        User user2 = new User(12, "zhuge2");
        System.out.println(user);
        Object userObj = ProtostuffUtil.serializerAll(user);
        User[] users = new User[2];
        users[0] = user;
        users[1] = user2;
        Object userObj1 = ProtostuffUtil.serializerAll(users);
        System.out.println(serObj);
        System.out.println(userObj);

        Boolean b = deserializerAll(serObj,Boolean.class);
        User user1 = deserializerAll(userObj,User.class);
        User[] user122 = deserializerAll(userObj1,User[].class);
        System.out.println(b);
        System.out.println(user1);
        System.out.println(user122[0]);
        System.out.println(user122[1]);*/


        Class clazz = String.class;
        System.out.println(tetst(clazz,2));
        System.out.println(tetst(Integer.class,2));
        System.out.println(tetst(int.class,2));

        Object[] obj = new Object[2];
        double[] obj1 = new double[2];
        double d1 = 1.1;
        double d2 = 1.2;
        obj[0] = d1;
        obj[1] = d2;
        obj1[0] = d1;
        obj1[1] = d2;
        Object o = tt();
        System.out.println(o);
        System.out.println(obj);

//        System.out.println(isWrapClass(boo.getClass()));
//        System.out.println(isWrapClass(Integer.class));
//        System.out.println(isWrapClass(Byte.class));
//        System.out.println(isWrapClass(User.class));
//        System.out.println(isWrapClass(String.class));
//        System.out.println(isWrapClass(User[].class));

//        Boolean boos = ProtostuffUtil.deserializer(Base64.decode(bs), User.class);
//        System.out.println(user);

//        byte[] userBytes = ProtostuffUtil.serializer(new User(1, "zhuge"));
//        Base64.getEncoder().encodeToString()
//        String bs = Base64(userBytes);
//        System.out.println(bs);
//        User user = ProtostuffUtil.deserializer(Base64.decode(bs), User.class);
//        System.out.println(user);
//
//        System.out.println(int.class.isPrimitive());
//        byte[] userBytes1 = ProtostuffUtil.serializer(new Boolean(true));
//        Boolean user1 = ProtostuffUtil.deserializer(userBytes1, Boolean.class);
//        System.out.println(user1);
    }
}