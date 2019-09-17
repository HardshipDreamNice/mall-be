/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.invitation.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.invitation.InvitationDao;
import com.torinosrc.model.entity.invitation.Invitation;
import com.torinosrc.model.view.invitation.InvitationView;
import com.torinosrc.service.invitation.InvitationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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

/**
* <b><code>InvitationImpl</code></b>
* <p/>
* Invitation的具体实现
* <p/>
* <b>Creation Time:</b> 2018-07-12 09:30:20.
*
* @author ${model.author}
* @version 2.0.0
* @since torinosrc-spring-boot-be 2.0.0
*/
@Service
public class InvitationServiceImpl implements InvitationService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(InvitationServiceImpl.class);

    @Autowired
    private InvitationDao invitationDao;

    @Override
    public InvitationView getEntity(long id) {
        // 获取Entity
        Invitation invitation = invitationDao.getOne(id);
        // 复制Dao层属性到view属性
        InvitationView invitationView = new InvitationView();
        TorinoSrcBeanUtils.copyBean(invitation, invitationView);
        return invitationView;
    }

    @Override
    public Page<InvitationView> getEntitiesByParms(InvitationView invitationView, int currentPage, int pageSize) {
        Specification<Invitation> invitationSpecification = new Specification<Invitation>() {
            @Override
            public Predicate toPredicate(Root<Invitation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,invitationView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Invitation> invitations = invitationDao.findAll(invitationSpecification, pageable);

        // 转换成View对象并返回
        return invitations.map(invitation->{
            InvitationView invitationView1 = new InvitationView();
            TorinoSrcBeanUtils.copyBean(invitation, invitationView1);
            return invitationView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return invitationDao.count();
    }

    @Override
    public List<InvitationView> findAll() {
        List<InvitationView> invitationViews = new ArrayList<>();
        List<Invitation> invitations = invitationDao.findAll();
        for (Invitation invitation : invitations){
            InvitationView invitationView = new InvitationView();
            TorinoSrcBeanUtils.copyBean(invitation, invitationView);
            invitationViews.add(invitationView);
        }
        return invitationViews;
    }

    @Override
    public InvitationView saveEntity(InvitationView invitationView) {
        //判断该用户是否存在邀请记录
        Invitation invitationBySearch = invitationDao.findByUserId(invitationView.getUserId());

        if(!ObjectUtils.isEmpty(invitationBySearch)){
            if(!ObjectUtils.isEmpty(invitationView.getShopId()) && invitationView.getShopId()!= invitationBySearch.getShopId()){
                invitationBySearch.setShopId(invitationView.getShopId());
                invitationBySearch.setUpdateTime(System.currentTimeMillis());
                invitationDao.save(invitationBySearch);
            }
            TorinoSrcBeanUtils.copyBean(invitationBySearch,invitationView);

        }else{
            // 保存的业务逻辑
            Invitation invitation = new Invitation();
            TorinoSrcBeanUtils.copyBean(invitationView, invitation);
            // user数据库映射传给dao进行存储
            invitation.setCreateTime(System.currentTimeMillis());
            invitation.setUpdateTime(System.currentTimeMillis());
            invitation.setEnabled(1);
            invitationDao.save(invitation);
            TorinoSrcBeanUtils.copyBean(invitation,invitationView);
        }

        return invitationView;
    }

    @Override
    public void deleteEntity(long id) {
        Invitation invitation = new Invitation();
        invitation.setId(id);
        invitationDao.delete(invitation);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<Invitation> invitations = new ArrayList<>();
        for(String entityId : entityIds){
            Invitation invitation = new Invitation();
            invitation.setId(Long.valueOf(entityId));
            invitations.add(invitation);
        }
        invitationDao.deleteInBatch(invitations);
    }

    @Override
    public void updateEntity(InvitationView invitationView) {
        Specification<Invitation> invitationSpecification = Optional.ofNullable(invitationView).map( s -> {
            return new Specification<Invitation>() {
                @Override
                public Predicate toPredicate(Root<Invitation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("InvitationView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<Invitation> invitationOptionalBySearch = invitationDao.findOne(invitationSpecification);
        invitationOptionalBySearch.map(invitationBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(invitationView,invitationBySearch);
            invitationBySearch.setUpdateTime(System.currentTimeMillis());
            invitationDao.save(invitationBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + invitationView.getId() + "的数据记录"));
    }

}
