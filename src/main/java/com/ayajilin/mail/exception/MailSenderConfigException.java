package com.ayajilin.mail.exception;

public class MailSenderConfigException extends Exception {
    public MailSenderConfigException() {
        super();
    }

    public MailSenderConfigException(Exception e){
        super(e);
    }

    public MailSenderConfigException(String str) {
        super(str);
    }

    public MailSenderConfigException(String str, Exception e){
        super(str, e);
    }
}
