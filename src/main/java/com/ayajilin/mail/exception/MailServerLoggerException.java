package com.ayajilin.mail.exception;

public class MailServerLoggerException extends Exception {
    public MailServerLoggerException() {
        super();
    }

    public MailServerLoggerException(Exception e){
        super(e);
    }

    public MailServerLoggerException(String str) {
        super(str);
    }

    public MailServerLoggerException(String str, Exception e){
        super(str, e);
    }
}
