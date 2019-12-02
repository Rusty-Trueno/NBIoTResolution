package com.gantch.nbiot.service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dyvmsapi.model.v20170525.SingleCallByTtsRequest;
import com.aliyuncs.dyvmsapi.model.v20170525.SingleCallByTtsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.gantch.nbiot.model.DeviceMessage;
import com.gantch.nbiot.model.NbiotAlarmLog;
import com.gantch.nbiot.model.NbiotDevice;
import com.gantch.nbiot.mqtt.DataMessageCallBack;
import com.gantch.nbiot.mqtt.DataMessageClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Hex;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by rongshuai on 2019/9/11
 */

public class DataService {

    private DataMessageCallBack dataMessageCallBack = new DataMessageCallBack();
    public void resolution(byte[] validData,int length,String mac,NbiotTokenRelationService nbiotTokenRelationService,NbiotDeviceService nbiotDeviceService,DeviceMessageDao deviceMessageDao,NbiotAlarmLogDao nbiotAlarmLogDao) throws Exception {
        String deviceType = Hex.encodeHexString(new byte[]{validData[0]},false);//获取设备类型
        int infoLength = validData[1];
        System.out.println(deviceType);
        NbiotDevice device = new NbiotDevice();
        device.setMac(mac);
        device.setDeviceType(deviceType);
        dataMessageCallBack.device_CallBack(device,nbiotTokenRelationService,nbiotDeviceService);//检查设备的注册情况
        String deviceToken = nbiotTokenRelationService.getRelationByMac(mac).getToken();
        switch (deviceType){
            case "20":
                Boolean isSmoking;
                String status = "normal";
                if(validData[2] == (byte)0x01){
                    isSmoking = true;
                    NbiotDevice nbiotDevice = nbiotDeviceService.getNbiotDevice(mac);
                    String deviceName = nbiotDevice.getName();
                    String deviceId = nbiotDevice.getDeviceId();
                    Double latitude = nbiotDevice.getLatitude();
                    Double longitude = nbiotDevice.getLongitude();
                    String alarmType = "烟雾报警";
                    String location = nbiotDevice.getLocation();
                    String userName = nbiotDevice.getUserName();
                    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                    System.out.println("当前设备的名字为：" + deviceName);
                    System.out.println("当前设备的Id为：" + deviceId);
                    System.out.println("当前设备的纬度为：" + latitude);
                    System.out.println("当前设备的经度为：" + longitude);
                    System.out.println("当前时间戳为：" + currentTime.toString());
                    NbiotAlarmLog nbiotAlarmLog = new NbiotAlarmLog(deviceId,currentTime,deviceName,latitude,longitude,alarmType,userName);
                    nbiotAlarmLogDao.addNbiotAlarmLog(nbiotAlarmLog);
                    try{
                        List<DeviceMessage> deviceMessages = deviceMessageDao.getDeviceMessageById(deviceId);
                        System.out.println(deviceMessages.toString());
                        for (DeviceMessage deviceMessage : deviceMessages) {
                            String phoneNumber = deviceMessage.getPhoneNumber();
                            System.out.println("出现报警，向：" + phoneNumber + "发送短信");
                            System.out.println(deviceName +"," + userName +"," + location +"," + alarmType +"," + phoneNumber);
                            sendMs(deviceName,userName,location,alarmType,phoneNumber);//向对应手机号发送报警短信
                        }
                    }catch (Exception e){
                        System.out.println("当前尚无设备对应的报警手机号");
                    }
                    //sendVoice(deviceName);


                }else{ isSmoking = false; }
                if(isSmoking){
                    if(validData[3] == (byte)0x00){
                        status = "normal";
                    }else{
                        status = "low";
                    }
                }
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("isSmoking",isSmoking);
                jsonObject.addProperty("status",status);
                jsonObject.addProperty("online",1D);
                DataMessageClient dataMessageClient = new DataMessageClient();
                System.out.println(jsonObject.toString());
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

    public void addNbiotAlarmLog(NbiotAlarmLog nbiotAlarmLog){

    }

    public void sendMs(String deviceName,String userName,String location,String alarmType,String phoneNumber){//发送短信报警通知
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FgntVB75X2BoJQR5qUr", "UIlRGb6N2eX1boNTuFxMhQoYKQEzhz");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "云消防");
        request.putQueryParameter("TemplateCode", "SMS_177544537");
        request.putQueryParameter("TemplateParam", "{\"deviceName\":\"" +deviceName+ "\", \"userName\":\"" +userName+ "\",\"location\":\"" + location + "\" ,\"alarmType\":\"" + alarmType + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    public void sendVoice(String deviceName){//发送语音报警通知
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FgntVB75X2BoJQR5qUr", "UIlRGb6N2eX1boNTuFxMhQoYKQEzhz");
        IAcsClient client = new DefaultAcsClient(profile);

        SingleCallByTtsRequest request = new SingleCallByTtsRequest();
        request.setRegionId("cn-hangzhou");
        request.setCalledShowNumber("02566823407");
        request.setCalledNumber("18813157220");
        request.setTtsCode("TTS_176936831");
        request.setTtsParam("{\"name\":\"" + deviceName + "\"}");
        request.setPlayTimes(1);
        request.setVolume(60);

        try {
            SingleCallByTtsResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }

    }
}
