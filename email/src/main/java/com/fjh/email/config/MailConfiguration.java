package com.fjh.email.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/1/5 15:24
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mail.config")
public class MailConfiguration {
    private String smtpHost;
    private int smtpPort;
    private boolean smtpAuth;
    private String smtpUser;
    private String smtpPass;
    private String smtpFrom;
    private String smtpFromnick;
}
