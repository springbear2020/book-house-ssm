package edu.whut.bear.panda.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/27 21:36
 */
@Component
public class EmailUtils {
    @Autowired
    private PropertyUtils propertyUtils;
    @Autowired
    private Session session;

    /**
     * Get a session between this server and the email server
     *
     * @return java.mail.Session
     */
    @Bean
    public Session getSession() {
        // Configure the attribute information required
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", propertyUtils.getSmtpHost());
        // Authorize needed
        properties.setProperty("mail.smtp.auth", "true");
        // Can check the details though debug mode by the code [session.setDebug(true);]
        return Session.getInstance(properties);
    }

    /**
     * Build email content
     *
     * @return MimeMessage
     */
    public MimeMessage createMailContent(String receiverEmail, String verifyCode) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = new MimeMessage(session);
        // Set the sender
        message.setFrom(new InternetAddress(propertyUtils.getFromEmail(), "Book House", "UTF-8"));
        // Set the receiver
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiverEmail));
        // Email subject
        message.setSubject("欢迎使用 Book House 身份验证系统", "UTF-8");
        // Email message body
        message.setContent("<h3>\n" + "\t<span style=\"font-size:16px;\">亲爱的用户：</span> \n" + "</h3>\n" +
                        "<p>\n" + "\t<span style=\"font-size:14px;\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:14px;\">&nbsp; <span style=\"font-size:16px;\">&nbsp;&nbsp;您好！本次请求的验证码是：<span style=\"font-size:16px;color:#1d44ff;\"> " + verifyCode + "</span> ，验证码 10 分钟内有效，请在 10 分钟内完成验证。</span></span>\n" + "</p>\n" +
                        "<p>\n" + "\t<span style=\"font-size:14px;\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:14px;\">&nbsp; <span style=\"font-size:16px;\">&nbsp;&nbsp;您正在进行身份验证，打死都不要将验证码告诉别人哦。如非本人操作，请忽略此邮件。</span></span>\n" + "</p>\n" +
                        "<p style=\"text-align:right;\">\n" + "\t<span style=\"background-color:#FFFFFF;font-size:16px;color:#000000;\"><span style=\"color:#000000;font-size:16px;background-color:#FFFFFF;\"><span class=\"token string\" style=\"font-family:&quot;font-size:16px;color:#000000;line-height:normal !important;background-color:#FFFFFF;\">Book House</span></span></span> \n" + "</p>\n" +
                        "<p style=\"text-align:right;\">\n" + "\t<span style=\"background-color:#FFFFFF;font-size:14px;\"><span style=\"color:#FF9900;font-size:18px;\"><span class=\"token string\" style=\"font-family:&quot;font-size:16px;color:#000000;line-height:normal !important;\"><span style=\"font-size:16px;color:#000000;background-color:#FFFFFF;\">" + DateUtils.dateIntoDatetime(new Date()) + "</span><span style=\"font-size:18px;color:#000000;background-color:#FFFFFF;\"></span></span></span></span> \n" + "</p>",
                "text/html;charset=UTF-8");
        // Set delivery time
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    /**
     * Send email, if no exception occur meaning the email deliver successfully
     */
    @Bean
    public synchronized boolean sendEmail(String receiverEmail, String verifyCode) {
        Transport transport;
        try {
            // Make a connection with the email server
            transport = session.getTransport();
            transport.connect(propertyUtils.getFromEmail(), propertyUtils.getEmailPassword());
            // Build the email content
            MimeMessage message = createMailContent(receiverEmail, verifyCode);
            // Send the email
            transport.sendMessage(message, message.getAllRecipients());
            // Close the connection with email server
            transport.close();
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
