package com.fjh.security.kaptcha;

import com.alicp.jetcache.Cache;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 * @author fanjh37
 * @since 2023/2/5 16:44
 */
@RestController
public class KaptchaController {


    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private KaptchaService kaptchaService;

    @GetMapping("captchaImage")
    public KaptchaVO captchaImage() throws Exception {
        // 生成文字验证码
        String code = defaultKaptcha.createText();
        // 生成图片验证码
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage image = defaultKaptcha.createImage(code);
        ImageIO.write(image, "jpg", outputStream);
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";
        String base64Img = str + encoder.encode(outputStream.toByteArray()).replace("\n", "").replace("\r", "");
        KaptchaVO kaptchaVO = kaptchaService.saveKaptchaCode(code);
        kaptchaVO.setBase64Img(base64Img);
        return kaptchaVO;
    }
}
