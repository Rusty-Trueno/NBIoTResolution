package com.gantch.nbiot.transform;

import com.gantch.nbiot.mqtt.DataMessageClient;
import com.gantch.nbiot.service.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by duanyue on 2019-08-14.
 */
@Service
public class UdpServer {

    @Autowired
    private NbiotDeviceService nbiotDeviceService;

    @Autowired
    private UplinkService uplinkService;

    @Autowired
    private NbiotTokenRelationService nbiotTokenRelationService;

    @Autowired
    private DeviceMessageDao deviceMessageDao;

    @Autowired
    private NbiotAlarmLogDao nbiotAlarmLogDao;

//    public MqttClient client =  DataMessageClient

    private Integer port = 8095;
    private static Map<String, Channel> map = new ConcurrentHashMap<String,Channel>();//让设备的IP地址和设备的channel进行映射
    @PostConstruct
    public void start() throws Exception {
        DataMessageClient dataMessageClient = new DataMessageClient();
        MqttClient client = dataMessageClient.getClient();//Mqtt客户端的初始化
        dataMessageClient.connect(client);
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(eventLoopGroup)
                    .channel(NioDatagramChannel.class)
                    .handler(new UdpServerHandler(uplinkService,nbiotDeviceService,nbiotTokenRelationService,deviceMessageDao,nbiotAlarmLogDao));
            System.out.println("first");
            b.bind(port).sync().channel().closeFuture().await();
            System.out.println("second");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }
    public static void setMap(Map<String,Channel> map){
        UdpServer.map = map;
    }
    public static Map<String,Channel> getMap(){
        return map;
    }
}
