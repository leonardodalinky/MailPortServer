package com.ayajilin.mail;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ayajilin.mail.exception.MailMessageFormatException;

public class MailMessageImp implements MailMessage {
    private String toHost;
    private String message;
    // 邮件标题
    private String subject;

    public static MailMessageImp Parse(String jsonStr) throws MailMessageFormatException {
        if (!JSON.isValidObject(jsonStr)) {
            throw new MailMessageFormatException("Json Format Error");
        }
        JSONObject obj = JSON.parseObject(jsonStr);
        if (!obj.containsKey("ToHost") || !obj.containsKey("Message") || !obj.containsKey("Subject")) {
            throw new MailMessageFormatException("Json Format Error");
        }
        return new MailMessageImp(obj.getString("ToHost"), obj.getString("Subject"), obj.getString("Message"));
    }

    /**
     *
     * @param toHost 接收方的host地址。
     * @param subject 邮件标题。
     * @param message 邮件信息。
     */
    public MailMessageImp(String toHost, String subject, String message){
        this.toHost = toHost;
        this.message = message;
        this.subject = subject;
    }

    public String toJsonString() {
        JSONObject obj = new JSONObject();
        obj.put("ToHost", this.toHost);
        obj.put("Message", this.message);
        obj.put("Subject", this.subject);
        return JSON.toJSONString(obj, SerializerFeature.PrettyFormat);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToHost(String toHost) {
        this.toHost = toHost;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getToHost() {
        return toHost;
    }

    @Override
    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
