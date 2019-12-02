package com.gantch.nbiot.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by rongshuai on 2019/11/20 20:53
 */
@Data
public class NbiotAlarmLog {
    private Integer id;
    private String deviceId;
    private Timestamp timestamp;
    private String name;
    private Double latitude;
    private Double longitude;
    private String alarmType;
    private String userName;

    public NbiotAlarmLog(Integer id, String deviceId, Timestamp timestamp, String name, Double latitude, Double longitude, String alarmType, String userName) {
        this.id = id;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.alarmType = alarmType;
        this.userName = userName;
    }

    public NbiotAlarmLog(String deviceId, Timestamp timestamp, String name, Double latitude, Double longitude, String alarmType, String userName) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.alarmType = alarmType;
        this.userName = userName;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


}
