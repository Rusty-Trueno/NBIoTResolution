package com.gantch.nbiot.model;

import lombok.Data;
/**
 * Created by rongshuai on 2019/9/11
 */
@Data
public class NbiotDevice
{
    private String mac;
    private String deviceId;
    private String deviceType;

    public String getMac() {
        return mac;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String toString(){
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"mac\":")
                .append("\""+mac+"\"");
        sb.append(",\"deviceId\":\"")
                .append(deviceId).append("\"");
        sb.append(",\"deviceType\":\"")
                .append(deviceType).append("\"");
        sb.append('}');
        return sb.toString();
    }
}
