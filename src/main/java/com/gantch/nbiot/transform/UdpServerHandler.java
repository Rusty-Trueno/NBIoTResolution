package com.gantch.nbiot.transform;

import com.gantch.nbiot.httpRequest.httpRequest;
import com.gantch.nbiot.model.NbiotDevice;
import com.gantch.nbiot.model.NbiotTokenRelation;
import com.gantch.nbiot.mqtt.DataMessageClient;
import com.gantch.nbiot.service.DataService;
import com.gantch.nbiot.service.NbiotDeviceService;
import com.gantch.nbiot.service.NbiotTokenRelationService;
import com.gantch.nbiot.service.UplinkService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import org.apache.commons.codec.binary.Hex;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by rongshuai on 2019/9/11
 */
public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private UplinkService uplinkService;
    private NbiotDeviceService nbiotDeviceService;
    private NbiotTokenRelationService nbiotTokenRelationService;
    private httpRequest hr = new httpRequest();
    private DataService dataService = new DataService();
    String ips = null;
    public UdpServerHandler(UplinkService uplinkService,NbiotDeviceService nbiotDeviceService,NbiotTokenRelationService nbiotTokenRelationService){
        this.uplinkService = uplinkService;
        this.nbiotDeviceService = nbiotDeviceService;
        this.nbiotTokenRelationService = nbiotTokenRelationService;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        System.out.println("UdpServer channelRead0");
        String data = msg.content().toString(CharsetUtil.UTF_8);//为接收到的字符串进行解码
        byte[] req = Hex.decodeHex(data);//将接收到的字符串转换为16进制的字节数组
        System.out.println("数据长度为：" + req.length);
        System.out.println("UDP Server Receive message => " + data);//输出接收到的数据
        Channel channel = ctx.channel();
        String mac = data.substring(2,18);
        byte frameType = req[9];//获取数据帧的类型
        int dataLength = req[10];//有效数据长度
        byte[] validData = new byte[dataLength];//有效数据数组
        for(int i=0;i<dataLength;i++){
            validData[i] = req[11 + i];
        }
        if(frameType == (byte) 0x81){//当前的帧类型为“硬件请求数据”
            System.out.println("成功接收到设备数据");
            System.out.println("有效数据长度为：" + dataLength);
            System.out.println("mac:" + mac);
            UdpServer.getMap().put(mac,channel);//绑定设备的mac地址以及channel
            dataService.resolution(validData,dataLength,mac,nbiotTokenRelationService);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
    public static String getRemoteAddress(ChannelHandlerContext ctx) {//获得远端的ip地址
        String socketString = "";
        socketString = ctx.channel().remoteAddress().toString();
        return socketString;
    }
}
