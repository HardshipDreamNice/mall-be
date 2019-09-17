/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.redenvelope.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.redenvelope.RedEnvelopeDao;
import com.torinosrc.model.entity.redenvelope.RedEnvelope;
import com.torinosrc.model.view.redenvelope.RedEnvelopeView;
import com.torinosrc.service.redenvelope.RedEnvelopeService;
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
* <b><code>RedEnvelopeImpl</code></b>
* <p/>
* RedEnvelope的具体实现
* <p/>
* <b>Creation Time:</b> 2018-12-04 12:09:47.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class RedEnvelopeServiceImpl implements RedEnvelopeService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(RedEnvelopeServiceImpl.class);

    @Autowired
    private RedEnvelopeDao redEnvelopeDao;

    @Override
    public RedEnvelopeView getEntity(long id) {
        // 获取Entity
        RedEnvelope redEnvelope = redEnvelopeDao.getOne(id);
        // 复制Dao层属性到view属性
        RedEnvelopeView redEnvelopeView = new RedEnvelopeView();
        TorinoSrcBeanUtils.copyBean(redEnvelope, redEnvelopeView);
        return redEnvelopeView;
    }

    @Override
    public Page<RedEnvelopeView> getEntitiesByParms(RedEnvelopeView redEnvelopeView, int currentPage, int pageSize) {
        Specification<RedEnvelope> redEnvelopeSpecification = new Specification<RedEnvelope>() {
            @Override
            public Predicate toPredicate(Root<RedEnvelope> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,redEnvelopeView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<RedEnvelope> redEnvelopes = redEnvelopeDao.findAll(redEnvelopeSpecification, pageable);

        // 转换成View对象并返回
        return redEnvelopes.map(redEnvelope->{
            RedEnvelopeView redEnvelopeView1 = new RedEnvelopeView();
            TorinoSrcBeanUtils.copyBean(redEnvelope, redEnvelopeView1);
            return redEnvelopeView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return redEnvelopeDao.count();
    }

    @Override
    public List<RedEnvelopeView> findAll() {
        List<RedEnvelopeView> redEnvelopeViews = new ArrayList<>();
        List<RedEnvelope> redEnvelopes = redEnvelopeDao.findAll();
        for (RedEnvelope redEnvelope : redEnvelopes){
            RedEnvelopeView redEnvelopeView = new RedEnvelopeView();
            TorinoSrcBeanUtils.copyBean(redEnvelope, redEnvelopeView);
            redEnvelopeViews.add(redEnvelopeView);
        }
        return redEnvelopeViews;
    }

    @Override
    public RedEnvelopeView saveEntity(RedEnvelopeView redEnvelopeView) {
        // 保存的业务逻辑
        RedEnvelope redEnvelope = new RedEnvelope();
        TorinoSrcBeanUtils.copyBean(redEnvelopeView, redEnvelope);
        // user数据库映射传给dao进行存储
        redEnvelope.setCreateTime(System.currentTimeMillis());
        redEnvelope.setUpdateTime(System.currentTimeMillis());
        redEnvelope.setEnabled(1);
        redEnvelopeDao.save(redEnvelope);
        TorinoSrcBeanUtils.copyBean(redEnvelope,redEnvelopeView);
        return redEnvelopeView;
    }

    @Override
    public void deleteEntity(long id) {
        RedEnvelope redEnvelope = new RedEnvelope();
        redEnvelope.setId(id);
        redEnvelopeDao.delete(redEnvelope);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<RedEnvelope> redEnvelopes = new ArrayList<>();
        for(String entityId : entityIds){
            RedEnvelope redEnvelope = new RedEnvelope();
            redEnvelope.setId(Long.valueOf(entityId));
            redEnvelopes.add(redEnvelope);
        }
        redEnvelopeDao.deleteInBatch(redEnvelopes);
    }

    @Override
    public void updateEntity(RedEnvelopeView redEnvelopeView) {
        Specification<RedEnvelope> redEnvelopeSpecification = Optional.ofNullable(redEnvelopeView).map( s -> {
            return new Specification<RedEnvelope>() {
                @Override
                public Predicate toPredicate(Root<RedEnvelope> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("RedEnvelopeView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<RedEnvelope> redEnvelopeOptionalBySearch = redEnvelopeDao.findOne(redEnvelopeSpecification);
        redEnvelopeOptionalBySearch.map(redEnvelopeBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(redEnvelopeView,redEnvelopeBySearch);
            redEnvelopeBySearch.setUpdateTime(System.currentTimeMillis());
            redEnvelopeDao.save(redEnvelopeBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + redEnvelopeView.getId() + "的数据记录"));
    }
}
