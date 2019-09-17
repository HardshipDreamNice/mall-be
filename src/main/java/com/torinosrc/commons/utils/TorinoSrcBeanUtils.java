/*
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.commons.utils;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import org.springframework.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.Map;

/**
 * <b><code>TorinoSrcBeanUtils</code></b>
 * <p/>
 * TorinoSrcBeanUtils
 * <p/>
 * <b>Creation Time:</b> 2018/1/21.
 *
 * @author PhoeniX
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
public final class TorinoSrcBeanUtils {

    /**
     * BeanCopier 的缓存，BeanCopier 的拷贝速度很快，但是初始化开销很大，所以将其缓存起来
     */
    private static Map<String, BeanCopier> beanCopierMap = new HashMap<>();

    /**
     * 拷贝对象的全部非空属性的 BeanCopier 的缓存
     */
    private static Map<String, BeanCopier> beanCopierExcludeNullMap = new HashMap<>();

    private static String getBeanCopierKey(Class<?> class1, Class<?> class2) {
        return class1.getName() + class2.getName();
    }

    /**
     * 拷贝对象的全部属性
     *
     * @param source 被拷贝的源对象
     * @param target 拷贝的目标对象
     */
    public static void copyBean(Object source, Object target) {
        String beanCopierKey = getBeanCopierKey(source.getClass(), target.getClass());
        BeanCopier beanCopier = beanCopierMap.computeIfAbsent(beanCopierKey, k -> BeanCopier.create(source.getClass(), target.getClass(),
                false));
        beanCopier.copy(source, target, null);
    }

    /**
     * 拷贝对象的全部属性
     *
     * @param source      被拷贝的源对象
     * @param targetClass 拷贝的目标对象Class
     * @param <T>         拷贝的目标对象类型
     * @return 拷贝的结果
     */
    public static <T> T copyBean(Object source, Class<T> targetClass) {
        String beanCopierKey = getBeanCopierKey(source.getClass(), targetClass);
        BeanCopier beanCopier = beanCopierMap.computeIfAbsent(beanCopierKey, k -> BeanCopier.create(source.getClass(), targetClass,
                false));
        T target;
        try {
            target = targetClass.newInstance();
        } catch (Exception e) {
            throw new TorinoSrcServiceException("Bean 复制错误");
        }
        beanCopier.copy(source, target, null);
        return target;
    }

    /**
     * 拷贝对象的全部非空属性
     *
     * @param source 被拷贝的源对象
     * @param target 拷贝的目标对象
     */
    public static void copyBeanExcludeNull(Object source, Object target) {
        String beanCopierKey = getBeanCopierKey(source.getClass(), target.getClass());
        BeanCopier beanCopier = beanCopierExcludeNullMap.computeIfAbsent(beanCopierKey, k ->
                BeanCopier.create(source.getClass(), target.getClass(),
                true));
        beanCopier.copy(source, target, (value, targetClass, context) -> {
            if (value == null) {
                try {
                    value = target.getClass().getMethod("g" + context.toString().substring(1)).invoke(target);
                } catch (ReflectiveOperationException e) {
                    throw new TorinoSrcServiceException("Bean 复制错误");
                }
            }
            return value;
        });
    }


    /**
     * 测试两种拷贝方法的性能：结果：使用 BeanCopier 的速度是使用反射的三倍
     */
//    public static void main(String[] args) {
//        int count = 9999;
//        SysUser user = new SysUser();
//        user.setUserName("test");
//        user.setPassword("test");
//        user.setCreateTime(12345678L);
//        long time1 = System.nanoTime();
//        copyBeanExcludeNull(user, new SysUser());
//        long time2 = System.nanoTime();
//        copyBeanExcludeNull(user, new SysUser());
//        for (int i = 0; i < count; i++) {
//            copyBeanExcludeNull(user, new SysUser());
//        }
//        long time3 = System.nanoTime();
//        for (int i = 0; i < count; i++) {
//            ReflectUtils.flushModel(user, new SysUser());
//        }
//        long time4 = System.nanoTime();
//        System.out.println("用Copier初始化+缓存BeanCopier的时间（纳秒）：" + (time2 - time1));
//        System.out.println("用Copier复制Bean的时间（纳秒）：" + (time3 - time2));
//        System.out.println("用反射复制Bean的时间（纳秒）：" + (time4 - time3));
//        System.out.println("用反射的时间-用Copier的时间（纳秒）：" + ((time4 - time3) - (time3 - time2)));
//        System.out.println("用反射的时间-用Copier的时间倍数：" + ((time4 - time3) / (time3 - time2)));
//    }

}
