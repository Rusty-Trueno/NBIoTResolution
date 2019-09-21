package com.gantch.nbiot.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by rongshuai on 2019/9/11
 */
public class DataMessageClient {
    private static class SingletonMqttClient{//静态内部类实现单例模式
        private static final MqttClient client ;
        static{
            client = getClient();
        }
        private static MqttClient getClient(){
            MqttClient client = null;
            try{
                client = new MqttClient(Config.HOST,"publisher",new MemoryPersistence());
            }catch (MqttException me){
                System.out.println("reason " + me.getReasonCode());
                System.out.println("msg " + me.getMessage());
                System.out.println("loc " + me.getLocalizedMessage());
                System.out.println("cause " + me.getCause());
                System.out.println("excep " + me);
                me.printStackTrace();
            }
            return client;
        }

    }
    public MqttClient getClient(){
        return SingletonMqttClient.client;
    }
    public void connect(MqttClient client){
        try{
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(Config.userName);
//            connOpts.setPassword(Config.password.toCharArray());
            client.connect(connOpts);
        }catch (MqttException me){
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
    public static synchronized void publishData(MqttClient client, MqttMessage message, String deviceToken){
        try{
            message.setQos(0);
            message.setRetained(false);
            client.publish(Config.datatopic + "/" + deviceToken ,message);
        }catch (MqttException me){
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
    public static synchronized void publishAttribute(MqttClient client,String token,String data) throws Exception{
        //发布属性
        try{
            MqttMessage message = new MqttMessage(data.getBytes());
            message.setRetained(false);
            message.setQos(0);
            client.publish(Config.attributetopic + "/" + token ,message);//消息发布
        }catch (MqttException me){
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }


    }
}
