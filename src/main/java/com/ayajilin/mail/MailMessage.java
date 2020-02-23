package com.ayajilin.mail;

import com.ayajilin.mail.exception.MailMessageFormatException;

public interface MailMessage {
    public abstract String getMessage();
    public abstract String getToHost();
    public abstract String getSubject();
}
