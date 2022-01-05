package com.fjh.email.service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/1/5 15:44
 */

public class MailSMTPAuthenticator extends Authenticator{
    private String sName;
    private String sPassword;
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(sName, sPassword);
    }
    public String getsName() {
        return sName;
    }
    public void setsName(String sName) {
        this.sName = sName;
    }
    public String getsPassword() {
        return sPassword;
    }
    public void setsPassword(String sPassword) {
        this.sPassword = sPassword;
    }
}