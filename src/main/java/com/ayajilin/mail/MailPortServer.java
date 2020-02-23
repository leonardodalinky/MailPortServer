package com.ayajilin.mail;

import com.alibaba.fastjson.JSON;
import com.ayajilin.mail.exception.MailMessageFormatException;
import com.ayajilin.mail.exception.MailServerLoggerException;
import com.ayajilin.mail.utils.DateFormatter;

import javax.mail.MessagingException;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class MailPortServer extends Thread{
    private String toHost;
    private int listenPort;
    private MailServerLogger mailServerLogger;
    private boolean stop;
    private MailSenderConfig userConfig;

    /**
     *
     * @param listenPort 监听的端口。
     * @param userConfig 邮件发送者的设置。
     */
    public MailPortServer(int listenPort, MailSenderConfig userConfig){
        this.listenPort = listenPort;
        try {
            this.mailServerLogger = new MailServerLogger();
        }
        catch (Exception e){
            // nothing
            e.printStackTrace();
        }
        this.stop = false;
        this.userConfig = userConfig;
    }

    public int getListenPort() {
        return listenPort;
    }

    public void setListenPort(int listenPort) {
        this.listenPort = listenPort;
    }

    /**
     * 阻塞式监听。
     */
    public void SyncRun() {
        try {
            ServerSocket serverSocket = new ServerSocket(listenPort);
            while (!this.stop) {
                try {
                    // 开启监听
                    Socket socket = serverSocket.accept();
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                    byte[] bytes = inputStream.readAllBytes();
                    String strJson = new String(bytes, StandardCharsets.UTF_8);
                    if (!JSON.isValidObject(strJson)) {
                        try {
                            Date date = new Date();
                            mailServerLogger.Write(DateFormatter.toLogTime(date, "Wrong Format of Json.\n")
                                    .getBytes(StandardCharsets.UTF_8));
                        } catch (MailServerLoggerException e) {
                            e.printStackTrace();
                        }
                    }
                    MailMessageImp messageImp = MailMessageImp.Parse(strJson);
                    // 寄信
                    String to[] = {messageImp.getToHost()};
                    MailSender.getInstance().send(to, null, null, messageImp.getSubject(),
                            messageImp.getMessage(), this.userConfig, null);

                    try {
                        Date date = new Date();
                        StringBuilder stringBuffer = new StringBuilder();
                        stringBuffer.append(messageImp.getToHost());
                        stringBuffer.append("  ");
                        stringBuffer.append(messageImp.getSubject());
                        mailServerLogger.Write(DateFormatter.toLogTime(date, stringBuffer.toString())
                                .getBytes(StandardCharsets.UTF_8));
                    } catch (MailServerLoggerException MSLEx) {
                        MSLEx.printStackTrace();
                    }
                } catch (MessagingException e) {
                    e.printStackTrace();
                    try {
                        Date date = new Date();
                        mailServerLogger.Write(DateFormatter.toLogTime(date, "Messaging or Encoding Exception.\n")
                                .getBytes(StandardCharsets.UTF_8));
                    } catch (MailServerLoggerException MSLEx) {
                        MSLEx.printStackTrace();
                    }
                } catch (MailMessageFormatException e) {
                    try {
                        Date date = new Date();
                        mailServerLogger.Write(DateFormatter.toLogTime(date, "Wrong Format of Json.\n")
                                .getBytes(StandardCharsets.UTF_8));
                    } catch (MailServerLoggerException loggerExt) {
                        loggerExt.printStackTrace();
                    }
                } catch (Exception e) {
                    stop = true;
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 异步执行。继承于Thread类。
     */
    @Override
    public void run() {
        this.SyncRun();
    }

    public MailSenderConfig getUserConfig() {
        return userConfig;
    }

    public void setUserConfig(MailSenderConfig userConfig) {
        this.userConfig = userConfig;
    }
}
