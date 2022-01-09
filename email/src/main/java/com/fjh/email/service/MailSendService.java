package com.fjh.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/1/7 9:46
 */
@Service
public class MailSendService {

    @Autowired
    private JavaMailSender mailSender;

   @Autowired
   private MailProperties mailProperties;
    /**
     * 无附件 简单文本内容发送
     * @param email 接收方email
     * @param subject 邮件内容主题
     * @param text 邮件内容
     */
    public  void simpleMailSend(String email,String subject,String text) {
        //创建邮件内容
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());//这里指的是发送者的账号
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        //发送邮件
        mailSender.send(message);
        System.out.println("\033[32;1m"+"发送给 "+email+" 的邮件发送成功"+"\033[0m");
    }

    /**
     * 发送带附件的邮件
     *
     * @param to      接受人
     * @param subject 主题
     * @param html    发送内容
     * @param filePath  附件路径
     * @throws MessagingException           异常
     * @throws UnsupportedEncodingException 异常
     */
    public  void sendAttachmentMail(String to, String subject, String html, String filePath) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 设置utf-8或GBK编码，否则邮件会有乱码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(mailProperties.getUsername(),"范先生");
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(html, true);
        FileSystemResource file=new FileSystemResource(new File(filePath));
        String fileName=filePath.substring(filePath.lastIndexOf(File.separator));
        messageHelper.addAttachment(fileName,file);
        mailSender.send(mimeMessage);
    }

    /**
     * 发送html内容的 邮件
     * @param email
     * @param subject
     * @param text
     */
    public void sendSimpleMailHtml(String email,String subject,String text) throws MessagingException, UnsupportedEncodingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(mailProperties.getUsername(),"范先生");
        helper.setTo(email);
        helper.setSubject(subject);
        // 注意<img/>标签，src='cid:jpg'，'cid'是contentId的缩写，'jpg'是一个标记
        helper.setText("<html><body><img src=\"cid:jpg\"></body></html>", true);
        // 加载文件资源，作为附件
        FileSystemResource file = new FileSystemResource(new File("d:\\1.png"));
        // 调用MimeMessageHelper的addInline方法替代成文件('jpg[标记]', file[文件])
        helper.addInline("jpg", file);
        // 发送邮件
        mailSender.send(mimeMessage);
    }
}
