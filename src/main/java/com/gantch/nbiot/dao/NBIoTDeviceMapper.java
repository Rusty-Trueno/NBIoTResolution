package com.gantch.nbiot.dao;

import com.gantch.nbiot.model.NbiotDevice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NBIoTDeviceMapper {
    @Insert("INSERT INTO nbiotdevice(mac,deviceId) VALUES (#{mac}, #{deviceId})")
    int addNbiotDevice(NbiotDevice nbiotDevice);

    @Delete("DELETE FROM nbiotdevice Where mac =#{mac}")
    void removeNbiotDeviceByMac(String mac);

    @Select("SELECT * FROM nbiotdevice where mac = #{mac}")
    NbiotDevice getNbiotDevice(String mac);


}
