package com.sunvote.util;

import java.lang.reflect.ParameterizedType;

public class TUtil {

    /***
     * 根据指定泛型类型，实例化指定泛型参数
     * @param o 具体引用对象
     * @param i 第几个泛型参数
     * @param <T> 泛型类型
     * @return 泛型对象
     */
    public static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过类名来查找对应的类
     * @param className
     * @return
     */
    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
