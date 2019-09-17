package com.gantch.nbiot.model;

import lombok.Data;

@Data
public class NbiotDevice
{
    private String mac;
    private String deviceId;

    public NbiotDevice(String mac, String deviceId){
        this.mac = mac;
        this.deviceId = deviceId;
    }
}
