/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.membershipgrade.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.membershipgrade.MembershipGradeDao;
import com.torinosrc.model.entity.membershipgrade.MembershipGrade;
import com.torinosrc.model.view.membershipgrade.MembershipGradeView;
import com.torinosrc.service.membershipgrade.MembershipGradeService;
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
* <b><code>MembershipGradeImpl</code></b>
* <p/>
* MembershipGrade的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-28 18:42:13.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class MembershipGradeServiceImpl implements MembershipGradeService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(MembershipGradeServiceImpl.class);

    @Autowired
    private MembershipGradeDao membershipGradeDao;

    @Override
    public MembershipGradeView getEntity(long id) {
        // 获取Entity
        MembershipGrade membershipGrade = membershipGradeDao.getOne(id);
        // 复制Dao层属性到view属性
        MembershipGradeView membershipGradeView = new MembershipGradeView();
        TorinoSrcBeanUtils.copyBean(membershipGrade, membershipGradeView);
        return membershipGradeView;
    }

    @Override
    public Page<MembershipGradeView> getEntitiesByParms(MembershipGradeView membershipGradeView, int currentPage, int pageSize) {
        Specification<MembershipGrade> membershipGradeSpecification = new Specification<MembershipGrade>() {
            @Override
            public Predicate toPredicate(Root<MembershipGrade> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,membershipGradeView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<MembershipGrade> membershipGrades = membershipGradeDao.findAll(membershipGradeSpecification, pageable);

        // 转换成View对象并返回
        return membershipGrades.map(membershipGrade->{
            MembershipGradeView membershipGradeView1 = new MembershipGradeView();
            TorinoSrcBeanUtils.copyBean(membershipGrade, membershipGradeView1);
            return membershipGradeView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return membershipGradeDao.count();
    }

    @Override
    public List<MembershipGradeView> findAll() {
        List<MembershipGradeView> membershipGradeViews = new ArrayList<>();
        List<MembershipGrade> membershipGrades = membershipGradeDao.findAll();
        for (MembershipGrade membershipGrade : membershipGrades){
            MembershipGradeView membershipGradeView = new MembershipGradeView();
            TorinoSrcBeanUtils.copyBean(membershipGrade, membershipGradeView);
            membershipGradeViews.add(membershipGradeView);
        }
        return membershipGradeViews;
    }

    @Override
    public MembershipGradeView saveEntity(MembershipGradeView membershipGradeView) {
        // 保存的业务逻辑
        MembershipGrade membershipGrade = new MembershipGrade();
        TorinoSrcBeanUtils.copyBean(membershipGradeView, membershipGrade);
        // user数据库映射传给dao进行存储
        membershipGrade.setCreateTime(System.currentTimeMillis());
        membershipGrade.setUpdateTime(System.currentTimeMillis());
        membershipGrade.setEnabled(1);
        membershipGradeDao.save(membershipGrade);
        TorinoSrcBeanUtils.copyBean(membershipGrade,membershipGradeView);
        return membershipGradeView;
    }

    @Override
    public void deleteEntity(long id) {
        MembershipGrade membershipGrade = new MembershipGrade();
        membershipGrade.setId(id);
        membershipGradeDao.delete(membershipGrade);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<MembershipGrade> membershipGrades = new ArrayList<>();
        for(String entityId : entityIds){
            MembershipGrade membershipGrade = new MembershipGrade();
            membershipGrade.setId(Long.valueOf(entityId));
            membershipGrades.add(membershipGrade);
        }
        membershipGradeDao.deleteInBatch(membershipGrades);
    }

    @Override
    public void updateEntity(MembershipGradeView membershipGradeView) {
        Specification<MembershipGrade> membershipGradeSpecification = Optional.ofNullable(membershipGradeView).map( s -> {
            return new Specification<MembershipGrade>() {
                @Override
                public Predicate toPredicate(Root<MembershipGrade> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("MembershipGradeView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<MembershipGrade> membershipGradeOptionalBySearch = membershipGradeDao.findOne(membershipGradeSpecification);
        membershipGradeOptionalBySearch.map(membershipGradeBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(membershipGradeView,membershipGradeBySearch);
            membershipGradeBySearch.setUpdateTime(System.currentTimeMillis());
            membershipGradeDao.save(membershipGradeBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + membershipGradeView.getId() + "的数据记录"));
    }

}
