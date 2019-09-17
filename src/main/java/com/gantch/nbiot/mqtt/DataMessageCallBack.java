package com.gantch.nbiot.mqtt;

import com.gantch.nbiot.httpRequest.httpRequest;
import com.gantch.nbiot.model.NbiotDevice;
import com.gantch.nbiot.model.NbiotTokenRelation;
import com.gantch.nbiot.service.DataService;
import com.gantch.nbiot.service.NbiotTokenRelationService;

/**
 * Created by rongshuai on 2019/9/11
 */
public class DataMessageCallBack {
    private httpRequest hr = new httpRequest();
    public void device_CallBack(NbiotDevice device, NbiotTokenRelationService nbiotTokenRelationService){
        System.out.println(device.toString());
        String deviceMac = device.getMac();
        String deviceId = device.getDeviceId();
        NbiotTokenRelation nbiotTokenRelation = nbiotTokenRelationService.getRelationByMac(deviceMac);//根据设备的mac查询设备是否存在token
        if(nbiotTokenRelation == null){//如果设备的token不存在
            System.out.println("设备尚未在deviceaccess中创建");
            String token = null;
            String type = DataService.deviceId2Type(deviceId);//根据设备的id获取设备的类型
            token = hr.httpcreate(deviceMac,type);//获取设备的token
            NbiotTokenRelation newNbiotTokenRelation = new NbiotTokenRelation(token,deviceMac,type);
            nbiotTokenRelationService.addARelation(newNbiotTokenRelation);//将设备的token与设备关系信息入库
        }
        else{//如果设备的token已经存在
            System.out.println("设备已经在deviceaccess中创建了~");
            System.out.println("设备的类型为：" + DataService.deviceId2Type(deviceId));
        }
    }
}
