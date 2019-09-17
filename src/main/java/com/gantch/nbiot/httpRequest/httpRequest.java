package com.gantch.nbiot.httpRequest;

import java.util.Random;

public class httpRequest {
    public String httpcreate(String deviceMac,String deviceType){
        System.out.println("deviceMac:" + deviceMac);
        System.out.println("deviceType:" + deviceType);
        return getRandomString(10);
    }
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
