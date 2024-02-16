package com.zyx.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils(){

    }

    public static <V> V copyBean(Object source,Class<V> clazz) {
        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            //实现属性拷贝
            BeanUtils.copyProperties(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

        //返回结果
    }

    public static <V> List<V> copyBeanList(List<?> list,Class<V> clazz){
        return list.stream()
                .map(o-> copyBean(o,clazz))
                .collect(Collectors.toList());
    }
}
