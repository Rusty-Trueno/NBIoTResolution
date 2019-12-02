package com.gantch.nbiot.service;

import com.gantch.nbiot.dao.NBIoTDeviceMapper;
import com.gantch.nbiot.model.NbiotDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Created by rongshuai on 2019/9/11
 */
@Service
public class NbiotDeviceService {
    @Autowired
    private NBIoTDeviceMapper nbIoTDeviceMapper ;

    public Integer addNbiotDevice(NbiotDevice nbiotDevice){

        return nbIoTDeviceMapper.addNbiotDevice(nbiotDevice);
    }

    public NbiotDevice getNbiotDevice(String mac){
        return nbIoTDeviceMapper.getNbiotDevice(mac);
    }

    public int getNbiotDeviceNumber(){
        return nbIoTDeviceMapper.getNbiotDeviceNumber();
    }
}
