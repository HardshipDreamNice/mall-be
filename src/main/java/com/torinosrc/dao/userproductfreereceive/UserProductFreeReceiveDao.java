/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.userproductfreereceive;

import com.torinosrc.model.entity.user.User;
import com.torinosrc.model.entity.userproductfreereceive.UserProductFreeReceive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

/**
 * <b><code>UserProductFreeReceiveDao</code></b>
 * <p/>
 * UserProductFreeReceive的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-11-27 19:45:53.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface UserProductFreeReceiveDao extends JpaRepository<UserProductFreeReceive, Long>, JpaSpecificationExecutor<UserProductFreeReceive> {

           /**
          * 根据免费领的user的id查询免费领中间表是否有这一行记录
         * @param productFreeReceiveId
          * @return
          */
           @Query(value = "select * from t_user_product_free_receive t where t.share_user_id=?1 and t.product_free_receive_id=?2",nativeQuery = true)
            UserProductFreeReceive findOneByShareUserIdAndProductFreeReceiveId(Long shareUserId,Long productFreeReceiveId);



}
