/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.service.userproductfreereceive.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializableSerializer;
import com.torinosrc.commons.exceptions.TorinoSrcServiceException;
import com.torinosrc.commons.utils.TorinoSrcBeanUtils;
import com.torinosrc.commons.utils.TorinoSrcCommonUtils;
import com.torinosrc.commons.utils.TorinoSrcConditionUtils;
import com.torinosrc.dao.productfreereceive.ProductFreeReceiveDao;
import com.torinosrc.dao.productfreereceivedetail.ProductFreeReceiveDetailDao;
import com.torinosrc.dao.userproductfreereceive.UserProductFreeReceiveDao;
import com.torinosrc.model.entity.productfreereceive.ProductFreeReceive;
import com.torinosrc.model.entity.productfreereceivedetail.ProductFreeReceiveDetail;
import com.torinosrc.model.entity.userproductfreereceive.UserProductFreeReceive;
import com.torinosrc.model.view.productfreereceive.ProductFreeReceiveView;
import com.torinosrc.model.view.userproductfreereceive.UserProductFreeReceiveView;
import com.torinosrc.service.userproductfreereceive.UserProductFreeReceiveService;
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
* <b><code>UserProductFreeReceiveImpl</code></b>
* <p/>
* UserProductFreeReceive的具体实现
* <p/>
* <b>Creation Time:</b> 2018-11-27 19:45:53.
*
* @author ${model.author}
* @version 1.0.0
* @since torinosrc-spring-boot-be 1.0.0
*/
@Service
public class UserProductFreeReceiveServiceImpl implements UserProductFreeReceiveService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(UserProductFreeReceiveServiceImpl.class);

    @Autowired
    private UserProductFreeReceiveDao userProductFreeReceiveDao;

    @Autowired
    private ProductFreeReceiveDao productFreeReceiveDao;

    @Autowired
    private ProductFreeReceiveDetailDao productFreeReceiveDetailDao;

    @Override
    public UserProductFreeReceiveView getEntity(long id) {
        // 获取Entity
        UserProductFreeReceive userProductFreeReceive = userProductFreeReceiveDao.getOne(id);
        // 复制Dao层属性到view属性
        UserProductFreeReceiveView userProductFreeReceiveView = new UserProductFreeReceiveView();
        TorinoSrcBeanUtils.copyBean(userProductFreeReceive, userProductFreeReceiveView);
        return userProductFreeReceiveView;
    }

    @Override
    public Page<UserProductFreeReceiveView> getEntitiesByParms(UserProductFreeReceiveView userProductFreeReceiveView, int currentPage, int pageSize) {
        Specification<UserProductFreeReceive> userProductFreeReceiveSpecification = new Specification<UserProductFreeReceive>() {
            @Override
            public Predicate toPredicate(Root<UserProductFreeReceive> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return TorinoSrcConditionUtils.getPredicate(root,criteriaQuery,criteriaBuilder,userProductFreeReceiveView.getCondition());
            }
        };
        // 设置分页
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<UserProductFreeReceive> userProductFreeReceives = userProductFreeReceiveDao.findAll(userProductFreeReceiveSpecification, pageable);

        // 转换成View对象并返回
        return userProductFreeReceives.map(userProductFreeReceive->{
            UserProductFreeReceiveView userProductFreeReceiveView1 = new UserProductFreeReceiveView();
            TorinoSrcBeanUtils.copyBean(userProductFreeReceive, userProductFreeReceiveView1);
            return userProductFreeReceiveView1;
        });
    }

    @Override
    public long getEntitiesCount() {
        return userProductFreeReceiveDao.count();
    }

    @Override
    public List<UserProductFreeReceiveView> findAll() {
        List<UserProductFreeReceiveView> userProductFreeReceiveViews = new ArrayList<>();
        List<UserProductFreeReceive> userProductFreeReceives = userProductFreeReceiveDao.findAll();
        for (UserProductFreeReceive userProductFreeReceive : userProductFreeReceives){
            UserProductFreeReceiveView userProductFreeReceiveView = new UserProductFreeReceiveView();
            TorinoSrcBeanUtils.copyBean(userProductFreeReceive, userProductFreeReceiveView);
            userProductFreeReceiveViews.add(userProductFreeReceiveView);
        }
        return userProductFreeReceiveViews;
    }

    @Override
    public UserProductFreeReceiveView saveEntity(UserProductFreeReceiveView userProductFreeReceiveView) {
        // 保存的业务逻辑
        UserProductFreeReceive userProductFreeReceive = new UserProductFreeReceive();
        TorinoSrcBeanUtils.copyBean(userProductFreeReceiveView, userProductFreeReceive);
        // user数据库映射传给dao进行存储
        userProductFreeReceive.setCreateTime(System.currentTimeMillis());
        userProductFreeReceive.setUpdateTime(System.currentTimeMillis());
        userProductFreeReceive.setEnabled(1);
        userProductFreeReceiveDao.save(userProductFreeReceive);
        TorinoSrcBeanUtils.copyBean(userProductFreeReceive,userProductFreeReceiveView);
        return userProductFreeReceiveView;
    }

    @Override
    public void deleteEntity(long id) {
        UserProductFreeReceive userProductFreeReceive = new UserProductFreeReceive();
        userProductFreeReceive.setId(id);
        userProductFreeReceiveDao.delete(userProductFreeReceive);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= TorinoSrcCommonUtils.splitString(ids,
                TorinoSrcCommonUtils.COMMA);
        List<UserProductFreeReceive> userProductFreeReceives = new ArrayList<>();
        for(String entityId : entityIds){
            UserProductFreeReceive userProductFreeReceive = new UserProductFreeReceive();
            userProductFreeReceive.setId(Long.valueOf(entityId));
            userProductFreeReceives.add(userProductFreeReceive);
        }
        userProductFreeReceiveDao.deleteInBatch(userProductFreeReceives);
    }

    @Override
    public void updateEntity(UserProductFreeReceiveView userProductFreeReceiveView) {
        Specification<UserProductFreeReceive> userProductFreeReceiveSpecification = Optional.ofNullable(userProductFreeReceiveView).map( s -> {
            return new Specification<UserProductFreeReceive>() {
                @Override
                public Predicate toPredicate(Root<UserProductFreeReceive> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    // id
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), s.getId()));

                    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                    return criteriaQuery.getRestriction();
                }
            };
        }).orElseThrow(()->new IllegalArgumentException("UserProductFreeReceiveView is null"));


        // 获取原有的属性，把不变的属性覆盖
        Optional<UserProductFreeReceive> userProductFreeReceiveOptionalBySearch = userProductFreeReceiveDao.findOne(userProductFreeReceiveSpecification);
        userProductFreeReceiveOptionalBySearch.map(userProductFreeReceiveBySearch -> {
            TorinoSrcBeanUtils.copyBeanExcludeNull(userProductFreeReceiveView,userProductFreeReceiveBySearch);
            userProductFreeReceiveBySearch.setUpdateTime(System.currentTimeMillis());
            userProductFreeReceiveDao.save(userProductFreeReceiveBySearch);
            return "";
        }).orElseThrow(()->new TorinoSrcServiceException("无法找到id为" + userProductFreeReceiveView.getId() + "的数据记录"));
    }

    //用户点击分享免费领接口
    @Override
    public UserProductFreeReceiveView insertUserProductFreeReceiveEntity(UserProductFreeReceiveView userProductFreeReceiveView){

//             System.out.println("分享者id:"+userProductFreeReceiveView.getShareUserId()+"商品id"+userProductFreeReceiveView.getProductFreeReceiveId());
//              //这个view初始传入属性有免费领商品id，share用户的id
              //逻辑检验，在免费领表中有这个商品，在用户表中有这个用户
             ProductFreeReceive productFreeReceive=productFreeReceiveDao.getOne(userProductFreeReceiveView.getProductFreeReceiveId());
             ProductFreeReceiveDetail productFreeReceiveDetail=productFreeReceiveDetailDao.getOne(userProductFreeReceiveView.getProductFreeReceiveDetailId());
            if(ObjectUtils.isEmpty(productFreeReceive)){
                throw new TorinoSrcServiceException("没有该商品，无法免费领");
            }
             if (productFreeReceiveDetail.getInventory()<=0){
                 throw new TorinoSrcServiceException("没有库存，无法免费领");
             }

//            System.out.println("查询到的商品信息 "+productFreeReceive.getTitle());
              UserProductFreeReceive userProductFreeReceive=userProductFreeReceiveDao.findOneByShareUserIdAndProductFreeReceiveId(userProductFreeReceiveView.getShareUserId(),userProductFreeReceiveView.getProductFreeReceiveId());


          //系统当前时间，和免费领商品表中的过期时间的比较
                 Long currentTime=System.currentTimeMillis();
   //            System.out.println(com.alibaba.fastjson.JSON.toJSONString(productFreeReceive));
                 Long expiredTime=productFreeReceive.getExpiredTime();
           if (currentTime.longValue()>expiredTime.longValue()) {
                throw new TorinoSrcServiceException("已过期，无法免费领");
            }
           //有这条记录的话
           else if (!ObjectUtils.isEmpty(userProductFreeReceive)&&ObjectUtils.isEmpty(userProductFreeReceive.getHelpUserId())){
               TorinoSrcBeanUtils.copyBean(userProductFreeReceive, userProductFreeReceiveView);
               return userProductFreeReceiveView;
           }
           else if(!ObjectUtils.isEmpty(userProductFreeReceive)&&!ObjectUtils.isEmpty(userProductFreeReceive.getHelpUserId())){
                throw new TorinoSrcServiceException("已领过，无法免费领");
            }
            // 保存的业务逻辑
            UserProductFreeReceive userProductFreeReceive1 = new UserProductFreeReceive();
            //将左view内容复制给右view
            TorinoSrcBeanUtils.copyBean(userProductFreeReceiveView, userProductFreeReceive1);
            // user数据库映射传给dao进行存储
            userProductFreeReceive1.setCreateTime(System.currentTimeMillis());
            userProductFreeReceive1.setUpdateTime(System.currentTimeMillis());
            userProductFreeReceive1.setEnabled(1);
            userProductFreeReceive1.setStatus(0);
            userProductFreeReceiveDao.save(userProductFreeReceive1);
            TorinoSrcBeanUtils.copyBean(userProductFreeReceive1, userProductFreeReceiveView);
            return userProductFreeReceiveView;
    }

    // 其他人帮忙免费领接口
    @Override
    public void otherHelpProductFreeReceivesUpdate(UserProductFreeReceiveView userProductFreeReceiveView) {

        if(userProductFreeReceiveView.getShareUserId()==userProductFreeReceiveView.getHelpUserId()){
            throw new TorinoSrcServiceException("自己无法免费领");
        }
         ProductFreeReceive productFreeReceive=productFreeReceiveDao.getOne(userProductFreeReceiveView.getProductFreeReceiveId());

//        System.out.println(JSON.toJSONString(productFreeReceive.getProductFreeReceiveDetailList()));

        ProductFreeReceiveDetail productFreeReceiveDetail=productFreeReceiveDetailDao.getOne(userProductFreeReceiveView.getProductFreeReceiveDetailId());

        if (productFreeReceiveDetail.getInventory()<=0){
            throw new TorinoSrcServiceException("商品没有库存");
        }
              //从后台获得中间表的数据
         UserProductFreeReceive userProductFreeReceive=userProductFreeReceiveDao.findOneByShareUserIdAndProductFreeReceiveId(userProductFreeReceiveView.getShareUserId(),userProductFreeReceiveView.getProductFreeReceiveId());
//         System.out.println("查询到的用户信息 "+userProductFreeReceive.getShareUserId());
         UserProductFreeReceive userProductFreeReceive1 = userProductFreeReceiveDao.findOneByShareUserIdAndProductFreeReceiveId(userProductFreeReceiveView.getHelpUserId(),userProductFreeReceiveView.getProductFreeReceiveId());
//        System.out.println("查询到的用户信息 "+userProductFreeReceive1.getShareUserId());

         Long currentTime = System.currentTimeMillis();
         Long expiredTime = productFreeReceive.getExpiredTime();


         if (currentTime.longValue() > expiredTime.longValue()) {
            throw new TorinoSrcServiceException("已过期，无法免费领");
         }
         else if (ObjectUtils.isEmpty(userProductFreeReceive)){
            throw new TorinoSrcServiceException("无法帮助免费领");
         }
         else if (!ObjectUtils.isEmpty(userProductFreeReceive.getHelpUserId())){
            throw new TorinoSrcServiceException("已领过，无法免费领");
        }
         else if(!ObjectUtils.isEmpty(userProductFreeReceive1)){
            if (userProductFreeReceive.getProductFreeReceiveId()==userProductFreeReceive1.getProductFreeReceiveId()) {
                  if(userProductFreeReceive1.getStatus()==1) {
                      if (userProductFreeReceiveView.getHelpUserId() == userProductFreeReceive1.getShareUserId()) {
                          throw new TorinoSrcServiceException("无法相互领");
                      }
                  }
            }
         }

        userProductFreeReceive.setHelpUserId(userProductFreeReceiveView.getHelpUserId());
        userProductFreeReceive.setStatus(1);
        userProductFreeReceive.setUpdateTime(productFreeReceive.getUpdateTime());
        userProductFreeReceive.setProductFreeReceiveDetailId(userProductFreeReceiveView.getProductFreeReceiveDetailId());
        userProductFreeReceiveDao.save(userProductFreeReceive);
        }
}
