package com.seed.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/10 19:28
 */
public class BeanCopyUtils {
    private BeanCopyUtils(){}

    public static <V> V copyBean(Object source,Class<V> targetClass){
        //创建目标对象
        V result=null;
        try {
            result = targetClass.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(source,result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static <S,V> List<V> copyBeanList(List<S> source, Class<V> targetClass){
        List<V> result = source.stream()
                .map(s -> copyBean(s, targetClass))
                .collect(Collectors.toList());
        return result;
    }
}
