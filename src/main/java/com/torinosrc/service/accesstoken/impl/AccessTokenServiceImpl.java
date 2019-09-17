/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.accesstoken.impl;

import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.HttpGetUtils;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.accesstoken.AccessTokenDao;
import com.torinosrc.model.entity.accesstoken.AccessToken;
import com.torinosrc.model.view.accesstoken.AccessTokenView;
import com.torinosrc.service.accesstoken.AccessTokenService;
import com.torinosrc.service.weixin.WechatService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * <b><code>AccessTokenImpl</code></b>
 * <p/>
 * AccessToken的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-08-09 19:42:30.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(AccessTokenServiceImpl.class);

    private final static String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    @Autowired
    private AccessTokenDao accessTokenDao;

    @Autowired
    private WechatService wechatService;

    @Value("${weixin.appId}")
    private String APP_ID;

    @Value("${weixin.secret}")
    private String SECRET;

    private final String ACCESS_TOKEN = "ACCESS_TOKEN";

    @Override
    public AccessTokenView getEntity(long id) {
        // 获取Entity
        AccessToken accessToken = accessTokenDao.getOne(id);
        // 复制Dao层属性到view属性
        AccessTokenView accessTokenView = new AccessTokenView();
        TorinoSrcBeanUtils.copyBean(accessToken, accessTokenView);
        return accessTokenView;
    }

    @Override
    public Page<AccessTokenView> getEntitiesByParms(AccessTokenView accessTokenView, int currentPage, int pageSize) {
        Specification<AccessToken> accessTokenSpecification = new Specification<AccessToken>() {
            @Override
            public Predicate toPredicate(Root<AccessToken> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root, criteriaQuery, criteriaBuilder, accessTokenView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<AccessToken> accessTokens = accessTokenDao.findAll(accessTokenSpecification, pageable);

        // 转换成View对象并返回
        return accessTokens.map(accessToken -> {
            AccessTokenView accessTokenView1 = new AccessTokenView();
            TorinoSrcBeanUtils.copyBean(accessToken, accessTokenView1);
            return accessTokenView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return accessTokenDao.count();
    }

    @Override
    public List<AccessTokenView> findAll() {
        List<AccessTokenView> accessTokenViews = new ArrayList<>();
        List<AccessToken> accessTokens = accessTokenDao.findAll();
        for (AccessToken accessToken : accessTokens) {
            AccessTokenView accessTokenView = new AccessTokenView();
            TorinoSrcBeanUtils.copyBean(accessToken, accessTokenView);
            accessTokenViews.add(accessTokenView);
        }
        return accessTokenViews;
    }

    @Override
    public AccessTokenView saveEntity(AccessTokenView accessTokenView) {
        // 保存的业务逻辑
        AccessToken accessToken = new AccessToken();
        TorinoSrcBeanUtils.copyBean(accessTokenView, accessToken);
        // user数据库映射传给dao进行存储
        accessToken.setCreateTime(System.currentTimeMillis());
        accessToken.setUpdateTime(System.currentTimeMillis());
        accessToken.setEnabled(1);
        accessTokenDao.save(accessToken);
        TorinoSrcBeanUtils.copyBean(accessToken, accessTokenView);
        return accessTokenView;
    }

    @Override
    public void deleteEntity(long id) {
        AccessToken accessToken = new AccessToken();
        accessToken.setId(id);
        accessTokenDao.delete(accessToken);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteEntities(String ids) {
        String[] entityIds = TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<AccessToken> accessTokens = new ArrayList<>();
        for (String entityId : entityIds) {
            AccessToken accessToken = new AccessToken();
            accessToken.setId(Long.valueOf(entityId));
            accessTokens.add(accessToken);
        }
        accessTokenDao.deleteInBatch(accessTokens);
    }

    @Override
    public void updateEntity(AccessTokenView accessTokenView) {
        Specification<AccessToken> accessTokenSpecification = Optional.ofNullable(accessTokenView).map(s -> {
            return new Specification<AccessToken>() {
                @Override
                public Predicate toPredicate(Root<AccessToken> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(() -> new IllegalArgumentException("AccessTokenView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<AccessToken> accessTokenOptionalBySearch = accessTokenDao.findOne(accessTokenSpecification);
        accessTokenOptionalBySearch.map(accessTokenBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(accessTokenView, accessTokenBySearch);
            accessTokenBySearch.setUpdateTime(System.currentTimeMillis());
            accessTokenDao.save(accessTokenBySearch);
            return "";
        }).orElseThrow(() -> new TorinoSrcServiceException("无法找到id为" + accessTokenView.getId() + "的数据记录"));
    }

    @Override
    public String getAccessToken() {

        AccessToken accessTokenEntity = accessTokenDao.findByName(ACCESS_TOKEN);

        String accessToken;
        if (ObjectUtils.isEmpty(accessTokenEntity)) {
            accessToken = this.createOrUpdateAccessToken();
        } else {
            if (accessTokenEntity.getStatus() == 0) {
                accessToken = this.updateAccessToken();
            } else {
                accessToken = accessTokenEntity.getValue();
            }
        }

        return accessToken;
    }

    /**
     * 向微信获取 access_token
     * @return
     */
    private String createAccessToken() {
        String getAccessTokenUrl = GET_ACCESSTOKEN_URL + "?grant_type=client_credential&appId=" + APP_ID + "&secret=" + SECRET;
        String accessTokenRes = HttpGetUtils.get(getAccessTokenUrl);
        JSONObject accessTokenJson = JSONObject.fromObject(accessTokenRes);

        Integer errcode = (Integer) accessTokenJson.get("errcode");
        if (!ObjectUtils.isEmpty(accessTokenJson.get("errcode")) && errcode != 0) {
            LOG.error("获取 access_token 时发生错误: \n" + accessTokenJson);
            throw new TorinoSrcServiceException("获取 access_token 时发生错误");
        }

        return accessTokenJson.getString("access_token");
    }

    /**
     * 向微信获取 access_token，并更新到数据库
     * @return
     */
    private String updateAccessToken() {
        String accessToken = this.createAccessToken();
        // 将 token 更新到数据库
        AccessToken accessTokenEntity = accessTokenDao.findByName(ACCESS_TOKEN);
        accessTokenEntity.setValue(accessToken);
        accessTokenEntity.setUpdateTime(System.currentTimeMillis());
        accessTokenEntity.setStatus(1);
        accessTokenDao.save(accessTokenEntity);
        return accessToken;
    }

    @Override
    public String createOrUpdateAccessToken() {
        String accessToken;
        AccessToken accessTokenEntityFromDB = accessTokenDao.findByName(ACCESS_TOKEN);
        if (ObjectUtils.isEmpty(accessTokenEntityFromDB)) {
            accessToken = this.createAccessToken();
            AccessTokenView accessTokenView = new AccessTokenView();
            accessTokenView.setStatus(1);
            accessTokenView.setName(ACCESS_TOKEN);
            accessTokenView.setValue(accessToken);
            this.saveEntity(accessTokenView);
        } else {
            accessToken = this.updateAccessToken();
        }

        return accessToken;
    }
}
