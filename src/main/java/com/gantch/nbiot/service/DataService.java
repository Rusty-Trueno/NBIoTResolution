package com.gantch.nbiot.service;

import com.gantch.nbiot.GatewayMethod.GatewayMethod;
import com.gantch.nbiot.GatewayMethod.Impl.GatewayMethodImpl;
import com.gantch.nbiot.model.NbiotDevice;
import com.gantch.nbiot.model.NbiotTokenRelation;
import com.gantch.nbiot.mqtt.DataMessageCallBack;
import com.gantch.nbiot.mqtt.DataMessageClient;
import org.apache.commons.codec.binary.Hex;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class DataService {
    private DataMessageCallBack dataMessageCallBack = new DataMessageCallBack();
    public void resolution(byte[] validData,int length,String mac,NbiotTokenRelationService nbiotTokenRelationService){
        String deviceId = Hex.encodeHexString(new byte[]{validData[0]},false);//获取设备类型
        System.out.println(deviceId);
        NbiotDevice device = new NbiotDevice(mac,deviceId);
        dataMessageCallBack.device_CallBack(device,nbiotTokenRelationService);//检查设备的注册情况
        String deviceToken = nbiotTokenRelationService.getRelationByMac(mac).getToken();
        DataMessageClient dataMessageClient = new DataMessageClient();
        switch (deviceId){
            case "20":
                Boolean isSmoking;
                String contents;
                if(validData[1] == (byte)0x01){
                    isSmoking = true;
                }else{ isSmoking = false; }
                if(isSmoking){
                    if(validData[2] == (byte)0x00){
                        contents = "当前设备为烟感，当前有烟，但是烟浓度正常~";
                    }else{
                        contents = "当前设备为烟感，当前有烟，且烟浓度低点~";
                    }
                }else{
                    contents = "当前设备为烟感，当前无烟~";
                }
                MqttMessage message = new MqttMessage(contents.getBytes());
                MqttClient client = dataMessageClient.getClient();
                dataMessageClient.publish(client,message,deviceToken);
                break;
            default:
                System.out.println("无匹配设备");
                break;
        }
    }
    public static String deviceId2Type(String deviceId){
        String type = null;
        switch (deviceId){
            case "20":
                type = "smoke_detector";
                break;
            default:
                type = "unknown";
                break;
        }
        return type;
    }
}
