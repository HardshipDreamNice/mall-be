package com.torinosrc.service.weixin.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.weixin.WxRefundDao;
import com.torinosrc.model.entity.weixin.WxRefund;
import com.torinosrc.model.view.weixin.WxRefundView;
import com.torinosrc.service.weixin.WxRefundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

@Service
public class WxRefundServiceImpl implements WxRefundService {
    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(WxRefundServiceImpl.class);

    @Autowired
    private WxRefundDao wxRefundDao;

    @Override
    public WxRefundView getEntity(long id) {
        // 获取Entity
        WxRefund wxRefund = wxRefundDao.getOne(id);
        // 复制Dao层属性到view属性
        WxRefundView wxRefundView = new WxRefundView();
        TorinoSrcBeanUtils.copyBean(wxRefund, wxRefundView);
        return wxRefundView;
    }

    @Override
    public Page<WxRefundView> getEntitiesByParms(WxRefundView wxRefundView, int currentPage, int pageSize) {
        Specification<WxRefund> wxRefundSpecification = new Specification<WxRefund>() {
            @Override
            public Predicate toPredicate(Root<WxRefund> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,wxRefundView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<WxRefund> wxRefunds = wxRefundDao.findAll(wxRefundSpecification, pageable);

        // 转换成View对象并返回
        return wxRefunds.map(wxRefund->{
            WxRefundView wxRefundView1 = new WxRefundView();
            TorinoSrcBeanUtils.copyBean(wxRefund, wxRefundView1);
            return wxRefundView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return wxRefundDao.count();
    }

    @Override
    public List<WxRefundView> findAll() {
        List<WxRefundView> wxRefundViews = new ArrayList<>();
        List<WxRefund> wxRefunds = wxRefundDao.findAll();
        for (WxRefund wxRefund : wxRefunds){
            WxRefundView wxRefundView = new WxRefundView();
            TorinoSrcBeanUtils.copyBean(wxRefund, wxRefundView);
            wxRefundViews.add(wxRefundView);
        }
        return wxRefundViews;
    }

    @Override
    public WxRefundView saveEntity(WxRefundView wxRefundView) {
        // 保存的业务逻辑
        WxRefund wxRefund = new WxRefund();
        TorinoSrcBeanUtils.copyBean(wxRefundView, wxRefund);
        // wxRefund数据库映射传给dao进行存储
        wxRefund.setCreateTime(System.currentTimeMillis());
        wxRefund.setUpdateTime(System.currentTimeMillis());
        wxRefundDao.save(wxRefund);
        TorinoSrcBeanUtils.copyBean(wxRefund,wxRefundView);
        return wxRefundView;
    }

    @Override
    public void deleteEntity(long id) {
        WxRefund wxRefund = new WxRefund();
        wxRefund.setId(id);
        wxRefundDao.delete(wxRefund);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<WxRefund> wxRefunds = new ArrayList<>();
        for(String entityId : entityIds){
            WxRefund wxRefund = new WxRefund();
            wxRefund.setId(Long.valueOf(entityId));
            wxRefunds.add(wxRefund);
        }
        wxRefundDao.deleteInBatch(wxRefunds);
    }

    @Override
    public void updateEntity(WxRefundView wxRefundView) {
        Specification<WxRefund> wxRefundSpecification = Optional.ofNullable(wxRefundView).map(s -> {
            return new Specification<WxRefund>() {
                @Override
                public Predicate toPredicate(Root<WxRefund> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("WxRefundView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<WxRefund> wxRefundOptionalBySearch = wxRefundDao.findOne(wxRefundSpecification);
        wxRefundOptionalBySearch.map(wxRefundBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(wxRefundView,wxRefundBySearch);
            wxRefundBySearch.setUpdateTime(System.currentTimeMillis());
            wxRefundDao.save(wxRefundBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + wxRefundView.getId() + "的数据记录"));
    }

    @Override
    public WxRefundView findByTransactionId(String transactionId) {
        // 获取Entity
        WxRefund wxRefund = wxRefundDao.findByTransactionId(transactionId);
        // 复制Dao层属性到view属性
        WxRefundView wxRefundView = new WxRefundView();
        TorinoSrcBeanUtils.copyBean(wxRefund, wxRefundView);
        return wxRefundView;
    }
}
