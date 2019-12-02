package com.gantch.nbiot.service;

import com.gantch.nbiot.dao.NbiotAlarmLogMapper;
import com.gantch.nbiot.model.NbiotAlarmLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rongshuai on 2019/11/20 21:07
 */
@Service
public class NbiotAlarmLogDao {
    @Autowired
    private NbiotAlarmLogMapper nbiotAlarmLogMapper;

    public Boolean addNbiotAlarmLog(NbiotAlarmLog nbiotAlarmLog){
        return nbiotAlarmLogMapper.addNbiotAlarmLog(nbiotAlarmLog);
    }

    public List<NbiotAlarmLog> getNbiotAlarmLogs(String deviceId){
        return nbiotAlarmLogMapper.getNbiotAlarmLogs(deviceId);
    }
}
