package com.fjh.email.service;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/1/5 15:27
 */


import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

import com.fjh.email.config.MailConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@SuppressWarnings("unused")
public class MailService {

    @Autowired
    private MailConfiguration mailConfiguration;

    public void senMail() {
        System.out.println(mailConfiguration.toString());
        MimeMessage mMessage = null;
        Session mailSession = null;
        //1、连接邮件服务器的参数配置附件名称过长乱码解决，关键词false
        System.setProperty("mail.mime.splitlongparameters", "false");
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", mailConfiguration.getSmtpHost());
        props.setProperty("mail.smtp.port", mailConfiguration.getSmtpPort() + "");
        props.setProperty("mail.smtp.auth", mailConfiguration.isSmtpAuth() + "");
        // 设置SMTP连接和发送邮件的超时时间，因为缺省是无限超时，单位毫秒
        props.setProperty("mail.smtp.connectiontimeout", "15000");//SMTP服务器连接超时时间
        props.setProperty("mail.smtp.timeout", "60000");//发送邮件超时时间
        if (mailConfiguration.isSmtpAuth()) {
            MailSMTPAuthenticator smtpAutr = new MailSMTPAuthenticator();
            smtpAutr.setsName(mailConfiguration.getSmtpUser());
            smtpAutr.setsPassword("sunwei951025");
            mailSession = Session.getInstance(props, smtpAutr);
        } else {
            mailSession = Session.getInstance(props);
        }

        mailSession.setDebug(true);
        mMessage = new MimeMessage(mailSession);
        try {
            //发件人
            InternetAddress formAddress = new InternetAddress(mailConfiguration.getSmtpFrom());
            formAddress.setPersonal("了然于心");
            mMessage.setFrom(formAddress);
            //发送时间
            mMessage.setSentDate(new Date());
            //收件人
            InternetAddress[] toAddress = {new InternetAddress("sunw@hrocloud.com"), new InternetAddress("sunwei1995sh@126.com")};
            mMessage.setRecipients(Message.RecipientType.TO, toAddress);
            //主题
            mMessage.setSubject("孙维主题");

            //内容
            MimeBodyPart contentBodyPart = new MimeBodyPart();
            contentBodyPart.setContent("此邮件为系统自己主动发送<img src='cid:a'><img src='cid:a'>", "text/html;charset=UTF-8");
            //cid必须和相关图片的ContentID相同才会在邮件正文显示图片。//图片 MimeBodyPart imgAff = new MimeBodyPart();        imgAff.setDataHandler(new DataHandler(new FileDataSource("d:\\IO.jpg")));        imgAff.setContentID("a"); MimeMultipart addM = new MimeMultipart();       addM.addBodyPart(contentBodyPart);       addM.addBodyPart(imgAff);      addM.setSubType("related"); // 图班与正文的 body MimeBodyPart affContent = new MimeBodyPart();     affContent.setContent(addM); //附件MimeBodyPart affDoc = new MimeBodyPart();    affDoc.setDataHandler(new DataHandler(new FileDataSource("d:\\业务流程脚本.sql")));    affDoc.setFileName(MimeUtility.encodeText("业务流程脚本.sql"));    affDoc.setContentID("UUud"); MimeMultipart text = new MimeMultipart(); text.addBodyPart(affContent); text.addBodyPart(affDoc); text.setSubType("mixed"); mMessage.setContent(text); mMessage.saveChanges(); Transport transport = null; transport = mailSession.getTransport("smtp"); transport.connect(mailConfiguration.getSTMP_user(),mailConfiguration.getSTMP_pass()); transport.sendMessage(mMessage, mMessage.getAllRecipients()); transport.close(); } catch (MessagingException | UnsupportedEncodingException e) { // TODO Auto-generated catch block  e.printStackTrace(); } } }
            //图片
            MimeBodyPart imgAff = new MimeBodyPart();
            imgAff.setDataHandler(new DataHandler(new FileDataSource("d:\\IO.jpg")));
            imgAff.setContentID("a");
            MimeMultipart addM = new MimeMultipart();
            addM.addBodyPart(contentBodyPart);
            addM.addBodyPart(imgAff);
            addM.setSubType("related");
            // 图班与正文的 body
            MimeBodyPart affContent = new MimeBodyPart();
            affContent.setContent(addM);
//附件
            MimeBodyPart affDoc = new MimeBodyPart();
            affDoc.setDataHandler(new DataHandler(new FileDataSource("d:\\业务流程脚本.sql")));
            affDoc.setFileName(MimeUtility.encodeText("业务流程脚本.sql"));
            affDoc.setContentID("UUud");
            MimeMultipart text = new MimeMultipart();
            text.addBodyPart(affContent);
            text.addBodyPart(affDoc);
            text.setSubType("mixed");
            mMessage.setContent(text);
            mMessage.saveChanges();
            Transport transport = null;
            transport = mailSession.getTransport("smtp");
            transport.connect(mailConfiguration.getSmtpUser(), mailConfiguration.getSmtpPass());
            transport.sendMessage(mMessage, mMessage.getAllRecipients());
            transport.close();
        } catch (MessagingException | UnsupportedEncodingException e) {
            //TODO Auto-generated catch block  e.printStackTrace();
            System.out.println("异常：" + e.getMessage());
        }
    }
}