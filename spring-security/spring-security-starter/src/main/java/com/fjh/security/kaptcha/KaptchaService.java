package com.fjh.security.kaptcha;

import com.alicp.jetcache.Cache;
import com.fjh.security.config.JetcacheConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

/**
 * @author fanjh37
 * @since 2023/2/6 18:15
 */
@Slf4j
@Service
public class KaptchaService {

    @Autowired
    private JetcacheConfig jetcacheConfig;

    /**
     * jetcache 缓存
     */
    @Autowired
    private Cache<String, String> kaptchaCache;

    /**
     * 存储 code
     *
     * @param code
     * @return
     */
    public KaptchaVO saveKaptchaCode(String code) {
        String key = UUID.randomUUID().toString();
        kaptchaCache.put(key, code);
        log.info("验证码：key:{}.code",key,code);
        KaptchaVO captchaVO = new KaptchaVO(key, jetcacheConfig.getKaptchaExpiredTime());
        return captchaVO;
    }

    /**
     * 是否 存在验证码
     *
     * @param captchaKey
     * @param captchaValue
     * @return
     */
    public Boolean verify(String captchaKey, String captchaValue) {
        if (Objects.isNull(captchaKey)) {
            return false;
        }
        String value = kaptchaCache.get(captchaKey);
        if (Objects.isNull(value)) {
            return false;
        }
        return value.equals(captchaValue);

    }

    /**
     * 移除
     *
     * @param key
     */
    public void removeByKey(String key) {
        kaptchaCache.remove(key);
    }
}
