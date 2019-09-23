package com.gantch.nbiot.service;

import com.gantch.nbiot.model.NbiotDevice;
import com.gantch.nbiot.mqtt.DataMessageCallBack;
import com.gantch.nbiot.mqtt.DataMessageClient;
import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Hex;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
/**
 * Created by rongshuai on 2019/9/11
 */
public class DataService {
    private DataMessageCallBack dataMessageCallBack = new DataMessageCallBack();
    public void resolution(byte[] validData,int length,String mac,NbiotTokenRelationService nbiotTokenRelationService) throws Exception {
        String deviceType = Hex.encodeHexString(new byte[]{validData[0]},false);//获取设备类型
        int infoLength = validData[1];
        System.out.println(deviceType);
        NbiotDevice device = new NbiotDevice();
        device.setMac(mac);
        device.setDeviceType(deviceType);
        dataMessageCallBack.device_CallBack(device,nbiotTokenRelationService);//检查设备的注册情况
        String deviceToken = nbiotTokenRelationService.getRelationByMac(mac).getToken();
        switch (deviceType){
            case "20":
                Boolean isSmoking;
                String contents;
                if(validData[2] == (byte)0x01){
                    isSmoking = true;
                }else{ isSmoking = false; }
                if(isSmoking){
                    if(validData[3] == (byte)0x00){
                        contents = "当前设备为烟感，当前有烟，但是烟浓度正常~";
                    }else{
                        contents = "当前设备为烟感，当前有烟，且烟浓度低点~";
                    }
                }else{
                    contents = "当前设备为烟感，当前无烟~";
                }
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("contents",contents);
                DataMessageClient dataMessageClient = new DataMessageClient();
                MqttMessage message = new MqttMessage(jsonObject.toString().getBytes());
                MqttClient client = dataMessageClient.getClient();
                DataMessageClient.publishData(client,message,deviceToken);//向mqtt的服务端（模拟deviceaccess）发送数据消息
                break;
            default:
                System.out.println("无匹配设备");
                break;
        }
    }
    public static String deviceType2Type(String deviceId){
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
