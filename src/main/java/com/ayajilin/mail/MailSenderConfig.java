package com.ayajilin.mail;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ayajilin.mail.exception.MailSenderConfigException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class MailSenderConfig {
    private String userMail;
    private String authCode;

    /**
     *
     * @param configFilePath config.json文件的路径名。
     * @throws MailSenderConfigException
     */
    public MailSenderConfig(String configFilePath) throws MailSenderConfigException {
        try {
            FileInputStream inputStream = new FileInputStream(configFilePath);
            JSONObject jsonObject = JSON.parseObject(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8));
            if (!jsonObject.containsKey("userMail") || !jsonObject.containsKey("authCode")){
                throw new MailSenderConfigException("Config file format Error.");
            }
            String uMail = jsonObject.getString("userMail");
            String aCode = jsonObject.getString("authCode");
            this.userMail = uMail;
            this.authCode = aCode;
        } catch (FileNotFoundException e) {
            throw new MailSenderConfigException("Could not find config file.", e);
        } catch (IOException e) {
            throw new MailSenderConfigException(e);
        }
    }

    /**
     *
     * @param userMail 发送者的邮箱。
     * @param authCode 校验码。
     * @throws MailSenderConfigException
     */
    public MailSenderConfig(String userMail, String authCode) throws MailSenderConfigException {
        if (!Pattern.matches("^.+@.+\\..+$", userMail)){
            throw new MailSenderConfigException("userMail format error.");
        }
        this.userMail = userMail;
        this.authCode = authCode;
    }

    public String getUserMail() {
        return userMail;
    }

    public String getAuthCode() {
        return authCode;
    }
}
