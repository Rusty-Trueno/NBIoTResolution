package com.gantch.nbiot.dao;

import com.gantch.nbiot.model.NbiotDevice;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
/**
 * Created by rongshuai on 2019/9/11
 */
@Mapper
public interface NBIoTDeviceMapper {
    @Insert("INSERT INTO nbiotdevice(mac,name,deviceId,tenantId,manufacture,deviceType,model,parentDeviceId) " +
            "VALUES (#{mac},#{name}, #{deviceId},#{tenantId},#{manufacture},#{deviceType},#{model},#{parentDeviceId})")
    int addNbiotDevice(NbiotDevice nbiotDevice);


    @Select("SELECT * FROM nbiotdevice where mac = #{mac}")
    NbiotDevice getNbiotDevice(String mac);


}
