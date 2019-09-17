/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.feedback.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.feedback.FeedBackDao;
import com.torinosrc.model.entity.feedback.FeedBack;
import com.torinosrc.model.view.feedback.FeedBackView;
import com.torinosrc.service.feedback.FeedBackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
* <b><code>FeedBackImpl</code></b>
* <p/>
* FeedBack的具体实现
* <p/>
* <b>Creation Time:</b> 2018-08-15 17:58:37.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class FeedBackServiceImpl implements FeedBackService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(FeedBackServiceImpl.class);

    @Autowired
    private FeedBackDao feedBackDao;

    @Override
    public FeedBackView getEntity(long id) {
        // 获取Entity
        FeedBack feedBack = feedBackDao.getOne(id);
        // 复制Dao层属性到view属性
        FeedBackView feedBackView = new FeedBackView();
        TorinoSrcBeanUtils.copyBean(feedBack, feedBackView);
        return feedBackView;
    }

    @Override
    public Page<FeedBackView> getEntitiesByParms(FeedBackView feedBackView, int currentPage, int pageSize) {
        Specification<FeedBack> feedBackSpecification = new Specification<FeedBack>() {
            @Override
            public Predicate toPredicate(Root<FeedBack> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,feedBackView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<FeedBack> feedBacks = feedBackDao.findAll(feedBackSpecification, pageable);

        // 转换成View对象并返回
        return feedBacks.map(feedBack->{
            FeedBackView feedBackView1 = new FeedBackView();
            TorinoSrcBeanUtils.copyBean(feedBack, feedBackView1);
            return feedBackView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return feedBackDao.count();
    }

    @Override
    public List<FeedBackView> findAll() {
        List<FeedBackView> feedBackViews = new ArrayList<>();
        List<FeedBack> feedBacks = feedBackDao.findAll();
        for (FeedBack feedBack : feedBacks){
            FeedBackView feedBackView = new FeedBackView();
            TorinoSrcBeanUtils.copyBean(feedBack, feedBackView);
            feedBackViews.add(feedBackView);
        }
        return feedBackViews;
    }

    @Override
    public FeedBackView saveEntity(FeedBackView feedBackView) {
        // 保存的业务逻辑
        FeedBack feedBack = new FeedBack();
        TorinoSrcBeanUtils.copyBean(feedBackView, feedBack);
        // user数据库映射传给dao进行存储
        feedBack.setCreateTime(System.currentTimeMillis());
        feedBack.setUpdateTime(System.currentTimeMillis());
//        feedBack.setEnabled(1);
        feedBackDao.save(feedBack);
        TorinoSrcBeanUtils.copyBean(feedBack,feedBackView);
        return feedBackView;
    }

    @Override
    public void deleteEntity(long id) {
        FeedBack feedBack = new FeedBack();
        feedBack.setId(id);
        feedBackDao.delete(feedBack);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<FeedBack> feedBacks = new ArrayList<>();
        for(String entityId : entityIds){
            FeedBack feedBack = new FeedBack();
            feedBack.setId(Long.valueOf(entityId));
            feedBacks.add(feedBack);
        }
        feedBackDao.deleteInBatch(feedBacks);
    }

    @Override
    public void updateEntity(FeedBackView feedBackView) {
        Specification<FeedBack> feedBackSpecification = Optional.ofNullable(feedBackView).map( s -> {
            return new Specification<FeedBack>() {
                @Override
                public Predicate toPredicate(Root<FeedBack> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("FeedBackView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<FeedBack> feedBackOptionalBySearch = feedBackDao.findOne(feedBackSpecification);
        feedBackOptionalBySearch.map(feedBackBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(feedBackView,feedBackBySearch);
            feedBackBySearch.setUpdateTime(System.currentTimeMillis());
            feedBackDao.save(feedBackBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + feedBackView.getId() + "的数据记录"));
    }
}
