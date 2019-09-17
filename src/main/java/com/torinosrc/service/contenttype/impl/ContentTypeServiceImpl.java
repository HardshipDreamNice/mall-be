/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.contenttype.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.contenttype.ContentTypeDao;
import com.torinosrc.dao.contenttypecontent.ContentTypeContentDao;
import com.torinosrc.model.entity.contenttype.ContentType;
import com.torinosrc.model.view.contenttype.ContentTypeView;
import com.torinosrc.service.contenttype.ContentTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * <b><code>ContentTypeImpl</code></b>
 * <p/>
 * ContentType的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-05-28 12:08:05.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public class ContentTypeServiceImpl implements ContentTypeService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ContentTypeServiceImpl.class);

    @Autowired
    private ContentTypeDao contentTypeDao;

    @Autowired
    private ContentTypeContentDao contentTypeContentDao;

    @Override
    public ContentTypeView getEntity(long id) {
        // 获取Entity
        ContentType contentType = contentTypeDao.getOne(id);
        // 复制Dao层属性到view属性
        ContentTypeView contentTypeView = new ContentTypeView();
        TorinoSrcBeanUtils.copyBean(contentType, contentTypeView);
        return contentTypeView;
    }

    @Override
    public Page<ContentTypeView> getEntitiesByParms(ContentTypeView contentTypeView, int currentPage, int pageSize) {
        Specification<ContentType> contentTypeSpecification = new Specification<ContentType>() {
            @Override
            public Predicate toPredicate(Root<ContentType> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,contentTypeView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ContentType> contentTypes = contentTypeDao.findAll(contentTypeSpecification, pageable);

        // 转换成View对象并返回
        return contentTypes.map(contentType->{
            ContentTypeView contentTypeView1 = new ContentTypeView();
            TorinoSrcBeanUtils.copyBean(contentType, contentTypeView1);
            return contentTypeView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return contentTypeDao.count();
    }

    @Override
    public List<ContentTypeView> findAll() {
        List<ContentTypeView> contentTypeViews = new ArrayList<>();
        List<ContentType> contentTypes = contentTypeDao.findAll();
        for (ContentType contentType : contentTypes){
            ContentTypeView contentTypeView = new ContentTypeView();
            TorinoSrcBeanUtils.copyBean(contentType, contentTypeView);
            contentTypeViews.add(contentTypeView);
        }
        return contentTypeViews;
    }

    @Override
    public ContentTypeView saveEntity(ContentTypeView contentTypeView) {
        // 保存的业务逻辑
        ContentType contentType = new ContentType();
        TorinoSrcBeanUtils.copyBean(contentTypeView, contentType);
        // user数据库映射传给dao进行存储
        contentType.setCreateTime(System.currentTimeMillis());
        contentType.setUpdateTime(System.currentTimeMillis());
        contentType.setEnabled(1);
        contentTypeDao.save(contentType);
        TorinoSrcBeanUtils.copyBean(contentType,contentTypeView);
        return contentTypeView;
    }

    @Override
    public void deleteEntity(long id) {
        ContentType contentType = new ContentType();
        contentType.setId(id);
        contentTypeDao.delete(contentType);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<ContentType> contentTypes = new ArrayList<>();
        for(String entityId : entityIds){
            ContentType contentType = new ContentType();
            contentType.setId(Long.valueOf(entityId));
            contentTypes.add(contentType);
        }
        contentTypeDao.deleteInBatch(contentTypes);
    }

    @Override
    public void updateEntity(ContentTypeView contentTypeView) {
        Specification<ContentType> contentTypeSpecification = Optional.ofNullable(contentTypeView).map(s -> {
            return new Specification<ContentType>() {
                @Override
                public Predicate toPredicate(Root<ContentType> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("ContentTypeView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<ContentType> contentTypeOptionalBySearch = contentTypeDao.findOne(contentTypeSpecification);
        contentTypeOptionalBySearch.map(contentTypeBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(contentTypeView,contentTypeBySearch);
            contentTypeBySearch.setUpdateTime(System.currentTimeMillis());
            contentTypeDao.save(contentTypeBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + contentTypeView.getId() + "的数据记录"));
    }

    /**
     * 递归删除每个子节点（先判断是否有文章内容，有则不能删除）
     * @param id
     */
    @Override
    public void recursionDelNode(Long id) throws TorinoSrcServiceException {
        //获取类别
        ContentType contentType=contentTypeDao.getOne(id);
        //判断该类别不为null
        if(!ObjectUtils.isEmpty(contentType)){
            if(!ObjectUtils.isEmpty(contentTypeContentDao.findByContenttypeId(contentType.getId()))){
                throw new TorinoSrcServiceException("删除失败，该类别下含有文章内容");
            }
            //子类别
            Set<ContentType> childrens = contentType.getChildren();
            //判断该类别是否有子类别
            if(!childrens.isEmpty()){
                //多个子类别，递归遍历所有子类别
                for(ContentType contentType1:childrens){
                    //判断是否有文章内容
                    if(!ObjectUtils.isEmpty(contentTypeContentDao.findByContenttypeId(contentType1.getId()))){
                        throw new TorinoSrcServiceException("删除失败，该类别下含有文章内容");
                    }
                    recursionDelNode(contentType1.getId());
                }
            }
        }
        //没有子类别则直接删除
        deleteEntity(id);
    }

   /* 子查询出顶层id，然后通过顶层id级联查询子
    @param id
    */
//   @Override
//   public ContentTypeView recursionFindParent(Long id){
//       //获取类别
//       ContentType contentType=contentTypeDao.getOne(id);
//       StringBuilder builder = new StringBuilder();
//       while(!ObjectUtils.isEmpty(contentType.getParentId())){
//               contentType=contentTypeDao.getOne(contentType.getParentId());
//       }
//       ContentTypeView contentTypeView=getEntity(contentType.getId());
//       return contentTypeView;
//   }

}
