package com.ayajilin.mail;

import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2){
            System.out.println("Wrong Port Parameter.");
            return;
        }
        else if (!Pattern.matches("^[0-9]+$", args[0])) {
            System.out.println("Wrong Port Parameter.");
            return;
        }
        int portNum = Integer.parseInt(args[0]);
        if (portNum <= 4000 || portNum >= 50000){
            System.out.println("Port number should be between 4001 and 49999.");
            return;
        }

        try{
        	/*
            File currentPathFile = new File(".");
            // 读取同目录下的config.json文件
            MailSenderConfig config = new MailSenderConfig(currentPathFile.getCanonicalPath() + "/config.json");
            */
        	MailSenderConfig config = new MailSenderConfig(args[1]);
            MailPortServer mailPortServer = new MailPortServer(portNum, config);
            mailPortServer.SyncRun();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
