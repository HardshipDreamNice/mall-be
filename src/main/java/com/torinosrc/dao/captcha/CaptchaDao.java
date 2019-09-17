package com.torinosrc.dao.captcha;


import com.torinosrc.model.entity.captcha.Captcha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * <b><code>CaptchaDao</code></b>
 * <p/>
 * Captcha的具体实现
 * <p/>
 * <b>Creation Time:</b> Wed Apr 11 16:25:56 CST 2018.
 *
 * @author maxuepeng
 * @version 1.0.0
 * @since torinosrc-core 1.0.0
 */
@Repository
public interface CaptchaDao extends JpaRepository<Captcha, Long>, JpaSpecificationExecutor<Captcha> {

    Captcha findCaptchaByUserName(String userName);

    void deleteByUserName(String userName);

}
