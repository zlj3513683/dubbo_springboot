package zlj.study.consumer.enums;

/**
 * @author zoulinjun
 * @title: BaseType
 * @projectName dubbo_springboot
 * @description: TODO
 * @date 2021/2/20 15:22
 */
public enum BaseType {
    INT("int",int.class,Integer.class),
    LONG("long",long.class,Long.class),
    BYTE("byte",byte.class,Byte.class),
    DOUBLE("double",double.class,Double.class),
    CHAR("char",char.class,Character.class),
    SHORT("short",short.class,Short.class),
    FLOAT("float",float.class,Float.class),
    BOOLEAN("boolean",boolean.class,Boolean.class);

    private String baseTypeStr;
    private Class baseTypeClass;
    private Class packClass;

    BaseType(String baseTypeStr, Class baseTypeClass, Class packClass) {
        this.baseTypeStr = baseTypeStr;
        this.baseTypeClass = baseTypeClass;
        this.packClass = packClass;
    }

    public Class getPackClass() {
        return packClass;
    }

    public void setPackClass(Class packClass) {
        this.packClass = packClass;
    }

    public static Class getBaseTypeClass(String baseTypeStr){
        for (BaseType type:
        BaseType.values()) {
            if(type.baseTypeStr.equals(baseTypeStr)){
                return type.baseTypeClass;
            }
        }
        return null;
    }

    public static String getBaseTypeStr(Class baseTypeClass){
        for (BaseType type:
        BaseType.values()) {
            if(type.baseTypeClass.equals(baseTypeClass)){
                return type.baseTypeStr;
            }
        }
        return null;
    }

    public static Class getPackClass(Class baseTypeClass){
        for (BaseType type:
        BaseType.values()) {
            if(type.baseTypeClass.equals(baseTypeClass)){
                return type.packClass;
            }
        }
        return null;
    }

    public String getBaseTypeStr() {
        return baseTypeStr;
    }

    public void setBaseTypeStr(String baseTypeStr) {
        this.baseTypeStr = baseTypeStr;
    }

    public Class getBaseTypeClass() {
        return baseTypeClass;
    }

    public void setBaseTypeClass(Class baseTypeClass) {
        this.baseTypeClass = baseTypeClass;
    }
}
