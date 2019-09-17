/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.content.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.content.ContentDao;
import com.torinosrc.dao.contenttype.ContentTypeDao;
import com.torinosrc.dao.contenttypecontent.ContentTypeContentDao;
import com.torinosrc.model.entity.content.Content;
import com.torinosrc.model.entity.contenttype.ContentType;
import com.torinosrc.model.entity.contenttypecontent.ContentTypeContent;
import com.torinosrc.model.view.content.ContentView;
import com.torinosrc.service.content.ContentService;
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
import java.util.*;

/**
* <b><code>ContentImpl</code></b>
* <p/>
* Content的具体实现
* <p/>
* <b>Creation Time:</b> 2018-05-28 12:06:23.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class ContentServiceImpl implements ContentService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ContentServiceImpl.class);

    @Autowired
    private ContentDao contentDao;

    @Autowired
    private ContentTypeDao contentTypeDao;

    @Autowired
    private ContentTypeContentDao contentTypeContentDao;

    @Override
    public ContentView getEntity(long id) {
        // 获取Entity
        Content content = contentDao.getOne(id);
        // 复制Dao层属性到view属性
        ContentView contentView = new ContentView();
        TorinoSrcBeanUtils.copyBean(content, contentView);
        return contentView;
    }

    @Override
    public Page<ContentView> getEntitiesByParms(ContentView contentView, int currentPage, int pageSize) {
        Specification<Content> contentSpecification = new Specification<Content>() {
            @Override
            public Predicate toPredicate(Root<Content> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,contentView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Content> contents = contentDao.findAll(contentSpecification, pageable);

        // 转换成View对象并返回
        return contents.map(content->{
            ContentView contentView1 = new ContentView();
            TorinoSrcBeanUtils.copyBean(content, contentView1);
            return contentView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return contentDao.count();
    }

    @Override
    public List<ContentView> findAll() {
        List<ContentView> contentViews = new ArrayList<>();
        List<Content> contents = contentDao.findAll();
        for (Content content : contents){
            ContentView contentView = new ContentView();
            TorinoSrcBeanUtils.copyBean(content, contentView);
            contentViews.add(contentView);
        }
        return contentViews;
    }

    @Override
    public ContentView saveEntity(ContentView contentView) {
        // 保存的业务逻辑
        Content content = new Content();
        TorinoSrcBeanUtils.copyBean(contentView, content);
        // user数据库映射传给dao进行存储
        content.setCreateTime(System.currentTimeMillis());
        content.setUpdateTime(System.currentTimeMillis());
        contentDao.save(content);
        TorinoSrcBeanUtils.copyBean(content,contentView);
        return contentView;
    }

    @Override
    public void deleteEntity(long id) {
        Content content = new Content();
        content.setId(id);
        contentDao.delete(content);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<Content> contents = new ArrayList<>();
        for(String entityId : entityIds){
            Content content = new Content();
            content.setId(Long.valueOf(entityId));
            contents.add(content);
        }
        contentDao.deleteInBatch(contents);
    }

    @Override
    public void updateEntity(ContentView contentView) {
        Specification<Content> contentSpecification = Optional.ofNullable(contentView).map(s -> {
            return new Specification<Content>() {
                @Override
                public Predicate toPredicate(Root<Content> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("ContentView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<Content> contentOptionalBySearch = contentDao.findOne(contentSpecification);
        contentOptionalBySearch.map(contentBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(contentView,contentBySearch);
            contentBySearch.setUpdateTime(System.currentTimeMillis());
            contentDao.save(contentBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + contentView.getId() + "的数据记录"));
    }

    /*
     * @param ids 类别idset
     * @return 文章内容
     */
    @Override
    public List<Long> findContent(List<Long> contTypeIds){
        // 存放内容id的set
        Set<Long> contentIds=new HashSet<>();
        for(Long id:contTypeIds){
           Set<Long> newList=frecursionNode(id);
           contentIds.addAll(newList);
        }
        return  new ArrayList<Long>(contentIds);
    }

    /**
     * 类别id找出内容的id，然后放到一个数组
     * @param id
     */
    public Set<Long> frecursionNode(Long id){
        // 存放内容id的set
//        List<Long> contentId=new ArrayList<Long>();
        Set<Long> contentId=new HashSet<>();
        //获取类别
        ContentType contentType=contentTypeDao.getOne(id);

        //类别对应的内容id获取并存到内容idset
        List<ContentTypeContent> contentTypeContents=contentTypeContentDao.findByContenttypeId(contentType.getId());
        if(!ObjectUtils.isEmpty(contentTypeContents)){
            for(ContentTypeContent contentTypeContent:contentTypeContents){
                contentId.add(contentTypeContent.getContentId());
            }
        }
        //获取子类别
        Set<ContentType> childrens = contentType.getChildren();

        while(!childrens.isEmpty()){
            Set<ContentType> tempchildrens = new HashSet<ContentType>();
            for(ContentType contentType1:childrens){
                if(!ObjectUtils.isEmpty(contentTypeDao.getOne(contentType1.getId()).getChildren())){
                    childrens=contentTypeDao.getOne(contentType1.getId()).getChildren();
                    for(ContentType contentType2:childrens){
                        tempchildrens.add(contentType2);
                    }
                }
                //子类别对应的内容id获取也并存到内容idset
                contentTypeContents=contentTypeContentDao.findByContenttypeId(contentType1.getId());
                if(!ObjectUtils.isEmpty(contentTypeContents)){
                    for(ContentTypeContent contentTypeContent:contentTypeContents){
                        contentId.add(contentTypeContent.getContentId());
                    }
                }
            }
            childrens=tempchildrens;
        }
        return contentId;
    }


}
