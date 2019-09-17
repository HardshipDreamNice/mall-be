/*
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.commons.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.model.view.json.Condition;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * <b><code>TorinoSrcConditionUtils</code></b>
 * <p/>
 * 解析查询条件JSON，自动生成Specification的相关信息
 * <p/>
 * <b>Creation Time:</b> 2018/04/11 18:18.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
public class TorinoSrcConditionUtils {

    /**
     * 返回specification需要的查询条件
     *
     * @param root
     * @param criteriaQuery
     * @param criteriaBuilder
     * @param condition JSON字段串，查询条件
     * @return Predicate
     */
    public static Predicate getPredicate(Root<?> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder, String condition){
        List<Predicate> wherePredicates = new ArrayList<>();
        List<Order> orderBys = new ArrayList<>();

        // 判断条件不为空时才进行条件组装
        if(!StringUtils.isEmpty(condition)){
            Condition condition1 = null;
            try{
                condition1 =JSON.parseObject(condition, Condition.class);
            }catch (Exception e){
                // 可不写
                throw new TorinoSrcServiceException("ERROR: 转化成JSON出错! " + e.toString()+ "。数据为： " + condition);
            }

            if(ObjectUtils.isEmpty(condition1)){
                throw new TorinoSrcServiceException("ERROR: 转化成JSON出错，对象为null！数据为： " + condition);
            }

            String where = condition1.getWhere();
            if(!StringUtils.isEmpty(where)){
                LinkedHashMap<String, Object> whereMap = getJsonMap(String.valueOf(condition1.getWhere()));
                try {
                    // 递归解析where条件
                    Predicate predicate = getMetaWhere(whereMap, root,criteriaQuery,criteriaBuilder);
                    if(!ObjectUtils.isEmpty(predicate)){
                        wherePredicates.add(predicate);
                    }

                } catch (Exception e) {
                    throw new TorinoSrcServiceException("解析where条件出错", e);
                }
            }

            String orderBy = condition1.getOrderBy();
            if(!StringUtils.isEmpty(orderBy)){
                LinkedHashMap<String, Object> orderByMap = getJsonMap(String.valueOf(condition1.getOrderBy()));
                // 解析orderBy条件
                orderBys = getMetaOrderBy(orderByMap, root,criteriaQuery,criteriaBuilder);
            }
        }

        criteriaQuery.where(wherePredicates.toArray(new Predicate[wherePredicates.size()])).orderBy(orderBys);
        return criteriaQuery.getRestriction();
    }

    /**
     * 按给定的JSON字符串jsonStr生成LinkedHashMap<String, Object>对象
     * @param jsonStr JSON字符串
     * @return LinkedHashMap<String, Object>
     */
    public static LinkedHashMap<String, Object> getJsonMap(String jsonStr){
        return JSON.parseObject(jsonStr, new TypeReference<LinkedHashMap<String, Object>>() {});
    }

    /**
     * 按给定的LinkedHashMap<String, Object>对象生成List<Order>用于排序
     *
     * @param map JSON字符串生成的LinkedHashMap<String, Object>
     * @return List<Order> orderBy的list集合
     */
    public static List<Order> getMetaOrderBy(LinkedHashMap<String, Object> map, Root<?> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Order> orders = new ArrayList<>();
        // i从1开始，这与查询条件定义结构有关
        for(int i=1;i<Integer.MAX_VALUE;i++){
            Object o = map.get(String.valueOf(i));
            if(ObjectUtils.isEmpty(o)){
                // 当找不到条件时，跳出
                break;
            }
            // 解析orderBy条件，格式为id:asc
            String value = String.valueOf(o);
            String[] values = value.split(":");
            if(values.length!=2){
                throw new TorinoSrcServiceException("OrderBy参数键" + i + ", 值为" + value + "的结构不正确！");
            }
            switch (values[1].toLowerCase()){
                case "asc":
                    orders.add(criteriaBuilder.asc(root.get(values[0])));;
                    break;
                case "desc":
                    orders.add(criteriaBuilder.desc(root.get(values[0])));;
                    break;
                default:
                    throw new TorinoSrcServiceException("OrderBy参数键" + i + ", 值为" + value + "有误，只可以埴asc和desc！");

            }
        }
        return orders;
    }

    /**按给定的LinkedHashMap<String, Object>对象生成Predicate用于过滤条件
     *
     *
     * @param map JSON字符串生成的LinkedHashMap<String, Object>
     * @return where条件下的Predicate
     */
    public static Predicate getMetaWhere(LinkedHashMap<String, Object> map, Root<?> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder){
        // 判断Or或者And的是否在当前层次
        boolean hasOrAnd = false;
        // 定义返回值
        Predicate predicate = null;
        // 定义集合
        List<Predicate> predicates = new ArrayList<>();
        for(Map.Entry<String, Object> entry : map.entrySet()){
            switch (entry.getKey().toLowerCase()){
                case "$and":
                    // 判断Or或者And的是否在当前层次
                    hasOrAnd = true;
                    predicates = getMetaString(String.valueOf(entry.getValue()),root,criteriaQuery,criteriaBuilder);
                    predicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                    break;
                case "$or":
                    // 判断Or或者And的是否在当前层次
                    hasOrAnd = true;
                    predicates = getMetaString(String.valueOf(entry.getValue()),root,criteriaQuery,criteriaBuilder);
                    predicate = criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
                    break;
                default:
                    // 判断Or或者And的是否在当前层次
                    if(hasOrAnd){
                        throw new TorinoSrcServiceException("Condition have worng $or or $and structure!");
                    }
                    String key = entry.getKey();
                    String value = String.valueOf(entry.getValue());
                    if(StringUtils.isEmpty(key)){
                        throw new TorinoSrcServiceException("表达式的主键为空");
                    }
                    if(StringUtils.isEmpty(value)){
                        throw new TorinoSrcServiceException("表达式的主键为空");
                    }
                    if(ObjectUtils.isEmpty(predicate)){
                        predicate = anaylseCondition(root, criteriaBuilder, key, value);
                    }else {
                        predicate = criteriaBuilder.and(predicate, anaylseCondition(root, criteriaBuilder, key, value));
                    }

                    break;
            }
        }
        return predicate;
    }

    /**
     * 解析查询操作符
     * @param root
     * @param criteriaBuilder
     * @param key
     * @param value
     * @return
     */
    public static Predicate anaylseCondition(Root<?> root, CriteriaBuilder criteriaBuilder, String key, String value){
        Predicate predicate = null;
        String[] keys = key.split("//.");
        LinkedHashMap<String, Object> condition = getJsonMap(value);
        if(keys.length>1){
            // TODO: 添加多实体关联查询
        }else {
            String field = keys[0];
            for(Map.Entry<String, Object> entry : condition.entrySet()) {
                String exp = entry.getKey();
                String expValue = String.valueOf(entry.getValue());

                switch (exp.toLowerCase()){
                    case "$eq":
                        predicate = criteriaBuilder.equal(root.get(field), expValue);
                        break;
                    case "$ne":
                        predicate = criteriaBuilder.notEqual(root.get(field), expValue);
                        break;
                    case "$lt":
                        predicate = criteriaBuilder.lt(root.get(field), Long.parseLong(expValue));
                        break;
                    case "$lte":
                        predicate = criteriaBuilder.le(root.get(field), Long.parseLong(expValue));
                        break;
                    case "$gt":
                        predicate = criteriaBuilder.gt(root.get(field), Long.parseLong(expValue));
                        break;
                    case "$gte":
                        predicate = criteriaBuilder.ge(root.get(field), Long.parseLong(expValue));
                        break;
                    case "$like":
                        predicate = criteriaBuilder.like(root.get(field).as(String.class), "%"+ expValue + "%");
                        break;
                    case "$in":
                        List<String> objects = Arrays.asList(expValue.split(","));
                        predicate = root.get(field).in(objects);
                        break;
                    default:
                        throw new TorinoSrcServiceException("查询操作符暂不支持：" + exp);
                }
            }

        }

        return predicate;
    }


    /**
     * 对$and和$or的内层JSON字符串进行解析，返回Predicate集合
     * @param entry $and和$or的内层JSON字符串
     * @return List<Predicate>
     */
    public static List<Predicate> getMetaString(String entry, Root<?> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(entry);
        Iterator<Object> a = jsonArray.iterator();
        while (a.hasNext()){
            Object o = a.next();
            // 递归调用
            Predicate predicate = getMetaWhere(getJsonMap(String.valueOf(o)), root, criteriaQuery, criteriaBuilder);
            predicates.add(predicate);
        }
        return predicates;
    }

}
