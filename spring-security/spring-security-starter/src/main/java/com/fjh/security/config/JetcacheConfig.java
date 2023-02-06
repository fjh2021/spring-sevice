package com.fjh.security.config;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.template.QuickConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.boot.autoconfigure.session.StoreType;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author fanjh37
 * @since 2023/2/6 18:10
 */
@Component
public class JetcacheConfig {

    @Value("${kaptcha.expired.time:600}")
    private Long kaptchaExpiredTime;
    @Autowired
    private CacheManager cacheManager;

    @Bean
    public Cache<String, String> kaptchaCache(SessionProperties sessionProperties) {

        CacheType cacheType = CacheType.LOCAL;
        if (sessionProperties.getStoreType() == StoreType.REDIS) {
            cacheType = CacheType.REMOTE;
        }
        QuickConfig qc = QuickConfig.newBuilder("captcha:verification").cacheType(cacheType).expire(Duration.ofSeconds(kaptchaExpiredTime)).build();
        Cache<String, String> kaptchaCache = cacheManager.getOrCreateCache(qc);
        return kaptchaCache;
    }

    public Long getKaptchaExpiredTime() {
        return kaptchaExpiredTime;
    }
}
