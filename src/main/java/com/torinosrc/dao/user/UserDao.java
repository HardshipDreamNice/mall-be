/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.dao.user;

import com.torinosrc.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <b><code>UserDao</code></b>
 * <p/>
 * User的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-04-16 14:17:14.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@Repository
public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    public User findByOpenId(String openId);

    @Query(value = "select p.avatar  from t_user p where p.id=?1",nativeQuery = true)
    public String findAvatarByUserId(Long userId);

    @Query(value = "select p.nick_name  from t_user p where p.id=?1",nativeQuery = true)
    public String findNickNameByUserId(Long userId);
}
