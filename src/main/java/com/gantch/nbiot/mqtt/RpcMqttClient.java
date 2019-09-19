package com.gantch.nbiot.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;

/**
 * Created by rongshuai on 2019/9/11
 */
public class RpcMqttClient {
    private MqttClient rpcMqtt;
    private String gatewayName;
    private String token;

    public RpcMqttClient(String gatewayName,String token){
        this.gatewayName = gatewayName;
        this.token = token;
    }

    public boolean init(){

        return true;
    }
}
