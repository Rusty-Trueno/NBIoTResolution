package com.gantch.nbiot.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by rongshuai on 2019/9/11
 */
@Data
public class NbiotDevice
{
    private Integer id;
    private String mac;
    private String name;
    private String deviceId;
    private Integer tenantId;
    private String manufacture;
    private String deviceType;
    private String model;
    private String parentDeviceId;
    private Double latitude;
    private Double longitude;
    private Timestamp createTime;
    private String userName;
    private String district;
    private String location;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getParentDeviceId() {
        return parentDeviceId;
    }

    public void setParentDeviceId(String parentDeviceId) {
        this.parentDeviceId = parentDeviceId;
    }

    public String toString(){
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"mac\":")
                .append("\""+mac+"\"");
        sb.append(",\"name\":\"")
                .append(name).append("\"");
        sb.append(",\"id\":\"")
                .append(id).append("\"");
        sb.append(",\"deviceId\":\"")
                .append(deviceId).append("\"");
        sb.append(",\"tenantId\":\"")
                .append(tenantId).append("\"");
        sb.append(",\"manufacture\":\"")
                .append(manufacture).append("\"");
        sb.append(",\"deviceType\":\"")
                .append(deviceType).append("\"");
        sb.append(",\"model\":\"")
                .append(model).append("\"");
        sb.append(",\"parentDeviceId\":\"")
                .append(parentDeviceId).append("\"");
        sb.append(",\"latitude\":\"")
                .append(latitude).append("\"");
        sb.append(",\"longitude\":\"")
                .append(longitude).append("\"");
        sb.append('}');
        return sb.toString();
    }
}
