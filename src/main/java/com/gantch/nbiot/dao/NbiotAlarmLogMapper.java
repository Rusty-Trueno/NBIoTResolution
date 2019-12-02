package com.gantch.nbiot.dao;

import com.gantch.nbiot.model.NbiotAlarmLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by rongshuai on 2019/11/20 21:02
 */
@Mapper
public interface NbiotAlarmLogMapper {
    @Insert("INSERT INTO nbiot_alarm_log(deviceId,timestamp,name,latitude,longitude,alarmType,userName) VALUES (#{deviceId},#{timestamp},#{name},#{latitude},#{longitude},#{alarmType},#{userName})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    Boolean addNbiotAlarmLog(NbiotAlarmLog nbiotAlarmLog);//报警日志的插入

    @Select("SELECT FROM nbiot_alarm_log WHERE deviceId = #{deviceId}")
    List<NbiotAlarmLog> getNbiotAlarmLogs(@Param("deviceId") String deviceId);
}
