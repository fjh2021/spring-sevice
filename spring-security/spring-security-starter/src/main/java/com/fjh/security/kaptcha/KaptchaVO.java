package com.fjh.security.kaptcha;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author fanjh37
 * @since 2023/2/6 17:21
 */
@Data
@AllArgsConstructor
public class KaptchaVO implements Serializable {
    /**
     * 验证码标识符
     */
    private String captchaKey;
    /**
     * 验证码过期时间
     */
    private Long expire;
    /**
     * base64字符串
     */
    private String base64Img;

    public KaptchaVO(String captchaKey, Long expire) {
        this.captchaKey = captchaKey;
        this.expire = expire;
    }
}
