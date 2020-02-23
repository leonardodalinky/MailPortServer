package com.ayajilin.mail.exception;

public class MailMessageFormatException extends Exception {
    public MailMessageFormatException() {
        super();
    }

    public MailMessageFormatException(Exception e){
        super(e);
    }

    public MailMessageFormatException(String str) {
        super(str);
    }

    public MailMessageFormatException(String str, Exception e){
        super(str, e);
    }
}
