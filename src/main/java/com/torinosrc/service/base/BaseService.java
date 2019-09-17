/*
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.base;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <b><code>BaseService<T></code></b>
 * <p/>
 * Pre-default methods provided by service.
 * <p/>
 * <b>Creation Time:</b> Mon Feb 21 22:37:40 CST 2017.
 *
 * @param <T> the type parameter from view
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-core 1.0.0
 */
public interface BaseService<T> {

    /**
     * 保存 entity.
     *
     * @param entity the entity
     * @return entity
     */
    public T saveEntity(T entity);

    /**
     * 删除 entity.
     *
     * @param id the id
     */
    public void deleteEntity(long id);

    /**
     * 批量删除 entities.
     *
     * @param ids the ids
     * @return int int
     */
    public void deleteEntities(String ids);

    /**
     * 更新 entity.
     *
     * @param entity the entity
     */
    public void updateEntity(T entity);

    /**
     * 获取 entity.
     *
     * @param id the id
     * @return entity the entity
     */
    public T getEntity(long id);

    /**
     * 按条件分页获取
     *
     * @param t entity of parms
     * @param currentPage the current page
     * @param pageSize    the page size
     * @return Page<T>   entities
     */
    public Page<T> getEntitiesByParms(T t, int currentPage,
                                     int pageSize);

    /**
     * 总记录数
     *
     * @return int entities count
     */
    public long getEntitiesCount();

    /**
     * 查找所有记录数，数据量大谨慎使用
     *
     * @return the list
     */
    public List<T> findAll();

}

