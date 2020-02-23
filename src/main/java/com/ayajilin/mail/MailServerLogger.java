package com.ayajilin.mail;

import com.ayajilin.mail.exception.MailServerLoggerException;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.regex.Pattern;

public class MailServerLogger {
    private File logFileDir;
    private File logFilePath;
    private Date nowDate;

    public MailServerLogger() throws MailServerLoggerException {
        nowDate = new Date();
        logFileDir = new File("./log");
        logFilePath = new File("./log/mserver_" + nowDate.getTime() + ".log");
    }

    public void Write(byte[] bytes) throws MailServerLoggerException {
        if (!logFileDir.exists() || !logFileDir.isDirectory()){
            if (!logFileDir.mkdirs()) {
                throw new MailServerLoggerException("Could not create new directories.");
            }
        }
        if (!logFilePath.exists() || !logFilePath.isFile()) {
            try{
                logFilePath.createNewFile();
            }
            catch (Exception e){
                throw new MailServerLoggerException(e);
            }
        }
        try{
            FileOutputStream fo = new FileOutputStream(logFilePath, true);
            fo.write(bytes);
            fo.close();
        }
        catch (Exception e){
            throw new MailServerLoggerException(e);
        }
    }
}
