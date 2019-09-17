package com.gantch.nbiot.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

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
//        if(gatewayGroupService.getGatewayGroup(gatewayName)!=null){
//            try{
//                if(rpcMqtt!=null){
//                    rpcMqtt.close();
//                }
//                rpcMqtt = null;
//                rpcMqtt = new MqttClient(Config.HOST,"receiveRPC",new MemoryPersistence());
//
//            }catch(Exception e){
//                e.printStackTrace();
//                return false;
//            }
//        }
        return true;
    }
}
