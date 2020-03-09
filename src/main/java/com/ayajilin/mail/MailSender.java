package com.ayajilin.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

public class MailSender {
    private static MailSender instance = null;

    private MailSender() {

    }

    public static MailSender getInstance(){
        if (instance == null){
            instance = new MailSender();
        }
        return instance;
    }

    /**
     *
     * @param to 接受者的邮箱。可输入多个。
     * @param cs 接受抄送者的邮箱。可输入多个。
     * @param ms 接受密送者的邮箱。可输入多个。
     * @param subject 邮件的标题。
     * @param content 邮件的内容。
     * @param userConfig 发送者的邮箱与授权码的设置。
     * @param fileList 附录文件。
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public void send(String to[], String cs[], String ms[], String subject,
                     String content, MailSenderConfig userConfig, String fileList[])
            throws MessagingException, UnsupportedEncodingException {
        Properties p = new Properties(); // Properties p =
        // System.getProperties();
        p.put("mail.smtp.auth", "true");
        p.put("mail.transport.protocol", "smtp");
        p.put("mail.smtp.host", "smtp.qq.com");
        p.put("mail.smtp.port", "465");
        p.put("mail.smtp.ssl.enable", "true");
        // 建立会话
        Session session = Session.getInstance(p);
        Message msg = new MimeMessage(session); // 建立信息
        BodyPart messageBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        msg.setFrom(new InternetAddress(userConfig.getUserMail())); // 发件人

        String toList = null;
        String toListcs = null;
        String toListms = null;

        // 发送,
        if (to != null) {
            toList = getMailList(to);
            InternetAddress[] iaToList = InternetAddress.parse(toList);
            msg.setRecipients(Message.RecipientType.TO, iaToList); // 收件人
        }

        // 抄送
        if (cs != null) {
            toListcs = getMailList(cs);
            InternetAddress[] iaToListcs = InternetAddress.parse(toListcs);
            msg.setRecipients(Message.RecipientType.CC, iaToListcs); // 抄送人
        }

        // 密送
        if (ms != null) {
            toListms = getMailList(ms);
            InternetAddress[] iaToListms = InternetAddress.parse(toListms);
            msg.setRecipients(Message.RecipientType.BCC, iaToListms); // 密送人
        }
        msg.setSentDate(new Date()); // 发送日期
        msg.setSubject(subject); // 主题
        msg.setText(content); // 内容
        // 显示以html格式的文本内容
        messageBodyPart.setContent(content, "text/html;charset=utf-8");
        multipart.addBodyPart(messageBodyPart);

        // 2.保存多个附件
        if (fileList != null) {
            AddTach(fileList, multipart);
        }

        msg.setContent(multipart);
        // 邮件服务器进行验证
        Transport tran = session.getTransport("smtp");
        // 设置自己的邮箱及授权码
        tran.connect("smtp.qq.com", userConfig.getUserMail(), userConfig.getAuthCode());
        tran.sendMessage(msg, msg.getAllRecipients()); // 发送
        // System.out.println("邮件发送成功");
    }

    private String getMailList(String[] mailArray) {

        StringBuffer toList = new StringBuffer();
        int length = mailArray.length;
        if (mailArray != null && length < 2) {
            toList.append(mailArray[0]);
        } else {
            for (int i = 0; i < length; i++) {
                toList.append(mailArray[i]);
                if (i != (length - 1)) {
                    toList.append(",");
                }

            }
        }
        return toList.toString();
    }

    public void AddTach(String fileList[], Multipart multipart)
            throws MessagingException, UnsupportedEncodingException {
        for (int index = 0; index < fileList.length; index++) {
            MimeBodyPart mailArchieve = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(fileList[index]);
            mailArchieve.setDataHandler(new DataHandler(fds));
            mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(),
                    "UTF-8", "B"));
            multipart.addBodyPart(mailArchieve);
        }
    }

    /*
    public static void main(String args[]) {
        MailSender send = MailSender.getInstance();
        String to[] = { "493987054@qq.com" };
        String cs[] = null;
        String ms[] = null;
        String subject = "测试一下";
        String content = "这是邮件内容，仅仅是测试，不需要回复";
        String formEmail = "493987054@qq.com";
        // 2.保存多个附件
        //String[] arrArchievList = new String[4];
        //arrArchievList[0] = "c:\\2012052914033429140297.rar";
        //arrArchievList[1] = "c:\\topSearch.html";
        //arrArchievList[2] = "c:\\topSearch2.html";
        //arrArchievList[3] = "c:\\logo_white.png";
        send.send(to, cs, ms, subject, content, formEmail, null);
    }
    */
}
