package com.mail.controller;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@RestController
@RequestMapping("mail")
public class MailController {
    
    //引入邮件组件
    @Resource
    private JavaMailSenderImpl mailSender;

    /**
    * @description 发送简单邮件
    * @author lryepoch
    * @date 2020/11/28 8:51
    *
    */
    @GetMapping("sendString")
    public Object sendStringEmail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //发件人（一定要和主配置文件中的发送账号一致）
        simpleMailMessage.setFrom("lryepoch@qq.com");
        simpleMailMessage.setTo("lryepoch@163.com");
        //标题
        simpleMailMessage.setSubject("测试邮件主题");
        //内容
        simpleMailMessage.setText("测试邮件内容");
        mailSender.send(simpleMailMessage);

        return "success";
    }

    /**
    * @description 发送带附件和html样式的复杂邮件
    * @author lryepoch
    * @date 2020/11/28 8:53
    *
    */
    @GetMapping("sendComplex")
    public Object sendComplexEmail() throws MessagingException {
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        //发件人（一定要和主配置文件中的发送账号一致）
        messageHelper.setFrom("lryepoch@foxmail.com");
        messageHelper.setTo("634226718@qq.com");
        //标题
        messageHelper.setSubject("通知：今晚开会");
        //内容：第二个参数html，默认是false，即默认html不生效
        messageHelper.setText("<b style='color:red'>各位领导，今天23:00，在大会议室开会，请准时到场！</b>", true);
        //发送附件
        messageHelper.addAttachment("Java高手-代码篇.pdf", new File("C:\\Users\\260408\\Desktop\\Java高手-代码篇.pdf"));
        messageHelper.addAttachment("985.png", new File("C:\\Users\\260408\\Desktop\\985.png"));
        mailSender.send(mimeMessage);

        return "success";
    }
}
