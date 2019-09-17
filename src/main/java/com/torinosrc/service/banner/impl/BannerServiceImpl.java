/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.banner.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.banner.BannerDao;
import com.torinosrc.model.entity.banner.Banner;
import com.torinosrc.model.view.banner.BannerView;
import com.torinosrc.service.banner.BannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <b><code>BannerImpl</code></b>
 * <p/>
 * Banner的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-05 10:29:40.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public class BannerServiceImpl implements BannerService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(BannerServiceImpl.class);

    @Autowired
    private BannerDao bannerDao;

    @Override
    public BannerView getEntity(long id) {
        // 获取Entity
        Banner banner = bannerDao.getOne(id);
        // 复制Dao层属性到view属性
        BannerView bannerView = new BannerView();
        TorinoSrcBeanUtils.copyBean(banner, bannerView);
        return bannerView;
    }

    @Override
    public Page<BannerView> getEntitiesByParms(BannerView bannerView, int currentPage, int pageSize) {
        Specification<Banner> bannerSpecification = new Specification<Banner>() {
            @Override
            public Predicate toPredicate(Root<Banner> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if(!ObjectUtils.isEmpty(bannerView.getEnabled())){
                    predicates.add(criteriaBuilder.equal(root.get("enabled").as(Integer.class), bannerView.getEnabled()));
                }

                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };


        int sortType = bannerView.getSortType();
        Sort sort;
        switch (sortType) {
            case 1:
                sort = new Sort(Sort.Direction.ASC, "weight");
                break;
            case 2:
                sort = new Sort(Sort.Direction.DESC, "weight");
                break;
            default:
                sort = new Sort(Sort.Direction.DESC, "weight");
                break;
        }
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Banner> banners = bannerDao.findAll(bannerSpecification, pageable);

        // 转换成View对象并返回
        return banners.map(banner->{
            BannerView bannerView1 = new BannerView();
            TorinoSrcBeanUtils.copyBean(banner, bannerView1);
            return bannerView1;
        });

    }

    @Override
    public long getEntitiesCount() {
        return bannerDao.count();
    }

    @Override
    public List<BannerView> findAll() {
        List<BannerView> bannerViews = new ArrayList<>();
        List<Banner> banners = bannerDao.findAll();
        for (Banner banner : banners){
            BannerView bannerView = new BannerView();
            TorinoSrcBeanUtils.copyBean(banner, bannerView);
            bannerViews.add(bannerView);
        }
        return bannerViews;
    }

    @Override
    public BannerView saveEntity(BannerView bannerView) {
        // 保存的业务逻辑
        Banner banner = new Banner();
        TorinoSrcBeanUtils.copyBean(bannerView, banner);
        // user数据库映射传给dao进行存储
        banner.setCreateTime(System.currentTimeMillis());
        banner.setUpdateTime(System.currentTimeMillis());
        banner.setEnabled(1);
        bannerDao.save(banner);
        TorinoSrcBeanUtils.copyBean(banner,bannerView);
        return bannerView;
    }

    @Override
    public void deleteEntity(long id) {
        Banner banner = new Banner();
        banner.setId(id);
        bannerDao.delete(banner);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<Banner> banners = new ArrayList<>();
        for(String entityId : entityIds){
            Banner banner = new Banner();
            banner.setId(Long.valueOf(entityId));
            banners.add(banner);
        }
        bannerDao.deleteInBatch(banners);
    }

    @Override
    public void updateEntity(BannerView bannerView) {
        Specification<Banner> bannerSpecification = Optional.ofNullable(bannerView).map( s -> {
            return new Specification<Banner>() {
                @Override
                public Predicate toPredicate(Root<Banner> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("BannerView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<Banner> bannerOptionalBySearch = bannerDao.findOne(bannerSpecification);
        bannerOptionalBySearch.map(bannerBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(bannerView,bannerBySearch);
            bannerBySearch.setUpdateTime(System.currentTimeMillis());
            bannerDao.save(bannerBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + bannerView.getId() + "的数据记录"));
    }

}
