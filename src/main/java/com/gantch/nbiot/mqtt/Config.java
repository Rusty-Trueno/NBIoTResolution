package com.gantch.nbiot.mqtt;

/**
 * Created by rongshuai on 2019/9/11
 */
public class Config {
    public static String HOST = "tcp://127.0.0.1:1884";
    public static String userName = "nbiot";
//    public static String password = "password";
    public static String RPC_TOPIC = "v1/devices/me/rpc/request/+";
    public static String datatopic = "v1/devices/me/telemetry";
    public static String attributetopic = "v1/devices/me/attributes";
}
