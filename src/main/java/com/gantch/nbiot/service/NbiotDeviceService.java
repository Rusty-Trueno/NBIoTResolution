package com.gantch.nbiot.service;

import com.gantch.nbiot.dao.NBIoTDeviceMapper;
import com.gantch.nbiot.model.NbiotDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NbiotDeviceService {
    @Autowired
    private NBIoTDeviceMapper nbIoTDeviceMapper ;

    public Integer addNbiotDevice(NbiotDevice nbiotDevice){
        return nbIoTDeviceMapper.addNbiotDevice(nbiotDevice);
    }

    public boolean removeNbiotDeviceByMac(String mac){
        try{
            nbIoTDeviceMapper.removeNbiotDeviceByMac(mac);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public NbiotDevice getNbiotDevice(String mac){
        return nbIoTDeviceMapper.getNbiotDevice(mac);
    }
}
